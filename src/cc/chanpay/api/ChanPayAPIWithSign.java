package cc.chanpay.api;

import cc.ProjectLogger;
import cc.chanpay.api.RequestBean.RequestData;
import cc.chanpay.utils.Signature;
import framework.utils.HttpUtils;
import framework.utils.StringUtils;
import framework.utils.XMLParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

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
        String requestBody = requestData_.generateRequestData();
        httpPost.setEntity(new StringEntity(requestBody, "UTF-8"));

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
            ProjectLogger.error("Request Data:\r\n" + requestBody);
            ProjectLogger.error("Response Data:\r\n" + responseString);
        }

        return ret;
    }

    protected boolean parseResponse(String responseString) throws Exception {
        if (Signature.verifySign(responseString)) {
            responseResult_ = XMLParser.convertMapFromXml(responseString);
            if (responseResult_ != null && responseResult_.containsKey("INFO")) {
                Map<String, Object> info = (Map<String, Object>) responseResult_.get("INFO");
                return info != null && StringUtils.convertNullableString(info.get("RET_CODE")).compareTo("0000") == 0;
            }
        }

        return false;
    }

    protected boolean handlerResponse(Map<String, Object> responseResult) throws Exception {
        return true;
    }

    protected RequestData requestData_;
    private Map<String, Object> responseResult_;
}
