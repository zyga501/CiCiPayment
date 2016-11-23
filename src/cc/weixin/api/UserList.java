package cc.weixin.api;

import QimCommon.utils.HttpClient;

public class UserList extends HttpClient {
    private static final String USERLIST_API = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=%s&next_openid=%s";

    public UserList(String accessToken) {
        accessToken_ = accessToken;
    }

    @Override
    public boolean postRequest(String postData) throws Exception {
        postData_ = postData;
        return super.postRequest(postData);
    }

    @Override
    protected String getAPIUri() {
        return String.format(USERLIST_API, accessToken_);
    }

    @Override
    protected boolean parseResponse(String responseString) throws Exception {
        return false;
    }

    private String accessToken_;
    protected String postData_;
}
