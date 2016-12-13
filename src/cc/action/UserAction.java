package cc.action;

import QimCommon.utils.StringUtils;
import cc.ProjectLogger;
import cc.ProjectSettings;
import cc.database.merchant.*;
import cc.database.order.ChanOrderInfo;
import cc.database.order.PayOrderInfo;
import cc.message.WeixinMessage;
import cc.weixin.api.OpenId;
import QimCommon.struts.AjaxActionSupport;
import net.sf.json.JSONArray;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserAction extends AjaxActionSupport {

    public void fetchWxCode()  {
        try {
            String appid = ProjectSettings.getMapData("weixinServerInfo").get("appid").toString();
            String redirect_uri = getRequest().getScheme() + "://" + getRequest().getServerName() + getRequest().getContextPath() + "/User!fetchWxOpenid";
            String fetchOpenidUri = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +
                            "%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=%s#wechat_redirect",
                    appid, redirect_uri, getParameter("redirect_url").toString());
            getResponse().sendRedirect(fetchOpenidUri);
        }
        catch (Exception e){
            ProjectLogger.error("fetchwxcode:"+e.getMessage());
        }
    }

    public void fetchWxOpenid()  {
        try {
            String appid = ProjectSettings.getMapData("weixinServerInfo").get("appid").toString();
            String appsecret = ProjectSettings.getMapData("weixinServerInfo").get("appSecret").toString();
            OpenId weixinOpenId = new OpenId(appid, appsecret, getParameter("code").toString());
            if (weixinOpenId.getRequest()) {
                getResponse().sendRedirect(getParameter("state").toString() + "&openid=" + weixinOpenId.getOpenId());
            }
        }
        catch (Exception e){
            e.printStackTrace();
            StringBuffer sb = new StringBuffer();
            StackTraceElement[] stackArray = e.getStackTrace();
            for (int i = 0; i < stackArray.length; i++) {
                StackTraceElement element = stackArray[i];
                sb.append(element.toString() + "\n");
            }
            ProjectLogger.error("fetchWxOpenid:"+sb);
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

    public String  Logout(){
        getRequest().getSession().invalidate();
        return "loginjsp";
    }

    public String login() {
        List<UserInfo> lu= UserInfo.getUserInfoByMap(getParameter("username").toString(),getParameter("password").toString(),null,null);
        if (lu.size()!=1)
            return AjaxActionComplete(false);
        setAttribute("roletype",lu.get(0).getRoletype());
        setAttribute("userid",lu.get(0).getId());
        setAttribute("unick",getParameter("username").toString());
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
        List<UserInfo> lu =  UserInfo.getUserInfoByMap(null,null,"0",null);
        setAttribute("userList",lu);
        return "makeCard";
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

    public String showMyQcode(){
        try {
            if (getAttribute("openid").equals("") && (StringUtils.convertNullableString(getParameter("openid")).length() == 0)) {
                setParameter("redirect_url", "User!showMyQcode");
                return "fetchWxCode";
            }
            if (getAttribute("openid").equals(""))
                getRequest().getSession().setAttribute("openid", getParameter("openid").toString());
            Map map = new HashMap<>();
            map.put("openid", getParameter("openid").toString());
            List<MerchantInfo> lm = MerchantInfo.getMerchantInfoByMap(map);
            if (lm.size() != 1) {
                return "page404";
            }
            CardInfo cardInfo = CardInfo.getCardInfoById(lm.get(0).getId());
            setAttribute("qcode", cardInfo.getSaltcode());
            setAttribute("merchantname", lm.get(0).getName());
            setAttribute("status", lm.get(0).getPaymentStatus() ? "开通" : "暂停");
        }
        catch (Exception e){  e.printStackTrace();
            StringBuffer sb = new StringBuffer();
            StackTraceElement[] stackArray = e.getStackTrace();
            for (int i = 0; i < stackArray.length; i++) {
                StackTraceElement element = stackArray[i];
                sb.append(element.toString() + "\n");
            }
            ProjectLogger.info("showMyQcode:"+sb);
            return "page404";
        }
        return "mpindex";
    }
    public String showMyCiCiInfo(){
        try {
            if (getAttribute("openid").equals("") && (StringUtils.convertNullableString(getParameter("openid")).length() == 0)) {
                setParameter("redirect_url", "User!showMyCiCiInfo");
                return "fetchWxCode";
            }
            if (getAttribute("openid").equals(""))
                getRequest().getSession().setAttribute("openid", getParameter("openid").toString());
            Map map = new HashMap<>();
            map.put("openid", getParameter("openid").toString());
            List<MerchantInfo> lm = MerchantInfo.getMerchantInfoByMap(map);
            if (lm.size() != 1) {
                return "page404";
            }
            CardInfo cardInfo = CardInfo.getCardInfoById(lm.get(0).getId());
            setAttribute("qcode", cardInfo.getSaltcode());
            setAttribute("merchantname", lm.get(0).getName());
            setAttribute("status", lm.get(0).getPaymentStatus() ? "开通" : "暂停");
            AgentInfo agentInfo = AgentInfo.getAgentInfoById(cardInfo.getAgentid());
            setAttribute("agentinfo", agentInfo.getName()+"("+agentInfo.getcontactPhone()+")");

            Map paramap = new HashMap<>();
            SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
            String ctime1 = formatter.format(new Date().getTime())+" 00:00:01";
            String ctime2 = formatter.format(new Date().getTime())+" 23:59:59";
            paramap.put("createtime1",ctime1);
            paramap.put("createtime2",ctime2);
            List<Map> lp = PayOrderInfo.getOrderInfoTotalByLimit(paramap);
            Map payinfo = new HashMap<>();
            payinfo.put("wx","---");
            payinfo.put("ali","---");
            payinfo.put("jd","---");
            payinfo.put("best","---");
            payinfo.put("esay","---");
            payinfo.put("bd","---");
            for (int i=0;i<lp.size();i++){
                if (lp.get(i).get("tradetype").equals("WEIXIN"))
                    payinfo.put("wx",lp.get(i).get("tradeamount"));//'WEIXIN','ALI','JD','BEST'
                else if (lp.get(i).get("tradetype").equals("ALI"))
                    payinfo.put("ali",lp.get(i).get("tradeamount"));
                else if (lp.get(i).get("tradetype").equals("JD"))
                    payinfo.put("jd",lp.get(i).get("tradeamount"));
                else if (lp.get(i).get("tradetype").equals("BEST"))
                    payinfo.put("best",lp.get(i).get("tradeamount"));
            }
            setAttribute("payinfo",payinfo);
        }
        catch (Exception e){  e.printStackTrace();
            StringBuffer sb = new StringBuffer();
            StackTraceElement[] stackArray = e.getStackTrace();
            for (int i = 0; i < stackArray.length; i++) {
                StackTraceElement element = stackArray[i];
                sb.append(element.toString() + "\n");
            }
            ProjectLogger.info("showMyQcode:"+sb);
            return "page404";
        }
        return "mpindex";
    }
}