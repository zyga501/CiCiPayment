package cc.database.merchant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PendingMerchant extends MerchantInfo {
    public static void main(String[] args) throws Exception {
        System.exit(0);
    }

    public static PendingMerchant getPendingMerchantById(long id, String openid) {
        String statement = "cc.database.merchant.mapping.pendingMerchant.getPendingMerchantById";
        return Database.Instance().selectOne(statement, new HashMap<String, Object>(){{put("id", id);put("openid", openid);}});
    }

    public static List<Map> getPendingMerchant(Map map) {
        String statement = "cc.database.merchant.mapping.pendingMerchant.getPendingMerchant";
        return Database.Instance().selectList(statement, map);
    }
    public static boolean insertPendingMerchant(PendingMerchant pendingMerchant) {
        String statement = "cc.database.merchant.mapping.pendingMerchant.insertPendingMerchant";
        return Database.Instance().insert(statement, pendingMerchant) == 1;
    }

    public static boolean updatePendingMerchant(PendingMerchant pendingMerchant) {
        String statement = "cc.database.merchant.mapping.pendingMerchant.updatePendingMerchant";
        return Database.Instance().update(statement, pendingMerchant) == 1;
    }

    public static boolean deletePendingMerchant(PendingMerchant pendingMerchant) {
        String statement = "cc.database.merchant.mapping.pendingMerchant.deletePendingMerchant";
        return Database.Instance().delete(statement, pendingMerchant) == 1;
    }
}
