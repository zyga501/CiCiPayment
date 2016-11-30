package cc.weixin.api;

import cc.ProjectSettings;
import QimCommon.utils.HttpClient;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccessToken extends HttpClient {
    private final static String ACCESS_TOKEN_API = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    private static  Map<String, String> accessTokenMap_ = new HashMap<>();

    public  static String getAccessTokenByAccessToken(String accessToken) {
        synchronized(accessTokenMap_) {
            if (accessTokenMap_.containsValue(accessToken)) {
                for (Map.Entry<String, String> entry : accessTokenMap_.entrySet()) {
                    if (entry.getValue().compareTo(accessToken) == 0) {
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
                if (accessTokenMap_.get(appid).toString().equals(""))
                    updateAccessToken(appid);
                return accessTokenMap_.get(appid);
            }

            throw new NoSuchFieldException("Unknown Appid!");
        }
    }

    public static void updateAccessToken(String appid) throws Exception {
        synchronized(accessTokenMap_) {
            String appsecret = new String();
            appsecret = appSecret_;

            if (!appsecret.isEmpty()) {
                AccessToken accessToken = new AccessToken();
                accessToken.AccessTokenInit(appid, appsecret);
                if (accessToken.getRequest()) {
                    accessTokenMap_.put(appid,accessToken_);
                }
            }
        }
    }

    public  void updateAccessToken(List<String> accessTokenList) throws Exception {
        synchronized(accessTokenMap_) {
            for (int index = 0; index < accessTokenList.size(); ++index) {
                updateAccessToken(accessTokenList.get(index));
            }
        }
    }

    public static void AccessTokenInit(String appid, String appSecret) {
        appid_ = appid;
        appSecret_ = appSecret;
        if (accessTokenMap_.isEmpty())
          accessTokenMap_.put(appid,"");
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

    private static String appid_;
    private static String appSecret_;
    private static String accessToken_;
}
