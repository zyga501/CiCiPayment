package cc.chanpay.api;

import cc.ProjectSettings;
import QimCommon.utils.HttpClient;

import java.util.Map;

public class ChanPayAPI extends HttpClient {
    @Override
    protected String getAPIUri() {
        try {
            return ((Map<Object, Object>)ProjectSettings.getData("chanPay")).get("CJ_GATEWAY_URL").toString();
        }
        catch (Exception exception) {

        }

        return "";
    }
}
