package components;

import util.Validator;
import api.ApiClient;
import com.google.gson.JsonObject;
import exceptions.InvalidArgumentException;
import org.apache.http.NameValuePair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatChannels {

    public JsonObject listChannels(List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getThrottledInstance().getRequest("/chat/users/me/channels", params);
    }

    public JsonObject createChannel(String name, int type, List<String> members) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {

        Validator.validateString("name", name);
        Validator.validateBoundaries("type", type, 1, 4);

        Map<String, Object> data = new HashMap<>();

        data.put("name", name);
        data.put("type", type);

        if (members != null) {
            List<Map<String, String>> membersData = new ArrayList<>();
            for (String email : members) {
                Map<String, String> memberData = new HashMap<>();
                memberData.put("email", email);

                membersData.add(memberData);
            }

            data.put("members", membersData);
        }

        return ApiClient.getThrottledInstance().postRequest("/chat/users/me/channels", data);
    }

    public JsonObject getChannel(String channelId) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("channelId", channelId);
        return ApiClient.getThrottledInstance().getRequest("/chat/channels/" + channelId);
    }

    public JsonObject deleteChannel(String channelId) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("channelId", channelId);
        return ApiClient.getThrottledInstance().deleteRequest("/chat/channels/" + channelId);
    }

    public JsonObject listMembers(String channelId, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("channelId", channelId);
        return ApiClient.getThrottledInstance().getRequest("/chat/channels/" + channelId +"/members", params);
    }

    public JsonObject updateChannel(String channelId, String name) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("channelId", channelId);
        Validator.validateString("name", name);

        Map<String, Object> data = new HashMap<>();
        data.put(name, name);

        return ApiClient.getThrottledInstance().patchRequest("/chat/channels/" + channelId, data);
    }

    public JsonObject joinChannel(String channelId) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("channelId", channelId);
        return ApiClient.getThrottledInstance().postRequest("/chat/channels/"+ channelId +"/members/me", null);
    }

    public JsonObject inviteMembers(String channelId, List<String> members) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("channelId", channelId);
        Validator.validateList("members", members);

        Map<String, Object> data = new HashMap<>();
        List<Map<String, String>> membersData = new ArrayList<>();
        for (String email : members) {
            Map<String, String> memberData = new HashMap<>();
            memberData.put("email", email);

            membersData.add(memberData);
        }

        data.put("members", membersData);

        return ApiClient.getThrottledInstance().postRequest("/chat/channels/" + channelId + "/members", data);
    }

    public JsonObject removeMember(String channelId, String memberId) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("channelId", channelId);
        Validator.validateString("memberId", memberId);

        return ApiClient.getThrottledInstance().deleteRequest("/chat/channels/"+ channelId +"/members/"+ memberId);
    }

    public JsonObject leaveChannel(String channelId) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("channelId", channelId);

        return ApiClient.getThrottledInstance().deleteRequest("/chat/channels/" + channelId + "/members/me");
    }
}
