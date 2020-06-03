package entities;

import annonations.*;

import java.time.LocalDateTime;

@Table("messages")
public class MessageEntity {

    @PrimaryKey
    @Size(36)
    @Column("id")
    private String id;

    @PrimaryKey
    @NotNull
    @Size(22)
    @Column("client_id")
    @ForeignKey(CredentialEntity.class)
    private String clientId;

    @NotNull
    @Column("message")
    private String message;

    @NotNull
    @Column("sender")
    private String sender;

    @NotNull
    @Column("date_time")
    private LocalDateTime dateTime;

    @NotNull
    @Column("timestamp")
    private Long timestamp;

    @NotNull
    @Size(36)
    @Column("channel_id")
    private String channelId;

    @NotNull
    @Column("cached_date")
    private LocalDateTime cachedDate;

    @NotNull
    @Column("retrieved")
    private boolean retrieved;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public LocalDateTime getCachedDate() {
        return cachedDate;
    }

    public void setCachedDate(LocalDateTime cacheDate) {
        this.cachedDate = cacheDate;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public boolean isRetrieved() {
        return retrieved;
    }

    public void setRetrieved(boolean retrieved) {
        this.retrieved = retrieved;
    }
}
