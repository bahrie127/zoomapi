package repositories;

import entities.ChannelMemberEntity;
import exceptions.InvalidEntityException;

public class ChannelMemberRepository extends Repository<ChannelMemberEntity, String> {
    public ChannelMemberRepository() throws InvalidEntityException {
        super(ChannelMemberEntity.class);
    }
}
