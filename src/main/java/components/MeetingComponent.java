package components;

import api.ApiClient;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.InvalidArgumentException;
import exceptions.InvalidComponentException;
import exceptions.InvalidRequestException;
import models.Meeting;
import models.MeetingCollection;
import util.DateUtil;
import util.Validator;

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

    public MeetingCollection list(String userId, Map<String, Object> params) throws InvalidComponentException {
        try {
            Validator.validateString("userId", userId);
            HttpResponse response = ApiClient.getThrottledInstance().getRequest("/users/"+userId+"/meetings", params);
            return gson.fromJson(response.body().toString(), MeetingCollection.class);
        } catch (InterruptedException | InvalidRequestException | InvalidArgumentException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }

    public Meeting create(String userID, Map<String, Object> params, Map<String, Object> data) throws InvalidComponentException {
        try {
            Validator.validateString("userId", userID);

            if(params.get("start_time") != null) {
                params.put("start_time", DateUtil.dateToString((Date) params.get("start_time")));
            }

            HttpResponse response = ApiClient.getThrottledInstance().postRequest("/users/"+userID+"/meetings", params, data);

            return gson.fromJson(response.body().toString(), Meeting.class);
        } catch (InterruptedException | InvalidRequestException | InvalidArgumentException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }

    public Meeting get(String id, Map<String, Object> params) throws InvalidComponentException {
        try {
            Validator.validateString("id", id);
            HttpResponse response = ApiClient.getThrottledInstance().getRequest("/meetings/"+id, params);

            return gson.fromJson(response.body().toString(), Meeting.class);
        } catch (InterruptedException | InvalidRequestException | InvalidArgumentException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }

    public void update(String id, Map<String, Object> params) throws InvalidComponentException {
        try {
            Validator.validateString("id", id);

            if(params.get("start_time") != null) {
                params.put("start_time", DateUtil.dateToString((Date) params.get("start_time")));
            }

            ApiClient.getThrottledInstance().patchRequest("/meetings/"+id, params);
        } catch (InterruptedException | InvalidRequestException | InvalidArgumentException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }

    public void delete(String id) throws InvalidComponentException {
        try {
            Validator.validateString("id", id);
            ApiClient.getThrottledInstance().deleteRequest("/meetings/"+id);
        } catch (InterruptedException | InvalidRequestException | InvalidArgumentException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }
}
