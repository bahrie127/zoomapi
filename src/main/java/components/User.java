package components;

import api.ApiClient;
import com.google.gson.JsonObject;
import exceptions.InvalidArgumentException;
import util.Validator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class User {

     public JsonObject listUsers(Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getThrottledInstance().getRequest("/user/list", params);
    }

    public JsonObject createUser(Map<String, Object> params,  Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getThrottledInstance().postRequest("/users", params, data);
    }

    public JsonObject updateUser(String id, Map<String, Object> params,  Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getThrottledInstance().patchRequest("/users/"+id, data);
    }

    public JsonObject deleteUser(String id, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getThrottledInstance().deleteRequest("/users/"+id);
    }

    public JsonObject get(String id, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getThrottledInstance().getRequest("/users/"+id, params);
    }

    public JsonObject getPermissions(String userId) throws InvalidArgumentException, InterruptedException, IOException, URISyntaxException {
        Validator.validateString("userId", userId);
         return ApiClient.getThrottledInstance().getRequest("/users/" + userId + "/permissions");
    }
}
