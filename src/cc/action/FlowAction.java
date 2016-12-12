package cc.action;

import QimCommon.struts.AjaxActionSupport;
import QimCommon.utils.IdWorker;
import QimCommon.utils.StringUtils;
import cc.ProjectSettings;
import cc.database.merchant.*;
import cc.utils.ZipRar;
import cc.utils.Zxing;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class FlowAction extends AjaxActionSupport {

    public String makeOrder(){
        try {
            String agentid = getAttribute("userid").toLowerCase();
            SaleInfo saleInfo =new SaleInfo();
            saleInfo.setNum(Long.parseLong(getParameter("num").toString()));
            saleInfo.setPriceper(Float.parseFloat(getParameter("price").toString()));
            saleInfo.setAgentid(Long.parseLong(agentid));
            SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
            saleInfo.setOrderno(sdf.format(new Date()));
            if (SaleInfo.insertSaleInfo(saleInfo)){
                return AjaxActionComplete(true,new HashMap(){{put("rtval",saleInfo.getOrderno());}});
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return AjaxActionComplete(false);
    }

    public String fetchCiCiOrderNo(){
        Map<Object, Object> param= new HashMap<>();
        List<SaleInfo> allmerchantlist = null;
        allmerchantlist =SaleInfo.getsaleInfoByOrderNo(StringUtils.convertNullableString(getParameter("orderno"),""));
        Map map=new HashMap<>();
        map.put("totalcount",allmerchantlist.size());
        map.put("rtlist",allmerchantlist);
        return  AjaxActionComplete(map);
    }

    public String orderDetail(){
        try {
            List<CardInfo> lc = CardInfo.getCardInfoByOrder(getParameter("ord").toString());
            if ((!getAttribute("roletype").equals("111")) && (lc.get(0).getAgentid()!=Long.parseLong(getAttribute("userid").toString())))
                return AjaxActionComplete(false);
            Map map =new HashMap<>();
            map.put("rtlist",lc);
            return AjaxActionComplete(true,map);
        }catch (Exception e){
            return AjaxActionComplete(false);
        }
    }

    public String orderList(){
        Map<Object, Object> param= new HashMap<>();

        List<Map> allmerchantlist = null;
        allmerchantlist =SaleInfo.getsaleInfoByMap(StringUtils.convertNullableString(getParameter("uname")),StringUtils.
                convertNullableString(getParameter("orderno")));
        if ((!getAttribute("roletype").equals("111")))
            return AjaxActionComplete(false);
        Map map=new HashMap<>();
        map.put("totalcount",allmerchantlist.size());
        allmerchantlist.add(0, map);
        return  AjaxActionComplete(allmerchantlist);
    }

    public String  orderTurnok(){
        if (!getAttribute("roletype").equals("111"))
            return AjaxActionComplete(false);
        List<SaleInfo> sl = SaleInfo.getsaleInfoByOrderNo(getParameter("ord").toString());
        if (sl.size()==1) {
            IdWorker worker2 = new IdWorker(2);
            CardInfo cardInfo = new CardInfo();
            String rtnstr = "";
            for (int i = 0; i < sl.get(0).getNum(); i++) {
                long before = worker2.nextId();
                long after = (before * 100 + System.currentTimeMillis() % 100) ^ 1361753741828L;
                cardInfo.setId(before);
                cardInfo.setAgentid(sl.get(0).getAgentid());
                cardInfo.setOrderno(sl.get(0).getOrderno());
                cardInfo.setSaltcode(String.valueOf(after));
                if (CardInfo.insertCardInfo(cardInfo)) {
                    rtnstr += String.valueOf(after) + ",";
                }
            }
            Map map =new HashMap<>();
            map.clear();
            map.put("idslist", rtnstr.substring(0, rtnstr.length() - 1));
            return AjaxActionComplete(true, map);
        }
        return AjaxActionComplete(false);
    }

    public void downQcode() throws Exception {
        List<CardInfo> cl = CardInfo.getCardInfoByOrder(getParameter("ord").toString());
        if ((!getAttribute("roletype").equals("111")) && (cl.get(0).getAgentid()!=Long.parseLong(getAttribute("userid").toString())))
            return ;
        File[] fs =new File[cl.size()];
        for (int i=0;i<cl.size();i++) {
            String text = ProjectSettings.getData("ccqcodewebpath").toString()+cl.get(i).getSaltcode(); // 二维码内容
            int width = 300; // 二维码图片宽度
            int height = 300; // 二维码图片高度
            String format = "png";// 二维码的图片格式
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");   // 内容所使用字符集编码
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
            // 生成二维码
            File outputFile = new File(ProjectSettings.getData("ccqcodepath").toString() + cl.get(i).getSaltcode() + ".png");
            Zxing.writeToFile(bitMatrix, format, outputFile);
            fs[i] = outputFile;//new File(outputFile.getPath());
        }
        ZipRar.ZipFiles(fs,new File(ProjectSettings.getData("ccqcodepath").toString()+getParameter("ord").toString()+".rar"));
        //获得请求文件名

        //设置文件MIME类型
        getResponse().setContentType(getRequest().getServletContext().getMimeType(getParameter("ord").toString()+".rar"));
        //设置Content-Disposition
        getResponse().setHeader("Content-Disposition", "attachment;filename="+getParameter("ord").toString()+".rar");
        //System.out.println(fullFileName);
        //读取文件
        InputStream in = new FileInputStream(ProjectSettings.getData("ccqcodepath").toString()+getParameter("ord").toString()+".rar");
        OutputStream out = getResponse().getOutputStream();
        //写文件
        int b;
        while((b=in.read())!= -1)
        {
            out.write(b);
        }

        in.close();
        out.close();
    }
}