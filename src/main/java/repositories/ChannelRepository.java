package repositories;

import models.Channel;

public class ChannelRepository extends Repository<Channel> {

    public ChannelRepository() {
        super("channels", Channel.class);
    }
}
