package cc.database.merchant;

public class Codeinfo {
    private int cid;
    private String  uname="" ;
    private String saleorder="" ;
    private String  makedate="" ;
    private int merchantid;
    private String openid="" ;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getSaleorder() {
        return saleorder;
    }

    public void setSaleorder(String saleorder) {
        this.saleorder = saleorder;
    }

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }

    public int getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(int merchantid) {
        this.merchantid = merchantid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

}
