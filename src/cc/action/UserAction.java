package cc.action;

import cc.ProjectSettings;
import cc.database.merchant.CodeInfo;
import cc.database.merchant.MerchantInfo;
import cc.database.merchant.UserInfo;
import cc.message.WeixinMessage;
import cc.swiftpass.api.WeixinOpenId;
import com.opensymphony.xwork2.ActionContext;
import framework.action.AjaxActionSupport;
import framework.utils.IdWorker;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAction extends AjaxActionSupport {

    public void wxlogin() throws IOException {
        String appid = ProjectSettings.getMapData("weixinServerInfo").get("appid").toString();
        String redirect_uri =  getRequest().getScheme()+"://" + getRequest().getServerName() + getRequest().getContextPath() + "/wxlogin.jsp";
        String petOpenidUri = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
                        "%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=login#wechat_redirect",
                appid, redirect_uri);
        System.out.println("petOpenidUri"+petOpenidUri);
        getResponse().sendRedirect(petOpenidUri);
    }

    public String fetchopenid() throws Exception {
        String appid =  ProjectSettings.getMapData("weixinServerInfo").get("appid").toString();
        String appsecret =  ProjectSettings.getMapData("weixinServerInfo").get("appSecret").toString();
        WeixinOpenId weixinOpenId = new WeixinOpenId(appid, appsecret, getParameter("code").toString());
        if (!weixinOpenId.getRequest()) {
            return AjaxActionComplete(false) ;
        }
        System.out.println("openid"+ weixinOpenId.getOpenId());
        getRequest().getSession().setAttribute("openid", weixinOpenId.getOpenId());
        Map map = new HashMap<>();
        map.put("url", getAttribute("params"));
        return AjaxActionComplete(true,map);
    }

    public String userreg() {
        try {
            getRequest().getSession().setAttribute("params", "User!userreg?username=" + getParameter("username").toString()+
            "&password="+ getParameter("password").toString());
            if (null == getAttribute("openid") || getAttribute("openid").equals(""))
                return "wxopenid";
            Map map = new HashMap<>();
            map.put("openid",getAttribute("openid"));
            map.put("username",getParameter("username"));
            map.put("password",getParameter("password"));
            if (UserInfo.updateUserInfo(map))
                return AjaxActionComplete(true);
            else
                return AjaxActionComplete(false);
        } finally {
            getRequest().getSession().removeAttribute("params");
        }
    }

    public void wxpayaction() throws IOException {
        try {
            String appid = ProjectSettings.getMapData("weixinServerInfo").get("appid").toString();
            String redirect_uri = getRequest().getScheme() + "://" + getRequest().getServerName() + getRequest().getContextPath() + "/swiftpass/index.jsp";
            String ccid = getParameter("cid").toString();
            String petOpenidUri = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
                            "%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=%s#wechat_redirect",
                    appid, redirect_uri, ccid);
            System.out.println("petOpenidUri :" + petOpenidUri);
            getResponse().sendRedirect(petOpenidUri);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public String login() {
        Map map = new HashMap<>();
        map.put("username",getParameter("username"));
        map.put("password",getParameter("password"));
        List<UserInfo> lu= UserInfo.getUserInfoByMap(map);
        if (lu.size()!=1)
            return AjaxActionComplete(false);
        setAttribute("roletype",lu.get(0).getRoletype());
        setAttribute("userid",lu.get(0).getId());
        if (lu.get(0).getRoletype()==0)
            return "agentpage";
        if (lu.get(0).getRoletype()==1)
            return "adminpage";
        return "";
    }

    public String msgpage(){
        if (!getAttribute("roletype").equals("1"))
            return "";
        List<MerchantInfo> lu =  MerchantInfo.getMerchantInfoByMap(null);
        setAttribute("userList",lu);
        return "msgpage";
    }

    public String sendmsg() throws Exception {
        String[] userarrary =  getRequest().getParameterValues("ulist");
        String[] msgstr = getRequest().getParameterValues("msgstr");
        Map map= new HashMap<>();
        map.put("idlist",userarrary);
        List<MerchantInfo> lu =  MerchantInfo.getMerchantInfoByMap(map);
        for (MerchantInfo merchantInfo :  lu){
            WeixinMessage.sendNoticeMessage(merchantInfo.getOpenid(),msgstr);
        }
        return AjaxActionComplete(true);
    }

    public String makecard(){
        if (!getAttribute("roletype").equals("1"))
            return "";
        Map map = new HashMap<>();
        map.put("roletype",0);
        List<UserInfo> lu =  UserInfo.getUserInfoByMap(map);
        setAttribute("userList",lu);
        return "makecard";
    }

    public String sendmakecardmsg(){
        IdWorker worker2 = new IdWorker(2);
        Map map = new HashMap<>();
        String rtnstr="";
        for (int i=0;i<Integer.parseInt(getParameter("cardnum").toString());i++) {
            long before = worker2.nextId();
            map.clear();
            map.put("id",before);
            map.put("agentid",getParameter("agentid"));
            if (CodeInfo.insertCodeInfo(map)) {
                long after = (before * 100 + System.currentTimeMillis() % 100) ^ 1361753741828L;
                rtnstr += String.valueOf(after) + "\r\n";
            }
        }
        map.clear();
        map.put("idslist",rtnstr);
        return AjaxActionComplete(true);
    }
}

