package components;

import util.RequireKeys;
import api.ApiClient;
import com.google.gson.JsonObject;
import org.apache.http.NameValuePair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Recording {

    // FIXME: Not sure how to get the to and from
    public JsonObject list(List<String> userID, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(userID, "meeting_id");
        return ApiClient.getThrottledInstance().getRequest("/users/"+userID.get(0)+"/recordings", params);
    }

    public JsonObject get(List<String> meetingID, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(meetingID, "meeting_id");
        return ApiClient.getThrottledInstance().getRequest("/meeting/"+meetingID.get(0)+"/recordings", params);
    }

    public JsonObject delete(List<String> meetingID, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(meetingID, "meeting_id");
        return ApiClient.getThrottledInstance().deleteRequest("/meeting/"+meetingID.get(0)+"/recordings");
    }
}
