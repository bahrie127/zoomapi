package components;

import api.ApiClient;
import org.apache.http.NameValuePair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class ChatChannels {

    ApiClient apiClient;

    public ChatChannels() {
        this.apiClient = ApiClient.getInstance();
    }

    public HttpResponse listChannels(List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return apiClient.getRequest("/chat/users/me/channels", params);
    }

    public HttpResponse createChannel(List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return apiClient.postRequest("/chat/users/me/channels", params, data);
    }

    public HttpResponse getChannel(String channelID, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return apiClient.getRequest("/chat/channels/"+channelID, params);
    }

    public HttpResponse deleteChannel(String channelID, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return apiClient.deleteRequest("/chat/channels/"+channelID, params);
    }

    public HttpResponse listMembers(String channelID, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return apiClient.getRequest("/chat/channels/"+channelID+"/members", params);
    }

    public HttpResponse updateChannel(String channelID, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return apiClient.patchRequest("/chat/channels/"+channelID, params, data);
    }

    public HttpResponse joinChannel(String channelID, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return apiClient.postRequest("/chat/channels/"+channelID+"/members/me", params, data);
    }

    public HttpResponse inviteMembers(String channelID, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return apiClient.postRequest("/chat/channels/"+channelID+"/members", params, data);
    }

    public HttpResponse removeMembers(String channelID, String memberID, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return apiClient.deleteRequest("/chat/channels/"+channelID+"/members/"+memberID, params);
    }

    public HttpResponse leaveChannelDel(String channelID, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return apiClient.deleteRequest("/chat/channels/"+channelID+"/members/me", params);
    }

    public HttpResponse leaveChannel(String channelID, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        leaveChannelDel(channelID, params);
        return apiClient.postRequest("/chat/channels/"+channelID+"/members/me", params, data);
    }
}
