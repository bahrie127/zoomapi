package entities;

import annonations.*;

import java.time.LocalDateTime;

@Table("channels")
public class ChannelEntity {

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
    @Column("name")
    private String name;

    @NotNull
    @Column("type")
    private int type;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public boolean isRetrieved() {
        return retrieved;
    }

    public void setRetrieved(boolean retrieved) {
        this.retrieved = retrieved;
    }
}
