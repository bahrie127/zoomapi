package components;

import api.ApiClient;
import org.apache.http.NameValuePair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class MeetingV2 {

    public HttpResponse list(String userID, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getInstance().getRequest("/users/"+userID+"/meetings", params);
    }

    public HttpResponse create(String userID, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getInstance().postRequest("/users/"+userID+"/meetings", params, data);
    }

    public HttpResponse get(String id, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getInstance().getRequest("/meetings/"+id, params);
    }

    public HttpResponse update(String id, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getInstance().patchRequest("/meetings/"+id, params, data);
    }

    public HttpResponse delete(String id, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getInstance().deleteRequest("/meetings/"+id, params);
    }
}
