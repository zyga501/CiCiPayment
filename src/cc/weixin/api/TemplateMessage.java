package cc.weixin.api;

import QimCommon.utils.HttpClient;
import net.sf.json.JSONObject;

public class TemplateMessage extends HttpClient {
    private static final String SEND_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";

    public TemplateMessage(String accessToken) {
        accessToken_ = accessToken;
    }

    @Override
    public boolean postRequest(String postData) throws Exception {
        postData_ = postData;
        return super.postRequest(postData);
    }

    @Override
    protected String getAPIUri() {
        return String.format(SEND_MESSAGE, accessToken_);
    }

    @Override
    protected boolean parseResponse(String responseString) throws Exception {
        JSONObject jsonParse = JSONObject.fromObject(responseString);
        if (jsonParse.get("errcode") != null) {
            String errorCode = jsonParse.get("errcode").toString();
            switch (errorCode) {
                case "0": {
                    return true;
                }
                case "40001": {
                    AccessToken accessToken = AccessToken.getAccessTokenByAccessToken(accessToken_);
                    if (accessToken == null) {
                        return false;
                    }

                    accessToken_ = AccessToken.updateAccessToken(accessToken, accessToken_);
                    return postRequest(postData_);
                }
                default: {
                    return false;
                }
            }
        }
        return super.parseResponse(responseString);
    }

    private String accessToken_;
    protected String postData_;
}