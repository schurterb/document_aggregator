package webserver.http.websocket;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import webserver.http.websocket.AggregatorSocket;

@SuppressWarnings("serial")
public class AggregatorServlet extends WebSocketServlet
{
    @Override
    public void configure(WebSocketServletFactory factory)
    {
        factory.register(AggregatorSocket.class);
    }
}