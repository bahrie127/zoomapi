package components;

import Util.DateToString;
import Util.RequireKeys;
import api.ApiClient;
import org.apache.http.NameValuePair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class Recording {

//    FIXME: need to add start and end
    public HttpResponse list(List<String> hostId, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(hostId, "host_id");
        return ApiClient.getInstance().postRequest("/recording/list", params, data);
    }

    public HttpResponse delete(List<String> meetingID, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(meetingID, "meeting_id");
        return ApiClient.getInstance().postRequest("/recording/delete", params, data);
    }

    public HttpResponse get(List<String> meetingID, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(meetingID, "meeting_id");
        return ApiClient.getInstance().postRequest("/recording/get", params, data);
    }
}

/*
def list(self, **kwargs):
        util.require_keys(kwargs, "host_id")
        start = kwargs.pop("start", None)
        if start:
            kwargs["from"] = util.date_to_str(start)
        end = kwargs.pop("end", None)
        if end:
            kwargs["to"] = util.date_to_str(end)
        return self.post_request("/recording/list", params=kwargs)

 */
