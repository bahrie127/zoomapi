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

public class Webinar {

    public HttpResponse list(List<String> hostID, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(hostID, "host_id");
        if(data.get("start_time") != null) {
            data.put("start_time", DateToString.dateToString(data.get("start_time")));
        }
        return ApiClient.getInstance().postRequest("/webinar/list", params, data);
    }

    public HttpResponse upcoming(List<String> hostID, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(hostID, "host_id");
        if(data.get("start_time") != null) {
            data.put("start_time", DateToString.dateToString(data.get("start_time")));
        }
        return ApiClient.getInstance().postRequest("/webinar/list/registration", params, data);
    }

    public HttpResponse create(List<String> userValues, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(userValues, Arrays.asList("host_id","topic"));
        if(data.get("start_time") != null) {
            data.put("start_time", DateToString.dateToString(data.get("start_time")));
        }
        return ApiClient.getInstance().postRequest("/webinar/create", params, data);
    }

    public HttpResponse update(List<String> userValues, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(userValues, Arrays.asList("id","host_id"));
        if(data.get("start_time") != null) {
            data.put("start_time", DateToString.dateToString(data.get("start_time")));
        }
        return ApiClient.getInstance().postRequest("/webinar/update", params, data);
    }

    public HttpResponse delete(List<String> userValues, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(userValues, Arrays.asList("id","host_id"));
        return ApiClient.getInstance().postRequest("/webinar/delete", params, data);
    }

    public HttpResponse end(List<String> userValues, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(userValues, Arrays.asList("id","host_id"));
        return ApiClient.getInstance().postRequest("/webinar/end", params, data);
    }

    public HttpResponse get(List<String> userValues, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(userValues, Arrays.asList("id","host_id"));
        return ApiClient.getInstance().postRequest("/webinar/get", params, data);
    }

    public HttpResponse register(List<String> userValues, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(userValues, Arrays.asList("id", "email", "first_name", "last_name"));
        if(data.get("start_time") != null) {
            data.put("start_time", DateToString.dateToString(data.get("start_time")));
        }
        return ApiClient.getInstance().postRequest("/webinar/register", params, data);
    }
}