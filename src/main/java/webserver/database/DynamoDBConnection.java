package webserver.database;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.amazonaws.services.dynamodbv2.model.QueryResult;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import webserver.database.DatabaseConnection;
import webserver.resources.*;

public class DynamoDBConnection implements DatabaseConnection {
    
    // -- Data Fields --
    private static final Logger _logger = LogManager.getLogger("DynamoDBConnection");
    
    private AmazonDynamoDB _client = null;
    
    private String _endpoint = "";
    private String _region = "";
    private String _topicsTableName = "DocumentationAggregatorTopics";
    private String _scrapingResultsTableName = "DocumentationAggregatorRawScrapingResults";
    
    // -- Constructor(s) --
    public DynamoDBConnection(String region) {
        _region = region;
        _endpoint = "https://dynamodb."+region+".amazonaws.com";
        _client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                new AwsClientBuilder.EndpointConfiguration(_endpoint, _region)
            ).build();
        _logger.info("Connection established.");
    }
    public DynamoDBConnection() {
        this("us-east-1");
    }
    
    
    // -- Public Methods --
    //Database methods for topics
    public Topic addTopic( String topic, String user ) {
        
        HashMap<String,AttributeValue> attributes = new HashMap<>();
        attributes.put("Username", new AttributeValue(user));
        attributes.put("Topic", new AttributeValue(topic));
        
        try {
            PutItemResult result = _client.putItem(new PutItemRequest(_topicsTableName, attributes));
            return new Topic(topic, user);
        } catch(Exception e) {
            _logger.error("Failed to add topic to DyanmoDB "+_topicsTableName+" table.  Reason: "+e.getMessage(), e);
            return null;
        }
    }
    public Topic removeTopic( String topic, String user ) {
        return null;
    }
    public List<Topic> listTopics( String user ) {
        
        ArrayList<Topic> topics = new ArrayList<>();
        Condition condition = new Condition();
        condition.setComparisonOperator(ComparisonOperator.EQ);
        ArrayList<AttributeValue> attributes = new ArrayList<>();
        attributes.add(new AttributeValue(user));
        condition.setAttributeValueList(attributes);
        QueryRequest request = new QueryRequest(_topicsTableName)
                                    .addKeyConditionsEntry("Username",condition);
                                    
        try {
            QueryResult result = _client.query(request);
            
            for(Map<String,AttributeValue> item : result.getItems()) {
                topics.add(new Topic(item.get("Topic").getS(), item.get("Username").getS()));
            }
        } catch(Exception e) {
            _logger.error("Failed to list topics in DyanmoDB "+_topicsTableName+" table.  Reason: "+e.getMessage(), e);
            e.printStackTrace();
        }
        
        return topics;
    }

    //Database methods for documents
    public List<Document> listDocumentsByTopic( String topic, String user ) {
        ArrayList<Document> documents = new ArrayList<>();
        
        Condition userCondition = new Condition();
        userCondition.setComparisonOperator(ComparisonOperator.EQ);
        ArrayList<AttributeValue> userAttribute = new ArrayList<>();
        userAttribute.add(new AttributeValue(user));
        userCondition.setAttributeValueList(userAttribute);
        
        Condition topicCondition = new Condition();
        topicCondition.setComparisonOperator(ComparisonOperator.EQ);
        ArrayList<AttributeValue> topicAttribute = new ArrayList<>();
        topicAttribute.add(new AttributeValue(topic));
        topicCondition.setAttributeValueList(topicAttribute);
        
        QueryRequest request = new QueryRequest(_scrapingResultsTableName)
                                    .addKeyConditionsEntry("Username",userCondition)
                                    .addQueryFilterEntry("Topic",topicCondition);
        
        try {
            QueryResult result = _client.query(request);
            
            for(Map<String,AttributeValue> item : result.getItems()) {
                documents.add(new Document(item.get("Topic").getS(), 
                                           item.get("URL").getS(),
                                           item.get("Text").getS(),
                                           item.get("Username").getS()));
            }
        } catch(Exception e) {
            _logger.error("Failed to list documents by topic in DyanmoDB "+_scrapingResultsTableName+" table.  Reason: "+e.getMessage(), e);
            e.printStackTrace();
        }
        
        return documents;
    }
    public List<Document> listDocumentsBySummary( String summaryId, String user ) {
        return new ArrayList<Document>();
    }
    public Document getDocument( String documentLink, String user ) {
        
        Condition userCondition = new Condition();
        userCondition.setComparisonOperator(ComparisonOperator.EQ);
        ArrayList<AttributeValue> userAttribute = new ArrayList<>();
        userAttribute.add(new AttributeValue(user));
        userCondition.setAttributeValueList(userAttribute);
        
        Condition urlCondition = new Condition();
        urlCondition.setComparisonOperator(ComparisonOperator.EQ);
        ArrayList<AttributeValue> urlAttribute = new ArrayList<>();
        urlAttribute.add(new AttributeValue(documentLink));
        urlCondition.setAttributeValueList(urlAttribute);
        
        QueryRequest request = new QueryRequest(_scrapingResultsTableName)
                                    .addKeyConditionsEntry("Username",userCondition)
                                    .addKeyConditionsEntry("URL",urlCondition);
        
        try {
            QueryResult result = _client.query(request);
            
            Map<String,AttributeValue> item = result.getItems().get(0);
            return new Document(item.get("Topic").getS(), 
                                item.get("URL").getS(),
                                item.get("Text").getS(),
                                item.get("Username").getS());
        } catch(Exception e) {
            _logger.error("Failed to get document with url '"+documentLink+"' from "+_scrapingResultsTableName+" table.  Reason: "+e.getMessage(), e);
            e.printStackTrace();
        }
        
        return null;
    }

    //Database methods for web links
    public List<String> listWebLinksByTopic( String topic, String user ) {
        ArrayList<String> urls = new ArrayList<>();
        
        Condition userCondition = new Condition();
        userCondition.setComparisonOperator(ComparisonOperator.EQ);
        ArrayList<AttributeValue> userAttribute = new ArrayList<>();
        userAttribute.add(new AttributeValue(user));
        userCondition.setAttributeValueList(userAttribute);
        
        Condition topicCondition = new Condition();
        topicCondition.setComparisonOperator(ComparisonOperator.EQ);
        ArrayList<AttributeValue> topicAttribute = new ArrayList<>();
        topicAttribute.add(new AttributeValue(topic));
        topicCondition.setAttributeValueList(topicAttribute);
        
        QueryRequest request = new QueryRequest(_scrapingResultsTableName)
                                    .addKeyConditionsEntry("Username",userCondition)
                                    .addQueryFilterEntry("Topic",topicCondition);
        
        try {
            QueryResult result = _client.query(request);
            
            for(Map<String,AttributeValue> item : result.getItems()) {
                urls.add(item.get("URL").getS());
            }
        } catch(Exception e) {
            _logger.error("Failed to list URLs by topic in DyanmoDB "+_scrapingResultsTableName+" table.  Reason: "+e.getMessage(), e);
            e.printStackTrace();
        }
        
        return urls;
    }
    public List<String> listWebLinksBySummary( String summaryId, String user ) {
        return new ArrayList<String>();
    }
    
    //Database methods for summaries
    public Summary getSummary( String topic, String user ) {
        return null;
    }
    
    // -- Private Methods --
    
    
}