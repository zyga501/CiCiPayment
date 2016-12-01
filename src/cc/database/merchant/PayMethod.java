package cc.database.merchant;

public class PayMethod {
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

    public String getMerchantId() {
        return merchantId_;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId_ = merchantId;
    }

    public String getMethod() {
        return method_;
    }

    public void setMethod(String method) {
        this.method_ = method;
    }

    private int id_;
    private String mode_;
    private String merchantId_;
    private String method_;
}
