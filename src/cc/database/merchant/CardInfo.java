package cc.database.merchant;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class CardInfo {
    public static boolean insertCardInfo(CardInfo cardInfo) {
        String statement = "cc.database.merchant.mapping.cardInfo.insertCardInfo";
        return Database.Instance().insert(statement, cardInfo)==1;
    }

    public static CardInfo getCardInfoById(long id) {
        String statement = "cc.database.merchant.mapping.cardInfo.getCardInfoById";
        return Database.Instance().selectOne(statement, id);
    }

    public static List<CardInfo> getCardInfoByOrder(String ord) {
        String statement = "cc.database.merchant.mapping.cardInfo.getCardInfoByOrder";
        return Database.Instance().selectList(statement, ord);
    }

    public long getId() {
        return id_;
    }

    public void setId(long id) {
        this.id_ = id;
    }

    public long getAgentid() {
        return agentid_;
    }

    public void setAgentid(long agentId) {
        this.agentid_ = agentId;
    }

    public Timestamp getCreatetime() {
        return createtime_;
    }

    public void setCreatetime(Timestamp createTime) {
        this.createtime_ = createTime;
    }

    public String getOrderno() {
        return orderno_;
    }

    public void setOrderno(String orderno_) {
        this.orderno_ = orderno_;
    }

    public String getSaltcode() {
        return saltcode_;
    }

    public void setSaltcode(String saltcode_) {
        this.saltcode_ = saltcode_;
    }

    private long id_;
    private long agentid_;
    private Timestamp createtime_;
    private String saltcode_;
    private String orderno_;
}
