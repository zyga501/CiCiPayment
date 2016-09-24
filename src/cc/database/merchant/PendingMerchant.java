package cc.database.merchant;

import java.util.List;

public class PendingMerchant extends MerchantInfo {
    public static void main(String[] args) throws Exception {
        List<PendingMerchant> pendingMerchantList = getPendingMerchantById(123);
        System.exit(0);
    }

    public static List<PendingMerchant> getPendingMerchantById(long id) {
        String statement = "cc.database.merchant.mapping.pendingMerchant.getPendingMerchantById";
        return Database.Instance().selectList(statement, id);
    }
}
