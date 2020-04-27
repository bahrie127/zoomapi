package components;

import api.ApiClient;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import exceptions.InvalidArgumentException;
import exceptions.InvalidComponentException;
import exceptions.InvalidRequestException;
import models.User;
import models.UserPage;
import util.Validator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.Map;

public class UserComponent {

    private Gson gson;

    public UserComponent() {
        this.gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();
    }

    public UserPage listUsers(Map<String, Object> params) throws InvalidComponentException {
        try {
            HttpResponse response = ApiClient.getThrottledInstance().getRequest("/user/list", params);

            return gson.fromJson(response.body().toString(), UserPage.class);
        } catch (InterruptedException | InvalidRequestException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }

    public User createUser(Map<String, Object> params, Map<String, Object> data) throws InvalidComponentException {
        try {
            HttpResponse response = ApiClient.getThrottledInstance().postRequest("/users", params, data);

            return gson.fromJson(response.body().toString(), User.class);
        } catch (InterruptedException | InvalidRequestException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }

    public void updateUser(String id,  Map<String, Object> data) throws InvalidComponentException {
        try {
            Validator.validateString("id", id);
            ApiClient.getThrottledInstance().patchRequest("/users/"+id, data);
        } catch (InterruptedException | InvalidRequestException | InvalidArgumentException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }

    public void deleteUser(String id) throws InvalidComponentException {
        try {
            Validator.validateString("id", id);
            ApiClient.getThrottledInstance().deleteRequest("/users/"+id);
        } catch (InterruptedException | InvalidRequestException | InvalidArgumentException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }

    public User get(String id, Map<String, Object> params) throws InvalidComponentException {
        try {
            Validator.validateString("id", id);
            HttpResponse response = ApiClient.getThrottledInstance().getRequest("/users/"+id, params);

            return gson.fromJson(response.body().toString(), User.class);
        } catch (InterruptedException | InvalidRequestException | InvalidArgumentException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }
}
