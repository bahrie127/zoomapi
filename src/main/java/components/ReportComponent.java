package components;

import api.ApiClient;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import exceptions.InvalidArgumentException;
import exceptions.InvalidComponentException;
import exceptions.InvalidRequestException;
import models.AccountReportCollection;
import models.MeetingReportCollection;
import util.DateUtil;
import util.Validator;


import java.net.http.HttpResponse;
import java.util.Date;
import java.util.Map;

public class ReportComponent {

    private Gson gson;

    public ReportComponent() {
        this.gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();
    }

    public MeetingReportCollection getUserReport(String userId, Map<String, Object> params) throws InvalidComponentException {
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

            HttpResponse response = ApiClient.getThrottledInstance().getRequest("report/users/" + userId + "/meetings", params);

            return gson.fromJson(response.body().toString(), MeetingReportCollection.class);
        } catch (InterruptedException | InvalidRequestException | InvalidArgumentException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }

    public AccountReportCollection getAccountReport(Map<String, Object>params) throws InvalidComponentException {
        try {
            if (params.containsKey("start")) {
                params.put("from", DateUtil.dateToString((Date) params.get("start")));
                params.remove("start");
            }

            if (params.containsKey("end")) {
                params.put("to", DateUtil.dateToString((Date) params.get("end")));
                params.remove("end");
            }

            HttpResponse response = ApiClient.getThrottledInstance().getRequest("report/users", params);

            return gson.fromJson(response.body().toString(), AccountReportCollection.class);
        } catch (InterruptedException | InvalidRequestException exception) {
            throw new InvalidComponentException(exception.getMessage());
        }
    }
}