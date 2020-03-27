package webserver.test;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import webserver.database.DynamoDBConnection;
import webserver.resources.*;

public class DBConnectionTest {
    private static final Logger log = LogManager.getLogger(DBConnectionTest.class);
    public static void main(String[] args) {
        
        log.info("Connecting to DynamoDB");
        DynamoDBConnection conn = new DynamoDBConnection();
        
        log.info("Current topics: ");
        for(Topic topic : conn.listTopics("Test"))
            log.info(" - "+topic.getTopic()+" by "+topic.getUsername());
            
        log.info("Adding test topic: ");
        conn.addTopic("Test Topic", "Test");
        
        log.info("Current topics: ");
        for(Topic topic : conn.listTopics("Test"))
            log.info(" - "+topic.getTopic()+" by "+topic.getUsername());
        
        log.info("Removing test topic:");
        conn.addTopic("Test Topic", "Test");
        
        String testTopic = null;
        log.info("Current topics: ");
        for(Topic topic : conn.listTopics("Test"))
        {  
            if(testTopic == null)
                testTopic = topic.getTopic();
            log.info(" - "+topic.getTopic()+" by "+topic.getUsername());
        }
        
        String testUrl = "";
        log.info("Current Documents on "+testTopic+":");
        List<Document> documents = conn.listDocumentsByTopic(testTopic, "Test");
        for(Document document : documents)
        {
            testUrl = document.getUrl();
            log.info(" - "+document.getTopic()+" : "+document.getUrl());
        }
        
        log.info("Getting full text for url:");
        Document document = conn.getDocument(testUrl, "Test");
        log.info(document.getText());
        
        log.info("Listing weblinks for topic "+testTopic+":");
        for(String url : conn.listWebLinksByTopic(testTopic, "Test")) {
            log.info(" - "+url);
        }
    }
}