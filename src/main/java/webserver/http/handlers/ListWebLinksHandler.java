package webserver.http.handlers;

import java.util.List;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import webserver.http.messages.WebsocketRequest;
import webserver.http.messages.WebsocketResponse;
import webserver.http.messages.requests.ListWebLinksRequest;
import webserver.http.handlers.WebsocketMessageHandler;
import webserver.database.DynamoDBConnection;

public class ListWebLinksHandler extends WebsocketMessageHandler {
    
    // -- Data Fields --
    private final Logger _logger = LogManager.getLogger(this.getClass());
    private DynamoDBConnection _database;
    private Gson _gson = new Gson();
    
    // -- Constructor --
    public ListWebLinksHandler(DynamoDBConnection database) {
        _database = database;
    }
    
    // -- Public Methods --
    public WebsocketResponse handle(WebsocketRequest request) {
         if(request.getType().equals("ListWebLinks")) {
             ListWebLinksRequest r = _gson.fromJson(request.getBody(), ListWebLinksRequest.class);
             List<String> weblinks = new ArrayList<>();
             if(request.getSubType().equals("ByTopic")) {
                 weblinks = _database.listWebLinksByTopic(r.getTopic(), r.getUser());
             } else if(request.getSubType().equals("BySummary")) {
                 weblinks = _database.listWebLinksBySummary(r.getSummaryId(), r.getUser());
             } else {
                _logger.error("Unknown subtype for ListWebLinks: "+request.getSubType());
             }
             return new WebsocketResponse(request.getType(), request.getSubType(), _gson.toJson(weblinks), null);
         } else {
             return null;
         }
    }
    
    // -- Private Methods --
    
}