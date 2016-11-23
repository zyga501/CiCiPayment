package cc.weixin.api;

import cc.ProjectSettings;
import QimCommon.utils.HttpClient;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccessToken extends HttpClient {
    private final static String ACCESS_TOKEN_API = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    private static Map<String, AccessToken> accessTokenMap_ = new HashMap<>();

    public static AccessToken getAccessTokenByAccessToken(String accessToken) {
        synchronized(accessTokenMap_) {
            if (accessTokenMap_.containsValue(accessToken)) {
                for (Map.Entry<String, AccessToken> entry : accessTokenMap_.entrySet()) {
                    if (entry.getValue().getAccessToken().compareTo(accessToken) == 0) {
                        return entry.getValue();
                    }
                }
            }

            return null;
        }
    }

    public static String getAccessToken(String appid) throws Exception {
        synchronized(accessTokenMap_) {
            if (accessTokenMap_.get(appid) != null) {
                return accessTokenMap_.get(appid).getAccessToken();
            }

            throw new NoSuchFieldException("Unknown Appid!");
        }
    }

    public static void updateAccessToken(AccessToken accessToken) throws Exception {
        synchronized(accessTokenMap_) {
            if (accessToken.getRequest()) {
                accessTokenMap_.put(accessToken.getAppid(), accessToken);
            }
        }
    }

    public static String updateAccessToken(AccessToken accessToken, String invalidAccessToken) throws Exception {
        synchronized(accessTokenMap_) {
            if (!accessTokenMap_.containsValue(invalidAccessToken)) {
                return getAccessToken(accessToken.getAppid());
            }

            updateAccessToken(accessToken);
            return getAccessToken(accessToken.getAppid());
        }
    }

    public static void updateAccessToken(List<AccessToken> accessTokenList) throws Exception {
        synchronized(accessTokenMap_) {
            for (int index = 0; index < accessTokenList.size(); ++index) {
                updateAccessToken(accessTokenList.get(index));
            }
        }
    }

    public AccessToken(String appid, String appSecret) {
        appid_ = appid;
        appSecret_ = appSecret;
    }

    public String getAccessToken() { return accessToken_; }


    public String getAppid() {
        return appid_;
    }

    public String getAppSecret() {
        return appSecret_;
    }

    @Override
    protected String getAPIUri() {
        return String.format(ACCESS_TOKEN_API, appid_, appSecret_);
    }

    @Override
    protected boolean parseResponse(String responseString) throws Exception {
        JSONObject jsonParse = JSONObject.fromObject(responseString);
        if (jsonParse.get("access_token") != null) {
            accessToken_ = jsonParse.get("access_token").toString();
            return true;
        }
        return false;
    }

    private String appid_;
    private String appSecret_;
    private String accessToken_;
}
