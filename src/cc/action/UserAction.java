package cc.action;

import cc.ProjectSettings;
import cc.database.merchant.MerchantInfo;
import cc.swiftpass.api.WeixinOpenId;
import com.opensymphony.xwork2.ActionContext;
import framework.action.AjaxActionSupport;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        WeixinOpenId weixinOpenId = new WeixinOpenId(appid, appsecret, getParameter("code").toString());
        if (!weixinOpenId.getRequest()) {
            return AjaxActionComplete(false) ;
        }
        System.out.println("fopenId="+ weixinOpenId.getOpenId());
        getRequest().getSession().setAttribute("openid", weixinOpenId.getOpenId());
        Map map = new HashMap<>();
        map.put("url", getAttribute("acname"));
        return AjaxActionComplete(true,map);
    }
}

