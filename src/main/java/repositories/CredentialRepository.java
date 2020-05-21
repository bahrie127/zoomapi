package repositories;

import entities.Credential;

public class CredentialRepository extends Repository<Credential> {
    public CredentialRepository() {
        super("credentials", Credential.class);
    }
}
