package components;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.NameValuePair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

 // main user class
public class UserV2 extends BaseComponent {
    public UserV2(String baseUri, Integer timeout) throws UnirestException {
        super(baseUri, timeout);
    }

    public HttpResponse listUsers(List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return getRequest("/user/list", params);
    }

    public HttpResponse createUser(List<NameValuePair> params,  Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return postRequest("/users", params, data);
    }

    public HttpResponse updateUser(String id, List<NameValuePair> params,  Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return patchRequest("/users/"+id, params, data);
    }

    public HttpResponse deleteUser(String id, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return deleteRequest("/users/"+id, params);
    }

    public HttpResponse get(String id, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return getRequest("/users/"+id, params);
    }
}
