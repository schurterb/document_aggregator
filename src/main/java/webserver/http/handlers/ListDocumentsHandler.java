package webserver.http.handlers;

import java.util.List;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import webserver.http.messages.WebsocketRequest;
import webserver.http.messages.WebsocketResponse;
import webserver.http.messages.requests.ListDocumentsRequest;
import webserver.http.handlers.WebsocketMessageHandler;
import webserver.resources.Document;
import webserver.database.DynamoDBConnection;

public class ListDocumentsHandler extends WebsocketMessageHandler {
    
    // -- Data Fields --
    private final Logger _logger = LogManager.getLogger(this.getClass());
    private DynamoDBConnection _database;
    private Gson _gson = new Gson();
    
    // -- Constructor --
    public ListDocumentsHandler(DynamoDBConnection database) {
        _database = database;
    }
    
    // -- Public Methods --
    public WebsocketResponse handle(WebsocketRequest request) {
         if(request.getType().equals("ListDocuments")) {
             ListDocumentsRequest r = _gson.fromJson(request.getBody(), ListDocumentsRequest.class);
             List<Document> documents = new ArrayList<>();
             if(request.getSubType().equals("ByTopic")) {
                 documents = _database.listDocumentsByTopic(r.getTopic(), r.getUser());
             } else if(request.getSubType().equals("BySummary")) {
                 documents = _database.listDocumentsBySummary(r.getSummaryId(), r.getUser());
             } else {
                _logger.error("Unknown subtype for ListDocuments: "+request.getSubType());
             }
             return new WebsocketResponse(request.getType(), request.getSubType(), _gson.toJson(documents), null);
         } else {
             return null;
         }
    }
    
    // -- Private Methods --
    
}