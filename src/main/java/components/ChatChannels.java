package components;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.NameValuePair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.List;

public class ChatChannels extends BaseComponent{

    public ChatChannels(String baseUri, Integer timeout) throws UnirestException {
        super(baseUri, timeout);
    }


    public HttpResponse listChannels(List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return getRequest("/chat/users/me/channels", params);

    }

}
