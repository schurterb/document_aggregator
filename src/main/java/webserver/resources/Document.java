package webserver.resources;


public class Document {

    //Data Fields
    private String _username = null;
    private String _topic = null;
    private String _url = null;
    private String _text = null;
    private String _dateLastUpdated = null;


    //Constructor(s)
    public Document(String topic, String url, String text, String username) {
        _topic = topic;
        _url = url;
        _text = text;
        _username = username;
    }
    public Document(String topic, String url, String text, String username, String dateLastUpdated) {
        _topic = topic;
        _url = url;
        _text = text;
        _username = username;
        _dateLastUpdated = dateLastUpdated;
    }

    //Public Methods
    public void setTopic(String topic) {
        _topic = topic;
    }
    public String getTopic() {
        return _topic;
    }

    public void setUrl(String url) {
        _url = url;
    }
    public String getUrl() {
        return _url;
    }
    
    public void setText(String text) {
        _text = text;
    }
    public String getText() {
        return _text;
    }

    public void setUsername(String username) {
        _username = username;
    }
    public String getUsername() {
        return _username;
    }

    public void setDateLastUpdated(String dateLastUpdated) {
        _dateLastUpdated = dateLastUpdated;
    }
    public String getDateLastUpdated() {
        return _dateLastUpdated;
    }
}