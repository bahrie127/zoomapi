package components;

import api.ApiClient;
import com.google.gson.JsonObject;
import exceptions.InvalidArgumentException;
import util.DateUtil;
import util.Validator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;

public class Meeting {

    public JsonObject list(String userId, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("userId", userId);
        return ApiClient.getThrottledInstance().getRequest("/users/"+userId+"/meetings", params);
    }

    public JsonObject create(String userID, Map<String, Object> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("userId", userID);

        if(params.get("start_time") != null) {
            params.put("start_time", DateUtil.dateToString((Date) params.get("start_time")));
        }

        return ApiClient.getThrottledInstance().postRequest("/users/"+userID+"/meetings", params, data);
    }

    public JsonObject get(String id, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("id", id);
        return ApiClient.getThrottledInstance().getRequest("/meetings/"+id, params);
    }

    public JsonObject update(String id, Map<String, Object> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("id", id);

        if(params.get("start_time") != null) {
            params.put("start_time", DateUtil.dateToString((Date) params.get("start_time")));
        }

        return ApiClient.getThrottledInstance().patchRequest("/meetings/"+id, data);
    }

    public JsonObject delete(String id, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("id", id);
        return ApiClient.getThrottledInstance().deleteRequest("/meetings/"+id);
    }
}
