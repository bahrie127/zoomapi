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
import java.util.HashMap;
import java.util.Map;

public class WebinarComponent {

    private Gson gson;

    public WebinarComponent() {
        this.gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();
    }

    public JsonObject list(String userId, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("userId", userId);
        HttpResponse response = ApiClient.getThrottledInstance().getRequest("/users/"+userId+"/webinars", params);

        return gson.fromJson(response.body().toString(), JsonObject.class);
    }

    public JsonObject create(String userId, Map<String, Object> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("userId", userId);
        HttpResponse response = ApiClient.getThrottledInstance().postRequest("/users/"+userId+"/webinars", params, data);

        return gson.fromJson(response.body().toString(), JsonObject.class);
    }

    public JsonObject update(String id, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("id", id);
        HttpResponse response = ApiClient.getThrottledInstance().patchRequest("/webinars/"+id, data);

        return gson.fromJson(response.body().toString(), JsonObject.class);
    }

    public JsonObject delete(String id) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("id", id);
        HttpResponse response = ApiClient.getThrottledInstance().deleteRequest("/webinars/"+id);

        return gson.fromJson(response.body().toString(), JsonObject.class);
    }

    public JsonObject end(String id) throws InterruptedException, IOException, InvalidArgumentException {
        Validator.validateString("id", id);

        Map<String, Object> params = new HashMap<>();
        params.put("status", "end");

        HttpResponse response = ApiClient.getThrottledInstance().putRequest("/webinars/" + id + "/status", null);

        return gson.fromJson(response.body().toString(), JsonObject.class);
    }

    public JsonObject get(String id, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("id", id);
        HttpResponse response = ApiClient.getThrottledInstance().getRequest("/webinars/"+ id, params);

        return gson.fromJson(response.body().toString(), JsonObject.class);
    }

    public JsonObject register(String id, String email, String firstName, String lastName, Map<String, Object> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("id",id);
        Validator.validateString("email",email);
        Validator.validateString("firstName",firstName);
        Validator.validateString("lastName",lastName);

        HttpResponse response = ApiClient.getThrottledInstance().postRequest("/webinars/"+id+"/registrants", params, data);

        return gson.fromJson(response.body().toString(), JsonObject.class);
    }
}