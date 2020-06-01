package clients;

import api.ApiClient;
import components.*;
import exceptions.InvalidComponentException;
import exceptions.InvalidEntityException;
import listeners.ChatListener;
import models.ChannelMember;
import services.ChatService;

public class ZoomClient {

    private String apiKey;
    private String apiSecret;
    private ApiClient apiClient;
    private UserComponent user;
    private CachedChatChannelComponent chatChannels;
    private CachedChatMessageComponent chatMessages;
    private ChannelMember channelMember;
    private MeetingComponent meeting;
    private RecordingComponent recording;
    private ReportComponent report;
    private WebinarComponent webinar;
    private ChatService chat;
    private ChatListener chatListener;

    public ZoomClient(String apiKey, String apiSecret, Integer timeout) throws InterruptedException, InvalidEntityException, InvalidComponentException {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;

        this.apiClient = ApiClient.getThrottledInstance();
        apiClient.setBaseUri("https://api.zoom.us/v2");
        apiClient.setTimeout(timeout);

        this.user = new UserComponent();
        this.chatChannels = new CachedChatChannelComponent(apiKey);
        this.chatMessages = new CachedChatMessageComponent(apiKey);
        this.channelMember = new ChannelMember();
        this.meeting = new MeetingComponent();
        this.recording = new RecordingComponent();
        this.report = new ReportComponent();
        this.webinar = new WebinarComponent();
        this.chat = new ChatService();
        this.chatListener = new ChatListener();
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

    public UserComponent getUser() {
        return user;
    }

    public CachedChatChannelComponent getChatChannels() {
        return chatChannels;
    }

    public CachedChatMessageComponent getChatMessages() {
        return chatMessages;
    }

    public ChannelMember getChannelMember() {
        return channelMember;
    }

    public MeetingComponent getMeeting() {
        return meeting;
    }

    public RecordingComponent getRecording() {
        return recording;
    }

    public ReportComponent getReport() {
        return report;
    }

    public WebinarComponent getWebinar() {
        return webinar;
    }

    public ChatService getChat() {
        return chat;
    }

    public ChatListener getChatListener() {
        return chatListener;
    }
}
