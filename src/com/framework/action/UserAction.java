package com.framework.action;

import com.AjaxActionSupport;
import com.framework.ProjectSettings;
import com.framework.utils.OpenId;
import com.framework.utils.PublicFunc;
import com.opensymphony.xwork2.ActionContext;
import com.qimpay.database.Codeinfo;
import com.qimpay.database.DBmap;
import com.qimpay.database.MerchantInfo;
import com.qimpay.database.UserInfo;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.*;

public class UserAction extends AjaxActionSupport {

    public void wxlogin() throws IOException {
        String  acname =  ActionContext.getContext().getName()+"?"+getRequest().getQueryString();
        getRequest().getSession().setAttribute("acname",acname);
        System.out.println("acname:"+acname);
        String appid = "";
        MerchantInfo merchantInfo = MerchantInfo.getMerchantgzhById((ProjectSettings.getId()));
        if (merchantInfo != null) {
            appid = merchantInfo.getAppid();
        }
        else
            return;
        String redirect_uri =  getRequest().getScheme()+"://" + getRequest().getServerName() + getRequest().getContextPath() + "/wxlogin.jsp";
        String petOpenidUri = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
                        "%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=login#wechat_redirect",
                appid, redirect_uri);
        getResponse().sendRedirect(petOpenidUri);
    }

    public String fetchopenid() throws Exception {
        MerchantInfo merchantInfo = MerchantInfo.getMerchantgzhById((ProjectSettings.getId()));
        String appid =  merchantInfo.getAppid();
        String appsecret =  merchantInfo.getAppsecret();
        OpenId openId = new OpenId(appid, appsecret, getParameter("code").toString());
        if (!openId.getRequest()) {
            return AjaxActionComplete(false) ;
        }
        System.out.println("fopenId="+openId.getOpenId());
        getRequest().getSession().setAttribute("openid", openId.getOpenId());
        Map map = new HashMap<>();
        map.put("url", getAttribute("acname"));
        return AjaxActionComplete(true,map);
    }
}

