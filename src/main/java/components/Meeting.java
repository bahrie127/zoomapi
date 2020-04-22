package components;

import api.ApiClient;
import com.google.gson.JsonObject;
import util.RequireKeys;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class Meeting {

    public JsonObject list(List<String> userID, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(userID, "user_id");
        return ApiClient.getThrottledInstance().getRequest("/users/"+userID.get(0)+"/meetings", params);
    }

    public JsonObject create(List<String> userID, Map<String, Object> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        /*if(data.get("start_time") != null) {
            data.put("start_time", DateUtil.dateToString(data.get("start_time")));
        }*/
        RequireKeys.requireKeys(userID, "user_id");
        return ApiClient.getThrottledInstance().postRequest("/users/"+userID.get(0)+"/meetings", params, data);
    }

    public JsonObject get(List<String> id, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(id, "id");
        return ApiClient.getThrottledInstance().getRequest("/meetings/"+id.get(0), params);
    }

    public JsonObject update(List<String> id, Map<String, Object> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        /*if(data.get("start_time") != null) {
            data.put("start_time", DateUtil.dateToString(data.get("start_time")));
        }*/
        RequireKeys.requireKeys(id, "id");
        return ApiClient.getThrottledInstance().patchRequest("/meetings/"+id.get(0), data);
    }

    public JsonObject delete(List<String> id, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(id, "id");
        return ApiClient.getThrottledInstance().deleteRequest("/meetings/"+id.get(0));
    }
}
