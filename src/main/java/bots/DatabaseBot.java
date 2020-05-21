package bots;

import entities.Credential;
import repositories.CredentialRepository;

public class DatabaseBot {

    public static void main(String[] args) {
        CredentialRepository credentialRepository = new CredentialRepository();

        Credential credential = new Credential();
        credential.setId("TEST");
        credential.setToken("TEST");

        credentialRepository.store(credential);
    }
}
