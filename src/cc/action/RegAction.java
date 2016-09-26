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
    private String store ;
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
            getRequest().getSession().setAttribute("params","Reg!reg?cardid="+getParameter("cid").toString());
            if (null==getAttribute("openid")||getAttribute("openid").equals(""))
                return "wxopenid";
//            if (null == getOpenid() || getOpenid().equals("")) {
//                AjaxActionComplete(false);
//            }
//            setOpenid("_ooooiiiuujjhshshsy");
//            if (null!=getRequest().getSession().getAttribute("openid"))
//                return "wxopenid";

            // TODO
            List<CodeInfo> lc = CodeInfo.getCodeInfoById(Long.parseLong(getParameter("cid").toString()));
            List<MerchantInfo> lm = MerchantInfo.getMerchantInfoById(Long.parseLong(getParameter("cid").toString()));
            if (null==lc || (lc.size()==0)){
                return "page404";//goto pay page; 根据支付类型跳转
            }
            else if ((lm.size()==1)){
                return "paypage";
            }
            Map map = new HashMap<>();
            map.put("openid", getRequest().getSession().getAttribute("openid"));
            map.put("cid",Long.parseLong(getParameter("cid").toString()));
            List<PendingMerchant> lp =  PendingMerchant.getPendingMerchantById(map);
            if ( null==lp || lp.size()==0) {
                PendingMerchant pendingMerchant = new PendingMerchant();
                pendingMerchant.setOpenid(getParameter("openid").toString());
                PendingMerchant.insertPendingMerchant(pendingMerchant);
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
            PendingMerchant pendingMerchant =new PendingMerchant();
            pendingMerchant.setOpenid(getRequest().getSession().getAttribute("openid").toString());
            pendingMerchant.setId(Long.parseLong(getRequest().getSession().getAttribute("cardid").toString()));
            pendingMerchant.setName(getParameter("store").toString());
            pendingMerchant.setAddress(getParameter("address").toString());
            pendingMerchant.setContactName(getParameter("contact").toString());
            pendingMerchant.setContactPhone(getParameter("tel").toString());
            pendingMerchant.setIdCard(getParameter("idcardno").toString());
            // TODO
            if (!PendingMerchant.updatePendingMerchant(pendingMerchant))
                return AjaxActionComplete(false);
            Map map = new HashMap<>();
            map.put("openid", getRequest().getSession().getAttribute("openid"));
            map.put("cid",Long.parseLong(getParameter("cid").toString()));
            List<PendingMerchant> lp =  PendingMerchant.getPendingMerchantById(map);
            getRequest().setAttribute("reginfo",lp.get(0));
            return "register2";
        }
        catch (Exception e){
            return AjaxActionComplete(false);
        }
    }
    public String reg2() {
        try {
//            System.out.println(getRequest().getSession().getAttribute("openid"));
//            map.put("openid", getRequest().getSession().getAttribute("openid"));
//            map.put("cid", getRequest().getSession().getAttribute("cardid"));
//            map.put("city", getParameter("city"));
//            map.put("acountname", getParameter("acountname"));
//            map.put("acountcode", getParameter("acountcode"));
//            map.put("bank", getParameter("bank"));
//            map.put("contactnum", getParameter("contactnum"));
            PendingMerchant pendingMerchant =new PendingMerchant();
            pendingMerchant.setOpenid(getRequest().getSession().getAttribute("openid").toString());
            pendingMerchant.setId(Long.parseLong(getRequest().getSession().getAttribute("cardid").toString()));
            pendingMerchant.setBankCity(getParameter("city").toString());
            pendingMerchant.setAccountName(getParameter("acountname").toString());
            pendingMerchant.setAccountNo(getParameter("acountcode").toString());
            pendingMerchant.setBankName(getParameter("bank").toString());
            pendingMerchant.setBankCode(getParameter("contactnum").toString());
            // TODO
            if (!PendingMerchant.updatePendingMerchant(pendingMerchant))
                return AjaxActionComplete(false);
            Map map = new HashMap<>();
            map.put("openid", getRequest().getSession().getAttribute("openid"));
            map.put("cid",Long.parseLong(getParameter("cid").toString()));
            List<PendingMerchant> lp =  PendingMerchant.getPendingMerchantById(map);
            getRequest().setAttribute("reginfo",lp.get(0));
            return "register3";
        } catch (Exception e) {
            e.printStackTrace();
            return ("");
        }
    }

    public String uploadpic() {
        String msg="";
        try {System.out.println("in");
            File ff = (File) getParameter("fsfzz");//fdphy,fyhkf,fyhkz,fsfzf,fsfzz
            File fm = new File(ProjectSettings.getPicpath() + String.valueOf(getRequest().getSession().getAttribute("cardid"))
                    + getRequest().getSession().getAttribute("openid") + "sfzz.jpg");
            if (null != ff && ff.length() > 1000) {
                PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
            } else if (null == ff || fm.length()<100)
                msg = "身份证正面照文件不对";
            ff = (File) getParameter("fsfzf");//fdphy,fyhkf,fyhkz,fsfzf,fsfzz
            fm = new File(ProjectSettings.getPicpath() + String.valueOf(getRequest().getSession().getAttribute("cardid"))
                    + getRequest().getSession().getAttribute("openid") + "sfzf.jpg");
            if (null != ff && ff.length() > 1000) {
                {
                    PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
                }
            } else  if (null == ff || fm.length()<100)
                msg = "身份证反面照文件不对";
            ff = (File) getParameter("fscsfz");//fdphy,fyhkf,fyhkz,fsfzf,fsfzz
            fm = new File(ProjectSettings.getPicpath() + String.valueOf(getRequest().getSession().getAttribute("cardid"))
                    + getRequest().getSession().getAttribute("openid") + "scsfz.jpg");
            if (null != ff && ff.length() > 1000) {
                {
                    PublicFunc.copyFile(ff.getAbsolutePath(), fm.getAbsolutePath(), true);
                }
            } else  if (null == ff || fm.length()<1000)
                msg = "手持身份证照文件不对";
            ff = (File) getParameter("fyhk");//fdphy,fyhkf,fyhkz,fsfzf,fsfzz
            fm = new File(ProjectSettings.getPicpath() + String.valueOf(getRequest().getSession().getAttribute("cardid"))
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

    public void checkinfo(){
        try {
            String picname = getParameter("picname").toString();
            System.out.println(picname);
            getResponse().setHeader("Pragma", "No-cache");
            getResponse().setHeader("Cache-Control", "no-cache");
            getResponse().setDateHeader("Expires", 0);
            getResponse().setContentType("image/jpeg");
            ServletOutputStream out = null;
            String cardidstr = null== getRequest().getSession().getAttribute("cardid")?getParameter("cid").toString():getRequest().getSession().getAttribute("cardid").toString();
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

    public String queryallrtgsnode(){
        Map<String, String> mapParam = new HashMap<String, String>();
        mapParam.put("cityCode",getParameter("cityCode").toString());
        mapParam.put("clsCode",getParameter("clsCode").toString());
        String pathUrl = "http://www.zybank.com.cn/zyb/queryallrtgsnode";
        String result = HttpPostUrl.sendPost(pathUrl, mapParam);
        List list = new ArrayList<>();
        list.add(result);
        return AjaxActionComplete(list);
    }

    public String Fetchmerchant(){
        Map<Object, Object> param= new HashMap<>();
        List<HashMap> allmerchantlist = null;
        if (null!=getParameter("item").toString() && (!getParameter("item").toString().trim().equals(""))){
            param.put("item",getParameter("item"));
        }
        param.put("status","0");
        // TODO
        //allmerchantlist =DBmap.getpendingmerchant(param);
        Map map=new HashMap<>();
        map.put("totalcount",allmerchantlist.size());
        allmerchantlist.add(0, (HashMap) map);
        return  AjaxActionComplete(allmerchantlist);
    }


    public String selectone(){
        try {
            Map map = new HashMap<>();
            map.put("openid", getParameter("openid"));
            map.put("cid", getParameter("cid"));
            // TODO
            //List<HashMap> lm =  DBmap.getpendingmerchant(map);
            Map mapp = new HashMap<>();
            //getRequest().setAttribute("reginfo",lm.get(0));
            getRequest().setAttribute("openid", getParameter("openid"));
            getRequest().setAttribute("cid", getParameter("cid"));
            return "registerall";
        }
        catch (Exception e){
            return "page404";
        }
    }


    public String checkone() {
        try {
            Map map = new HashMap<>();
            map.put("cid",getParameter("cid").toString());
            map.put("openid",getParameter("openid").toString());
            map.put("status","1");
            if (null!=getParameter("wxpay"))
                map.put("wxpay","1");
            else
                map.put("wxpay","0");
            if (null!=getParameter("alipay"))
                map.put("alipay","1");
            else
                map.put("alipay","0");
            if (null!=getParameter("jdpay"))
                map.put("jdpay","1");
            else
                map.put("jdpay","0");
            if (null!=getParameter("bestpay"))
                map.put("bestpay","1");
            else
                map.put("bestpay","0");
            if (null!=getParameter("canpay"))
                map.put("canpay","1");
            else
                map.put("canpay","0");
            // TODO
            //if (DBmap.updpendingmerchant(map)){
            //    return AjaxActionComplete(DBmap.insertmerchant(map));
            //}
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return AjaxActionComplete(false);
    }
}
