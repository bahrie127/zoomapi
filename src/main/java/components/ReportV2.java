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

    public HttpResponse getUserReport(List<String> userValues, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getInstance().getRequest("report/users/"+userValues.get(0)+"/meetings", params);
    }

    public HttpResponse getAccountReport(List<String> userValues, List<NameValuePair> params) throws InterruptedException, IOException, URISyntaxException {
        return ApiClient.getInstance().getRequest("report/users", params);
    }
}