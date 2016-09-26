package cc.database.merchant;

import java.util.Arrays;
import java.util.List;

public class PendingMerchant extends MerchantInfo {
    public static void main(String[] args) throws Exception {
    }

    public static List<PendingMerchant> getPendingMerchantById(long id, String openid) {
        String statement = "cc.database.merchant.mapping.pendingMerchant.getPendingMerchantById";
        return Database.Instance().selectList(statement, (Arrays.asList(id, openid)));
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
