package components;

import api.ApiClient;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.InvalidArgumentException;
import models.MeetingPage;
import util.DateUtil;
import util.Validator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.Map;

public class MeetingComponent {

    private Gson gson;

    public MeetingComponent() {
        this.gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();
    }

    public MeetingPage list(String userId, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("userId", userId);
        HttpResponse response = ApiClient.getThrottledInstance().getRequest("/users/"+userId+"/meetings", params);
        return gson.fromJson(response.body().toString(), MeetingPage.class);
    }

    public MeetingComponent create(String userID, Map<String, Object> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("userId", userID);

        if(params.get("start_time") != null) {
            params.put("start_time", DateUtil.dateToString((Date) params.get("start_time")));
        }

        HttpResponse response = ApiClient.getThrottledInstance().postRequest("/users/"+userID+"/meetings", params, data);

        return gson.fromJson(response.body().toString(), MeetingComponent.class);
    }

    public MeetingComponent get(String id, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("id", id);
        HttpResponse response = ApiClient.getThrottledInstance().getRequest("/meetings/"+id, params);

        return gson.fromJson(response.body().toString(), MeetingComponent.class);
    }

    public void update(String id, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("id", id);

        if(params.get("start_time") != null) {
            params.put("start_time", DateUtil.dateToString((Date) params.get("start_time")));
        }

        ApiClient.getThrottledInstance().patchRequest("/meetings/"+id, params);
    }

    public void delete(String id) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("id", id);
        ApiClient.getThrottledInstance().deleteRequest("/meetings/"+id);
    }
}
