package cc.database.merchant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInfo {

    public static List<UserInfo> getUserInfoByMap(String  uname,String pwd,String roletype,String idlist ) {
        String statement = "cc.database.merchant.mapping.userinfo.getUserInfoByMap";
        return Database.Instance().selectList(statement, new HashMap(){{put("username",uname);put("password",pwd);
            put("roletype",roletype);put("idlist",idlist);}});
    }

    public static boolean updateUserInfo(Map map) {
        String statement = "cc.database.merchant.mapping.userinfo.updateUserInfo";
        return Database.Instance().update(statement, map)==1;
    }

    public static boolean initPassWord(long  id) {
        String statement = "cc.database.merchant.mapping.userinfo.initPassWord";
        return Database.Instance().update(statement, id)==1;
    }

    public static boolean insertUserInfo(UserInfo  userInfo) {
        String statement = "cc.database.merchant.mapping.userinfo.insertUserInfo";
        return Database.Instance().insert(statement, userInfo)==1;
    }
    public long getId() {
        return id_;
    }

    public void setId(long id_) {
        this.id_ = id_;
    }

    public String getUsername() {
        return username_;
    }

    public void setUsername(String username_) {
        this.username_ = username_;
    }

    public String getPassword() {
        return password_;
    }

    public void setPassword(String password_) {
        this.password_ = password_;
    }

    public String getOpenid() {
        return openid_;
    }

    public void setOpenid(String openid_) {
        this.openid_ = openid_;
    }

    public int getRoletype() {
        return roletype_;
    }

    public void setRoletype(int roletype_) {
        this.roletype_ = roletype_;
    }

    private long id_;
    private String username_;
    private String password_;
    private String openid_;
    private int roletype_;
}
