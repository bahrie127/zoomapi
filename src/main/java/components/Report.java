package components;

import com.google.gson.JsonObject;
import org.apache.http.NameValuePair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class Report {
    // FIXME: NOT SURE WHAT TO DO WITH TO AND FROM
    public JsonObject getUserReport(List<String> userValues, Map<String, Object> params) throws InterruptedException, IOException, URISyntaxException {
        /*RequireKeys.requireKeys(userValues, Arrays.asList("user_id", "start_time", "end_time"));
        String from = (String) DateUtil.dateToString(userValues.get(1));
        String to = (String) DateUtil.dateToString(userValues.get(2));
        return ApiClient.getThrottledInstance().getRequest("report/users/"+userValues.get(0)+"/meetings", params);*/
        return null;
    }

    // FIXME: NOT SURE WHAT TO DO WITH TO AND FROM
    public JsonObject getAccountReport(List<String> userValues, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        /*RequireKeys.requireKeys(userValues, Arrays.asList("start_time", "end_time"));
        String from = (String) DateUtil.dateToString(userValues.get(1));
        String to = (String) DateUtil.dateToString(userValues.get(2));
        return ApiClient.getThrottledInstance().getRequest("report/users", params);*/
        return null;
    }
}