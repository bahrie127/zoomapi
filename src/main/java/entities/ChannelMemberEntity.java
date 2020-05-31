package entities;

import annonations.*;

import java.time.LocalDateTime;

@Table("channels_membership")
public class ChannelMemberEntity {

    @PrimaryKey
    @Column("id")
    private String id;

    @NotNull
    @Column("email")
    private String email;

    @NotNull
    @Column("first_name")
    private String firstName;

    @NotNull
    @Column("last_name")
    private String lastName;

    @NotNull
    @Column("role")
    private String role;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public void setCachedDate(LocalDateTime cachedDate) {
        this.cachedDate = cachedDate;
    }
}
