package components;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.InvalidArgumentException;
import api.ApiClient;
import com.google.gson.JsonObject;
import util.DateUtil;
import util.Validator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.Date;
import java.util.Map;

public class RecordingComponent {

    private Gson gson;

    public RecordingComponent() {
        this.gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();
    }

    public JsonObject list(String userId, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("userId", userId);

        if (params.containsKey("start")) {
            params.put("from", DateUtil.dateToString((Date) params.get("start")));
            params.remove("start");
        }

        if (params.containsKey("end")) {
            params.put("to", DateUtil.dateToString((Date) params.get("end")));
            params.remove("end");
        }

        HttpResponse response = ApiClient.getThrottledInstance().getRequest("/users/" + userId + "/recordings", params);

        return gson.fromJson(response.body().toString(), JsonObject.class);
    }

    public JsonObject get(String meetingId, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("meetingId", meetingId);
        HttpResponse response = ApiClient.getThrottledInstance().getRequest("/meeting/" + meetingId + "/recordings", params);

        return gson.fromJson(response.body().toString(), JsonObject.class);
    }

    public void delete(String meetingId) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("meetingId", meetingId);
        ApiClient.getThrottledInstance().deleteRequest("/meeting/" + meetingId + "/recordings");
    }
}