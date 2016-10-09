package cc.action;

import cc.ProjectSettings;
import cc.database.merchant.AgentInfo;
import cc.database.merchant.CodeInfo;
import cc.database.merchant.MerchantInfo;
import cc.database.merchant.PendingMerchant;
import cc.utils.HttpPostUrl;
import cc.utils.PublicFunc;
import framework.action.AjaxActionSupport;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.util.*;

public class RegAction extends AjaxActionSupport {

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
        return   AjaxActionComplete(map);
    }

    public String reg(){
        try {
            System.out.println("rparam="+getRequest().getServletPath()+" ** "+getRequest().getQueryString());
            getRequest().getSession().setAttribute("params","Reg!reg?cid="+getParameter("cid").toString());
//            getRequest().getSession().setAttribute("openid","123123123123sss");
            if (null==getAttribute("openid")||getAttribute("openid").equals(""))
                return "wxopenid";
            getRequest().getSession().removeAttribute("params");
            List<CodeInfo> lc = null;
            try {
                lc= CodeInfo.getCodeInfoById(Long.parseLong(getParameter("cid").toString()));
            }
            catch (Exception e){
                e.printStackTrace();
                System.out.println("cid="+getParameter("cid").toString());
            }
            List<MerchantInfo> lm = MerchantInfo.getMerchantInfoById(Long.parseLong(getParameter("cid").toString()));
            if (null==lc || (lc.size()==0)){
                return "page404";//goto pay page; 根据支付类型跳转
            }
            else if ((lm.size()==1)){
                return "wxpayaction";
            }

            List<PendingMerchant> lp =  PendingMerchant.getPendingMerchantById(Long.parseLong(getParameter("cid").toString()), getRequest().getSession().getAttribute("openid").toString());
            if ( null==lp || lp.size()==0) {
                PendingMerchant pendingMerchant = new PendingMerchant();
                pendingMerchant.setId(Long.parseLong(getParameter("cid").toString()));
                pendingMerchant.setOpenid(getAttribute("openid").toString());
                PendingMerchant.insertPendingMerchant(pendingMerchant);
                getRequest().setAttribute("reginfo",pendingMerchant);
            }
            else
                getRequest().setAttribute("reginfo",lp.get(0));
            return "register1";
        }
        catch (Exception e){
            e.printStackTrace();
            return "page404";
        }
    }

    public String reg1(){
        try {
            if (null==getAttribute("openid"))
                return "User!wxlogin";

            List<CodeInfo> lc = CodeInfo.getCodeInfoById(Long.parseLong(getParameter("cid").toString()));
            List<AgentInfo> la = AgentInfo.getAgentInfoByName((getParameter("uname").toString()));
            if (! lc.get(0).getAgentId().equals(getParameter("uname").toString())) {
                return "register1";
            }
            List<PendingMerchant> lp =  PendingMerchant.getPendingMerchantById(Long.parseLong(getParameter("cid").toString()), getRequest().getSession().getAttribute("openid").toString());
            PendingMerchant pendingMerchant =lp.get(0);
            pendingMerchant.setName(getParameter("name").toString());
            pendingMerchant.setAddress(getParameter("address").toString());
            pendingMerchant.setContactName(getParameter("contactName").toString());
            pendingMerchant.setContactPhone(getParameter("contactPhone").toString());
            pendingMerchant.setIdCard(getParameter("idCard").toString());
            if (!PendingMerchant.updatePendingMerchant(pendingMerchant))
                return AjaxActionComplete(false);
            lp =  PendingMerchant.getPendingMerchantById(Long.parseLong(getParameter("cid").toString()), getRequest().getSession().getAttribute("openid").toString());
            getRequest().setAttribute("reginfo",lp.get(0));
            return "register2";
        }
        catch (Exception e){
            e.printStackTrace();
            return AjaxActionComplete(false);
        }
    }
    public String reg2() {
        try {
            List<PendingMerchant> lp =  PendingMerchant.getPendingMerchantById(Long.parseLong(getParameter("cid").toString()), getRequest().getSession().getAttribute("openid").toString());
            PendingMerchant pendingMerchant =lp.get(0);
            pendingMerchant.setBankCity(getParameter("bankCity").toString());
            pendingMerchant.setAccountName(getParameter("accountName").toString());
            pendingMerchant.setAccountNo(getParameter("accountNo").toString());
            pendingMerchant.setBankName(getParameter("bankName").toString());
            pendingMerchant.setBankCode(getParameter("bankCode").toString());
            // TODO
            if (!PendingMerchant.updatePendingMerchant(pendingMerchant))
                return AjaxActionComplete(false);
            lp =  PendingMerchant.getPendingMerchantById(Long.parseLong(getParameter("cid").toString()), getRequest().getSession().getAttribute("openid").toString());
            getRequest().setAttribute("reginfo",lp.get(0));
            return "register3";
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxActionComplete(false);
        }
    }

    public String uploadPic() {
        String msg="";
        try {
            File ff = (File) getParameter("fsfzz");//fdphy,fyhkf,fyhkz,fsfzf,fsfzz
            File fm = new File(ProjectSettings.getPicpath() +getParameter("cid").toString()
                    + getRequest().getSession().getAttribute("openid") + "sfzz.jpg");
            if (null != ff && ff.length() > 1000) {
                PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
            } else if (null == ff || fm.length()<100)
                msg = "身份证正面照文件不对";
            ff = (File) getParameter("fsfzf");//fdphy,fyhkf,fyhkz,fsfzf,fsfzz
            fm = new File(ProjectSettings.getPicpath() +(getParameter("cid").toString())
                    + getRequest().getSession().getAttribute("openid") + "sfzf.jpg");
            if (null != ff && ff.length() > 1000) {
                {
                    PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
                }
            } else  if (null == ff || fm.length()<100)
                msg = "身份证反面照文件不对";
            ff = (File) getParameter("fscsfz");//fdphy,fyhkf,fyhkz,fsfzf,fsfzz
            fm = new File(ProjectSettings.getPicpath() +(getParameter("cid").toString())
                    + getRequest().getSession().getAttribute("openid") + "scsfz.jpg");
            if (null != ff && ff.length() > 1000) {
                {
                    PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
                }
            } else  if (null == ff || fm.length()<1000)
                msg = "手持身份证照文件不对";
            ff = (File) getParameter("fyhk");//fdphy,fyhkf,fyhkz,fsfzf,fsfzz
            fm = new File(ProjectSettings.getPicpath() +(getParameter("cid").toString())
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
            System.out.println("scardstr"+cardidstr);System.out.print("openidstr"+cardidstr);
            byte[] bt = null;
            File fm = new File(ProjectSettings.getPicpath() + String.valueOf(cardidstr)
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
            // TODO
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
            List<PendingMerchant> lp =  PendingMerchant.getPendingMerchantById(Long.parseLong(getParameter("cid").toString()), getParameter("openid").toString());
            PendingMerchant pendingMerchant =lp.get(0);
            pendingMerchant.setWxStatus((null!=getParameter("wxpay"))&&(getParameter("wxpay").equals("on")));
            pendingMerchant.setJdStatus((null!=getParameter("jdpay"))&&(getParameter("jdpay").equals("on")));
            pendingMerchant.setAliStatus((null!=getParameter("alipay"))&&(getParameter("alipay").equals("on")));
            pendingMerchant.setBestStatus((null!=getParameter("bestpay"))&&(getParameter("bestpay").equals("on")));
            pendingMerchant.setPaymentStatus((null!=getParameter("canpay"))&&(getParameter("canpay").equals("on")));
            // TODO
            if (PendingMerchant.updatePendingMerchant(pendingMerchant)){
                return AjaxActionComplete(MerchantInfo.insertMerchantInfo(pendingMerchant));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return AjaxActionComplete(false);
    }
}
