package entities;

import annonations.Column;
import annonations.NotNull;
import annonations.PrimaryKey;
import annonations.Table;

@Table("credentials")
public class CredentialEntity {

    public CredentialEntity() {}

    public CredentialEntity(String clientId, String token) {
        this.clientId = clientId;
        this.token = token;
    }

    @PrimaryKey
    @Column("client_id")
    private String clientId;

    @NotNull
    @Column("token")
    private String token;

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
}
