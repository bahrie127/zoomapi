package entities;

import annonations.*;

import java.time.LocalDateTime;

@Table("channels_membership")
public class ChannelMemberEntity {

    @PrimaryKey
    @Size(22)
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
    @Size(36)
    @Column("channel_id")
    private String channelId;

    @NotNull
    @Size(22)
    @Column("client_id")
    @ForeignKey(CredentialEntity.class)
    private String clientId;

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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
