package repositories;

import entities.MessageEntity;
import exceptions.InvalidEntityException;
import util.DateUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MessageRepository extends Repository<MessageEntity> {
    public MessageRepository() throws InvalidEntityException {
        super(MessageEntity.class);
    }

    public Optional<MessageEntity> findByMessageIdAndClientId(String id, String clientId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("client_id", clientId);

        List<MessageEntity> messages = get(params);

        if (messages.size() > 0) {
            return Optional.of(messages.get(0));
        }

        return Optional.ofNullable(null);
    }

    public void removeByMessageIdAndClientId(String id, String clientId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("client_id", clientId);

        removeByCondition(params);
    }

    public List<MessageEntity> getByDateAndClientIdAndChannelId(LocalDate localDate, String clientId, String channelId) {
        String dateMin = DateUtil.localDateTimeToString(localDate.atTime(LocalTime.MIN));
        String dateMax = DateUtil.localDateTimeToString(localDate.atTime(LocalTime.MAX));
        String where = "client_id = '" + clientId + "' AND channel_id = '" + channelId + "' AND date_time BETWEEN '"
                + dateMin + "' AND '" + dateMax + "' ORDER BY date_time asc;";
        return get(where);
    }

    public void removeByDateAndClientIdAndChannelId(LocalDate localDate, String clientId, String channelId) {
        String dateMin = DateUtil.localDateTimeToString(localDate.atTime(LocalTime.MIN));
        String dateMax = DateUtil.localDateTimeToString(localDate.atTime(LocalTime.MAX));
        String where = "client_id = '" + clientId + "' AND channel_id = '" + channelId + "' AND date_time BETWEEN '"
                + dateMin + "' AND '" + dateMax + "';";

        removeByCondition(where);
    }
}
