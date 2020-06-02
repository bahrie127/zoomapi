package components;

import entities.ChannelEntity;
import entities.ChannelMemberEntity;
import exceptions.InvalidComponentException;
import exceptions.InvalidEntityException;
import models.*;
import repositories.ChannelMemberRepository;
import repositories.ChannelRepository;
import util.DateUtil;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CachedChatChannelComponent extends ChatChannelComponent implements CachedComponent {

    private ChannelRepository channelRepository;
    private ChannelMemberRepository channelMemberRepository = new ChannelMemberRepository();
    private static final long CACHE_INVALIDATION_TIME = 5;
    private String clientId;

    public CachedChatChannelComponent(String clientId) throws InvalidEntityException {
        this.channelRepository = new ChannelRepository();
        this.clientId = clientId;
    }

    @Override
    public ChannelCollection listChannels(Map<String, Object> params) throws InvalidComponentException {

        if (params == null || !params.containsKey("next_page_token") || ((String) params.get("next_page_token")).isEmpty()) {
            List<ChannelEntity> cachedChannels = channelRepository.findByClientId(clientId);

            if (cachedChannels.size() > 0 && !hasCachedChannelsExpired(cachedChannels)) {
                return formChannelCollection(cachedChannels);
            }

            //Makes sure the cache will only have the correct channels
            channelRepository.removeByClientId(clientId);
        }

        ChannelCollection channelCollection = super.listChannels(params);
        List<ChannelEntity> entities = new ArrayList<>();
        if (channelCollection != null) {
            for (Channel channel : channelCollection.getChannels()) {
                entities.add(modelToEntity(channel));
            }
        }

        this.channelRepository.save(entities);

        return channelCollection;
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
        Optional<ChannelEntity> optionalCachedEntity = this.channelRepository.findByIdAndClientId(channelId, this.clientId);

        if (optionalCachedEntity.isPresent()) {
            ChannelEntity cachedEntity = optionalCachedEntity.get();
            Channel cachedChannel = channelEntityToModel(cachedEntity);

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
        this.channelRepository.removeByIdAndClientId(channelId, this.clientId);
    }

    @Override
    public void updateChannel(String channelId, String name) throws InvalidComponentException {
        super.updateChannel(channelId, name);
        Optional<ChannelEntity> optionalCachedEntity = this.channelRepository.findByIdAndClientId(channelId, this.clientId);

        if (optionalCachedEntity.isPresent()) {
            ChannelEntity cachedEntity = optionalCachedEntity.get();
            cachedEntity.setName(name);
            cachedEntity.setCachedDate(LocalDateTime.now(ZoneOffset.UTC));
            this.channelRepository.save(cachedEntity);
        }
    }

    @Override
    public ChannelMemberCollection listMembers(String channelId, Map<String, Object> params) throws InvalidComponentException {
        if (params == null || !params.containsKey("next_page_token") || ((String) params.get("next_page_token")).isEmpty()) {
            List<ChannelMemberEntity> cachedChannelMembers = channelMemberRepository.findByChannelIdAndClientId(channelId, clientId);

            if (cachedChannelMembers.size() > 0 && !hasCachedMembersExpired(cachedChannelMembers)) {
                return formMemberCollection(cachedChannelMembers);
            }

            //Makes sure the cache will only have the correct channels
            channelMemberRepository.removeByChannelIdAndClientId(channelId, clientId);
        }

        ChannelMemberCollection channelMemberCollection = super.listMembers(channelId, params);

        List<ChannelMemberEntity> entities = new ArrayList<>();
        for(ChannelMember member: channelMemberCollection.getMembers()) {
            entities.add(memberModelToEntity(member, channelId));
        }

        this.channelMemberRepository.save(entities);
        return channelMemberCollection;
    }

    @Override
    public void leaveChannel(String channelId) throws InvalidComponentException {
        super.leaveChannel(channelId);
        this.channelRepository.removeByIdAndClientId(channelId, this.clientId);
        this.channelMemberRepository.removeByChannelIdAndClientId(channelId, this.clientId);
    }

    @Override
    public void removeMember(String channelId, String memberId) throws InvalidComponentException {
        this.channelMemberRepository.removeByIdAndClientId(memberId, this.clientId);
        super.removeMember(channelId, memberId);
    }

    private Channel channelEntityToModel(ChannelEntity entity) {
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
        entity.setCachedDate(LocalDateTime.now(ZoneOffset.UTC));
        entity.setClientId(this.clientId);

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
        entity.setCachedDate(LocalDateTime.now(ZoneOffset.UTC));
        entity.setClientId(this.clientId);

        return entity;
    }

    private ChannelMember memberEntityToModel(ChannelMemberEntity entity) {
        ChannelMember model = new ChannelMember();

        model.setId(entity.getId());
        model.setEmail(entity.getEmail());
        model.setFirstName(entity.getFirstName());
        model.setLastName(entity.getLastName());
        model.setRole(entity.getRole());

        return model;
    }

    private boolean hasCachedChannelsExpired(List<ChannelEntity> channelEntities) {
        for (ChannelEntity channelEntity : channelEntities) {
            long timeDifference = DateUtil.minutesBetween(channelEntity.getCachedDate(), LocalDateTime.now(ZoneOffset.UTC));

            if (timeDifference > CACHE_INVALIDATION_TIME) {
                return true;
            }
        }

        return false;
    }

    private boolean hasCachedMembersExpired(List<ChannelMemberEntity> channelMemberEntities) {
        for (ChannelMemberEntity channelMemberEntity : channelMemberEntities) {
            long timeDifference = DateUtil.minutesBetween(channelMemberEntity.getCachedDate(), LocalDateTime.now(ZoneOffset.UTC));

            if (timeDifference > CACHE_INVALIDATION_TIME) {
                return true;
            }
        }

        return false;
    }

    private ChannelCollection formChannelCollection(List<ChannelEntity> channelEntities) {
        ChannelCollection channelCollection = new ChannelCollection();

        List<Channel> channels = new ArrayList<>();
        for (ChannelEntity entity : channelEntities) {
            channels.add(channelEntityToModel(entity));
        }

        channelCollection.setChannels(channels);
        channelCollection.setPageSize(1);
        channelCollection.setTotalRecords(channels.size());

        return channelCollection;
    }

    private ChannelMemberCollection formMemberCollection(List<ChannelMemberEntity> entities) {
        ChannelMemberCollection collection = new ChannelMemberCollection();

        List<ChannelMember> members = new ArrayList<>();
        for (ChannelMemberEntity entity : entities) {
            members.add(memberEntityToModel(entity));
        }

        collection.setMembers(members);
        collection.setPageSize(1);
        collection.setTotalRecords(members.size());

        return collection;
    }

    public void close() {
        channelRepository.close();
        channelMemberRepository.close();
    }
}
