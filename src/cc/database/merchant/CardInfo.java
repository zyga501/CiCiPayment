package cc.database.merchant;

import java.sql.Timestamp;
import java.util.Map;

public class CardInfo {
    public static void main(String[] args) throws Exception {
        System.exit(0);
    }

    public static boolean insertCardInfo(Map map) {
        String statement = "cc.database.merchant.mapping.cardInfo.insertCardInfo";
        return Database.Instance().insert(statement, map)==1;
    }

    public static CardInfo getCardInfoById(long id) {
        String statement = "cc.database.merchant.mapping.cardInfo.getCardInfoById";
        return Database.Instance().selectOne(statement, id);
    }

    public long getId() {
        return id_;
    }

    public void setId(long id) {
        this.id_ = id;
    }

    public String getAgentId() {
        return agentId_;
    }

    public void setAgentId(String agentId) {
        this.agentId_ = agentId;
    }

    public Timestamp getCreateDate() {
        return createTime_;
    }

    public void setCreateDate(Timestamp createTime) {
        this.createTime_ = createTime;
    }

    private long id_;
    private String agentId_;
    private Timestamp createTime_;
}
