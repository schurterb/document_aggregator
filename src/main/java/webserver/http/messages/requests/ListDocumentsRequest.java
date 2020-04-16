package webserver.http.messages.requests;

public class ListDocumentsRequest extends Request {
    
    // -- Data Fields --
    private String _user;
    private String _topic;
    private String _summaryId;
    
    // -- Public Methods --
    public void setUser(String user) {
        _user = user;
    }
    public String getUser() {
        return _user;
    }
    
    public void setTopic(String topic) {
        _topic = topic;
    }
    public String getTopic() {
        return _topic;
    }
    
    public void setSummaryId(String summaryId) {
        _summaryId = summaryId;
    }
    public String getSummaryId() {
        return _summaryId;
    }
}