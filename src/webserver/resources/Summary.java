package webserver.resources;


public class Summary {

    //Data Fields
    private String _username = null;
    private String _topic = null;
    private String _summary = null;
    private String _dateCreated = null;
    private String _dateLastUpdated = null;
    
    //Constructor(s)
    public Summary(String topic, String summary, String username) {
        _topic = topic;
        _summary = summary;
        _username = username;
    }
    public Summary(String topic, String summary, String username, String dateCreated, String dateLastUpdated) {
        _topic = topic;
        _summary = summary;
        _username = username;
        _dateCreated = dateCreated;
        _dateLastUpdated = dateLastUpdated;
    }

    //Public Methods
    public void setTopic(String topic) {
        _topic = topic;
    }
    public String getTopic() {
        return _topic;
    }

    public void setSummary(String summary) {
        _summary = summary;
    }
    public String getSummary() {
        return _summary;
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

    public void setDateLastUpdated(String dateLastUpdated) {
        _dateLastUpdated = dateLastUpdated;
    }
    public String getDateLastUpdated() {
        return _dateLastUpdated;
    }
}