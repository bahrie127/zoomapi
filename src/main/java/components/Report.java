package components;

import api.ApiClient;
import com.google.gson.JsonObject;
import exceptions.InvalidArgumentException;
import util.DateUtil;
import util.Validator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;

public class Report {

    public JsonObject getUserReport(String userId, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException, InvalidArgumentException {
        Validator.validateString("userId", userId);

        if (params.containsKey("start")) {
            params.put("from", DateUtil.dateToString((Date) params.get("start")));
            params.remove("start");
        }

        if (params.containsKey("end")) {
            params.put("to", DateUtil.dateToString((Date) params.get("end")));
            params.remove("end");
        }

        return ApiClient.getThrottledInstance().getRequest("report/users/"+userId+"/meetings", params);
    }

    public JsonObject getAccountReport(Map<String, Object>params) throws InterruptedException, IOException, URISyntaxException {

        if (params.containsKey("start")) {
            params.put("from", DateUtil.dateToString((Date) params.get("start")));
            params.remove("start");
        }

        if (params.containsKey("end")) {
            params.put("to", DateUtil.dateToString((Date) params.get("end")));
            params.remove("end");
        }

        return ApiClient.getThrottledInstance().getRequest("report/users", params);
    }
}