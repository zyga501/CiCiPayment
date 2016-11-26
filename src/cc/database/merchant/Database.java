package cc.database.merchant;

import QimCommon.database.SuperDatabase;
import org.apache.ibatis.session.SqlSessionFactory;

public class Database extends SuperDatabase {

    static {
        String mybatisConfig = "cc/database/merchant/conf.xml";
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
