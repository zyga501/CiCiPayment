package cc.alipay.api;

import cc.alipay.api.RequestBean.RequestData;

import java.net.URLEncoder;

public class UserID extends AliPayAPI {
    public final static String USERID_API = "https://openapi.alipay.com/gateway.do?timestamp=%s" +
            "&method=alipay.system.oauth.token" +
            "&app_id=%s&sign_type=RSA" +
            "&sign=%s&version=1.0&grant_type=authorization_code" +
            "&code=%s";

    public UserID(String appid, String code, String privateKey) {
        appid_ = appid;
        code_ = code;
        privateKey_ = privateKey;
    }

    @Override
    protected String getAPIUri() {
        try {
            RequestData requestData = new RequestData();
            requestData.app_id = appid_;
            requestData.method = "alipay.system.oauth.token";
            requestData.buildSign(privateKey_);
            return String.format(USERID_API, URLEncoder.encode(requestData.timestamp, "utf-8"), appid_, URLEncoder.encode(requestData.sign, "utf-8"), code_);
        }
        catch (Exception exception) {

        }

        return "";
    }

    @Override
    protected boolean parseResponse(String responseString) throws Exception {
        return false;
    }

    String appid_;
    String code_;
    String privateKey_;
}