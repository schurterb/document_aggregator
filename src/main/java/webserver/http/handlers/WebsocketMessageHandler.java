package webserver.http.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import webserver.http.messages.WebsocketRequest;
import webserver.http.messages.WebsocketResponse;
import webserver.util.IObserver;

public abstract class WebsocketMessageHandler implements IObserver {
    
    // -- Data Fields --
    private final Logger _logger = LogManager.getLogger(this.getClass());
    
    // -- Public Methods --
    
    public abstract WebsocketResponse handle(WebsocketRequest request);
    
    @Override
    public void update(Object src, Object update) {
        if(update instanceof WebsocketRequest) {
            try {
                WebsocketResponse response = handle((WebsocketRequest) update);
                if(src instanceof IObserver) {
                    ((IObserver) src).update(this, response);
                }
            } catch(Exception e) {
                _logger.error("Failed to handle update of type "+update.getClass().getName()+" from "+src.getClass().getName()+".  Reason: "+e.getMessage(), e);
            }
        }
    }
}