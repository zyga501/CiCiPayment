package cc.utils;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

public class XMLDo {

    public static int setElementValue(String filename, String tagname, String val) {
    int returnValue = 0;
    try {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(new File(filename));
        /**
         * 修改内容之二: 把owner项内容改为Tshinghua
         * 并在owner节点中加入date节点,date节点的内容为2004-09-11,还为date节点添加一个属性type
         */
        List list = document.selectNodes("/project/"+tagname);
        Iterator iter = list.iterator();
        if (iter.hasNext()) {
            Element ownerElement = (Element) iter.next();
            ownerElement.setText(val);
        }
        try {
            /** 将document中的内容写入文件中 */
            XMLWriter writer = new XMLWriter(new FileOutputStream(new File(filename)));
            writer.write(document);
            writer.close();
            /** 执行成功,需返回1 */
            returnValue = 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
    return returnValue;
}
}
