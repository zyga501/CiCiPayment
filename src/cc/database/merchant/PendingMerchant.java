package cc.database.merchant;

import java.util.HashMap;
import java.util.List;

public class PendingMerchant extends MerchantInfo {
    public static void main(String[] args) throws Exception {
        List<PendingMerchant> pendingMerchantList = getPendingMerchantById(123, "321");
        System.exit(0);
    }

    public static List<PendingMerchant> getPendingMerchantById(long id, String openid) {
        String statement = "cc.database.merchant.mapping.pendingMerchant.getPendingMerchantById";
        return Database.Instance().selectList(statement, new HashMap<String, Object>(){{put("id", id);put("openid", openid);}});
    }

    public static boolean insertPendingMerchant(PendingMerchant pendingMerchant) {
        String statement = "cc.database.merchant.mapping.pendingMerchant.insertPendingMerchant";
        return Database.Instance().insert(statement, pendingMerchant) == 1;
    }

    public static boolean updatePendingMerchant(PendingMerchant pendingMerchant) {
        String statement = "cc.database.merchant.mapping.pendingMerchant.updatePendingMerchant";
        return Database.Instance().update(statement, pendingMerchant) == 1;
    }
}
