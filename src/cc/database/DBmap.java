package cc.database;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBmap {

    public static List<HashMap> getmerchanttemp(Map param) {
        String statement = "cc.database.mapping.merchanttemp.getmerchanttemp";
        return Database.Instance().selectList(statement,param);
    }

    public static boolean updmerchanttemp(Map param) {
        String statement = "cc.database.mapping.merchanttemp.updmerchanttemp";
        return Database.Instance().update(statement, param) > 0;
    }

    public static boolean insertmerchanttemp(Map param) {
        String statement = "cc.database.mapping.merchanttemp.insertmerchanttemp";
        return Database.Instance().insert(statement, param) > 0;
    }

    public static List<HashMap> getcodeinfo(Map param) {
        String statement = "cc.database.mapping.codeinfo.getcodeinfo";
        return Database.Instance().selectList(statement,param);
    }

    public static boolean insertmerchant(Map param) {
        String statement = "cc.database.mapping.merchantinfo.insertmerchant";
        return Database.Instance().insert(statement, param) > 0;
    }
    public static boolean deleteMerchantLink(List param) {
        String statement = "cc.database.mapping.allmerchant.deleteMerchantLink";
        return Database.Instance().insert(statement, param) > 0;
    }
}
