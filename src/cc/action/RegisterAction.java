package cc.action;

import cc.ProjectSettings;
import cc.database.merchant.CardInfo;
import cc.database.merchant.MerchantInfo;
import cc.database.merchant.PendingMerchant;
import cc.utils.HttpPostUrl;
import cc.utils.IdConvert;
import cc.utils.PublicFunc;
import QimCommon.struts.AjaxActionSupport;
import QimCommon.utils.StringUtils;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.util.*;

public class RegisterAction extends AjaxActionSupport {

    private String cardid ;
    private String openid ;
    private String address ;
    private String contact ;
    private String tel ;
    private String idcardno ;
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
    private String city ;
    private String bank ;
    private String contactnum ;
    private String acountcode ;
    private String acountname ;
    private String province;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
        return AjaxActionComplete(map);
    }

    public String registerPrepare() {
        try {
            if (StringUtils.convertNullableString(getParameter("openid")).length() == 0) {
                setParameter("redirect_url","Register!registerPrepare?cid=" + getParameter("cid").toString());
                return "fetchWxCode";
            }

            long merchantId = IdConvert.DecryptionId(Long.parseLong(getParameter("cid").toString()));
            CardInfo cardInfo = CardInfo.getCardInfoById(merchantId);
            if (cardInfo == null) {
                return "page404";
            }

            PendingMerchant pendingMerchant =  PendingMerchant.getPendingMerchantById(merchantId, getParameter("openid").toString());
            if (pendingMerchant == null) {
                pendingMerchant = new PendingMerchant();
                pendingMerchant.setId(merchantId);
                pendingMerchant.setOpenid(getParameter("openid").toString());
                PendingMerchant.insertPendingMerchant(pendingMerchant);
                getRequest().setAttribute("reginfo",pendingMerchant);
            }

            getRequest().setAttribute("reginfo", pendingMerchant);
            return "registerStep1";
        }
        catch (Exception e) {
            return "page404";
        }
    }

    public String registerStep1(){
        try {
            if (null==getAttribute("openid"))
                return "User!wxlogin";

            CardInfo cardInfo = CardInfo.getCardInfoById(Long.parseLong(getParameter("cid").toString()));
            if (cardInfo.getAgentId().compareTo(getParameter("uname").toString()) != 0) {
                return "registerStep1";
            }

            PendingMerchant pendingMerchant =  PendingMerchant.getPendingMerchantById(Long.parseLong(getParameter("cid").toString()), getRequest().getSession().getAttribute("openid").toString());
            pendingMerchant.setName(getParameter("name").toString());
            MerchantInfo.ExternInfo externInfo = new MerchantInfo.ExternInfo();
            externInfo.setAddress(getParameter("address").toString());
            externInfo.setContactName(getParameter("contactName").toString());
            externInfo.setContactPhone(getParameter("contactPhone").toString());
            externInfo.setIdCard(getParameter("idCard").toString());
            pendingMerchant.setExternInfo(externInfo.toString());
            if (!PendingMerchant.updatePendingMerchant(pendingMerchant))
                return AjaxActionComplete(false);
            getRequest().setAttribute("reginfo", pendingMerchant);
            return "registerStep2";
        }
        catch (Exception e){
            e.printStackTrace();
            return AjaxActionComplete(false);
        }
    }
    public String registerStep2() {
        try {
            PendingMerchant pendingMerchant =  PendingMerchant.getPendingMerchantById(Long.parseLong(getParameter("cid").toString()), getRequest().getSession().getAttribute("openid").toString());
            pendingMerchant.setBankCity(getParameter("bankCity").toString());
            pendingMerchant.setAccountName(getParameter("accountName").toString());
            pendingMerchant.setAccountNo(getParameter("accountNo").toString());
            pendingMerchant.setBankName(getParameter("bankName").toString());
            pendingMerchant.setBankCode(getParameter("bankCode").toString());
            if (!PendingMerchant.updatePendingMerchant(pendingMerchant))
                return AjaxActionComplete(false);
            getRequest().setAttribute("reginfo",pendingMerchant);
            return "registerStep3";
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxActionComplete(false);
        }
    }

    public String uploadIDCard() {
        String msg="";
        try {
            File ff = (File) getParameter("fsfzz");//fdphy,fyhkf,fyhkz,fsfzf,fsfzz
            File fm = new File(ProjectSettings.getCachePath() +getParameter("cid").toString()
                    + getRequest().getSession().getAttribute("openid") + "sfzz.jpg");
            if (null != ff && ff.length() > 1000) {
                PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
            } else if (null == ff || fm.length()<100)
                msg = "身份证正面照文件不对";
            ff = (File) getParameter("fsfzf");//fdphy,fyhkf,fyhkz,fsfzf,fsfzz
            fm = new File(ProjectSettings.getCachePath() +(getParameter("cid").toString())
                    + getRequest().getSession().getAttribute("openid") + "sfzf.jpg");
            if (null != ff && ff.length() > 1000) {
                {
                    PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
                }
            } else  if (null == ff || fm.length()<100)
                msg = "身份证反面照文件不对";
            ff = (File) getParameter("fscsfz");//fdphy,fyhkf,fyhkz,fsfzf,fsfzz
            fm = new File(ProjectSettings.getCachePath() +(getParameter("cid").toString())
                    + getRequest().getSession().getAttribute("openid") + "scsfz.jpg");
            if (null != ff && ff.length() > 1000) {
                {
                    PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
                }
            } else  if (null == ff || fm.length()<1000)
                msg = "手持身份证照文件不对";
            ff = (File) getParameter("fyhk");//fdphy,fyhkf,fyhkz,fsfzf,fsfzz
            fm = new File(ProjectSettings.getCachePath() +(getParameter("cid").toString())
                    + getRequest().getSession().getAttribute("openid") + "yhk.jpg");
            if (null != ff && ff.length() > 1000) {
                {
                    PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
                }
            } else if (null == ff || fm.length()<1000)
                msg = "银行卡正面照文件不对";
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            getResponse().setContentType("text/html");
            Map map = new HashMap<>();
            map.put("msgstr", msg);
            return AjaxActionComplete(true,map);
        }
    }

    public void checkInfo(){
        try {
            String picname = getParameter("picname").toString();
            System.out.println(picname);
            getResponse().setHeader("Pragma", "No-cache");
            getResponse().setHeader("Cache-Control", "no-cache");
            getResponse().setDateHeader("Expires", 0);
            getResponse().setContentType("image/jpeg");
            ServletOutputStream out = null;
            String cardidstr = getParameter("cid").toString();
            String openidstr = null== getRequest().getSession().getAttribute("openid")?getParameter("openid").toString():getRequest().getSession().getAttribute("openid").toString();
            byte[] bt = null;
            File fm = new File(ProjectSettings.getCachePath() + String.valueOf(cardidstr)
                    +openidstr + picname+".jpg");
            System.out.println("picname:"+picname);
            if (!fm.exists()) {
                fm = new File(getRequest()
                        .getServletContext().getRealPath("/")
                        + "img/"+picname+".jpg");
            }
            if (!fm.exists()) {
                return ;
            }
            bt = new byte[(int) fm.length()];
            FileInputStream fileInputStream = new FileInputStream(fm);
            int readSize = fileInputStream.read(bt);
            while (readSize != -1) {
                try {
                    getResponse().getOutputStream().write(bt, 0, readSize);
                    readSize = fileInputStream.read(bt);
                }
                catch (Exception ee){
                    break;
                }
            }
            fileInputStream.close();
            out = getResponse().getOutputStream();
            out.write(bt);
            out.flush();
            out.close();
        }
        catch (Exception e){

        }
    }

    public String queryCityByProvinceId(){
        Map<String, String> mapParam = new HashMap<String, String>();
        mapParam.put("provinceCode",getParameter("provinceCode").toString());
        String pathUrl = "http://www.zybank.com.cn/zyb/queryCityByProvinceId";
        String result = HttpPostUrl.sendPost(pathUrl, mapParam);
        System.out.println(result);
        List list = new ArrayList<>();
        list.add(result);
        return AjaxActionComplete(list);
    }

    public String queryAllRtgsNode(){
        Map<String, String> mapParam = new HashMap<String, String>();
        mapParam.put("cityCode",getParameter("cityCode").toString());
        mapParam.put("clsCode",getParameter("clsCode").toString());
        String pathUrl = "http://www.zybank.com.cn/zyb/queryallrtgsnode";
        String result = HttpPostUrl.sendPost(pathUrl, mapParam);
        List list = new ArrayList<>();
        list.add(result);
        return AjaxActionComplete(list);
    }

    public String fetchMerchant(){
        Map<Object, Object> param= new HashMap<>();
        List<Map> allmerchantlist = null;
        if (null!=getParameter("item").toString() && (!getParameter("item").toString().trim().equals(""))){
            param.put("item",getParameter("item"));
        }
        // TODO
        allmerchantlist =PendingMerchant.getPendingMerchant(param);
        Map map=new HashMap<>();
        map.put("totalcount",allmerchantlist.size());
        allmerchantlist.add(0, map);
        return  AjaxActionComplete(allmerchantlist);
    }


    public String selectOne(){
        try {
            Map map = new HashMap<>();
            map.put("openid", getParameter("openid"));
            map.put("cid", getParameter("cid"));
            List<Map> lm =  PendingMerchant.getPendingMerchant(map);
            Map mapp = new HashMap<>();
            getRequest().setAttribute("reginfo",lm.get(0));
            getRequest().setAttribute("openid", getParameter("openid"));
            getRequest().setAttribute("cid", getParameter("cid"));
            return "registerall";
        }
        catch (Exception e){
            return "page404";
        }
    }


    public String checkOne() {
        try {
            PendingMerchant pendingMerchant =  PendingMerchant.getPendingMerchantById(Long.parseLong(getParameter("cid").toString()), getParameter("openid").toString());
            pendingMerchant.setWxStatus((null!=getParameter("wxpay"))&&(getParameter("wxpay").equals("on")));
            pendingMerchant.setJdStatus((null!=getParameter("jdpay"))&&(getParameter("jdpay").equals("on")));
            pendingMerchant.setAliStatus((null!=getParameter("alipay"))&&(getParameter("alipay").equals("on")));
            pendingMerchant.setBestStatus((null!=getParameter("bestpay"))&&(getParameter("bestpay").equals("on")));
            pendingMerchant.setPaymentStatus((null!=getParameter("canpay"))&&(getParameter("canpay").equals("on")));
            if (PendingMerchant.updatePendingMerchant(pendingMerchant)){
                return AjaxActionComplete(MerchantInfo.insertMerchantInfo(pendingMerchant));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return AjaxActionComplete(false);
    }

    public String goMerchantList(){
        return "merchantlist";
    }
}
