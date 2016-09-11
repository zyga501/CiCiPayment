package com.framework.action;

import com.AjaxActionSupport;
import com.framework.ProjectSettings;
import com.framework.utils.OpenId;
import com.framework.utils.PublicFunc;
import com.qimpay.database.Codeinfo;
import com.qimpay.database.DBmap;
import com.qimpay.database.UserInfo;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.*;

public class UserAction extends AjaxActionSupport {
//
//    public void wxlogin() throws IOException {
//        String appid = "";
//        MerchantInfo merchantInfo = MerchantInfo.getMerchantInfoById((ProjectSettings.getId()));
//        if (merchantInfo != null) {
//            appid = merchantInfo.getAppid();
//        }
//        else
//            return;
//        String redirect_uri =  getRequest().getScheme()+"://" + getRequest().getServerName() + getRequest().getContextPath() + "/weixin/wxlogin.jsp";
//        String petOpenidUri = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
//                        "%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=login#wechat_redirect",
//                appid, redirect_uri);
//        getResponse().sendRedirect(petOpenidUri);
//    }
//
//    public String fetchopenid() throws Exception {
//        MerchantInfo merchantInfo = MerchantInfo.getMerchantInfoById((ProjectSettings.getId()));
//        String appid =  merchantInfo.getAppid();
//        String appsecret =  merchantInfo.getAppsecret();
//        OpenId openId = new OpenId(appid, appsecret, getParameter("code").toString());
//        if (!openId.getRequest()) {
//            return AjaxActionComplete(false) ;
//        }
//        System.out.println("fopenId="+openId.getOpenId());
//        UserInfo userInfo = UserInfo.getUserInfoByOpenid(openId.getOpenId());
//        if (null != userInfo) {
//            if (userInfo.getActive()!=1){
//                return AjaxActionComplete(false) ;
//            }
//            getRequest().getSession().setAttribute("uname", userInfo.getUname());
//            getRequest().getSession().setAttribute("unick", userInfo.getUnick());
//            getRequest().getSession().setAttribute("uid", userInfo.getId());
//            getRequest().getSession().setAttribute("roleval", userInfo.getRole());
//            getRequest().getSession().setAttribute("role", userInfo.getRole() == 999 ? "管理员" : userInfo.getRole() == 1 ? "机构" : userInfo.getRole() == 2 ? "销售" : userInfo.getRole() == 3 ? "职员" : "未知");
//
//            Map<String, String> resultMap = new HashMap<>();
//            resultMap.put("URL","../Khzlgl!OAkhzlgl");
//            return AjaxActionComplete(true,resultMap);
//        } else {
//            return AjaxActionComplete(false) ;
//        }
//    }
}

