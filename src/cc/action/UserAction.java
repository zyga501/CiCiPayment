package cc.action;

import QimCommon.utils.StringUtils;
import cc.ProjectLogger;
import cc.ProjectSettings;
import cc.database.merchant.*;
import cc.database.order.PayOrderInfo;
import cc.message.WeixinMessage;
import cc.utils.IdConvert;
import cc.weixin.api.OpenId;
import QimCommon.struts.AjaxActionSupport;
import net.sf.json.JSONArray;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserAction extends AjaxActionSupport {

    private String cid;

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

    public String initPassword(){
        if ((!getAttribute("roletype").equals("0"))&&(!getAttribute("roletype").equals("111")))
            return AjaxActionComplete(false);
        return AjaxActionComplete(UserInfo.initPassWord(Long.parseLong(getParameter("agentid").toString())));
    }

    public String msgPage(){
        if ((!getAttribute("roletype").equals("0"))&&(!getAttribute("roletype").equals("111")))
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
        if ((!getAttribute("roletype").equals("0"))&&(!getAttribute("roletype").equals("111")))
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
        if ((!getAttribute("roletype").equals("0"))&&(!getAttribute("roletype").equals("111")))
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
        if ((!getAttribute("roletype").equals("111")) &&(!getAttribute("roletype").equals("0")))
            return ;
        try {
            int pageSize = Math.max(1,Integer.parseInt(getParameter("pageSize").toString()));
            int pageNumber = Math.max(1,Integer.parseInt(getParameter("pageNumber").toString()));
            Map map = new HashMap<>();
            if (null!=getParameter("fliter")&&!getParameter("fliter").toString().equals(""))
                map.put("fliter",getParameter("fliter").toString());
            List<Map> payOrderInfo = PayOrderInfo.getOrderInfoCrossByLimit(map);
            int total_=payOrderInfo.size();
            map.put("ps",(pageNumber-1)*pageSize);
            map.put("pe",(pageNumber)*pageSize);
            payOrderInfo = PayOrderInfo.getOrderInfoCrossByLimit(map);
            getResponse().setCharacterEncoding("utf-8");
            PrintWriter pw = getResponse().getWriter();
            String jsont= JSONArray.fromObject(payOrderInfo).toString();
            String jsons = "{\"total\":" + total_ + ",\"rows\":" + jsont + "}";
            pw.println(jsons);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String showMyQcode(){
        try {
            //setParameter("openid","oBhD-wj1zMF5-FET_9dwK8rI2nt0");//本地测试用
            if (StringUtils.convertNullableString(getAttribute("openid")).equals("") && (StringUtils.convertNullableString(getParameter("openid")).length() == 0)) {
                setParameter("redirect_url", "User!showMyQcode?abc=1");
                return "fetchWxCode";
            }
            ProjectLogger.error("openid:"+getAttribute("openid"));
            if (StringUtils.convertNullableString(getAttribute("openid")).equals(""))
                getRequest().getSession().setAttribute("openid", getParameter("openid").toString());
            Map map = new HashMap<>();
            map.put("openid", getAttribute("openid").toString());
            List<MerchantInfo> lm = MerchantInfo.getMerchantInfoByMap(map);
            if (lm.size() != 1) {
                return "page404";
            }
            CardInfo cardInfo = CardInfo.getCardInfoById(lm.get(0).getId());
            setCid(cardInfo.getSaltcode());
        }
        catch (Exception e){
            e.printStackTrace();
            return "page404";
        }
        return "shareMyQcode";
    }

    public String shareMyQcode(){
        try {
            if (StringUtils.convertNullableString(getAttribute("openid")).equals("") && (StringUtils.convertNullableString(getParameter("openid")).length() == 0)) {
                setParameter("redirect_url", "User!shareMyQcode?cid="+getParameter("cid").toString());
                return "fetchWxCode";
            }
            MerchantInfo  merchantinfo = MerchantInfo.getMerchantInfoById(IdConvert.DecryptionId(Long.parseLong(getParameter("cid").toString())));
            setAttribute("qcode", getParameter("cid").toString());
            setAttribute("merchantname", merchantinfo.getName());
            setAttribute("status", merchantinfo.getPaymentStatus() ? "开通" : "暂停");
        }
        catch (Exception e){
            e.printStackTrace();
            return "page404";
        }
        return "mpindex";
    }

    public String showMyCiCiInfo(){
//        setParameter("openid","oBhD-wj1zMF5-FET_9dwK8rI2nt0");
        try {
            if (StringUtils.convertNullableString(getAttribute("openid")).equals("") && (StringUtils.convertNullableString(getParameter("openid")).length() == 0)) {
                setParameter("redirect_url", "User!showMyCiCiInfo?abc=1");
                return "fetchWxCode";
            }
            if (StringUtils.convertNullableString(getAttribute("openid")).equals(""))
                getRequest().getSession().setAttribute("openid", getParameter("openid").toString());
            Map map = new HashMap<>();
            map.put("openid", getAttribute("openid").toString());
            List<MerchantInfo> lm = MerchantInfo.getMerchantInfoByMap(map);
            if (lm.size() != 1) {
                return "page404";
            }
            CardInfo cardInfo = CardInfo.getCardInfoById(lm.get(0).getId());
            setAttribute("accountno", lm.get(0).getAccountNo());
            setAttribute("bankname", lm.get(0).getBankGeneralName());
            setAttribute("merchantname", lm.get(0).getName());
            AgentInfo agentInfo = AgentInfo.getAgentInfoById(cardInfo.getAgentid());
            setAttribute("agentinfo", agentInfo.getName()+"("+agentInfo.getcontactPhone()+")");

            Map paramap = new HashMap<>();
            SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
            String ctime1 = formatter.format(new Date().getTime())+" 00:00:01";
            String ctime2 = formatter.format(new Date().getTime())+" 23:59:59";
            paramap.put("merchantid",lm.get(0).getId());
            paramap.put("createtime1",ctime1);
            paramap.put("createtime2",ctime2);
            List<PayOrderInfo> lp = PayOrderInfo.getOrderInfoTotalByLimit(paramap);
            Map payinfo = new HashMap<>();
            payinfo.put("wx","---");
            payinfo.put("ali","---");
            payinfo.put("jd","---");
            payinfo.put("best","---");
            payinfo.put("easy","---");
            payinfo.put("bd","---");

            for (int i=0;(i<lp.size()) && (null!=lp.get(i));i++){
                String v = String.valueOf(lp.get(i).getTradeAmount()/100.00);
                if (lp.get(i).getTradeType().equals("WEIXIN"))
                    payinfo.put("wx",v);
                else if (lp.get(i).getTradeType().equals("ALI"))
                    payinfo.put("ali",v);
                else if (lp.get(i).getTradeType().equals("JD"))
                    payinfo.put("jd",v);
                else if (lp.get(i).getTradeType().equals("BEST"))
                    payinfo.put("best",v);
            }
            setAttribute("payinfo",payinfo);
        }
        catch (Exception e){
            return "page404";
        }
        return "mplist";
    }

    public String payinfoList(){
        Map map = new HashMap<>();
        map.put("openid", getAttribute("openid").toString());
        List<MerchantInfo> lm = MerchantInfo.getMerchantInfoByMap(map);
        if (lm.size() != 1) {
            return "page404";
        }
        int tabid = Integer.parseInt(StringUtils.convertNullableString(getParameter("pid"),"0"));
        List<Map> lplimit = null;
        if (getParameter("pagetype").toString().compareTo("first")==0)
            lplimit = PayOrderInfo.getPayAndChanDesc(lm.get(0).getId(),999999999,5);
        if (getParameter("pagetype").toString().compareTo("next")==0)
            lplimit = PayOrderInfo.getPayAndChanDesc(lm.get(0).getId(),tabid,5);
        if (getParameter("pagetype").toString().compareTo("pre")==0)
            lplimit = PayOrderInfo.getPayAndChanAsc(lm.get(0).getId(),tabid,5);
        if (getParameter("pagetype").toString().compareTo("last")==0)
            lplimit = PayOrderInfo.getPayAndChanAsc(lm.get(0).getId(),0,5);
        return AjaxActionComplete(lplimit);
    }

    public String fetchAgent(){
        try {
            Map map = new HashMap<>();
            List<AgentInfo> la =  new ArrayList();
            if ((null != getParameter("agentid")) && (getParameter("agentid").toString().compareTo("")!=0)) {
                AgentInfo agentInfo = AgentInfo.getAgentInfoById(Long.parseLong(getParameter("agentid").toString()));
                la.add(agentInfo);
                map.put("totalcount", agentInfo != null ? 1 : 0);
                map.put("rtlist",la);
                return AjaxActionComplete( map);
            } else if (null != getParameter("agentname")) {
               la = AgentInfo.getAgentInfoByName(getParameter("agentname").toString());
                map.put("totalcount", la.size());
                map.put("rtlist", la);
                return AjaxActionComplete( map);
            }
        }
        catch (Exception e){
            return AjaxActionComplete(false);
        }
        return AjaxActionComplete(false);
    }

    public String selectOneAgent(){
        try {
            Map map = new HashMap<>();
            if ((getParameter("dotype").toString().compareTo("insert")==0)) {
                setAttribute("agentinfo", null);
                setAttribute("dotype","insert");
                return "agentinfo";
            }
            if ((getParameter("dotype").toString().compareTo("select")==0) && (null != getParameter("id"))
                    && (getParameter("id").toString().compareTo("") != 0)) {
                AgentInfo agentInfo = AgentInfo.getAgentInfoById(Long.parseLong(getParameter("id").toString()));
                setAttribute("agentinfo", agentInfo);
                setAttribute("dotype","select");
                return "agentinfo";
            }
            if ((getParameter("dotype").toString().compareTo("edit")==0) && (null != getParameter("id"))
                    && (getParameter("id").toString().compareTo("") != 0)) {
                AgentInfo agentInfo = AgentInfo.getAgentInfoById(Long.parseLong(getParameter("id").toString()));
                setAttribute("agentinfo", agentInfo);
                setAttribute("dotype","edit");
                return "agentinfo";
            }
        }
        catch (Exception e){
            return "page404";
        }
        return "page404";
    }


    public String insertAgent(){
        if ((!getAttribute("roletype").equals("0"))&&(!getAttribute("roletype").equals("111")))
            return AjaxActionComplete(false);
        AgentInfo agent = new AgentInfo();
        try {
            agent.setName(getParameter("agentname").toString());
            agent.setAccountName(getParameter("accountname").toString());
            agent.setAccountPhone(getParameter("accountphone").toString());
            agent.setcontactPhone(getParameter("contactphone").toString());
            agent.setAccountNo(getParameter("accountno").toString());
            agent.setBankCity(getParameter("bankcity").toString());
            agent.setBankCode(getParameter("bankcode").toString());
            agent.setBankName(getParameter("bankname").toString());
            agent.setAliCost(Float.parseFloat(getParameter("alicost").toString()));
            agent.setAliProfit(Float.parseFloat(getParameter("aliprofit").toString()));
            agent.setJdCost(Float.parseFloat(getParameter("jdcost").toString()));
            agent.setJdProfit(Float.parseFloat(getParameter("jdprofit").toString()));
            agent.setWxCost(Float.parseFloat(getParameter("wxcost").toString()));
            agent.setWxProfit(Float.parseFloat(getParameter("wxprofit").toString()));
            agent.setBestCost(Float.parseFloat(getParameter("bestcost").toString()));
            agent.setBestProfit(Float.parseFloat(getParameter("bestprofit").toString()));
            UserInfo us=new UserInfo();
            us.setPassword("1234567");
            us.setUsername(getParameter("agentname").toString());
            UserInfo.insertUserInfo(us);
            List<UserInfo> lus = UserInfo.getUserInfoByMap(getParameter("agentname").toString(),"1234567",null,null);
            agent.setId(lus.get(0).getId());
            return AjaxActionComplete(AgentInfo.insertAgentInfo(agent));
        }
        catch (Exception e){
            return AjaxActionComplete(false);
        }
    }

    public String editAgent(){
        AgentInfo agent = new AgentInfo();
        try {
            agent.setId(Long.parseLong(getParameter("agentid").toString()));
            agent.setName(getParameter("agentname").toString());
            agent.setAccountName(getParameter("accountname").toString());
            agent.setAccountPhone(getParameter("accountphone").toString());
            agent.setcontactPhone(getParameter("contactphone").toString());
            agent.setAccountNo(getParameter("accountno").toString());
            agent.setBankCity(getParameter("bankcity").toString());
            agent.setBankCode(getParameter("bankcode").toString());
            agent.setBankName(getParameter("bankname").toString());
            agent.setAliCost(Float.parseFloat(getParameter("alicost").toString()));
            agent.setAliProfit(Float.parseFloat(getParameter("aliprofit").toString()));
            agent.setJdCost(Float.parseFloat(getParameter("jdcost").toString()));
            agent.setJdProfit(Float.parseFloat(getParameter("jdprofit").toString()));
            agent.setWxCost(Float.parseFloat(getParameter("wxcost").toString()));
            agent.setWxProfit(Float.parseFloat(getParameter("wxprofit").toString()));
            agent.setBestCost(Float.parseFloat(getParameter("bestcost").toString()));
            agent.setBestProfit(Float.parseFloat(getParameter("bestprofit").toString()));
            return AjaxActionComplete(AgentInfo.updateAgentInfo(agent));
        }
        catch (Exception e){
            return AjaxActionComplete(false);
        }
    }

    public String modMyCiCiInfo() {
        //setParameter("openid","oX2yrxK7iPmdKodGIMYAcDEdQtKc");
        if (StringUtils.convertNullableString(getAttribute("openid")).equals("") && (StringUtils.convertNullableString(getParameter("openid")).length() == 0)) {
            setParameter("redirect_url", "User!modMyCiCiInfo?abc=1");
            return "fetchWxCode";
        }
        if (StringUtils.convertNullableString(getAttribute("openid")).equals(""))
            getRequest().getSession().setAttribute("openid", getParameter("openid").toString());

        Map map = new HashMap<>();
        map.put("openid",getRequest().getSession().getAttribute("openid").toString());
        List<MerchantInfo> lp =  MerchantInfo.getMerchantInfoByMap(map);
        CardInfo cardindo = CardInfo.getCardInfoById((lp.get(0).getId()));
        lp.get(0).setCid(Long.parseLong(cardindo.getSaltcode()));
        getRequest().setAttribute("reginfo",lp.get(0));

        return "registermodfiy";
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCid() {
        return cid;
    }
}