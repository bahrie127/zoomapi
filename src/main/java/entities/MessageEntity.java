package entities;

import annonations.*;

import java.time.LocalDateTime;
import java.util.Date;

@Table("messages")
public class MessageEntity {

    @PrimaryKey
    @Column("id")
    private String id;

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
    @Column("channel_id")
    @ForeignKey(ChannelEntity.class)
    private String channelId;

    @NotNull
    @Column("cached_date")
    private LocalDateTime cachedDate;

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
}
