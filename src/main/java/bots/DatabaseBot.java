package bots;

import com.google.gson.Gson;
import entities.ChannelEntity;
import entities.ChannelMemberEntity;
import entities.CredentialEntity;
import exceptions.InvalidEntityException;
import repositories.ChannelMemberRepository;
import repositories.ChannelRepository;
import repositories.CredentialRepository;

public class DatabaseBot {

    public static void main(String[] args) throws InvalidEntityException {
        Gson gson = new Gson();

        CredentialRepository credentialRepository = new CredentialRepository();
        ChannelRepository channelRepository = new ChannelRepository();
        ChannelMemberRepository channelMemberRepository = new ChannelMemberRepository();


        CredentialEntity credential = new CredentialEntity("TEST", "TEST");
        credentialRepository.save(credential);

        CredentialEntity updatedCredential = new CredentialEntity("TEST", "DIFFERENT_TEST");
        credentialRepository.save(updatedCredential);

        CredentialEntity credential2 = new CredentialEntity("TEST2", "TEST2");
        credentialRepository.save(credential2);
        credentialRepository.remove(credential2.getClientId());

        ChannelEntity channelEntity = new ChannelEntity();
        channelEntity.setId("test");
        channelEntity.setName("name");
        channelEntity.setType(1);

        channelRepository.save(channelEntity);

        ChannelMemberEntity channelMemberEntity = new ChannelMemberEntity();
        channelMemberEntity.setId("test");
        channelMemberEntity.setEmail("test");
        channelMemberEntity.setFirstName("name");
        channelMemberEntity.setLastName("last");
        channelMemberEntity.setRole("role");
        channelMemberEntity.setChannelId("test");

        channelMemberRepository.save(channelMemberEntity);

        System.out.println(gson.toJson(channelMemberRepository.findById("test")));
    }
}
