package components;

import Util.RequireKeys;
import api.ApiClient;
import org.apache.http.NameValuePair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RecordingV2 {

    // FIXME: Not sure how to get the to and from
    public HttpResponse list(List<String> userID, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(userID, "meeting_id");
        return ApiClient.getInstance().getRequest("/users/"+userID.get(0)+"/recordings", params);
    }

    public HttpResponse get(List<String> meetingID, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(meetingID, "meeting_id");
        return ApiClient.getInstance().getRequest("/meeting/"+meetingID+"/recordings", params);
    }

    public HttpResponse delete(List<String> meetingID, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(meetingID, "meeting_id");
        return ApiClient.getInstance().deleteRequest("/meeting/"+meetingID+"/recordings", params);
    }
}
/*
  def list(self, **kwargs):
        util.require_keys(kwargs, "user_id")
        start = kwargs.pop("start", None)
        if start:
            kwargs["from"] = util.date_to_str(start)
        end = kwargs.pop("end", None)
        if end:
            kwargs["to"] = util.date_to_str(end)
        return self.get_request(
            "/users/{}/recordings".format(kwargs.get("user_id")), params=kwargs
        )
 */