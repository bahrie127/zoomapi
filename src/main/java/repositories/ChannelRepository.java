package repositories;

import entities.ChannelEntity;
import exceptions.InvalidEntityException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ChannelRepository extends Repository<ChannelEntity> {

    public ChannelRepository() throws InvalidEntityException {
        super(ChannelEntity.class);
    }

    public Optional<ChannelEntity> findByIdAndClientId(String id, String clientId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("client_id", clientId);

        List<ChannelEntity> channels = get(params);

        if (channels.size() > 0) {
            return Optional.of(channels.get(0));
        }

        return Optional.ofNullable(null);

    }

    public List<ChannelEntity> findByClientId(String clientId) {
        Map<String, Object> params = new HashMap<>();
        params.put("client_id", clientId);

        return get(params);
    }

    public void removeByClientId(String clientId) {
        Map<String, Object> params = new HashMap<>();

        params.put("client_id", clientId);
        removeByCondition(params);
    }

    public void removeByIdAndClientId(String id, String clientId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("client_id", clientId);

        removeByCondition(params);
    }
}
