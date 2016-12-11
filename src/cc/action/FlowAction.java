package cc.action;

import QimCommon.struts.AjaxActionSupport;
import QimCommon.utils.IdWorker;
import QimCommon.utils.StringUtils;
import cc.database.merchant.*;

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
            List<String> lc = CardInfo.getCardInfoByOrder(getParameter("ord").toString());
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
        Map map=new HashMap<>();
        map.put("totalcount",allmerchantlist.size());
        allmerchantlist.add(0, map);
        return  AjaxActionComplete(allmerchantlist);
    }

    public String  orderturnok(){
        List<SaleInfo> sl = SaleInfo.getsaleInfoByOrderNo(getParameter("ord").toString());
        if (sl.size()==1) {
            IdWorker worker2 = new IdWorker(2);
            Map map = new HashMap<>();
            String rtnstr = "";
            for (int i = 0; i < sl.get(0).getNum(); i++) {
                long before = worker2.nextId();
                map.clear();
                map.put("id", before);
                map.put("agentid", sl.get(0).getAgentid());
                if (CardInfo.insertCardInfo(map)) {
                    long after = (before * 100 + System.currentTimeMillis() % 100) ^ 1361753741828L;
                    rtnstr += String.valueOf(after) + ",";
                }
            }
            map.clear();
            map.put("idslist", rtnstr.substring(0, rtnstr.length() - 1));
            return AjaxActionComplete(true, map);
        }
        return AjaxActionComplete(false);
    }

}