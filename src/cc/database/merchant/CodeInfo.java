package cc.database.merchant;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class CodeInfo {
    public static void main(String[] args) throws Exception {
        List<CodeInfo> codeInfoList = getCodeInfoById(1871367318371400l);
        System.exit(0);
    }

    public static boolean insertCodeInfo(Map map) {
        String statement = "cc.database.merchant.mapping.codeInfo.insertCodeInfo";
        return Database.Instance().insert(statement, map)==1;
    }

    public static List<CodeInfo> getCodeInfoById(long id) {
        String statement = "cc.database.merchant.mapping.codeInfo.getCodeInfoById";
        return Database.Instance().selectList(statement, id);
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

    public Timestamp getMakeDate() {
        return makeDate_;
    }

    public void setMakeDate(Timestamp makeDate) {
        this.makeDate_ = makeDate;
    }

    private long id_;
    private String agentId_;
    private Timestamp makeDate_;
}
