package repositories;

import exceptions.InvalidEntityException;
import models.Channel;

public class ChannelRepository extends Repository<Channel, String> {

    public ChannelRepository() throws InvalidEntityException {
        super(Channel.class);
    }
}
