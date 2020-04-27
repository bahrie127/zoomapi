package components;

import api.ApiClient;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.InvalidArgumentException;
import exceptions.InvalidComponentException;
import exceptions.InvalidRequestException;
import models.ChannelMemberPage;
import models.MessagePage;
import models.SentMessage;
import util.Validator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class ChatMessageComponent {

    private static final int TO_CONTACT = 0;
    private static final int TO_CHANNEL = 1;
    private Gson gson;

    public ChatMessageComponent() {
        this.gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();
    }

    public MessagePage listMessages(String userId, String to, int recipientType, Map<String, Object> params) throws InvalidComponentException {
        try {
            Validator.validateString("userId", userId);
            Validator.validateString("to", to);
            Validator.validateBoundaries("recipientType", recipientType, TO_CONTACT, TO_CHANNEL);

            if (params == null) {
                params = new HashMap<>();
            }
            params.put(toRecipientType(recipientType), to);
            HttpResponse response = ApiClient.getThrottledInstance().getRequest("/chat/users/" + userId + "/messages", params);

            return gson.fromJson(response.body().toString(), MessagePage.class);
        } catch (InterruptedException | InvalidRequestException | InvalidArgumentException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }

    public SentMessage postMessage(String message, String to, int recipientType) throws InvalidComponentException {
        try {
            Validator.validateString("message", message);
            Validator.validateString("to", to);
            Validator.validateBoundaries("recipientType", recipientType, TO_CONTACT, TO_CHANNEL);

            Map<String, Object> data = new HashMap<>();
            data.put("message", message);
            data.put(toRecipientType(recipientType), to);

            HttpResponse response = ApiClient.getThrottledInstance().postRequest("/chat/users/me/messages/", data);
            return gson.fromJson(response.body().toString(), SentMessage.class);
        } catch (InterruptedException | InvalidRequestException | InvalidArgumentException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }

    public void putMessage(String messageId, String message, String to, int recipientType) throws InvalidComponentException {
        try {
            Validator.validateString("messageId", messageId);
            Validator.validateString("message", message);
            Validator.validateString("to", to);
            Validator.validateBoundaries("recipientType", recipientType, TO_CONTACT, TO_CHANNEL);

            Map<String, Object> data = new HashMap<>();
            data.put("message", message);
            data.put(toRecipientType(recipientType), to);

            ApiClient.getThrottledInstance().putRequest("/chat/users/me/messages/" + messageId, data);
        } catch (InterruptedException | InvalidRequestException | InvalidArgumentException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }

    public void deleteMessage(String messageId, String to, int recipientType) throws InvalidComponentException {
        try {
            Validator.validateString("messageId", messageId);
            Validator.validateString("to", to);
            Validator.validateBoundaries("recipientType", recipientType, TO_CONTACT, TO_CHANNEL);

            Map<String, Object> params = new HashMap<>();
            params.put(toRecipientType(recipientType), to);

            ApiClient.getThrottledInstance().deleteRequest("/chat/users/me/messages/" + messageId, params);
        } catch (InterruptedException | InvalidRequestException | InvalidArgumentException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }

    private String toRecipientType(int recipientType) {
        if (recipientType == TO_CONTACT) {
            return "to_contact";
        }

        return "to_channel";
    }
}
