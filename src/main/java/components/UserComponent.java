package components;

import api.ApiClient;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import exceptions.InvalidArgumentException;
import util.Validator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.Map;

public class UserComponent {

    private Gson gson;

    public UserComponent() {
        this.gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();
    }

    public JsonObject listUsers(Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException {
        HttpResponse response = ApiClient.getThrottledInstance().getRequest("/user/list", params);

        return gson.fromJson(response.body().toString(), JsonObject.class);
    }

    public JsonObject createUser(Map<String, Object> params,  Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        HttpResponse response = ApiClient.getThrottledInstance().postRequest("/users", params, data);

        return gson.fromJson(response.body().toString(), JsonObject.class);
    }

    public JsonObject updateUser(String id,  Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("id", id);
        HttpResponse response = ApiClient.getThrottledInstance().patchRequest("/users/"+id, data);

        return gson.fromJson(response.body().toString(), JsonObject.class);
    }

    public JsonObject deleteUser(String id) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("id", id);
        HttpResponse response = ApiClient.getThrottledInstance().deleteRequest("/users/"+id);

        return gson.fromJson(response.body().toString(), JsonObject.class);
    }

    public JsonObject get(String id, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("id", id);
        HttpResponse response = ApiClient.getThrottledInstance().getRequest("/users/"+id, params);

        return gson.fromJson(response.body().toString(), JsonObject.class);
    }

    public JsonObject getPermissions(String userId) throws InvalidArgumentException, InterruptedException, IOException, URISyntaxException {
        Validator.validateString("userId", userId);
        HttpResponse response = ApiClient.getThrottledInstance().getRequest("/users/" + userId + "/permissions");

        return gson.fromJson(response.body().toString(), JsonObject.class);
    }
}
