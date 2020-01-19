package webserver.resources;


public class Document {

    //Data Fields
    private String _username = null;
    private String _topic = null;
    private String _url = null;
    private String _document = null;
    private String _dateLastUpdated = null;


    //Constructor(s)
    public Summary(String topic, String url, String document, String username) {
        _topic = topic;
        _url = url;
        _document = document;
        _username = username;
    }
    public Summary(String topic, String url, String document, String username, String dateLastUpdated) {
        _topic = topic;
        _url = url;
        _document = document;
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
    \
    public void setDocument(String document) {
        _document = document;
    }
    public String getDocument() {
        return _document;
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