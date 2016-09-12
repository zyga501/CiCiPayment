package com.framework.action;

import com.AjaxActionSupport;
import com.framework.ProjectSettings;
import com.framework.utils.PublicFunc;
import com.qimpay.database.DBmap;
import com.qimpay.database.UserInfo;

import java.io.File;
import java.text.DateFormat;
import java.util.*;

public class RegAction extends AjaxActionSupport {

    private String cardid ;
    private String openid ;
    private String store ;
    private String address ;
    private String contact ;
    private String tel ;
    private String idcardno ;
    private String picidcardz ;
    private String picidcardf ;
    private String pichandidcard ;
    private String regdate ;
    private String canpay ;
    private String wxpay ;
    private String alipay ;
    private String jdpay ;
    private String bestpay ;
    private String wxrate ;
    private String alirate ;
    private String jdrarte ;
    private String bestrate ;
    private String accountpc ;
    private String bank ;
    private String contactnum ;
    private String acountcode ;
    private String acountname ;

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIdcardno() {
        return idcardno;
    }

    public void setIdcardno(String idcardno) {
        this.idcardno = idcardno;
    }

    public String getPicidcardz() {
        return picidcardz;
    }

    public void setPicidcardz(String picidcardz) {
        this.picidcardz = picidcardz;
    }

    public String getPicidcardf() {
        return picidcardf;
    }

    public void setPicidcardf(String picidcardf) {
        this.picidcardf = picidcardf;
    }

    public String getPichandidcard() {
        return pichandidcard;
    }

    public void setPichandidcard(String pichandidcard) {
        this.pichandidcard = pichandidcard;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getCanpay() {
        return canpay;
    }

    public void setCanpay(String canpay) {
        this.canpay = canpay;
    }

    public String getWxpay() {
        return wxpay;
    }

    public void setWxpay(String wxpay) {
        this.wxpay = wxpay;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getJdpay() {
        return jdpay;
    }

    public void setJdpay(String jdpay) {
        this.jdpay = jdpay;
    }

    public String getBestpay() {
        return bestpay;
    }

    public void setBestpay(String bestpay) {
        this.bestpay = bestpay;
    }

    public String getWxrate() {
        return wxrate;
    }

    public void setWxrate(String wxrate) {
        this.wxrate = wxrate;
    }

    public String getAlirate() {
        return alirate;
    }

    public void setAlirate(String alirate) {
        this.alirate = alirate;
    }

    public String getJdrarte() {
        return jdrarte;
    }

    public void setJdrarte(String jdrarte) {
        this.jdrarte = jdrarte;
    }

    public String getBestrate() {
        return bestrate;
    }

    public void setBestrate(String bestrate) {
        this.bestrate = bestrate;
    }

    public String getAccountpc() {
        return accountpc;
    }

    public void setAccountpc(String accountpc) {
        this.accountpc = accountpc;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getContactnum() {
        return contactnum;
    }

    public void setContactnum(String contactnum) {
        this.contactnum = contactnum;
    }

    public String getAcountcode() {
        return acountcode;
    }

    public void setAcountcode(String acountcode) {
        this.acountcode = acountcode;
    }

    public String getAcountname() {
        return acountname;
    }

    public void setAcountname(String acountname) {
        this.acountname = acountname;
    }

    public String getdt(){
        Date date = new Date(System.currentTimeMillis());
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM,Locale.CHINA);
        String dt = df.format(date);
        Map  map=new HashMap<>();
        map.put("new",dt);
        return   AjaxActionComplete(map);
    }

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

    public String reg(){
        try {
//            if (null == getOpenid() || getOpenid().equals("")) {
//                AjaxActionComplete(false);
//            }
//            setOpenid("_ooooiiiuujjhshshsy");
//            if (null!=getRequest().getSession().getAttribute("openid"))
//                return "wxopenid";
            getRequest().getSession().setAttribute("cardid", getParameter("cid").toString());
            getRequest().getSession().setAttribute("openid","_ooooiiiuujjhshshsy");

            Map map = new HashMap<>();
            map.put("cid", getParameter("cid").toString() );
            List<HashMap> lc =DBmap.getcodeinfo(map);
            if (null==lc || (lc.size()==0)){
                return "page404";//goto pay page; 根据支付类型跳转
            }
            else if ((lc.size()==1) && (!lc.get(0).get("openid").equals("")) ){
                return "paypage";
            }
            map.put("openid","_ooooiiiuujjhshshsy");
            List<HashMap> lm =  DBmap.getmerchanttemp(map);
            if ( null==lm || lm.size()==0) {
                DBmap.insertmerchanttemp(map);
            }
            else
            getRequest().setAttribute("reginfo",lm.get(0));
            return "register1";
        }
        catch (Exception e){
            return "page404";
        }
    }

    public String reg1(){
        try {
            Map map = new HashMap<>();
            map.put("openid", getRequest().getSession().getAttribute("openid"));
            map.put("cid", getRequest().getSession().getAttribute("cardid"));
            map.put("store", getParameter("store"));
            map.put("address", getParameter("address"));
            map.put("contact", getParameter("contact"));
            map.put("tel", getParameter("tel"));
            map.put("idcardno", getParameter("idcardno"));
            map.put("uname", getParameter("uname"));
            if (!DBmap.updmerchanttemp(map))
                return AjaxActionComplete(false);;
            List<HashMap> lm =  DBmap.getmerchanttemp(map);
            getRequest().setAttribute("reginfo",lm.get(0));
            return "register2";
//            Map map2 = new HashMap<>();
//            map2.put("url", "../reg/register2.jsp");
//            return AjaxActionComplete(true, map2);
        }
        catch (Exception e){
            return AjaxActionComplete(false);
        }
    }
    public String reg2() {
        try {
            Map map = new HashMap<>();
            map.put("openid", getRequest().getSession().getAttribute("openid"));
            map.put("cid", getRequest().getSession().getAttribute("cardid"));
            map.put("accountpc", getParameter("accountpc"));
            map.put("acountname", getParameter("acountname"));
            map.put("acountcode", getParameter("acountcode"));
            map.put("bank", getParameter("bank"));
            map.put("contactnum", getParameter("contactnum"));
            if (!DBmap.updmerchanttemp(map))
                return AjaxActionComplete(false);
            List<HashMap> lm = DBmap.getmerchanttemp(map);
            getRequest().setAttribute("reginfo", lm.get(0));
            return "register3";
        } catch (Exception e) {
            e.printStackTrace();
            return ("");
        }
    }

    public String uploadpic() {
        String msg="";
        try {
            File ff = (File) getParameter("fsfzz");//fdphy,fyhkf,fyhkz,fsfzf,fsfzz
            if (null != ff && ff.length() > 1000) {
                File fm = new File(ProjectSettings.getPicpath() + String.valueOf(getRequest().getSession().getAttribute("cardid"))
                        + getRequest().getSession().getAttribute("openid") + "sfzz.jpg");
                {
                    PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
                }
            } else
                msg = "身份证正面照文件不对";
            ff = (File) getParameter("fsfzf");//fdphy,fyhkf,fyhkz,fsfzf,fsfzz
            if (null != ff && ff.length() > 1000) {
                File fm = new File(ProjectSettings.getPicpath() + String.valueOf(getRequest().getSession().getAttribute("cardid"))
                        + getRequest().getSession().getAttribute("openid") + "sfzf.jpg");
                {
                    PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
                }
            } else
                msg = "身份证反面照文件不对";
            ff = (File) getParameter("fscsfz");//fdphy,fyhkf,fyhkz,fsfzf,fsfzz
            if (null != ff && ff.length() > 1000) {
                File fm = new File(ProjectSettings.getPicpath() + String.valueOf(getRequest().getSession().getAttribute("cardid"))
                        + getRequest().getSession().getAttribute("openid") + "fscsfz.jpg");
                {
                    PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
                }
            } else
                msg = "手持身份证照文件不对";
            ff = (File) getParameter("fyhk");//fdphy,fyhkf,fyhkz,fsfzf,fsfzz
            if (null != ff && ff.length() > 1000) {
                File fm = new File(ProjectSettings.getPicpath() + String.valueOf(getRequest().getSession().getAttribute("cardid"))
                        + getRequest().getSession().getAttribute("openid") + "fyhk.jpg");
                {
                    PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
                }
            } else
                msg = "银行卡正面照文件不对";
        }
        finally {
            getResponse().setContentType("text/html");
            Map map = new HashMap<>();
            map.put("msgstr", msg);
            return AjaxActionComplete(true,map);
        }
    }

    public String checkinfo(){
        return "";
    }
}

