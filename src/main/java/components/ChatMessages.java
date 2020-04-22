package components;

import api.ApiClient;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class ChatMessages {

    public JsonObject listMessages(String id, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getThrottledInstance().getRequest("/chat/users"+id, params);
    }

    public JsonObject postMessage(Map<String, Object> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getThrottledInstance().postRequest("/chat/users/me/messages", params, data);
    }

    public JsonObject putMessage(String messageID, Map<String, Object> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getThrottledInstance().postRequest("/chat/users/me/messages/"+messageID, params, data);
    }

    public JsonObject deleteMessage(String messageID) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getThrottledInstance().deleteRequest("/chat/users/me/messages/"+messageID);
    }
}
