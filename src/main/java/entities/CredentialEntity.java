package entities;

import annonations.Column;
import annonations.NotNull;
import annonations.PrimaryKey;
import annonations.Table;

import java.time.LocalDateTime;

@Table("credentials")
public class CredentialEntity {

    public CredentialEntity() {}

    public CredentialEntity(String clientId, String token, LocalDateTime cachedDate) {
        this.clientId = clientId;
        this.token = token;
        this.cachedDate = cachedDate;
    }

    @PrimaryKey
    @Column("client_id")
    private String clientId;

    @NotNull
    @Column("token")
    private String token;

    @NotNull
    @Column("cached_date")
    private LocalDateTime cachedDate;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCachedDate() {
        return cachedDate;
    }

    public void setCachedDate(LocalDateTime cachedDate) {
        this.cachedDate = cachedDate;
    }
}
