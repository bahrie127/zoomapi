package components;

import api.ApiClient;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.InvalidArgumentException;
import exceptions.InvalidComponentException;
import exceptions.InvalidRequestException;
import models.Webinar;
import models.WebinarCollection;
import models.WebinarRegistrant;
import util.Validator;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class WebinarComponent {

    private Gson gson;

    public WebinarComponent() {
        this.gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();
    }

    public WebinarCollection list(String userId, Map<String, Object> params) throws InvalidComponentException {
        try {
            Validator.validateString("userId", userId);
            HttpResponse response = ApiClient.getThrottledInstance().getRequest("/users/"+userId+"/webinars", params);

            return gson.fromJson(response.body().toString(), WebinarCollection.class);
        } catch (InterruptedException | InvalidRequestException | InvalidArgumentException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }

    public Webinar create(String userId, Map<String, Object> params, Map<String, Object> data) throws InvalidComponentException {
        try {
            Validator.validateString("userId", userId);
            HttpResponse response = ApiClient.getThrottledInstance().postRequest("/users/"+userId+"/webinars", params, data);

            return gson.fromJson(response.body().toString(), Webinar.class);
        } catch (InterruptedException | InvalidRequestException | InvalidArgumentException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }

    public void update(String id, Map<String, Object> data) throws InvalidComponentException {
        try {
            Validator.validateString("id", id);
            ApiClient.getThrottledInstance().patchRequest("/webinars/"+id, data);
        } catch (InterruptedException | InvalidRequestException | InvalidArgumentException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }

    public void delete(String id) throws InvalidComponentException {
        try {
            Validator.validateString("id", id);
            ApiClient.getThrottledInstance().deleteRequest("/webinars/"+id);
        } catch (InterruptedException | InvalidRequestException | InvalidArgumentException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }

    public void end(String id) throws InvalidComponentException {
        try {
            Validator.validateString("id", id);

            Map<String, Object> params = new HashMap<>();
            params.put("status", "end");

            ApiClient.getThrottledInstance().putRequest("/webinars/" + id + "/status", null);
        } catch (InterruptedException | InvalidRequestException | InvalidArgumentException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }

    public Webinar get(String id, Map<String, Object> params) throws InvalidComponentException {
        try {
            Validator.validateString("id", id);
            HttpResponse response = ApiClient.getThrottledInstance().getRequest("/webinars/"+ id, params);

            return gson.fromJson(response.body().toString(), Webinar.class);
        } catch (InterruptedException | InvalidRequestException | InvalidArgumentException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }

    public WebinarRegistrant register(String id, String email, String firstName, String lastName, Map<String, Object> params, Map<String, Object> data) throws InvalidComponentException {
        try {
            Validator.validateString("id",id);
            Validator.validateString("email",email);
            Validator.validateString("firstName",firstName);
            Validator.validateString("lastName",lastName);

            HttpResponse response = ApiClient.getThrottledInstance().postRequest("/webinars/"+id+"/registrants", params, data);

            return gson.fromJson(response.body().toString(), WebinarRegistrant.class);
        } catch (InterruptedException | InvalidRequestException | InvalidArgumentException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }
}