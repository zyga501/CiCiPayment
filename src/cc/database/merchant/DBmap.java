package cc.database.merchant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBmap {

    public static List<HashMap> getpendingmerchant(Map param) {
        String statement = "cc.database.merchant.mapping.pendingmerchant.getpendingmerchant";
        return Database.Instance().selectList(statement,param);
    }

    public static boolean updpendingmerchant(Map param) {
        String statement = "cc.database.merchant.mapping.pendingmerchant.updpendingmerchant";
        return Database.Instance().update(statement, param) > 0;
    }

    public static boolean insertpendingmerchant(Map param) {
        String statement = "cc.database.merchant.mapping.pendingmerchant.insertpendingmerchant";
        return Database.Instance().insert(statement, param) > 0;
    }

    public static List<HashMap> getcodeinfo(Map param) {
        String statement = "cc.database.merchant.mapping.codeinfo.getcodeinfo";
        return Database.Instance().selectList(statement,param);
    }

    public static boolean insertmerchant(Map param) {
        String statement = "cc.database.merchant.mapping.merchantinfo.insertmerchant";
        return Database.Instance().insert(statement, param) > 0;
    }
    public static boolean deleteMerchantLink(List param) {
        String statement = "cc.database.merchant.mapping.allmerchant.deleteMerchantLink";
        return Database.Instance().insert(statement, param) > 0;
    }
}