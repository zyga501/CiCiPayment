package cc.database.order;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayOrderInfo {
    public static List<PayOrderInfo> getOrderInfo() {
        String statement = "cc.database.order.mapping.payOrderInfo.getOrderInfo";
        return Database.Instance().selectList(statement);
    }

    public static PayOrderInfo getOrderInfoByTradeNo(String tradeNo) {
        String statement = "cc.database.order.mapping.payOrderInfo.getOrderInfoByTradeNo";
        return Database.Instance().selectOne(statement, tradeNo);
    }

    public static List<Map> getOrderInfoByLimit(Map map) {
        String statement = "cc.database.order.mapping.payOrderInfo.getOrderInfoByLimit";
        return Database.Instance().selectList(statement, map);
    }

    public static List<Map> getOrderInfoCrossByLimit(Map map) {
        String statement = "cc.database.order.mapping.payOrderInfo.getOrderInfoCrossByLimit";
        return Database.Instance().selectList(statement, map);
    }

    public static List<PayOrderInfo> getOrderInfoTotalByLimit(Map map) {
        String statement = "cc.database.order.mapping.payOrderInfo.getOrderInfoTotalByLimit";
        return Database.Instance().selectList(statement, map);
    }

    public static List<Map> getPayAndChanAsc(long merchantid,int id,int pagecontent  ) {
        String statement = "cc.database.order.mapping.payOrderInfo.getPayAndChanAsc";
        return Database.Instance().selectList(statement, new HashMap(){{put("merchantid",merchantid);put("id",id);put("pagecontent",pagecontent);}});
    }

    public static List<Map> getPayAndChanDesc(long merchantid,int id,int pagecontent  ) {
        String statement = "cc.database.order.mapping.payOrderInfo.getPayAndChanDesc";
        return Database.Instance().selectList(statement, new HashMap(){{put("merchantid",merchantid);put("id",id);put("pagecontent",pagecontent);}});
    }

    public static Long getOrderTotal() {
        String statement = "cc.database.order.mapping.payOrderInfo.getOrderTotal";
        return Database.Instance().selectOne(statement);
    }

    public static boolean insertOrderInfo(PayOrderInfo payOrderInfo) {
        String statement = "cc.database.order.mapping.payOrderInfo.insertOrderInfo";
        return Database.Instance().insert(statement, payOrderInfo) == 1;
    }

    public long getId() {
        return id_;
    }

    public void setId(long id) {
        this.id_ = id;
    }

    public long getMerchantId() {
        return merchantId_;
    }

    public void setMerchantId(long merchantId) {
        this.merchantId_ = merchantId;
    }

    public String getTradeNo() {
        return tradeNo_;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo_ = tradeNo;
    }

    public int getTradeAmount() {
        return tradeAmount_;
    }

    public void setTradeAmount(int tradeAmount) {
        this.tradeAmount_ = tradeAmount;
    }

    public String getTradeType() {
        return tradeType_;
    }

    public void setTradeType(String tradeType) {
        this.tradeType_ = tradeType;
    }

    public String getTradeTime() {
        return tradeTime_;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime_ = tradeTime;
    }

    public double getTradeRate() {
        return tradeRate_;
    }

    public void setTradeRate(double tradeRate) {
        this.tradeRate_ = tradeRate;
    }

    public boolean getPaid() {
        return paid_;
    }

    public void setPaid(boolean paid) {
        paid_ = paid;
    }

    public Timestamp getCreatetime() {
        return createTime_;
    }

    public void setCreatetime(Timestamp createTime) {
        this.createTime_ = createTime;
    }

    private long id_;
    private long merchantId_;
    private String tradeNo_;
    private int tradeAmount_;
    private String tradeType_;
    private String tradeTime_;
    private double tradeRate_;
    private boolean paid_;
    private Timestamp createTime_;
}
