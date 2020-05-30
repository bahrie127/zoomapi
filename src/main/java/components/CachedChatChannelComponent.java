package components;

import entities.ChannelEntity;
import exceptions.InvalidComponentException;
import exceptions.InvalidEntityException;
import models.Channel;
import models.ChannelCollection;
import repositories.ChannelRepository;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CachedChatChannelComponent {

    private ChatChannelComponent chatChannelComponent = new ChatChannelComponent();
    private ChannelRepository channelRepository;

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

        Optional<ChannelEntity> optionalCachedEntity = this.channelRepository.findById(channelId);

        if (optionalCachedEntity.isPresent()) {
            ChannelEntity cachedEntity = optionalCachedEntity.get();
            this.channelRepository.remove(cachedEntity.getId());
        }
        channelRepository.close();
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
}
