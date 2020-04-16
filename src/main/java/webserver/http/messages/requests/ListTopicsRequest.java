package webserver.http.messages.requests;

public class ListTopicsRequest extends Request {
    
    // -- Data Fields --
    private String _user;
    
    // -- Public Methods --
    public void setUser(String user) {
        _user = user;
    }
    public String getUser() {
        return _user;
    }
}