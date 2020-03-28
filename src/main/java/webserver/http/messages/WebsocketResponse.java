package webserver.http.messages;

public class WebsocketResponse {
    
    // -- Data Fields --
    private String _type = "";
    private String _subtype = null;
    private String _body = "";
    private String _metadata = null;
    
    // -- Constructor(s) --
    public WebsocketResponse(String type, String body) {
        this(type, null, body, null);
    }
    public WebsocketResponse(String type, String subtype, String body, String metadata) {
        this._type = type;
        this._subtype = subtype;
        this._body = body;
        this._metadata = metadata;
    }
    
    // -- Public Methods --
    
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
    
    public void setMetadata(String metadata) {
        this._metadata = metadata;
    }
    public String getMetadata() {
        return this._metadata;
    }
}