package repositories;

import models.ChannelMember;

public class ChannelMemberRepository extends Repository<ChannelMember> {
    public ChannelMemberRepository() {
        super("channel_membership", ChannelMember.class);
    }
}
