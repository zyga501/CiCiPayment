package cc.servlet;

import cc.ProjectSettings;
import cc.message.api.AccessToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.List;

public class AccessTokenServlet extends HttpServlet {
    private class AccessTokenThread implements Runnable {
        public void run() {
            while (true) {
                try {
                    List<String> appidList = new ArrayList<>();
                    appidList.add(ProjectSettings.getMapData("weixinServerInfo").get("appid").toString());
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
