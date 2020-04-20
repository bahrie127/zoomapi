package components;

import api.ApiClient;
import org.apache.http.NameValuePair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class ChatMessages {

    public HttpResponse listMessages(String id, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getInstance().getRequest("/chat/users"+id, params);
    }

    public HttpResponse postMessage(List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getInstance().postRequest("/chat/users/me/messages", params, data);
    }

    public HttpResponse putMessage(String messageID, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getInstance().postRequest("/chat/users/me/messages/"+messageID, params, data);
    }

    public HttpResponse deleteMessage(String messageID, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getInstance().deleteRequest("/chat/users/me/messages/"+messageID, params);
    }
}
