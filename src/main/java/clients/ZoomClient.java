package clients;

import api.ApiClient;
import components.*;

public class ZoomClient {

    private String apiKey;
    private String apiSecret;
    private ApiClient apiClient;
    private User user;
    private ChatChannels chatChannels;
    private ChatMessages chatMessages;
    private Meeting meeting;
    private Recording recording;
    private Report report;
    private Webinar webinar;

    public ZoomClient(String apiKey, String apiSecret, Integer timeout) throws InterruptedException {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;

        this.apiClient = ApiClient.getInstance();
        apiClient.setBaseUri("https://api.zoom.us/v2");
        apiClient.setTimeout(timeout);

        this.user = new User();
        this.chatChannels = new ChatChannels();
        this.chatMessages = new ChatMessages();
    }

    public void refreshToken() {}

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
        refreshToken();
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
        refreshToken();
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setToken(String token) {
        apiClient.setToken(token);
    }

    public User getUser() {
        return user;
    }

    public ChatChannels getChatChannels() {
        return chatChannels;
    }

    public ChatMessages getChatMessages() {
        return chatMessages;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public Recording getRecording() {
        return recording;
    }

    public Report getReport() {
        return report;
    }

    public Webinar getWebinar() {
        return webinar;
    }

}
