package components;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.InvalidArgumentException;
import api.ApiClient;
import exceptions.InvalidComponentException;
import exceptions.InvalidRequestException;
import models.Recording;
import models.RecordingCollection;
import util.DateUtil;
import util.Validator;

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

    public RecordingCollection list(String userId, Map<String, Object> params) throws InvalidComponentException {
        try {
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

            return gson.fromJson(response.body().toString(), RecordingCollection.class);
        } catch (InterruptedException | InvalidRequestException | InvalidArgumentException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }

    public Recording get(String meetingId, Map<String, Object> params) throws InvalidComponentException {
        try {
            Validator.validateString("meetingId", meetingId);
            HttpResponse response = ApiClient.getThrottledInstance().getRequest("/meeting/" + meetingId + "/recordings", params);

            return gson.fromJson(response.body().toString(), Recording.class);
        } catch (InterruptedException | InvalidRequestException | InvalidArgumentException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }

    public void delete(String meetingId) throws InvalidComponentException {
        try {
            Validator.validateString("meetingId", meetingId);
            ApiClient.getThrottledInstance().deleteRequest("/meeting/" + meetingId + "/recordings");
        } catch (InterruptedException | InvalidRequestException | InvalidArgumentException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }
}
