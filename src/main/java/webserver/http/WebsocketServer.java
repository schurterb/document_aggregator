package webserver.http;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.ServletHolder;

import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import webserver.http.websocket.AggregatorServlet;

public class WebsocketServer {
    
    // -- Data Fields --
    private static final Logger _logger = LogManager.getLogger(WebsocketServer.class);
    
    private Server _server = new Server();
    private ServerConnector _connector = null;
    private ServletContextHandler _rootContext = null;
    private ServletHolder _websocketHolder = null;
    
    private static WebsocketServer _instance = null;
    
    // -- Constructor(s) --
    public WebsocketServer() {
        
    }
    
    // -- Public Methods --
    public synchronized WebsocketServer getInstance() {
        if(_instance != null) {
            _instance = new WebsocketServer();
        }
        return _instance;
    }
    
    
    // -- Private Methods --
    private void initializeConnectors() {
        _connector = new ServerConnector(_server);
        _connector.setPort(8080);
        _server.addConnector(_connector);
    }
    
    private void initializeContextHandlers() {
        _rootContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        _rootContext.setContextPath("/");
        _server.setHandler(_rootContext);
        
        _websocketHolder = new ServletHolder("ws", AggregatorServlet.class);
        _rootContext.addServlet(_websocketHolder, "/*");
    }
    
    private void initializeMessageHandlers() {
        //TODO: Figure out how to properly get AggregatorSocket and add handlers
        // - probably going to make aggregator socket a partial singleton and have
        // - static data fields
    }
}