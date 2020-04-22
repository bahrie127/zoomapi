package components;

import api.ApiClient;
import com.google.gson.JsonObject;
import org.apache.http.NameValuePair;
import util.DateToString;
import util.RequireKeys;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class Meeting {

    public JsonObject list(List<String> userID, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(userID, "user_id");
        return ApiClient.getThrottledInstance().getRequest("/users/"+userID.get(0)+"/meetings", params);
    }

    public JsonObject create(List<String> userID, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        if(data.get("start_time") != null) {
            data.put("start_time", DateToString.dateToString(data.get("start_time")));
        }
        RequireKeys.requireKeys(userID, "user_id");
        return ApiClient.getThrottledInstance().postRequest("/users/"+userID.get(0)+"/meetings", params, data);
    }

    public JsonObject get(List<String> id, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(id, "id");
        return ApiClient.getThrottledInstance().getRequest("/meetings/"+id.get(0), params);
    }

    public JsonObject update(List<String> id, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        if(data.get("start_time") != null) {
            data.put("start_time", DateToString.dateToString(data.get("start_time")));
        }
        RequireKeys.requireKeys(id, "id");
        return ApiClient.getThrottledInstance().patchRequest("/meetings/"+id.get(0), data);
    }

    public JsonObject delete(List<String> id, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(id, "id");
        return ApiClient.getThrottledInstance().deleteRequest("/meetings/"+id.get(0));
    }
}
