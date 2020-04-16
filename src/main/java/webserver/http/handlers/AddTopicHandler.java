package webserver.http.handlers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import webserver.http.messages.WebsocketRequest;
import webserver.http.messages.WebsocketResponse;
import webserver.http.messages.requests.AddTopicRequest;
import webserver.http.handlers.WebsocketMessageHandler;
import webserver.resources.Topic;
import webserver.database.DynamoDBConnection;

public class AddTopicHandler extends WebsocketMessageHandler {
    
    // -- Data Fields --
    private final Logger _logger = LogManager.getLogger(this.getClass());
    private DynamoDBConnection _database;
    private Gson _gson = new Gson();
    
    // -- Constructor --
    public AddTopicHandler(DynamoDBConnection database) {
        _database = database;
    }
    
    // -- Public Methods --
    public WebsocketResponse handle(WebsocketRequest request) {
         if(request.getType().equals("AddTopic")) {
             AddTopicRequest r = _gson.fromJson(request.getBody(), AddTopicRequest.class);
             Topic topic = _database.addTopic(r.getTopic(), r.getUser());
             return new WebsocketResponse(request.getType(), _gson.toJson(topic));
         } else {
             return null;
         }
    }
    
    // -- Private Methods --
    
}