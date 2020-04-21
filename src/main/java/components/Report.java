package components;

import Util.DateToString;
import Util.RequireKeys;
import api.ApiClient;
import org.apache.http.NameValuePair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Report {

    public HttpResponse getAccountReport(List<String> userValues, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        data.put("from", DateToString.dateToString(userValues.get(0)));
        data.put("to",DateToString.dateToString(userValues.get(1)));
        return ApiClient.getInstance().postRequest("report/getaccountreport", params, data);
    }

    public HttpResponse getUserReport(List<String> userValues, List<NameValuePair> params, Map<String, Object> data) throws InterruptedException, IOException, URISyntaxException {
        data.put("from", DateToString.dateToString(userValues.get(0)));
        data.put("to",DateToString.dateToString(userValues.get(1)));
        return ApiClient.getInstance().postRequest("report/getuserreport", params, data);
    }
}
