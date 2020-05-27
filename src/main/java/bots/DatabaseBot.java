package bots;

import entities.CredentialEntity;
import exceptions.InvalidEntityException;
import repositories.CredentialRepository;

public class DatabaseBot {

    public static void main(String[] args) throws InvalidEntityException {
        CredentialRepository credentialRepository = new CredentialRepository();

        CredentialEntity credential = new CredentialEntity("TEST", "TEST");
        credentialRepository.save(credential);

        CredentialEntity updatedCredential = new CredentialEntity("TEST", "DIFFERENT_TEST");
        credentialRepository.save(updatedCredential);
    }
}
