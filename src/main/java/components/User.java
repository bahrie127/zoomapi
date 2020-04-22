package components;

import util.Validator;
import api.ApiClient;
import com.google.gson.JsonObject;
import exceptions.InvalidArgumentException;
import org.apache.http.NameValuePair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class User {

     public JsonObject listUsers(List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getThrottledInstance().getRequest("/user/list", params);
    }

    public JsonObject createUser(List<NameValuePair> params,  Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getThrottledInstance().postRequest("/users", params, data);
    }

    public JsonObject updateUser(String id, List<NameValuePair> params,  Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getThrottledInstance().patchRequest("/users/"+id, data);
    }

    public JsonObject deleteUser(String id, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getThrottledInstance().deleteRequest("/users/"+id);
    }

    public JsonObject get(String id, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getThrottledInstance().getRequest("/users/"+id, params);
    }

    public JsonObject getPermissions(String userId) throws InvalidArgumentException, InterruptedException, IOException, URISyntaxException {
        Validator.validateString("userId", userId);
         return ApiClient.getThrottledInstance().getRequest("/users/" + userId + "/permissions");
    }
}
