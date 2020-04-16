package webserver.http.handlers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import webserver.http.messages.WebsocketRequest;
import webserver.http.messages.WebsocketResponse;
import webserver.http.messages.requests.ListTopicsRequest;
import webserver.http.handlers.WebsocketMessageHandler;
import webserver.resources.Topic;
import webserver.database.DynamoDBConnection;

public class ListTopicsHandler extends WebsocketMessageHandler {
    
    // -- Data Fields --
    private final Logger _logger = LogManager.getLogger(this.getClass());
    private DynamoDBConnection _database;
    private Gson _gson = new Gson();
    
    // -- Constructor --
    public ListTopicsHandler(DynamoDBConnection database) {
        _database = database;
    }
    
    // -- Public Methods --
    public WebsocketResponse handle(WebsocketRequest request) {
         if(request.getType().equals("ListTopics")) {
             ListTopicsRequest r = _gson.fromJson(request.getBody(), ListTopicsRequest.class);
             List<Topic> topics = _database.listTopics(r.getUser());
             return new WebsocketResponse(request.getType(), _gson.toJson(topics));
         } else {
             return null;
         }
    }
    
    // -- Private Methods --
    
}