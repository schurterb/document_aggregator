package webserver.test;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import webserver.database.DynamoDBConnection;
import webserver.resources.*;

public class DBConnectionTest {
    private static final Logger _logger = LogManager.getLogger("DBConnectionTest");
    public static void main(String[] args) {

        _logger.trace("Connecting to DynamoDB");
        DynamoDBConnection conn = new DynamoDBConnection();
        
        System.out.println("Current topics: ");
        for(Topic topic : conn.listTopics("Test"))
            System.out.println(" - "+topic.getTopic()+" by "+topic.getUsername());
            
        System.out.println("Adding test topic: ");
        conn.addTopic("Test Topic", "Test");
        
        System.out.println("Current topics: ");
        for(Topic topic : conn.listTopics("Test"))
            System.out.println(" - "+topic.getTopic()+" by "+topic.getUsername());
        
        System.out.println("Removing test topic:");
        conn.addTopic("Test Topic", "Test");
        
        String testTopic = null;
        System.out.println("Current topics: ");
        for(Topic topic : conn.listTopics("Test"))
        {  
            if(testTopic == null)
                testTopic = topic.getTopic();
            System.out.println(" - "+topic.getTopic()+" by "+topic.getUsername());
        }
        
        String testUrl = "";
        System.out.println("Current Documents on "+testTopic+":");
        List<Document> documents = conn.listDocumentsByTopic(testTopic, "Test");
        for(Document document : documents)
        {
            testUrl = document.getUrl();
            System.out.println(" - "+document.getTopic()+" : "+document.getUrl());
        }
        
        System.out.println("Getting full text for url:");
        Document document = conn.getDocument(testUrl, "Test");
        System.out.println(document.getText());
        
        System.out.println("Listing weblinks for topic "+testTopic+":");
        for(String url : conn.listWebLinksByTopic(testTopic, "Test")) {
            System.out.println(" - "+url);
        }
    }
}