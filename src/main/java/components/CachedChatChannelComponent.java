package components;

import entities.ChannelEntity;
import exceptions.InvalidComponentException;
import exceptions.InvalidEntityException;
import models.Channel;
import repositories.ChannelRepository;

import java.util.List;

public class CachedChatChannelComponent {

    private ChatChannelComponent chatChannelComponent = new ChatChannelComponent();
    private ChannelRepository channelRepository;

    public CachedChatChannelComponent() throws InvalidEntityException {
        this.channelRepository = new ChannelRepository();
    }

    public Channel createChannel(String name, int type, List<String> members) throws InvalidComponentException {
        Channel channel = chatChannelComponent.createChannel(name, type, members);
        ChannelEntity channelEntity = new ChannelEntity();

        channelEntity.setId(channel.getId());
        channelEntity.setType(type);
        channelEntity.setName(name);

        this.channelRepository.save(channelEntity);

        return channel;
    }

    public void deleteChannel(String channelId) throws InvalidComponentException {
        chatChannelComponent.deleteChannel(channelId);
        this.channelRepository.remove(channelId);
    }
}
