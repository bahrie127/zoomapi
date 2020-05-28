package repositories;

import entities.ChannelEntity;
import exceptions.InvalidEntityException;

public class ChannelRepository extends Repository<ChannelEntity, String> {

    public ChannelRepository() throws InvalidEntityException {
        super(ChannelEntity.class);
    }
}
