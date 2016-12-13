package cc.database.merchant;

import QimCommon.utils.JsonUtils;
import QimCommon.utils.StringUtils;
import net.sf.json.JSONObject;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MerchantInfo {
    public static List<MerchantInfo> getMerchantInfoByMap(Map map) {
        String statement = "cc.database.merchant.mapping.merchantInfo.getMerchantInfoByMap";
        return Database.Instance().selectList(statement, map);
    }

    public static MerchantInfo getMerchantInfoById(long id) {
        String statement = "cc.database.merchant.mapping.merchantInfo.getMerchantInfoById";
        return Database.Instance().selectOne(statement, id);
    }

    public static List<Map> getMerchantInfoByQuery(Map map) {
        String statement = "cc.database.merchant.mapping.merchantInfo.getMerchantInfoByQuery";
        return Database.Instance().selectList(statement, map);
    }

    public static boolean updateMerchantPayMethodId(long id, long payMethonId) {
        String statement = "cc.database.merchant.mapping.merchantInfo.updateMerchantPayMethodId";
        return Database.Instance().update(statement, new HashMap<String, Object>(){{put("id", id);put("payMethonId", payMethonId);}}) == 1;
    }

    public static boolean updateMerchantPayBycheck(MerchantInfo merchantInfo) {
        String statement = "cc.database.merchant.mapping.merchantInfo.updateMerchantPayBycheck";
        return Database.Instance().update(statement, merchantInfo) == 1;
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
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Timestamp getRegisterDate() {
        return registerDate_;
    }

    public void setRegisterDate(Timestamp registerDate) {
        this.registerDate_ = registerDate;
    }

    public boolean getPaymentStatus() {
        return paymentStatus_;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus_ = paymentStatus;
    }

    public boolean getWxStatus() {
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

    public boolean getAliStatus() {
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

    public boolean getJdStatus() {
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

    public boolean getBestStatus() {
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

    public String getBankGeneralName() {
        return bankGeneralName_;
    }

    public void setBankGeneralName(String bankGeneralName) {
        this.bankGeneralName_ = bankGeneralName;
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

    public int getPayMethodWeixinId() {
        return payMethodWeixinId_;
    }

    public void setPayMethodWeixinId(int payMethodWeixinId) {
        payMethodWeixinId_ = payMethodWeixinId;
    }

    public int getPayMethodAliId() {
        return payMethodAliId_;
    }

    public void setPayMethodAliId(int payMethodAliId) {
        this.payMethodAliId_ = payMethodAliId;
    }

    public int getPayMethodJDId() {
        return payMethodJDId_;
    }

    public void setPayMethodJDId(int payMethodJDId) {
        this.payMethodJDId_ = payMethodJDId;
    }

    public int getPayMethodBestId() {
        return payMethodBestId_;
    }

    public void setPayMethodBestId(int payMethodBestId) {
        this.payMethodBestId_ = payMethodBestId;
    }

    public String getExternInfo() {
        return externInfo_;
    }

    public void setExternInfo(String externInfo) {
        externInfo_ = externInfo;
    }

    public class ExternInfo {
        public ExternInfo() {

        }

        public ExternInfo(String json) {
            Map<String, Object> resultMap = JsonUtils.toMap(json, true);
            if (resultMap == null) {
                return;
            }

            idCardPF = StringUtils.convertNullableString(resultMap.get("idCardPF"));
            idCardPB = StringUtils.convertNullableString(resultMap.get("idCardPB"));
            idCardPC = StringUtils.convertNullableString(resultMap.get("idCardPC"));
        }

        public String toString() {
            return JSONObject.fromObject(this).toString();
        }

        public String getIdCardPF() {
            return idCardPF;
        }

        public void setIdCardPF(String idCardPF) {
            this.idCardPF = idCardPF;
        }

        public String getIdCardPB() {
            return idCardPB;
        }

        public void setIdCardPB(String idCardPB) {
            this.idCardPB = idCardPB;
        }

        public String getIdCardPC() {
            return idCardPC;
        }

        public void setIdCardPC(String idCardPC) {
            this.idCardPC = idCardPC;
        }

        private String idCardPF;
        private String idCardPB;
        private String idCardPC;
    }

    private long id_;
    private String name_;
    private String address;
    private String contactName;
    private String contactPhone;
    private String idCard;
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
    private String bankGeneralName_;
    private String bankName_;
    private String bankCode_;
    private String accountNo_;
    private String accountName_;
    private String accountPhone_;
    private String openid_;
    private int payMethodWeixinId_;
    private int payMethodAliId_;
    private int payMethodJDId_;
    private int payMethodBestId_;
    private String externInfo_;
}
