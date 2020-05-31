package components;

import entities.ChannelEntity;
import entities.ChannelMemberEntity;
import exceptions.InvalidComponentException;
import exceptions.InvalidEntityException;
import models.*;
import repositories.ChannelMemberRepository;
import repositories.ChannelRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CachedChatChannelComponent extends ChatChannelComponent {

    private ChannelRepository channelRepository;
    private UserComponent userComponent = new UserComponent();
    private ChannelMemberRepository channelMemberRepository = new ChannelMemberRepository();

    public CachedChatChannelComponent() throws InvalidEntityException {
        this.channelRepository = new ChannelRepository();
    }

    @Override
    public ChannelCollection listChannels(Map<String, Object> params) throws InvalidComponentException {

        ChannelCollection channelCollection = super.listChannels(params);
        for (Channel channel : channelCollection.getChannels()) {
            ChannelEntity channelEntity = modelToEntity(channel);
            this.channelRepository.save(channelEntity);
        }

        return super.listChannels(params);
    }

    @Override
    public Channel createChannel(String name, int type, List<String> members) throws InvalidComponentException {
        Channel channel = super.createChannel(name, type, members);
        ChannelEntity channelEntity = modelToEntity(channel);

        this.channelRepository.save(channelEntity);

        return channel;
    }

    @Override
    public Channel getChannel(String channelId) throws InvalidComponentException {
        Optional<ChannelEntity> optionalCachedEntity = this.channelRepository.findById(channelId);

        if (optionalCachedEntity.isPresent()) {
            ChannelEntity cachedEntity = optionalCachedEntity.get();
            Channel cachedChannel = entityToModel(cachedEntity);

            return cachedChannel;
        }

        Channel channel = super.getChannel(channelId);
        ChannelEntity channelEntity = modelToEntity(channel);

        this.channelRepository.save(channelEntity);

        return channel;
    }

    @Override
    public void deleteChannel(String channelId) throws InvalidComponentException {
        super.deleteChannel(channelId);
        this.channelRepository.remove(channelId);
    }

    @Override
    public void updateChannel(String channelId, String name) throws InvalidComponentException {

        super.updateChannel(channelId, name);

        Optional<ChannelEntity> optionalCachedEntity = this.channelRepository.findById(channelId);

        if (optionalCachedEntity.isPresent()) {
            ChannelEntity cachedEntity = optionalCachedEntity.get();
            cachedEntity.setName(name);
            this.channelRepository.save(cachedEntity);
        }
    }

    @Override
    public ChannelMemberCollection listMembers(String channelId, Map<String, Object> params) throws InvalidComponentException {
        ChannelMemberCollection channelMemberCollection = super.listMembers(channelId, params);

        for(ChannelMember member: channelMemberCollection.getMembers()) {
            ChannelMemberEntity channelMemberEntity = memberModelToEntity(member, channelId);
            this.channelMemberRepository.save(channelMemberEntity);
        }

        return super.listMembers(channelId, params);
    }

    //TODO: Need to figure out how to cache channel members when email is the only thing provided
    @Override
    public InvitedChannelMembers inviteMembers(String channelId, List<String> members) throws InvalidComponentException {

        Optional<ChannelEntity> optionalCachedEntity = this.channelRepository.findById(channelId);

        if (optionalCachedEntity.isPresent()) {
            ChannelEntity cachedEntity = optionalCachedEntity.get();
            Channel cachedChannel = entityToModel(cachedEntity);
        }
            for (String email : members) {

        }
        return super.inviteMembers(channelId, members);
    }


    // TODO: Need to figure out how to cache channel members when email is the only thing provided
    @Override
    public JoinedMember joinChannel(String channelId) throws InvalidComponentException {

        JoinedMember joinedMember = super.joinChannel(channelId);
        return super.joinChannel(channelId);
    }

    @Override
    public void leaveChannel(String channelId) throws InvalidComponentException {

        User currentUser = this.userComponent.get("me", null);
        String memberId = "";

        ChannelMemberCollection channelMemberCollection = super.listMembers(channelId, null);

        for(ChannelMember member: channelMemberCollection.getMembers()) {
            if(member.getEmail().equals(currentUser.getEmail()))
                memberId = member.getId();
        }

        this.channelMemberRepository.remove(memberId);
        super.leaveChannel(channelId);
    }

    @Override
    public void removeMember(String channelId, String memberId) throws InvalidComponentException {
        this.channelMemberRepository.remove(memberId);
        super.removeMember(channelId, memberId);
    }

    private Channel entityToModel(ChannelEntity entity) {
        Channel channel = new Channel();

        channel.setId(entity.getId());
        channel.setName(entity.getName());
        channel.setType(entity.getType());

        return channel;
    }

    private ChannelEntity modelToEntity(Channel model) {
        ChannelEntity entity = new ChannelEntity();

        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setType(model.getType());

        return entity;
    }

    private ChannelMemberEntity memberModelToEntity(ChannelMember model, String channelId) {
        ChannelMemberEntity entity = new ChannelMemberEntity();

        entity.setId(model.getId());
        entity.setEmail(model.getEmail());
        entity.setFirstName(model.getFirstName());
        entity.setLastName(model.getLastName());
        entity.setRole(model.getRole());
        entity.setChannelId(channelId);

        return entity;
    }
}
