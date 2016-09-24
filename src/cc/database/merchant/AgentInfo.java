package cc.database.merchant;

import java.util.List;

public class AgentInfo {
    public static void main(String[] args) throws Exception {
        List<AgentInfo> codeInfoList = getAgentInfo(12);
        System.exit(0);
    }

    public static List<AgentInfo> getAgentInfo(long id) {
        String statement = "cc.database.merchant.mapping.agentInfo.getAgentInfoById";
        return Database.Instance().selectList(statement, id);
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

    public String getContactNo() {
        return contactNo_;
    }

    public void setContactNo(String contactNo) {
        this.contactNo_ = contactNo;
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
    private String contactNo_;
    private String bankCity_;
    private String bankName_;
    private String bankCode_;
    private String accountNo_;
    private String accountName_;
    private float wxProfit_;
    private float wxCost_;
    private float aliProfit_;
    private float aliCost_;
    private float jdProfit_;
    private float jdCost_;
    private float bestProfit_;
    private float bestCost_;
}
