package repositories;

import entities.ChannelMemberEntity;
import exceptions.InvalidEntityException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChannelMemberRepository extends Repository<ChannelMemberEntity, String> {
    public ChannelMemberRepository() throws InvalidEntityException {
        super(ChannelMemberEntity.class);
    }

    public List<ChannelMemberEntity> findByChannelId(String channelId) {
        String where = "channel_id = '" + channelId + "';";
        return get(where);
    }

    public void removeByChannelId(String channelId) {
        Map<String, Object> params = new HashMap<>();
        params.put("channel_id", channelId);

        removeByCondition(params);
    }
}
