package components;

import api.ApiClient;
import org.apache.http.NameValuePair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class UserV2 {

     private ApiClient apiClient;

     public UserV2() {
         this.apiClient = apiClient.getInstance();
     }

     public HttpResponse listUsers(List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return apiClient.getRequest("/user/list", params);
    }

    public HttpResponse createUser(List<NameValuePair> params,  Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return apiClient.postRequest("/users", params, data);
    }

    public HttpResponse updateUser(String id, List<NameValuePair> params,  Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return apiClient.patchRequest("/users/"+id, params, data);
    }

    public HttpResponse deleteUser(String id, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return apiClient.deleteRequest("/users/"+id, params);
    }

    public HttpResponse get(String id, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return apiClient.getRequest("/users/"+id, params);
    }
}
