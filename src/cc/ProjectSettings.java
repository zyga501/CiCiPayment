package cc;

import QimCommon.utils.PathUtils;
import cc.utils.XMLDo;
import QimCommon.utils.XMLParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Map;

public class ProjectSettings {
    static {
        try {
            String projectSettingsPath = PathUtils.getProjectPath() + "project.xml";
            projectSettings_ = XMLParser.convertMapFromXmlFile(projectSettingsPath);
        }
        catch (Exception exception) {
            ProjectLogger.error(exception.getMessage());
        }
    }

    public static String getTopPackagePath() {
        String projectPath = PathUtils.getProjectPath();
        String topPackageName = ProjectSettings.class.getPackage().getName();
        return projectPath.concat(topPackageName);
    }

    public static long getId() {
        try {
            if (projectSettings_ != null && projectSettings_.containsKey("id")) {
                return Long.parseLong(projectSettings_.get("id").toString());
            }
        }
        catch (NumberFormatException exception) {

        }

        ProjectLogger.error("ProjectSettings.getId() Failed!");
        return 0;
    }

    public static String getName() {
        try {
            if (projectSettings_ != null && projectSettings_.containsKey("name")) {
                return projectSettings_.get("name").toString();
            }
        }
        catch (NumberFormatException exception) {

        }

        ProjectLogger.error("ProjectSettings.getName() Failed!");
        return "";
    }

    public static long getIdWorkerSeed() {
        try {
            if (projectSettings_ != null && projectSettings_.containsKey("idWorkerSeed")) {
                return Long.parseLong(projectSettings_.get("idWorkerSeed").toString());
            }
        }
        catch (NumberFormatException exception) {

        }

        ProjectLogger.error("ProjectSettings.getIdWorkerSeed() Failed!");
        return 0;
    }

    public static String getCachePath() {
        try {
            if (projectSettings_ != null && projectSettings_.get("CachePath") != null) {
                return (projectSettings_.get("CachePath").toString());
            }
        }
        catch (NumberFormatException exception) {

        }

        return "";
    }

    public enum SettleMethod
    {
        t0,
        t1
    };
    public static SettleMethod getSettleMethod() {
        try {
            if (projectSettings_ != null && projectSettings_.get("SettleMethod") != null) {
                return Long.parseLong(projectSettings_.get("SettleMethod").toString()) == 1 ? SettleMethod.t1 : SettleMethod.t0;
            }
        }
        catch (NumberFormatException exception) {

        }

        return SettleMethod.t0;
    }

    public static long getSettleLimit() {
        try {
            if (projectSettings_ != null && projectSettings_.get("SettleLimit") != null) {
                return Long.parseLong(projectSettings_.get("SettleLimit").toString());
            }
        }
        catch (NumberFormatException exception) {

        }

        return 0;
    }

    public static Object getData(String key) {
        if (projectSettings_ != null && projectSettings_.containsKey(key)) {
            return projectSettings_.get(key);
        }

        return  null;
    }

    public static void setData(String tagName,String val) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        XMLDo.setElementValue(PathUtils.getProjectPath() + "project.xml",tagName,val);
    }

    public static Map<String, Object> getMapData(String key) {
        return (Map<String, Object>)getData(key);
    }

    private static Map<String, Object> projectSettings_;
}
