package cc.database.merchant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuTree {
    public static List<MenuTree> getAllMenuNode() {
        String statement = "cc.database.merchant.mapping.menutree.getAllMenuNode";
        return Database.Instance().selectList(statement);
    }

    public static List<MenuTree> getMenuNodeByPreId(long preid,long roleid) {
        String statement = "cc.database.merchant.mapping.menutree.getMenuNodeByPreId";
        Map<String, Object> param=new HashMap<String, Object>();
        param.put("preid",preid);
        param.put("roleid",roleid);
        return Database.Instance().selectList(statement, param);
    }
    public static List<MenuTree> getMenuNodeByUid(long uid) {
        String statement = "cc.database.merchant.mapping.menutree.getMenuNodeByUid";
        return Database.Instance().selectList(statement, uid);
    }

    public String getLabelico() {
        return labelico_;
    }

    public void setLabelico(String labelico_) {
        this.labelico_ = labelico_;
    }


    public long getId() {
        return id_;
    }

    public void setId(long id_) {
        this.id_ = id_;
    }

    public long getPreid() {
        return preid_;
    }

    public void setPreid(long preid_) {
        this.preid_ = preid_;
    }

    public String getMenuname() {
        return menuname_;
    }

    public void setMenuname(String menuname_) {
        this.menuname_ = menuname_;
    }

    public String getWebpath() {
        return webpath_;
    }

    public void setWebpath(String webpath_) {
        this.webpath_ = webpath_;
    }

    public long getMenuorder() {
        return menuorder_;
    }

    public void setMenuorder(long menuorder_) {
        this.menuorder_ = menuorder_;
    }

    private long id_;
    private long preid_;
    private String menuname_;
    private String webpath_;
    private String labelico_;
    private long menuorder_;
}