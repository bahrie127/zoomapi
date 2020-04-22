package components;

import api.ApiClient;
import com.google.gson.JsonObject;
import util.RequireKeys;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Webinar {

    public JsonObject list(List<String> userID, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(userID, "user_id");
        return ApiClient.getThrottledInstance().getRequest("/users/"+userID.get(0)+"/webinars", params);
    }

    public JsonObject create(List<String> userID, Map<String, Object> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(userID, "user_id");
        return ApiClient.getThrottledInstance().postRequest("/users/"+userID.get(0)+"/webinars", params, data);
    }

    public JsonObject update(List<String> id, Map<String, Object> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(id, "id");
        return ApiClient.getThrottledInstance().patchRequest("/webinars/"+id.get(0), data);
    }

    public JsonObject delete(List<String> id, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(id, "id");
        return ApiClient.getThrottledInstance().deleteRequest("/webinars/"+id.get(0));
    }

//    TODO: REVIEW PARAMS STATUS : END
    public JsonObject end(List<String> id, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(id, "id");
        return ApiClient.getThrottledInstance().putRequest("/webinars/"+id.get(0)+"/status", data);
    }

    public JsonObject get(List<String> id, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(id, "id");
        return ApiClient.getThrottledInstance().getRequest("/webinars/"+id.get(0), params);
    }

    public JsonObject register(List<String> userValues, Map<String, Object> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(userValues, Arrays.asList("id", "email", "first_name", "last_name"));
        return ApiClient.getThrottledInstance().postRequest("/webinars/"+userValues.get(0)+"/registrants", params, data);
    }
}