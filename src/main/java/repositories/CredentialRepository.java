package repositories;

import entities.CredentialEntity;
import exceptions.InvalidEntityException;

public class CredentialRepository extends Repository<CredentialEntity, String> {
    public CredentialRepository() throws InvalidEntityException {
        super(CredentialEntity.class);
    }
}
