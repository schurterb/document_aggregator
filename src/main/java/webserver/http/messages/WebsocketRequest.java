package webserver.http.messages;

public class WebsocketRequest {
    
    // -- Data Fields --
    private String _sessionToken = "";
    private String _type = "";
    private String _subtype = null;
    private String _body = null;
    
    // -- Constructor(s) --
    public WebsocketRequest(String sessionToken, String type) {
        this(sessionToken, type, null, null);
    }
    public WebsocketRequest(String sessionToken, String type, String body) {
        this(sessionToken, type, null, body);
    }
    public WebsocketRequest(String sessionToken, String type, String subtype, String body) {
        this._sessionToken = sessionToken;
        this._type = type;
        this._subtype = subtype;
        this._body = body;
    }
    
    // -- Public Methods --
    
    public void setSessionToken(String sessionToken) {
        this._sessionToken = sessionToken;
    }
    public String getSessionToken() {
        return this._sessionToken;
    }
    
    public void setType(String type) {
        this._type = type;
    }
    public String getType() {
        return this._type;
    }
    
    public void setSubType(String subtype) {
        this._subtype = subtype;
    }
    public String getSubType() {
        return this._subtype;
    }
    
    public void setBody(String body) {
        this._body = body;
    }
    public String getBody() {
        return this._body;
    }
}