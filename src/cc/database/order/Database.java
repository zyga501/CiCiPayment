package cc.database.order;

import QimCommon.database.SuperDatabase;
import org.apache.ibatis.session.SqlSessionFactory;

public class Database extends SuperDatabase {
    public static void main(String[] args) throws Exception {

    }

    static {
        String mybatisConfig = "cc/database/order/conf.xml";
        sqlSessionFactory_ = SuperDatabase.buildSqlSessionFactory(mybatisConfig);
    }

    public static Database Instance() {
        return instance_;
    }

    private Database() {}

    protected SqlSessionFactory sqlSessionFactory() {
        return sqlSessionFactory_;
    }

    private static final Database instance_ = new Database();
    private static SqlSessionFactory sqlSessionFactory_;
}
