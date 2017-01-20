package cc.action;

import cc.ProjectLogger;
import cc.ProjectSettings;
import cc.database.merchant.CardInfo;
import cc.database.merchant.MerchantInfo;
import cc.database.merchant.PendingMerchant;
import cc.utils.HttpPostUrl;
import cc.utils.IdConvert;
import cc.utils.PublicFunc;
import QimCommon.struts.AjaxActionSupport;
import QimCommon.utils.StringUtils;
import cc.utils.VerifyCodeUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.util.*;
import java.util.List;

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
        //setParameter("openid","oBhD-wj1zMF5-FET_9dwK8rI2nt0");
        getRequest().getSession().removeAttribute("ErrorMsg");
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
        }

        getRequest().getSession().setAttribute("openid", getParameter("openid").toString());
        pendingMerchant.setCid(Long.parseLong(getParameter("cid").toString()));
        getRequest().setAttribute("reginfo", pendingMerchant);
        return "registerStep1";
    }

    public String registerStep1() {
        if (null==getRequest().getSession().getAttribute("openid"))
            return "User!wxlogin";

        CardInfo cardInfo = CardInfo.getCardInfoById(IdConvert.DecryptionId(Long.parseLong(getParameter("cid").toString())));
        if (cardInfo.getAgentid()!=Long.parseLong(getParameter("uname").toString())) {
            getRequest().getSession().setAttribute("ErrorMsg","请填写正确的推广号");
            return "registerStep1";
        }

        PendingMerchant pendingMerchant =  PendingMerchant.getPendingMerchantById(IdConvert.DecryptionId(Long.parseLong(getParameter("cid").toString())), getRequest().getSession().getAttribute("openid").toString());
        pendingMerchant.setName(getParameter("name").toString());
        MerchantInfo.ExternInfo externInfo = pendingMerchant.new ExternInfo();
        pendingMerchant.setAddress(getParameter("address").toString());
        pendingMerchant.setContactName(getParameter("contactName").toString());
        pendingMerchant.setContactPhone(getParameter("contactPhone").toString());
        pendingMerchant.setIdCard(getParameter("idCard").toString());
        pendingMerchant.setExternInfo(externInfo.toString());
        if (!PendingMerchant.updatePendingMerchant(pendingMerchant))
            return AjaxActionComplete(false);
        pendingMerchant.setCid(Long.parseLong(getParameter("cid").toString()));
        getRequest().setAttribute("reginfo", pendingMerchant);
        return "registerStep2";
    }

    public String registerStep2() {
        PendingMerchant pendingMerchant =  PendingMerchant.getPendingMerchantById(IdConvert.DecryptionId(Long.parseLong(getParameter("cid").toString())),
                getRequest().getSession().getAttribute("openid").toString());
        pendingMerchant.setBankCity(getParameter("bankCity").toString());
        pendingMerchant.setAccountName(getParameter("accountName").toString());
        pendingMerchant.setAccountNo(getParameter("accountNo").toString());
        pendingMerchant.setBankName(getParameter("bankName").toString());
        pendingMerchant.setBankCode(getParameter("bankCode").toString());
        if (!PendingMerchant.updatePendingMerchant(pendingMerchant))
            return AjaxActionComplete(false);
        pendingMerchant.setCid(Long.parseLong(getParameter("cid").toString()));
        getRequest().setAttribute("reginfo",pendingMerchant);
        return "registerStep3";
    }

    public String modIDCard(){
        String ErrorMsg = "";
        try {
            long cid = IdConvert.DecryptionId(Long.parseLong(getParameter("cid").toString()));
            MerchantInfo merchantInfo = MerchantInfo.getMerchantInfoById(cid);
            if (merchantInfo==null)
                return AjaxActionComplete(false);
            PendingMerchant pendingMerchant =new PendingMerchant();
            pendingMerchant.setName(merchantInfo.getName());
            pendingMerchant.setBankCity(merchantInfo.getBankCity());
            pendingMerchant.setAccountName(merchantInfo.getAccountName());
            pendingMerchant.setAccountNo(merchantInfo.getAccountNo());
            pendingMerchant.setBankName(merchantInfo.getBankName());
            pendingMerchant.setBankCode(merchantInfo.getBankCode());
            pendingMerchant.setCid(merchantInfo.getCid());
            pendingMerchant.setId(merchantInfo.getId());
            pendingMerchant.setAccountPhone(merchantInfo.getAccountName());
            pendingMerchant.setAddress(merchantInfo.getAddress());
            pendingMerchant.setAliRate(merchantInfo.getAliRate());
            pendingMerchant.setAliStatus(merchantInfo.getAliStatus());
            pendingMerchant.setWxRate(merchantInfo.getWxRate());
            pendingMerchant.setWxStatus(merchantInfo.getWxStatus());
            pendingMerchant.setJdRate(merchantInfo.getJdRate());
            pendingMerchant.setJdStatus(merchantInfo.getJdStatus());
            pendingMerchant.setBestRate(merchantInfo.getBestRate());
            pendingMerchant.setBestStatus(merchantInfo.getBestStatus());
            pendingMerchant.setBankGeneralName(merchantInfo.getBankGeneralName());
            pendingMerchant.setContactName(merchantInfo.getContactName());
            pendingMerchant.setIdCard(merchantInfo.getIdCard());
            pendingMerchant.setOpenid(merchantInfo.getOpenid());
            pendingMerchant.setPayMethodAliId(merchantInfo.getPayMethodAliId());
            pendingMerchant.setPayMethodBestId(merchantInfo.getPayMethodBestId());
            pendingMerchant.setPayMethodWeixinId(merchantInfo.getPayMethodWeixinId());
            pendingMerchant.setPayMethodJDId(merchantInfo.getPayMethodJDId());
            pendingMerchant.setRegisterDate(merchantInfo.getRegisterDate());
            pendingMerchant.setContactPhone(merchantInfo.getContactPhone());

            pendingMerchant.setName(getParameter("name").toString());
            pendingMerchant.setBankCity(getParameter("bankCity").toString());
            pendingMerchant.setAccountName(getParameter("accountName").toString());
            pendingMerchant.setAccountNo(getParameter("accountNo").toString());
            pendingMerchant.setBankName(getParameter("bankName").toString());
            pendingMerchant.setBankCode(getParameter("bankCode").toString());
            pendingMerchant.setPaymentStatus(false);
            if (!PendingMerchant.insertPendingMerchant(pendingMerchant)){
                ErrorMsg = "请检查输入的信息是否正确";
                getResponse().setContentType("text/html");
                Map map = new HashMap<>();
                map.put("ErrorMsg", ErrorMsg);
                return AjaxActionComplete(true,map);
            }

            File ff = (File) getParameter("fyhk");
            File fm = new File(ProjectSettings.getCachePath() + String.valueOf(cid)
                    + getRequest().getSession().getAttribute("openid") + "yhk.jpg");
            if (null != ff && ff.length() > 1000) {
                {
                    PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
                }
            } else if (null == ff || fm.length() < 1000)
                ErrorMsg = "银行卡正面照文件不对";
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            getResponse().setContentType("text/html");
            Map map = new HashMap<>();
            map.put("ErrorMsg", ErrorMsg);
            return AjaxActionComplete(true,map);
        }
    }

    public String uploadIDCard() {
        String ErrorMsg="";
        try {
            File ff;
            File fm;
            String cid = String.valueOf(IdConvert.DecryptionId(Long.parseLong(getParameter("cid").toString())));
            ff = (File) getParameter("fsfzz");
            fm = new File(ProjectSettings.getCachePath() +cid
                    + getRequest().getSession().getAttribute("openid") + "sfzz.jpg");
            if (null != ff && ff.length() > 1000) {
                PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
            } else if (null == ff || fm.length()<100) {
                ErrorMsg = "身份证正面照文件不对";
                return "";
            }
            ff = (File) getParameter("fsfzf");
            fm = new File(ProjectSettings.getCachePath() +cid
                    + getRequest().getSession().getAttribute("openid") + "sfzf.jpg");
            if (null != ff && ff.length() > 1000) {
                {
                    PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
                }
            } else  if (null == ff || fm.length()<100){
                ErrorMsg = "身份证反面照文件不对";
                return "";
            }
            ff = (File) getParameter("fscsfz");
            fm = new File(ProjectSettings.getCachePath() +cid
                    + getRequest().getSession().getAttribute("openid") + "scsfz.jpg");
            if (null != ff && ff.length() > 1000) {
                {
                    PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
                }
            } else  if (null == ff || fm.length()<1000){
                ErrorMsg = "手持身份证照文件不对";
                return "";
            }
            ff = (File) getParameter("fyhk");
            fm = new File(ProjectSettings.getCachePath() +cid
                    + getRequest().getSession().getAttribute("openid") + "yhk.jpg");
            if (null != ff && ff.length() > 1000) {
                {
                    PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
                }
            } else if (null == ff || fm.length()<1000){
                ErrorMsg = "银行卡正面照文件不对";
                return "";
            }
            /*自有资质*/
            if (getParameter("chkzyzz")!=null){
                ff = (File) getParameter("fyyzz");
                fm = new File(ProjectSettings.getCachePath() +cid
                        + getRequest().getSession().getAttribute("openid") + "yyzz.jpg");
                if (null != ff && ff.length() > 1000) {
                    {
                        PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
                    }
                } else if (null == ff || fm.length()<1000){
                    ErrorMsg = "营业执照文件不对";
                    return "";
                }

                ff = (File) getParameter("fswdjzp");
                fm = new File(ProjectSettings.getCachePath() +cid
                        + getRequest().getSession().getAttribute("openid") + "swdjzp.jpg");
                if (null != ff && ff.length() > 1000) {
                    {
                        PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
                    }
                } else if (null == ff || fm.length()<1000){
                    ErrorMsg = "税务登记照片文件不对";
                    return "";
                }

                ff = (File) getParameter("fzzjgdmz");
                fm = new File(ProjectSettings.getCachePath() +cid
                        + getRequest().getSession().getAttribute("openid") + "zzjgdmz.jpg");
                if (null != ff && ff.length() > 1000) {
                    {
                        PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
                    }
                } else if (null == ff || fm.length()<1000){
                    ErrorMsg = "组织机构代码证文件不对";
                    return "";
                }

                ff = (File) getParameter("ffrsfzz");
                fm = new File(ProjectSettings.getCachePath() +cid
                        + getRequest().getSession().getAttribute("openid") + "frsfzz.jpg");
                if (null != ff && ff.length() > 1000) {
                    {
                        PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
                    }
                } else if (null == ff || fm.length()<1000){
                    ErrorMsg = "法人身份证照片正面文件不对";
                    return "";
                }

                ff = (File) getParameter("ffrsfzf");
                fm = new File(ProjectSettings.getCachePath() +cid
                        + getRequest().getSession().getAttribute("openid") + "frsfzf.jpg");
                if (null != ff && ff.length() > 1000) {
                    {
                        PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
                    }
                } else if (null == ff || fm.length()<1000){
                    ErrorMsg = "法人身份证照片反面文件不对";
                    return "";
                }

                ff = (File) getParameter("fshsyt");
                fm = new File(ProjectSettings.getCachePath() +cid
                        + getRequest().getSession().getAttribute("openid") + "shsyt.jpg");
                if (null != ff && ff.length() > 1000) {
                    {
                        PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
                    }
                } else if (null == ff || fm.length()<1000){
                    ErrorMsg = "商户收银台照片文件不对";
                    return "";
                }

                ff = (File) getParameter("fshmtz");
                fm = new File(ProjectSettings.getCachePath() +cid
                        + getRequest().getSession().getAttribute("openid") + "shmtz.jpg");
                if (null != ff && ff.length() > 1000) {
                    {
                        PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
                    }
                } else if (null == ff || fm.length()<1000){
                    ErrorMsg = "商户门头照片文件不对";
                    return "";
                }

                ff = (File) getParameter("fdncs");
                fm = new File(ProjectSettings.getCachePath() +cid
                        + getRequest().getSession().getAttribute("openid") + "dncs.jpg");
                if (null != ff && ff.length() > 1000) {
                    {
                        PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
                    }
                } else if (null == ff || fm.length()<1000){
                    ErrorMsg = "店内陈饰照片文件不对";
                    return "";
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            getResponse().setContentType("text/html");
            Map map = new HashMap<>();
            map.put("ErrorMsg", ErrorMsg);
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
            File fm = new File(ProjectSettings.getCachePath() + String.valueOf(IdConvert.DecryptionId(Long.parseLong(cardidstr)))
                    +openidstr + picname+".jpg");
            System.out.println("picname:"+picname);
            if (!fm.exists()) {
                fm = new File(getRequest()
                        .getServletContext().getRealPath("/")
                        + "img/"+picname+".jpg");
                if (!fm.exists()) {
                    fm = new File(getRequest()
                            .getServletContext().getRealPath("/")
                            + "img/nopic.jpg");
                }
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

    public String fetchPendingMerchant(){
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

    public String fetchMerchant(){
        Map<Object, Object> param= new HashMap<>();
        List<Map> allmerchantlist = null;
        if (null!=getParameter("cid").toString() && (!getParameter("cid").toString().trim().equals(""))){
            param.put("cid",IdConvert.DecryptionId(Long.parseLong(getParameter("cid").toString())));
        }
        if (null!=getParameter("name").toString() && (!getParameter("name").toString().trim().equals(""))){
            param.put("name",getParameter("name"));
        }
        if (null!=getParameter("agentname").toString() && (!getParameter("agentname").toString().trim().equals(""))){
            param.put("agentname",getParameter("agentname"));
        }
        if (null!=getParameter("createtime1").toString() && (!getParameter("createtime1").toString().trim().equals(""))){
            param.put("createtime1",getParameter("createtime1"));
        }
        if (null!=getParameter("createtime2").toString() && (!getParameter("createtime2").toString().trim().equals(""))){
            param.put("createtime2",getParameter("createtime2"));
        }
        // TODO
        allmerchantlist =MerchantInfo.getMerchantInfoByQuery(param);
        Map map=new HashMap<>();
        map.put("totalcount",allmerchantlist.size());
        allmerchantlist.add(0, map);
        return  AjaxActionComplete(allmerchantlist);
    }

    public String selectOne(){
        try {
            Map map = new HashMap<>();
            map.put("openid", getParameter("openid"));
            map.put("cid", IdConvert.DecryptionId(Long.parseLong(getParameter("cid").toString())));
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

    public String selectOneMerchant(){
        try {
            Map map = new HashMap<>();
            map.put("cid", getParameter("cid"));
            MerchantInfo merchantInfo =  MerchantInfo.getMerchantInfoById(IdConvert.DecryptionId(Long.parseLong( getParameter("cid").toString())));
            Map mapp = new HashMap<>();
            getRequest().setAttribute("reginfo",merchantInfo);
            getRequest().setAttribute("openid", merchantInfo.getOpenid());
            getRequest().setAttribute("cid", getParameter("cid"));
            return "registerall";
        }
        catch (Exception e){
            return "page404";
        }
    }
    public String checkOne() {
        try {
            PendingMerchant pendingMerchant =  PendingMerchant.getPendingMerchantById(IdConvert.DecryptionId(Long.parseLong(getParameter("cid").toString())), getParameter("openid").toString());
            if (pendingMerchant==null){
                pendingMerchant = new PendingMerchant();
                pendingMerchant.setCid(IdConvert.DecryptionId(Long.parseLong(getParameter("cid").toString())));
            }
            pendingMerchant.setWxStatus((null!=getParameter("wxpay"))&&(getParameter("wxpay").equals("on")));
            pendingMerchant.setJdStatus((null!=getParameter("jdpay"))&&(getParameter("jdpay").equals("on")));
            pendingMerchant.setAliStatus((null!=getParameter("alipay"))&&(getParameter("alipay").equals("on")));
            pendingMerchant.setBestStatus((null!=getParameter("bestpay"))&&(getParameter("bestpay").equals("on")));
            pendingMerchant.setPaymentStatus((null!=getParameter("canpay"))&&(getParameter("canpay").equals("on")));
            try {
                if (null != getParameter("wxrate"))
                    pendingMerchant.setWxRate(Float.parseFloat(getParameter("wxrate").toString()));
            }
            catch (Exception e){
                ProjectLogger.error("wxrate number Error!");
            }
            try {
                if (null != getParameter("alirate"))
                    pendingMerchant.setAliRate(Float.parseFloat(getParameter("alirate").toString()));
            }
            catch (Exception e){
                ProjectLogger.error("alirate number Error!");
            }
            try {
                if (null != getParameter("jdrate"))
                    pendingMerchant.setJdRate(Float.parseFloat(getParameter("jdrate").toString()));
            }
            catch (Exception e){
                ProjectLogger.error("jdrate number Error!");
            }
            try {
                if (null != getParameter("bestrate"))
                    pendingMerchant.setBestRate(Float.parseFloat(getParameter("bestrate").toString()));
            }
            catch (Exception e){
                ProjectLogger.error("bestrate number Error!");
            }
            if (PendingMerchant.updatePendingMerchant(pendingMerchant)){
                PendingMerchant.deletePendingMerchant(pendingMerchant);
                if (MerchantInfo.getMerchantInfoById(pendingMerchant.getId())!=null)
                    return AjaxActionComplete(MerchantInfo.updateMerchantPayBycheck(pendingMerchant));
                else
                    return AjaxActionComplete(MerchantInfo.insertMerchantInfo(pendingMerchant));
            }
            else{
                return AjaxActionComplete(MerchantInfo.updateMerchantPayBycheck(pendingMerchant));
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
    public String goMerchantQuery(){
        return "merchantquery";
    }
    public String  addCCCard(){
        if (StringUtils.convertNullableString(getAttribute("openid")).equals("") && (StringUtils.convertNullableString(getParameter("openid")).length() == 0)) {
            setParameter("redirect_url", "Register!addCCCard?abc=1");
            return "fetchWxCode";
        }
        if (StringUtils.convertNullableString(getAttribute("openid")).equals(""))
            getRequest().getSession().setAttribute("openid", getParameter("openid").toString());

        Map map = new HashMap<>();
        map.put("openid",getAttribute("openid"));
        List<MerchantInfo> lm = MerchantInfo.getMerchantInfoByMap(map);
        if (lm.size()<1) {
            return "page404";
        }
        return "multcodes";
    }
    private Color getRandColor(int s, int e){
        Random random=new Random ();
        if(s>255) s=255;
        if(e>255) e=255;
        int r,g,b;
        r=s+random.nextInt(e-s);    //随机生成RGB颜色中的r值
        g=s+random.nextInt(e-s);    //随机生成RGB颜色中的g值
        b=s+random.nextInt(e-s);    //随机生成RGB颜色中的b值
        return new Color(r,g,b);
    }

    public void randCheckimg() throws Exception {
        getResponse().setHeader("Pragma", "No-cache");
        getResponse().setHeader("Cache-Control", "No-cache");
        getResponse().setDateHeader("Expires", 0);
        getResponse().setContentType("image/jpeg");

        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //存入会话session
        HttpSession session = getRequest().getSession(true);
        session.setAttribute("randvalidate", verifyCode.toLowerCase());
        //生成图片
        int w = 200, h = 80;
        VerifyCodeUtils.outputImage(w, h, getResponse().getOutputStream(), verifyCode);
    }

    public String chkrandval(){
        try {
            return AjaxActionComplete(getAttribute("randvalidate").toString().contentEquals(getParameter("randval").toString()));
        }
        catch (Exception e){
            return  AjaxActionComplete(false);
        }
    }

    public String addmultcode(){
        Boolean rt;
        Map map =new HashMap<>();
        try {
            rt = getAttribute("randvalidate").toString().contentEquals(getParameter("randval").toString());
            if (!rt) {
                map.put("errormsg", "验证码不对");
                return AjaxActionComplete(rt, map);
            }
            setParameter("openid","oX2yrxK7iPmdKodGIMYAcDEdQtKc");
            setAttribute("openid","oX2yrxK7iPmdKodGIMYAcDEdQtKc");
            getRequest().getSession().removeAttribute("ErrorMsg");
            if (StringUtils.convertNullableString(getParameter("openid")).length() == 0) {
                setParameter("redirect_url","Register!addmultcode?cid=" + getParameter("cid").toString()+"&randvalidate="+ getParameter("randval").toString());
                return "fetchWxCode";
            }

            long merchantId = IdConvert.DecryptionId(Long.parseLong(getParameter("cid").toString()));
            CardInfo cardInfo = CardInfo.getCardInfoById(merchantId);
            if (cardInfo == null) {
                map.put("errormsg", "CiCi码不对");
                return AjaxActionComplete(false, map);
            }

            MerchantInfo merchantInfo = new MerchantInfo();
            merchantInfo.setId(merchantId);
            merchantInfo.setOpenid(getAttribute("openid"));
            rt = MerchantInfo.insertCopyMerchantInfo(merchantInfo);
            if (!rt) {
                map.put("errormsg", "商户不对，请走注册！");
                return AjaxActionComplete(false, map);
            }
            return AjaxActionComplete(rt,map);
        }
        catch (Exception e){
            return  AjaxActionComplete(false,map);
        }
    }
}
