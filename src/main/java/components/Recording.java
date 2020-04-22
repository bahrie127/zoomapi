package components;

import exceptions.InvalidArgumentException;
import api.ApiClient;
import com.google.gson.JsonObject;
import util.DateUtil;
import util.Validator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;

public class Recording {

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

        return ApiClient.getThrottledInstance().getRequest("/users/" + userId + "/recordings", params);
    }

    public JsonObject get(String meetingId, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("meetingId", meetingId);
        return ApiClient.getThrottledInstance().getRequest("/meeting/" + meetingId + "/recordings", params);
    }

    public JsonObject delete(String meetingId, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("meetingId", meetingId);
        return ApiClient.getThrottledInstance().deleteRequest("/meeting/" + meetingId + "/recordings");
    }
}
