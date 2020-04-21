package components;

import api.ApiClient;
import org.apache.http.NameValuePair;
import Util.DateToString;
import Util.RequireKeys;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class MeetingV2 {

    public HttpResponse list(List<String> userID, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(userID, "user_id");
        return ApiClient.getInstance().getRequest("/users/"+userID+"/meetings", params);
    }

    public HttpResponse create(List<String> userID, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        if(data.get("start_time") != null) {
            data.put("start_time", DateToString.dateToString(data));
        }
        RequireKeys.requireKeys(userID, "user_id");
        return ApiClient.getInstance().postRequest("/users/"+userID+"/meetings", params, data);
    }

    public HttpResponse get(List<String> id, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(id, "id");
        return ApiClient.getInstance().getRequest("/meetings/"+id, params);
    }

    public HttpResponse update(List<String> id, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        if(data.get("start_time") != null) {
            data.put("start_time", DateToString.dateToString(data));
        }
        RequireKeys.requireKeys(id, "id");
        return ApiClient.getInstance().patchRequest("/meetings/"+id, params, data);
    }

    public HttpResponse delete(List<String> id, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(id, "id");
        return ApiClient.getInstance().deleteRequest("/meetings/"+id, params);
    }
}
