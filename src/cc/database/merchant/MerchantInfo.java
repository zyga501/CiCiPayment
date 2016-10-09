package cc.database.merchant;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class MerchantInfo {
    public static void main(String[] args) throws Exception {
        System.exit(0);
    }

    public static List<MerchantInfo> getMerchantInfoByMap(Map map) {
        String statement = "cc.database.merchant.mapping.merchantInfo.getMerchantInfoByMap";
        return Database.Instance().selectList(statement, map);
    }

    public static MerchantInfo getMerchantInfoById(long id) {
        String statement = "cc.database.merchant.mapping.merchantInfo.getMerchantInfoById";
        return Database.Instance().selectOne(statement, id);
    }

    public static boolean insertMerchantInfo(MerchantInfo merchantInfo) {
        String statement = "cc.database.merchant.mapping.merchantInfo.insertMerchantInfo";
        return Database.Instance().insert(statement, merchantInfo)==1;
    }

    public long getId() {
        return id_;
    }

    public void setId(long id) {
        this.id_ = id;
    }

    public String getName() {
        return name_;
    }

    public void setName(String name) {
        this.name_ = name;
    }

    public String getAddress() {
        return address_;
    }

    public void setAddress(String address) {
        this.address_ = address;
    }

    public String getContactName() {
        return contactName_;
    }

    public void setContactName(String contactName) {
        this.contactName_ = contactName;
    }

    public String getContactPhone() {
        return contactPhone_;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone_ = contactPhone;
    }

    public String getIdCard() {
        return idCard_;
    }

    public void setIdCard(String idCard) {
        this.idCard_ = idCard;
    }

    public String getIdCardPF() {
        return idCardPF_;
    }

    public void setIdCardPF(String idCardPF) {
        this.idCardPF_ = idCardPF;
    }

    public String getIdCardPB() {
        return idCardPB_;
    }

    public void setIdCardPB(String idCardPB) {
        this.idCardPB_ = idCardPB;
    }

    public String getIdCardPC() {
        return idCardPC_;
    }

    public void setIdCardPC(String idCardPC) {
        this.idCardPC_ = idCardPC;
    }

    public Timestamp getRegisterDate() {
        return registerDate_;
    }

    public void setRegisterDate(Timestamp registerDate) {
        this.registerDate_ = registerDate;
    }

    public boolean isPaymentStatus() {
        return paymentStatus_;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus_ = paymentStatus;
    }

    public boolean isWxStatus() {
        return wxStatus_;
    }

    public void setWxStatus(boolean wxStatus) {
        this.wxStatus_ = wxStatus;
    }

    public float getWxRate() {
        return wxRate_;
    }

    public void setWxRate(float wxRate) {
        this.wxRate_ = wxRate;
    }

    public boolean isAliStatus() {
        return aliStatus_;
    }

    public void setAliStatus(boolean aliStatus) {
        this.aliStatus_ = aliStatus;
    }

    public float getAliRate() {
        return aliRate_;
    }

    public void setAliRate(float aliRate) {
        this.aliRate_ = aliRate;
    }

    public boolean isJdStatus() {
        return jdStatus_;
    }

    public void setJdStatus(boolean jdStatus) {
        this.jdStatus_ = jdStatus;
    }

    public float getJdRate() {
        return jdRate_;
    }

    public void setJdRate(float jdRate) {
        this.jdRate_ = jdRate;
    }

    public boolean isBestStatus() {
        return bestStatus_;
    }

    public void setBestStatus(boolean bestStatus) {
        this.bestStatus_ = bestStatus;
    }

    public float getBestRate() {
        return bestRate_;
    }

    public void setBestRate(float bestRate) {
        this.bestRate_ = bestRate;
    }

    public String getBankCity() {
        return bankCity_;
    }

    public void setBankCity(String bankCity) {
        this.bankCity_ = bankCity;
    }

    public String getBankName() {
        return bankName_;
    }

    public void setBankName(String bankName) {
        this.bankName_ = bankName;
    }

    public String getBankCode() {
        return bankCode_;
    }

    public void setBankCode(String bankCode) {
        this.bankCode_ = bankCode;
    }

    public String getAccountNo() {
        return accountNo_;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo_ = accountNo;
    }

    public String getAccountName() {
        return accountName_;
    }

    public void setAccountName(String accountName) {
        this.accountName_ = accountName;
    }

    public String getAccountPhone() {
        return accountPhone_;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone_ = accountPhone;
    }

    public String getOpenid() {
        return openid_;
    }

    public void setOpenid(String openid_) {
        this.openid_ = openid_;
    }

    private long id_;
    private String name_;
    private String address_;
    private String contactName_;
    private String contactPhone_;
    private String idCard_;
    private String idCardPF_;
    private String idCardPB_;
    private String idCardPC_;
    private Timestamp registerDate_;
    private boolean paymentStatus_;
    private boolean wxStatus_;
    private float wxRate_;
    private boolean aliStatus_;
    private float aliRate_;
    private boolean jdStatus_;
    private float jdRate_;
    private boolean bestStatus_;
    private float bestRate_;
    private String bankCity_;
    private String bankName_;
    private String bankCode_;
    private String accountNo_;
    private String accountName_;
    private String accountPhone_;
    private String openid_;
}
