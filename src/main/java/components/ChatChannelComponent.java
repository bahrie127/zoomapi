package components;

import api.ApiClient;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.InvalidArgumentException;
import models.*;
import util.Validator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatChannelComponent {

    private Gson gson;

    public ChatChannelComponent() {
        this.gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();
    }

    public ChannelPage listChannels(Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException {
        HttpResponse response = ApiClient.getThrottledInstance().getRequest("/chat/users/me/channels", params);

        return gson.fromJson(response.body().toString(), ChannelPage.class);
    }

    public Channel createChannel(String name, int type, List<String> members) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {

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

        HttpResponse response = ApiClient.getThrottledInstance().postRequest("/chat/users/me/channels", data);

        return gson.fromJson(response.body().toString(), Channel.class);
    }

    public Channel getChannel(String channelId) throws InterruptedException, IOException, InvalidArgumentException {
        Validator.validateString("channelId", channelId);
        HttpResponse response = ApiClient.getThrottledInstance().getRequest("/chat/channels/" + channelId);

        return gson.fromJson(response.body().toString(), Channel.class);
    }

    public void updateChannel(String channelId, String name) throws InterruptedException, IOException, InvalidArgumentException {
        Validator.validateString("channelId", channelId);
        Validator.validateString("name", name);

        Map<String, Object> data = new HashMap<>();
        data.put("name", name);

        ApiClient.getThrottledInstance().patchRequest("/chat/channels/" + channelId, data);
    }

    public void deleteChannel(String channelId) throws InterruptedException, IOException, InvalidArgumentException {
        Validator.validateString("channelId", channelId);
        ApiClient.getThrottledInstance().deleteRequest("/chat/channels/" + channelId);
    }

    public ChannelMemberPage listMembers(String channelId, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("channelId", channelId);
        HttpResponse response = ApiClient.getThrottledInstance().getRequest("/chat/channels/" + channelId +"/members", params);

        return gson.fromJson(response.body().toString(), ChannelMemberPage.class);
    }

    public InvitedChannelMembers inviteMembers(String channelId, List<String> members) throws InterruptedException, IOException, InvalidArgumentException {
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

        HttpResponse response = ApiClient.getThrottledInstance().postRequest("/chat/channels/" + channelId + "/members", data);

        return gson.fromJson(response.body().toString(), InvitedChannelMembers.class);
    }

    public JoinedMember joinChannel(String channelId) throws InterruptedException, IOException, InvalidArgumentException {
        Validator.validateString("channelId", channelId);
        HttpResponse response = ApiClient.getThrottledInstance().postRequest("/chat/channels/"+ channelId +"/members/me", null);

        return gson.fromJson(response.body().toString(), JoinedMember.class);
    }

    public void leaveChannel(String channelId) throws InterruptedException, IOException, InvalidArgumentException {
        Validator.validateString("channelId", channelId);
        ApiClient.getThrottledInstance().deleteRequest("/chat/channels/" + channelId + "/members/me");
    }

    public void removeMember(String channelId, String memberId) throws InterruptedException, IOException, InvalidArgumentException {
        Validator.validateString("channelId", channelId);
        Validator.validateString("memberId", memberId);
        ApiClient.getThrottledInstance().deleteRequest("/chat/channels/"+ channelId +"/members/"+ memberId);
    }

}
