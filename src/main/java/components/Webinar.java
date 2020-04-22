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

public class Webinar {

    public HttpResponse list(List<String> userID, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(userID, "user_id");
        return ApiClient.getInstance().getRequest("/users/"+userID.get(0)+"/webinars", params);
    }

    public HttpResponse create(List<String> userID, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(userID, "user_id");
        return ApiClient.getInstance().postRequest("/users/"+userID.get(0)+"/webinars", params, data);
    }

    public HttpResponse update(List<String> id, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(id, "id");
        return ApiClient.getInstance().patchRequest("/webinars/"+id.get(0), params, data);
    }

    public HttpResponse delete(List<String> id, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(id, "id");
        return ApiClient.getInstance().deleteRequest("/webinars/"+id.get(0), params);
    }

//    TODO: REVIEW PARAMS STATUS : END
    public HttpResponse end(List<String> id, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(id, "id");
        return ApiClient.getInstance().putRequest("/webinars/"+id.get(0)+"/status", params, data);
    }

    public HttpResponse get(List<String> id, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(id, "id");
        return ApiClient.getInstance().getRequest("/webinars/"+id.get(0), params);
    }

    public HttpResponse register(List<String> userValues, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(userValues, Arrays.asList("id", "email", "first_name", "last_name"));
        return ApiClient.getInstance().postRequest("/webinars/"+userValues.get(0)+"/registrants", params, data);
    }
}