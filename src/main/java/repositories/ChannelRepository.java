package repositories;

import entities.ChannelEntity;
import exceptions.InvalidEntityException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChannelRepository extends Repository<ChannelEntity, String> {

    public ChannelRepository() throws InvalidEntityException {
        super(ChannelEntity.class);
    }

    public List<ChannelEntity> findByClientId(String clientId) {
        String where = "client_id = '" + clientId + "';";
        return get(where);
    }

    public void removeByClientId(String clientId) {
        Map<String, Object> params = new HashMap<>();

        params.put("client_id", clientId);
        removeByCondition(params);
    }
}
