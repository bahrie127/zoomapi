package repositories;

import entities.MessageEntity;
import exceptions.InvalidEntityException;
import util.DateUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageRepository extends Repository<MessageEntity, String> {
    public MessageRepository() throws InvalidEntityException {
        super(MessageEntity.class);
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
