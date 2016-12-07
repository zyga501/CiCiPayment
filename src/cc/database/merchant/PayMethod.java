package cc.database.merchant;

public class PayMethod {
    public static void main(String[] args) throws Exception {
        PayMethod payMethod = PayMethod.getPayMethodById(2);
        System.out.print("");
    }

    public static PayMethod getPayMethodById(int id) {
        String statement = "cc.database.merchant.mapping.payMethod.getPayMethodById";
        return Database.Instance().selectOne(statement, id);
    }

    public int getId() {
        return id_;
    }

    public void setId(int id) {
        this.id_ = id;
    }

    public String getMode() {
        return mode_;
    }

    public void setMode(String mode) {
        this.mode_ = mode;
    }

    public long getMerchantId() {
        return merchantId_;
    }

    public void setMerchantId(long merchantId) {
        this.merchantId_ = merchantId;
    }

    public String getMethod() {
        return method_;
    }

    public void setMethod(String method) {
        this.method_ = method;
    }

    public boolean isPrivateQualification() {
        return privateQualification_;
    }

    public void setPrivateQualification(boolean privateQualification) {
        this.privateQualification_ = privateQualification;
    }

    public PayType getPayType() {
        return payType_;
    }

    public void setPayType(PayType payType) {
        this.payType_ = payType;
    }

    public String getComment() {
        return comment_;
    }

    public void setComment(String comment) {
        this.comment_ = comment;
    }

    private int id_;
    private String mode_;
    private long merchantId_;
    private String method_;
    private boolean privateQualification_;
    public enum PayType {
        WEIXIN,
        ALI,
        JD,
        BEST
    };
    private PayType payType_;
    private String comment_;
}
