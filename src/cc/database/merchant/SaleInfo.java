package cc.database.merchant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaleInfo {
    public static List<SaleInfo> getsaleInfoByOrderNo(String no) {
        String statement = "cc.database.merchant.mapping.saleInfo.getsaleInfoByOrderNo";
        return Database.Instance().selectList(statement, no);
    }

    public static List<Map> getsaleInfoByMap(String uname,String orderno) {
        String statement = "cc.database.merchant.mapping.saleInfo.getsaleInfoByMap";
        return Database.Instance().selectList(statement, new HashMap(){{put("name",uname);put("orderno",orderno);}});
    }

    public static List<SaleInfo> getsaleInfoByName(String uname) {
        String statement = "cc.database.merchant.mapping.saleInfo.getsaleInfoByName";
        return Database.Instance().selectList(statement, uname);
    }

    public static Boolean insertSaleInfo(SaleInfo saleInfo){
        String statement="cc.database.merchant.mapping.saleInfo.insertSaleInfo";
        return Database.Instance().insert(statement,saleInfo)==1;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public long getId() {
        return id_;
    }

    public void setId(long id_) {
        this.id_ = id_;
    }

    public long getAgentid() {
        return agentid;
    }

    public void setAgentid(long agentid) {
        this.agentid = agentid;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public float getPriceper() {
        return priceper;
    }

    public void setPriceper(float priceper) {
        this.priceper = priceper;
    }

    private long id_;
    private long agentid;
    private String paytype;
    private long num;
    private String memo;
    private String orderno;
    private float priceper;
}
