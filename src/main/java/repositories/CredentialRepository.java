package repositories;

import entities.CredentialEntity;
import exceptions.InvalidEntityException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CredentialRepository extends Repository<CredentialEntity> {
    public CredentialRepository() throws InvalidEntityException {
        super(CredentialEntity.class);
    }

    public Optional<CredentialEntity> findByClientId(String clientId) {
        Map<String, Object> params = new HashMap<>();

        params.put("client_id", clientId);

        List<CredentialEntity> credentials = get(params);

        if (credentials.size() > 0) {
            return Optional.of(credentials.get(0));
        }

        return Optional.ofNullable(null);
    }
}
