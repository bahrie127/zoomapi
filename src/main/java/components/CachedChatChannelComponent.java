package components;

import entities.ChannelEntity;
import entities.ChannelMemberEntity;
import exceptions.InvalidComponentException;
import exceptions.InvalidEntityException;
import models.*;
import repositories.ChannelMemberRepository;
import repositories.ChannelRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CachedChatChannelComponent {

    private ChatChannelComponent chatChannelComponent = new ChatChannelComponent();
    private ChannelRepository channelRepository;
    private UserComponent userComponent = new UserComponent();
    private ChannelMemberRepository channelMemberRepository = new ChannelMemberRepository();

    public CachedChatChannelComponent() throws InvalidEntityException {
        this.channelRepository = new ChannelRepository();
    }

    public ChannelCollection listChannels(Map<String, Object> params) throws InvalidComponentException {

        ChannelCollection channelCollection = chatChannelComponent.listChannels(params);
        for (Channel channel : channelCollection.getChannels()) {
            ChannelEntity channelEntity = modelToEntity(channel);
            this.channelRepository.save(channelEntity);
        }

        return chatChannelComponent.listChannels(params);
    }

    public Channel createChannel(String name, int type, List<String> members) throws InvalidComponentException {
        Channel channel = chatChannelComponent.createChannel(name, type, members);
        ChannelEntity channelEntity = modelToEntity(channel);

        this.channelRepository.save(channelEntity);

        return channel;
    }

    public Channel getChannel(String channelId) throws InvalidComponentException {
        Optional<ChannelEntity> optionalCachedEntity = this.channelRepository.findById(channelId);

        if (optionalCachedEntity.isPresent()) {
            ChannelEntity cachedEntity = optionalCachedEntity.get();
            Channel cachedChannel = entityToModel(cachedEntity);

            return cachedChannel;
        }

        Channel channel = chatChannelComponent.getChannel(channelId);
        ChannelEntity channelEntity = modelToEntity(channel);

        this.channelRepository.save(channelEntity);

        return channel;
    }


    public void deleteChannel(String channelId) throws InvalidComponentException {
        chatChannelComponent.deleteChannel(channelId);
        this.channelRepository.remove(channelId);
    }

    public void updateChannel(String channelId, String name) throws InvalidComponentException {

        chatChannelComponent.updateChannel(channelId, name);

        Optional<ChannelEntity> optionalCachedEntity = this.channelRepository.findById(channelId);

        if (optionalCachedEntity.isPresent()) {
            ChannelEntity cachedEntity = optionalCachedEntity.get();
            cachedEntity.setName(name);
            this.channelRepository.save(cachedEntity);
        }
    }

    public ChannelMemberCollection listMembers(String channelId, Map<String, Object> params) throws InvalidComponentException {
        ChannelMemberCollection channelMemberCollection = chatChannelComponent.listMembers(channelId, params);

        for(ChannelMember member: channelMemberCollection.getMembers()) {
            ChannelMemberEntity channelMemberEntity = memberModelToEntity(member, channelId);
            this.channelMemberRepository.save(channelMemberEntity);
        }

        return chatChannelComponent.listMembers(channelId, params);
    }


//    TODO: Need to figure out how to cache channel members when email is the only thing provided
    public InvitedChannelMembers inviteMembers(String channelId, List<String> members) throws InvalidComponentException {

        Optional<ChannelEntity> optionalCachedEntity = this.channelRepository.findById(channelId);

        if (optionalCachedEntity.isPresent()) {
            ChannelEntity cachedEntity = optionalCachedEntity.get();
            Channel cachedChannel = entityToModel(cachedEntity);
        }
            for (String email : members) {

        }
        return chatChannelComponent.inviteMembers(channelId, members);
    }


    //    TODO: THIS SHOULD WORK IF JOINING PUBLIC CHANNEL
    public JoinedMember joinChannel(String channelId) throws InvalidComponentException {

        User currentUser = this.userComponent.get("me", null);
        ChannelMember channelMember = new ChannelMember();
        JoinedMember joinedMember = chatChannelComponent.joinChannel(channelId);

        channelMember.setId(joinedMember.getId());
        channelMember.setEmail(currentUser.getEmail());
        channelMember.setFirstName(currentUser.getFirstName());
        channelMember.setLastName(currentUser.getFirstName());
        channelMember.setRole("member");

        ChannelMemberEntity channelMemberEntity = memberModelToEntity(channelMember, channelId);
        this.channelMemberRepository.save(channelMemberEntity);
        return chatChannelComponent.joinChannel(channelId);
    }


    public void leaveChannel(String channelId) throws InvalidComponentException {

        User currentUser = this.userComponent.get("me", null);
        String memberId = "";

        ChannelMemberCollection channelMemberCollection = chatChannelComponent.listMembers(channelId, null);

        for(ChannelMember member: channelMemberCollection.getMembers()) {
            if(member.getEmail().equals(currentUser.getEmail()))
                memberId = member.getId();
        }

        this.channelMemberRepository.remove(memberId);
        chatChannelComponent.leaveChannel(channelId);
    }


    public void removeMember(String channelId, String memberId) throws InvalidComponentException {
        this.channelMemberRepository.remove(memberId);
        chatChannelComponent.removeMember(channelId, memberId);
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
