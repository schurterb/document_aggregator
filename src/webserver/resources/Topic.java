package webserver.resources;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Topic {

    //Data Fields
    private String _username = null;
    private String _topic = null;
    private String _dateCreated = null;
    
    // private SimpleDateFormat _dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    //Constructor(s)
    public Topic(String topic, String username) {
        _topic = topic;
        _username = username;
    }
    public Topic(String topic, String username, String dateCreated) {
        _topic = topic;
        _username = username;
        _dateCreated = dateCreated;
    }

    //Public Methods
    public void setTopic(String topic) {
        _topic = topic;
    }
    public String getTopic() {
        return _topic;
    }

    public void setUsername(String username) {
        _username = username;
    }
    public String getUsername() {
        return _username;
    }

    public void setDateCreated(String dateCreated) {
        _dateCreated = dateCreated;
    }
    public String getDateCreated() {
        return _dateCreated;
    }
}