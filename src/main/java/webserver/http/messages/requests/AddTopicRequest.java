package webserver.http.messages.requests;

public class AddTopicRequest extends Request {
    
    // -- Data Fields --
    private String _user;
    private String _topic;
    
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
}