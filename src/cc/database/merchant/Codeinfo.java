package cc.database.merchant;

import java.sql.Timestamp;
import java.util.List;

public class CodeInfo {
    public static void main(String[] args) throws Exception {
        List<CodeInfo> codeInfoList = getCodeInfo(12332100);
        System.exit(0);
    }

    public static List<CodeInfo> getCodeInfo(long id) {
        String statement = "cc.database.merchant.mapping.codeInfo.getCodeInfoById";
        return Database.Instance().selectList(statement, id);
    }

    public int getId() {
        return id_;
    }

    public void setId(int id) {
        this.id_ = id;
    }

    public String getAgentId() {
        return agentId_;
    }

    public void setAgentId(String agentId) {
        this.agentId_ = agentId;
    }

    public Timestamp getMakeDate() {
        return makeDate_;
    }

    public void setMakeDate(Timestamp makeDate) {
        this.makeDate_ = makeDate;
    }

    private int id_;
    private String agentId_;
    private Timestamp makeDate_;
}
