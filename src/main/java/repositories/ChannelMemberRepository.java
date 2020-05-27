package repositories;

import exceptions.InvalidEntityException;
import models.ChannelMember;

public class ChannelMemberRepository extends Repository<ChannelMember, String> {
    public ChannelMemberRepository() throws InvalidEntityException {
        super(ChannelMember.class);
    }
}
