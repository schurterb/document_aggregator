package webserver.http.websocket;

import java.util.List;
import java.util.ArrayList;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import webserver.util.IObserver;
import webserver.util.IObservable;
import webserver.http.messages.WebsocketRequest;

public class AggregatorSocket extends WebSocketAdapter implements IObserver, IObservable
{
    // -- Data Fields --
    private static final Logger _logger = LogManager.getLogger(AggregatorSocket.class);
    
    private static List<Session> _sessionList = new ArrayList<>();
    private static List<IObserver> _observers = new ArrayList<>();
    private static Gson _gson = new Gson();
    
    private static Object _instanceLock = new Object();
    private static AggregatorSocket _instance = null;
    
    // -- Constructor --
    public AggregatorSocket() {
        if(_instance == null)
            _logger.info("Initializing "+this.getClass().getSimpleName());
        else
            _logger.info(this.getClass().getSimpleName()+" already initialized");
    }
    
    // -- Public Methods --
    
    public static AggregatorSocket getInstance() {
        if(_instance == null) {
            synchronized(_instanceLock) {
                _instance = new AggregatorSocket();
            }
        }
        return _instance;
    }
    
    @Override
    public void onWebSocketConnect(Session session)
    {
        synchronized(_instanceLock) {
            _logger.info("Received connection from "+session.getRemoteAddress().getAddress().getHostAddress());
            if(!_sessionList.contains(session))
                _sessionList.add(session);
        }
    }
    
    @Override
    public void onWebSocketText(String message)
    {
        synchronized(_instanceLock) {
            _logger.info("Received message: " + message);
            WebsocketRequest request = _gson.fromJson(message, WebsocketRequest.class);
            //TODO: Validate session token
            this.notifyObservers(request);
        }
    }
    
    @Override
    public void onWebSocketClose(int statusCode, String reason)
    {
        synchronized(_instanceLock) {
            super.onWebSocketClose(statusCode,reason);
            _logger.info("Socket Closed: [" + statusCode + "] " + reason);
        }
    }
    
    @Override
    public void onWebSocketError(Throwable cause)
    {
        synchronized(_instanceLock) {
            _logger.error(cause.getMessage(), cause);
            super.onWebSocketError(cause);
        }
    }
    
    @Override
    public void update(Object observable, Object data) 
    {
        synchronized(_instanceLock) {
            _logger.info("Pushing message from "+observable.getClass().getSimpleName()+" to "+_sessionList.size()+" client sessions.");
            
            String message;
            if(data instanceof String)
                message = (String) data;
            else
                message = _gson.toJson(data);
                
            List<Session> closedSessions = new ArrayList<>();
            for(Session session : _sessionList)
            {
                if(session.isOpen())
                {
                    try {
                        session.getRemote().sendString(message);
                        session.getRemote().flush();
                    } catch(Exception e) {
                        _logger.error("Failed to send message to client at "+session.getRemoteAddress().getAddress().getHostAddress()+".  Reason: "+e.getMessage(), e);
                        _logger.error("Failed Message = "+message);
                    }
                }
                else
                {
                    closedSessions.add(session);
                }
            }
            
            _logger.info("Removing "+closedSessions.size()+" closed sessions.");
            for(Session session : closedSessions)
                _sessionList.remove(session);
        }
    }
    
    @Override
    public void addObserver(IObserver observer) {
        synchronized(_instanceLock) {
            if(!_observers.contains(observer))
                _observers.add(observer);
        }
    }
    
    @Override
    public void removeObserver(IObserver observer) {
        synchronized(_instanceLock) {
            if(_observers.contains(observer))
                _observers.remove(observer);
        }
    }
    
    @Override
    public void notifyObservers(Object data) {
        synchronized(_instanceLock) {
            for(IObserver observer : _observers) {
                try {
                    observer.update(this, data);
                } catch(Exception e) {
                    _logger.error("Failed to push update to observer "+observer.getClass().getSimpleName()+". Reason: "+e.getMessage(), e);
                }
            }
        }
    }
}