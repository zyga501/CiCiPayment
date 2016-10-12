package cc.database.order;

import java.sql.Timestamp;
import java.util.List;

public class PayOrderInfo {
    public static List<PayOrderInfo> getOrderInfo() {
        String statement = "cc.database.order.mapping.payOrderInfo.getOrderInfo";
        return Database.Instance().selectList(statement);
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

    public boolean getPaid() {
        return paid_;
    }

    public void setPaid(boolean paid) {
        paid_ = paid;
    }

    public Timestamp getCreateTime() {
        return createTime_;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime_ = createTime;
    }

    private long id_;
    private long merchantId_;
    private String tradeNo_;
    private int tradeAmount_;
    private String tradeType_;
    private String tradeTime_;
    private boolean paid_;
    private Timestamp createTime_;
}
