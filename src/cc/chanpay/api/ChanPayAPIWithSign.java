package cc.chanpay.api;

import framework.utils.HttpUtils;
import framework.utils.StringUtils;
import framework.utils.XMLParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import cc.ProjectLogger;
import cc.chanpay.api.RequestBean.RequestData;

import java.util.Map;

public class ChanPayAPIWithSign extends ChanPayAPI {
    public boolean postRequest() throws Exception {
        if (!requestData_.checkParameter()) {
            ProjectLogger.error(this.getClass().getName() + " CheckParameter Failed!");
            return false;
        }

        String apiUri = getAPIUri();
        if (apiUri.isEmpty()) {
            return false;
        }

        HttpPost httpPost = new HttpPost(apiUri);
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(new StringEntity(requestData_.generateRequestData(), "UTF-8"));

        String responseString = new String();
        try {
            responseString = HttpUtils.PostRequest(httpPost, (HttpResponse httpResponse) -> {
                return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
            });
        }
        finally {
            httpPost.abort();
        }

        boolean ret = parseResponse(responseString) && handlerResponse(responseResult_);

        if (!ret) {
            ProjectLogger.error("Request Url:\r\n" + apiUri);
            ProjectLogger.error("Response Data:\r\n" + responseString);
        }

        return ret;
    }

    protected boolean parseResponse(String responseString) throws Exception {
        responseResult_ = XMLParser.convertMapFromXml(responseString);
        return StringUtils.convertNullableString(responseResult_.get("success")).compareTo("true") == 0;
    }

    protected boolean handlerResponse(Map<String, Object> responseResult) throws Exception {
        return true;
    }

    protected RequestData requestData_;
    private Map<String, Object> responseResult_;
}
