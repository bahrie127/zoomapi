package components;

import api.ApiClient;
import org.apache.http.NameValuePair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class User{

    public HttpResponse list(List<NameValuePair> params,  Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getInstance().postRequest("/user/list", params, data);
    }

    public HttpResponse pending(List<NameValuePair> params,  Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getInstance().postRequest("/user/pending", params, data);
    }

    public HttpResponse create(List<NameValuePair> params,  Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getInstance().postRequest("/user/create", params, data);
    }

    public HttpResponse update(String id, List<NameValuePair> params,  Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getInstance().postRequest("/user/update/"+id, params, data);
    }

    public HttpResponse delete(String id, List<NameValuePair> params,  Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getInstance().postRequest("/user/delete/"+id, params, data);
    }

    // "type" or "email"
    public HttpResponse custCreate(String email, List<NameValuePair> params,  Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getInstance().postRequest("/user/custcreate/"+email, params, data);
    }

    public HttpResponse get(String id, List<NameValuePair> params,  Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getInstance().postRequest("/user/get/"+id, params, data);
    }

    // "email" or "login type"
    public HttpResponse getByEmail(String email, List<NameValuePair> params,  Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getInstance().postRequest("/user/getbyemail/"+email, params, data);
    }
}
