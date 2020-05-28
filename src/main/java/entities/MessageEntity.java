package entities;

import annonations.Column;
import annonations.ForeignKey;
import annonations.NotNull;
import annonations.PrimaryKey;

import java.util.Date;

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
    private Date dateTime;

    @NotNull
    @Column("timestamp")
    private Long timestamp;

    @NotNull
    @Column("channel_id")
    @ForeignKey(ChannelEntity.class)
    private String channelId;

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

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
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
}
