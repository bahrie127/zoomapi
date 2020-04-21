package components;

import Util.RequireKeys;
import api.ApiClient;
import org.apache.http.NameValuePair;
import Util.DateToString;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class ReportV2 {
    // FIXME: NOT SURE WHAT TO DO WITH TO AND FROM
    public HttpResponse getUserReport(List<String> userValues, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(userValues, Arrays.asList("user_id", "start_time", "end_time"));
        String from = (String) DateToString.dateToString(userValues.get(1));
        String to = (String) DateToString.dateToString(userValues.get(2));
        return ApiClient.getInstance().getRequest("report/users/"+userValues.get(0)+"/meetings", params);
    }

    // FIXME: NOT SURE WHAT TO DO WITH TO AND FROM
    public HttpResponse getAccountReport(List<String> userValues, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(userValues, Arrays.asList("start_time", "end_time"));
        String from = (String) DateToString.dateToString(userValues.get(1));
        String to = (String) DateToString.dateToString(userValues.get(2));
        return ApiClient.getInstance().getRequest("report/users", params);
    }
}