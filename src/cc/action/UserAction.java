package cc.action;

import cc.ProjectSettings;
import cc.database.merchant.CardInfo;
import cc.database.merchant.MenuTree;
import cc.database.merchant.MerchantInfo;
import cc.database.merchant.UserInfo;
import cc.database.order.ChanOrderInfo;
import cc.database.order.PayOrderInfo;
import cc.message.WeixinMessage;
import cc.weixin.api.OpenId;
import QimCommon.struts.AjaxActionSupport;
import QimCommon.utils.IdWorker;
import net.sf.json.JSONArray;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAction extends AjaxActionSupport {

    public void fetchWxCode() throws IOException {
        String appid = ProjectSettings.getMapData("weixinServerInfo").get("appid").toString();
        String redirect_uri =  getRequest().getScheme()+"://" + getRequest().getServerName() + getRequest().getContextPath() + "/User!fetchWxOpenid";
        String fetchOpenidUri = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
                        "%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=%s#wechat_redirect",
                appid, redirect_uri, getParameter("redirect_url").toString());
        getResponse().sendRedirect(fetchOpenidUri);
    }

    public void fetchWxOpenid() throws Exception {
        String appid =  ProjectSettings.getMapData("weixinServerInfo").get("appid").toString();
        String appsecret =  ProjectSettings.getMapData("weixinServerInfo").get("appSecret").toString();
        OpenId weixinOpenId = new OpenId(appid, appsecret, getParameter("code").toString());
        if (weixinOpenId.getRequest()) {
            getResponse().sendRedirect(getParameter("state").toString() + "&openid=" + weixinOpenId.getOpenId());
        }
    }

    public String userRegPage() {
        getRequest().getSession().setAttribute("params", "User!userRegPage");
        if (null == getAttribute("openid") || getAttribute("openid").equals(""))
            return "wxopenid";
        return "agentRegister";
    }

    public String userReg() {
        try {
            getRequest().getSession().setAttribute("params", "User!userReg?username=" + getParameter("username").toString()+
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
        List<MenuTree> menutreelist = MenuTree.getMenuNodeByUid(Long.parseLong(getRequest().getSession().getAttribute("roletype").toString()));
        List<Object> menulist = new ArrayList<>();
        for (MenuTree m : menutreelist) {
            List<MenuTree> prem = MenuTree.getMenuNodeByPreId(m.getId(),Long.parseLong(getRequest().getSession().getAttribute("roletype").toString()));
            Map mapitem = new HashMap();
            mapitem.put("prenode",m);
            mapitem.put("subnode",prem);
            menulist.add(mapitem);
        }
        getRequest().getSession().setAttribute("menulist" ,menulist );
        return "mainpagejsp";
    }

    public String msgPage(){
        if ((!getAttribute("roletype").equals("1"))&&(!getAttribute("roletype").equals("111")))
            return "";
        List<MerchantInfo> lu =  MerchantInfo.getMerchantInfoByMap(null);
        setAttribute("userList",lu);
        return "msgpage";
    }

    public String sendMsg() throws Exception {
        String[] userarrary =  getRequest().getParameterValues("ulist");
        String[] msgstr = getRequest().getParameterValues("msgstr");
        Map map= new HashMap<>();
        map.put("idlist",userarrary);
        List<MerchantInfo> lu =  MerchantInfo.getMerchantInfoByMap(map);
        for (MerchantInfo merchantInfo :  lu){
            WeixinMessage.sendNoticeMessage(ProjectSettings.getMapData("weixinServerInfo").get("appid").toString(),
                    ProjectSettings.getMapData("weixinServerInfo").get("noticetemplate_id").toString(),
                    "",merchantInfo.getOpenid(),msgstr,ProjectSettings.getMapData("weixinServerInfo").get("editer").toString());
        }
        return AjaxActionComplete(true);
    }

    public String wxMsgPage(){
        if ((!getAttribute("roletype").equals("1"))&&(!getAttribute("roletype").equals("111")))
            return "";
        List<MerchantInfo> lu =  MerchantInfo.getMerchantInfoByMap(null);
        setAttribute("userList",lu);
        return "msgpage";
    }

    public String senWxMsg() throws Exception {
        String[] userarrary =  getRequest().getParameterValues("ulist");
        String[] msgstr = getRequest().getParameterValues("msgstr");
        String appid = getParameter("appid").toString();
        String templateid = getParameter("templateid").toString();
        String editer = getParameter("editer").toString();
        Map map= new HashMap<>();
        map.put("idlist",userarrary);
        List<MerchantInfo> lu =  MerchantInfo.getMerchantInfoByMap(map);
        for (MerchantInfo merchantInfo :  lu){
            WeixinMessage.sendNoticeMessage(appid,templateid,"",merchantInfo.getOpenid(),msgstr,editer);
        }
        return AjaxActionComplete(true);
    }

    public String makeCardPage(){
        if ((!getAttribute("roletype").equals("1"))&&(!getAttribute("roletype").equals("111")))
            return "";
        Map map = new HashMap<>();
        map.put("roletype",0);
        List<UserInfo> lu =  UserInfo.getUserInfoByMap(map);
        setAttribute("userList",lu);
        return "makeCard";
    }

    public String sendMakeCardMsg(){
        IdWorker worker2 = new IdWorker(2);
        Map map = new HashMap<>();
        String rtnstr="";
        for (int i=0;i<Integer.parseInt(getParameter("cardnum").toString());i++) {
            long before = worker2.nextId();
            map.clear();
            map.put("id",before);
            map.put("agentid",getParameter("agentid"));
            if (CardInfo.insertCardInfo(map)) {
                long after = (before * 100 + System.currentTimeMillis() % 100) ^ 1361753741828L;
                rtnstr += String.valueOf(after) + "<br>";
            }
        }
        map.clear();
        map.put("idslist",rtnstr);
        return AjaxActionComplete(true,map);
    }

    public void fetchPayorderList() {
        try {
            int pageSize = Math.max(1,Integer.parseInt(getParameter("pageSize").toString()));
            int pageNumber = Math.max(1,Integer.parseInt(getParameter("pageNumber").toString()));
            Map map = new HashMap<>();
            map.put("ps",(pageNumber-1)*pageSize);
            map.put("pe",(pageNumber)*pageSize);
            if (null!=getParameter("name")&&!getParameter("name").toString().equals(""))
                map.put("name",getParameter("name").toString());
            List<Map> payOrderInfo = PayOrderInfo.getOrderInfoByLimit(map);
            getResponse().setCharacterEncoding("utf-8");
            PrintWriter pw = getResponse().getWriter();
            String jsont= JSONArray.fromObject(payOrderInfo).toString();
            String jsons = "{\"total\":" + PayOrderInfo.getOrderTotal() + ",\"rows\":" + jsont + "}";
            pw.println(jsons);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void fetchChanorderList() {
        try {
            int pageSize = Math.max(1,Integer.parseInt(getParameter("pageSize").toString()));
            int pageNumber = Math.max(1,Integer.parseInt(getParameter("pageNumber").toString()));
            Map map = new HashMap<>();
            map.put("ps",(pageNumber-1)*pageSize);
            map.put("pe",(pageNumber)*pageSize);
            if (null!=getParameter("name")&&!getParameter("name").toString().equals(""))
                map.put("name",getParameter("name").toString());
            List<Map> payOrderInfo = ChanOrderInfo.getOrderInfoByLimit(map);
            getResponse().setCharacterEncoding("utf-8");
            PrintWriter pw = getResponse().getWriter();
            String jsont= JSONArray.fromObject(payOrderInfo).toString();
            String jsons = "{\"total\":" + ChanOrderInfo.getOrderTotal() + ",\"rows\":" + jsont + "}";
            pw.println(jsons);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}