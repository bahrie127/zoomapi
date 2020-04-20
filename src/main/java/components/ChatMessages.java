package components;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.NameValuePair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class ChatMessages extends BaseComponent{
    public ChatMessages(String baseUri, Integer timeout) throws UnirestException {
        super(baseUri, timeout);
    }

    public HttpResponse listMessages(String id, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return getRequest("/chat/users"+id, params);
    }

    public HttpResponse postMessage(List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return postRequest("/chat/users/me/messages", params, data);
    }

    public HttpResponse putMessage(String messageID, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return postRequest("/chat/users/me/messages/"+messageID, params, data);
    }

    public HttpResponse deleteMessage(String messageID, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return deleteRequest("/chat/users/me/messages/"+messageID, params);
    }
}
