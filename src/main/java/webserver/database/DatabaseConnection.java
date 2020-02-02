package webserver.database;

import java.util.List;

import webserver.resources.*;

public interface DatabaseConnection {

    //Database methods for topics
    public Topic addTopic( String topic, String user );
    public Topic removeTopic( String topic, String user );
    public List<Topic> listTopics( String user );

    //Database methods for documents
    public List<Document> listDocumentsByTopic( String topic, String user );
    public List<Document> listDocumentsBySummary( String summaryId, String user );
    public Document getDocument( String documentLink );

    //Database methods for web links
    public List<String> listWebLinksByTopic( String topic, String user );
    public List<String> listWebLinksBySummary( String summaryId, String user );
    
    //Database methods for summaries
    public Summary getSummary( String topic, String user );
    
}