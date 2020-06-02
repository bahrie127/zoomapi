package repositories;

import entities.ChannelMemberEntity;
import exceptions.InvalidEntityException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChannelMemberRepository extends Repository<ChannelMemberEntity> {
    public ChannelMemberRepository() throws InvalidEntityException {
        super(ChannelMemberEntity.class);
    }

    public List<ChannelMemberEntity> findByChannelIdAndClientId(String channelId, String clientId) {
        Map<String, Object> params = new HashMap<>();
        params.put("channel_id", channelId);
        params.put("client_id", clientId);

        return get(params);
    }

    public void removeByChannelIdAndClientId(String channelId, String clientId) {
        Map<String, Object> params = new HashMap<>();
        params.put("channel_id", channelId);
        params.put("client_id", clientId);

        removeByCondition(params);
    }

    public void removeByMemberIdAndClientId(String memberId, String clientId) {
        Map<String, Object> params = new HashMap<>();
        params.put("member_id", memberId);
        params.put("client_id", clientId);

        removeByCondition(params);
    }
}
