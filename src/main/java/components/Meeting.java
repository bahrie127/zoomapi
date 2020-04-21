package components;

import Util.DateToString;
import Util.RequireKeys;
import api.ApiClient;
import org.apache.http.NameValuePair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Meeting {

    public HttpResponse list(List<String> hostID, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        if(data.get("start_time") != null) {
            data.put("start_time", DateToString.dateToString(data));
        }
        RequireKeys.requireKeys(hostID, "host_id");
        return ApiClient.getInstance().postRequest("/meeting/list", params, data);
    }

    public HttpResponse create(List<String> userValues, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        if(data.get("start_time") != null) {
            data.put("start_time", DateToString.dateToString(data));
        }
        RequireKeys.requireKeys(userValues, Arrays.asList("host_id", "topic", "type"));
        return ApiClient.getInstance().postRequest("/meeting/create", params, data);
    }

    public HttpResponse update(List<String> userValues, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        if(data.get("start_time") != null) {
            data.put("start_time", DateToString.dateToString(data));
        }
        RequireKeys.requireKeys(userValues, Arrays.asList("id", "host_id"));
        return ApiClient.getInstance().postRequest("/meeting/update", params, data);
    }

    public HttpResponse delete(List<String> userValues, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(userValues, Arrays.asList("id", "host_id"));
        return ApiClient.getInstance().postRequest("/meeting/delete", params, data);
    }

    public HttpResponse end(List<String> userValues, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(userValues, Arrays.asList("id", "host_id"));
        return ApiClient.getInstance().postRequest("/meeting/end", params, data);
    }

    public HttpResponse get(List<String> userValues, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(userValues, Arrays.asList("id", "host_id"));
        return ApiClient.getInstance().postRequest("/meeting/get", params, data);
    }
}
