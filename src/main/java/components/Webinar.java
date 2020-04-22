package components;

import api.ApiClient;
import com.google.gson.JsonObject;
import exceptions.InvalidArgumentException;
import util.Validator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class Webinar {

    public JsonObject list(String userId, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("userId", userId);
        return ApiClient.getThrottledInstance().getRequest("/users/"+userId+"/webinars", params);
    }

    public JsonObject create(String userId, Map<String, Object> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("userId", userId);
        return ApiClient.getThrottledInstance().postRequest("/users/"+userId+"/webinars", params, data);
    }

    public JsonObject update(String id, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("id", id);
        return ApiClient.getThrottledInstance().patchRequest("/webinars/"+id, data);
    }

    public JsonObject delete(String id) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("id", id);
        return ApiClient.getThrottledInstance().deleteRequest("/webinars/"+id);
    }

    public JsonObject end(String id, Map<String, Object> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("id", id);

        if(params.get("status").equals("end"));{
            return ApiClient.getThrottledInstance().putRequest("/webinars/"+id+"/status", params, data);
        }
    }

    public JsonObject get(String id, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("id", id);
        return ApiClient.getThrottledInstance().getRequest("/webinars/"+id, params);
    }

    public JsonObject register(String id, String email, String firstName, String lastName, Map<String, Object> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("id",id);
        Validator.validateString("email",email);
        Validator.validateString("firstName",firstName);
        Validator.validateString("lastName",lastName);

        return ApiClient.getThrottledInstance().postRequest("/webinars/"+id+"/registrants", params, data);
    }
}