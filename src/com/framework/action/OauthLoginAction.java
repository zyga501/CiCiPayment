package com.framework.action;

import com.AjaxActionSupport;
import com.framework.ProjectSettings;
import com.framework.utils.OpenId;
import com.qimpay.database.UserInfo;
import net.sf.json.JSONObject;

import java.io.IOException;

public class OauthLoginAction extends AjaxActionSupport {

    public void oauthWX() throws IOException {
//        MerchantInfo merchantInfo = MerchantInfo.getMerchantInfoById(ProjectSettings.getId());
//        String appid = merchantInfo.getAppid();
//        String appsecret = merchantInfo.getAppsecret();
//        String dt = getParameter("dt").toString();
//        String mid = getParameter("id").toString();
//        getRequest().getSession().setAttribute("datajson", "{'dt':'" + dt + "','mid':'" + mid + "'}");
//        String redirect_uri = getRequest().getScheme() + "://" + getRequest().getServerName() + getRequest().getContextPath() + "/weixin/rtopenid.jsp";
//        String petOpenidUri = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
//                        "%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect",
//                appid, redirect_uri);
//        getResponse().sendRedirect(petOpenidUri);
    }

}
