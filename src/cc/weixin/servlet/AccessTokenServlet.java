package cc.weixin.servlet;

import cc.ProjectSettings;
import cc.weixin.api.AccessToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.List;

public class AccessTokenServlet extends HttpServlet {
    private class AccessTokenThread implements Runnable {
        public void run() {
            while (true) {
                try {
                    List<AccessToken> appidList = new ArrayList<>();
                    appidList.add(new AccessToken(ProjectSettings.getMapData("weixinServerInfo").get("appid").toString(), ProjectSettings.getMapData("weixinServerInfo").get("appSecret").toString()));
                    AccessToken.updateAccessToken(appidList);
                    // 休眠7000秒
                    Thread.sleep((DEFAULTEXPIRESTIME - 200) * 1000);
                }
                catch (Exception exception) {
                    try {
                        // 60秒后再获取
                        Thread.sleep(60 * 1000);
                    }
                    catch (InterruptedException e1) {
                    }
                }
            }
        }

        private final static int DEFAULTEXPIRESTIME = 7200;
    }

    public void init() throws ServletException {
        new Thread(new AccessTokenThread()).start();
    }
}
