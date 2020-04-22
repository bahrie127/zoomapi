package components;

import api.ApiClient;
import com.google.gson.JsonObject;
import exceptions.InvalidArgumentException;
import util.Validator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class ChatMessages {

    private static final int TO_CONTACT = 0;
    private static final int TO_CHANNEL = 1;

    public JsonObject listMessages(String userId, String to, int recipientType, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("userId", userId);
        Validator.validateString("to", to);
        Validator.validateBoundaries("recipientType", recipientType, TO_CONTACT, TO_CHANNEL);

        if (params == null) {
            params = new HashMap<>();
        }
        params.put(toRecipientType(recipientType), to);
        return ApiClient.getThrottledInstance().getRequest("/chat/users/" + userId + "/messages", params);
    }

    public JsonObject postMessage(String message, String to, int recipientType) throws InterruptedException, IOException, InvalidArgumentException {
        Validator.validateString("message", message);
        Validator.validateString("to", to);
        Validator.validateBoundaries("recipientType", recipientType, TO_CONTACT, TO_CHANNEL);

        Map<String, Object> data = new HashMap<>();
        data.put("message", message);
        data.put(toRecipientType(recipientType), to);

        return ApiClient.getThrottledInstance().postRequest("/chat/users/me/messages/", data);
    }

    public JsonObject putMessage(String messageId, String message, String to, int recipientType) throws InterruptedException, IOException, InvalidArgumentException {
        Validator.validateString("messageId", messageId);
        Validator.validateString("message", message);
        Validator.validateString("to", to);
        Validator.validateBoundaries("recipientType", recipientType, TO_CONTACT, TO_CHANNEL);

        Map<String, Object> data = new HashMap<>();
        data.put("message", message);
        data.put(toRecipientType(recipientType), to);

        return ApiClient.getThrottledInstance().putRequest("/chat/users/me/messages/" + messageId, data);
    }

    public JsonObject deleteMessage(String messageId, String to, int recipientType) throws InterruptedException, IOException, InvalidArgumentException, URISyntaxException {
        Validator.validateString("messageId", messageId);
        Validator.validateString("to", to);
        Validator.validateBoundaries("recipientType", recipientType, TO_CONTACT, TO_CHANNEL);

        Map<String, Object> params = new HashMap<>();
        params.put(toRecipientType(recipientType), to);

        return ApiClient.getThrottledInstance().deleteRequest("/chat/users/me/messages/" + messageId, params);
    }

    private String toRecipientType(int recipientType) {
        if (recipientType == TO_CONTACT) {
            return "to_contact";
        }

        return "to_channel";
    }
}
