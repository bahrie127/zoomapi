package clients;

import api.ApiClient;
import components.*;

public class ZoomClient {

    private String apiKey;
    private String apiSecret;
    private ApiClient apiClient;
    private UserV2 userV2;
    private ChatChannels chatChannels;
    private ChatMessages chatMessages;
    private MeetingV2 meetingV2;
    private RecordingV2 recordingV2;
    private ReportV2 reportV2;
    private WebinarV2 webinarV2;

    public ZoomClient(String apiKey, String apiSecret, Integer timeout) throws InterruptedException {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;

        this.apiClient = ApiClient.getInstance();
        apiClient.setBaseUri("https://api.zoom.us/v2");
        apiClient.setTimeout(timeout);

        this.userV2 = new UserV2();
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

    public UserV2 getUserV2() {
        return userV2;
    }

    public ChatChannels getChatChannels() {
        return chatChannels;
    }

    public ChatMessages getChatMessages() {
        return chatMessages;
    }

    public MeetingV2 getMeetingV2() {
        return meetingV2;
    }

    public RecordingV2 getRecordingV2() {
        return recordingV2;
    }

    public ReportV2 getReportV2() {
        return reportV2;
    }

    public WebinarV2 getWebinarV2() {
        return webinarV2;
    }

}
