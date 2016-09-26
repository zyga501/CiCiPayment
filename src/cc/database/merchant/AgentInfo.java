package cc.database.merchant;

import java.util.List;

public class AgentInfo {
    public static void main(String[] args) throws Exception {
        List<AgentInfo> codeInfoList = getAgentInfoById(12);
        System.exit(0);
    }

    public static List<AgentInfo> getAgentInfoById(long id) {
        String statement = "cc.database.merchant.mapping.agentInfo.getAgentInfoById";
        return Database.Instance().selectList(statement, id);
    }

    public static List<AgentInfo> getAgentInfoByName(String  uname) {
        String statement = "cc.database.merchant.mapping.agentInfo.getAgentInfoByName";
        return Database.Instance().selectList(statement, uname);
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

    public String getcontactPhone() {
        return contactPhone_;
    }

    public void setcontactPhone(String contactPhone) {
        this.contactPhone_ = contactPhone;
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

    public String getAccountPhone() {
        return accountPhone_;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone_ = accountPhone;
    }

    public String getAccountName() {
        return accountName_;
    }

    public void setAccountName(String accountName) {
        this.accountName_ = accountName;
    }



    public float getWxProfit() {
        return wxProfit_;
    }

    public void setWxProfit(float wxProfit) {
        this.wxProfit_ = wxProfit;
    }

    public float getWxCost() {
        return wxCost_;
    }

    public void setWxCost(float wxCost) {
        this.wxCost_ = wxCost;
    }

    public float getAliProfit() {
        return aliProfit_;
    }

    public void setAliProfit(float aliProfit) {
        this.aliProfit_ = aliProfit;
    }

    public float getAliCost() {
        return aliCost_;
    }

    public void setAliCost(float aliCost) {
        this.aliCost_ = aliCost;
    }

    public float getJdProfit() {
        return jdProfit_;
    }

    public void setJdProfit(float jdProfit) {
        this.jdProfit_ = jdProfit;
    }

    public float getJdCost() {
        return jdCost_;
    }

    public void setJdCost(float jdCost) {
        this.jdCost_ = jdCost;
    }

    public float getBestProfit() {
        return bestProfit_;
    }

    public void setBestProfit(float bestProfit) {
        this.bestProfit_ = bestProfit;
    }

    public float getBestCost() {
        return bestCost_;
    }

    public void setBestCost(float bestCost) {
        this.bestCost_ = bestCost;
    }

    private long id_;
    private String name_;
    private String contactPhone_;
    private String bankCity_;
    private String bankName_;
    private String bankCode_;
    private String accountNo_;
    private String accountName_;
    private String accountPhone_;
    private float wxProfit_;
    private float wxCost_;
    private float aliProfit_;
    private float aliCost_;
    private float jdProfit_;
    private float jdCost_;
    private float bestProfit_;
    private float bestCost_;
}
