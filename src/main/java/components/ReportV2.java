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
    public HttpResponse getUserReport(List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(params, Arrays.asList("user_id", "start_time", "end_time"));

        if(params.get(1).equals("start_time")) {
            params.set(1, (NameValuePair) DateToString.dateToString(params.get(1)));
        }
        if(params.get(2).equals("end_time")) {
            params.set(2, (NameValuePair) DateToString.dateToString(params.get(2)));
        }
        return ApiClient.getInstance().getRequest("report/users/"+params+"/meetings", params);
    }

    public HttpResponse getAccountReport(List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        RequireKeys.requireKeys(params, Arrays.asList("start_time", "end_time"));

        if(params.get(0).equals("start_time")) {
            params.set(0, (NameValuePair) DateToString.dateToString(params.get(0)));
        }
        if(params.get(1).equals("end_time")) {
            params.set(1, (NameValuePair) DateToString.dateToString(params.get(1)));
        }
        return ApiClient.getInstance().getRequest("report/users", params);
    }
}