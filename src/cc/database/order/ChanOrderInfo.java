package cc.database.order;

import java.sql.Timestamp;
import java.util.List;

public class ChanOrderInfo {
    public static List<ChanOrderInfo> getOrderInfo() {
        String statement = "cc.database.order.mapping.chanOrderInfo.getOrderInfo";
        return Database.Instance().selectList(statement);
    }

    public static boolean insertOrderInfo(ChanOrderInfo chanOrderInfo) {
        String statement = "cc.database.order.mapping.chanOrderInfo.insertOrderInfo";
        return Database.Instance().insert(statement, chanOrderInfo) == 1;
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

    public String getPayTradeNo() {
        return payTradeNo_;
    }

    public void setPayTradeNo(String payTradeNo) {
        payTradeNo_ = payTradeNo;
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

    public String getTradeTime() {
        return tradeTime_;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime_ = tradeTime;
    }

    public Timestamp getCreateTime() {
        return createTime_;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime_ = createTime;
    }

    private long id_;
    private long merchantId_;
    private String payTradeNo_;
    private String tradeNo_;
    private int tradeAmount_;
    private String tradeTime_;
    private Timestamp createTime_;
}
