package cptn.xml;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cptn.util.ProcessResult;

public class XmlParser {

    /**
     * 解析联通传输的 XML 通知
     *
     * @param xml
     * @return
     */
    public static ProcessResult parseXml(String xml) {
        ProcessResult pr = new ProcessResult();
        XmlNode xr;

        try {
            Document doc = DocumentHelper.parseText(xml);
            Element root = doc.getRootElement();

            xr = x(root);
            xr.setLevel(0);
            parse(root, xr);

            pr.setOK();
            pr.putDefData(xr);

        } catch (Exception e) {
            pr.setErr();
            pr.addMsg(e.getMessage());
        }

        return pr;
    }

    /**
     * 将 Dom4j 节点转换成 XmlNode
     *
     * @param element
     * @return
     */
    private static XmlNode x(Element element) {
        XmlNode xn = new XmlNode(element.getName(), element.getText());

        List<Attribute> list = element.attributes();

        for (Attribute ax : list) {
            xn.putAttr(ax.getName(), ax.getValue());
        }

        return xn;
    }

    /**
     * @param root
     * @param xr
     */
    private static void parse(Element root, XmlNode xr) {
        for (Iterator<Element> iter = root.elementIterator(); iter.hasNext(); ) {
            Element node = iter.next();
            XmlNode xn = x(node);
            xn.setLevel(xr.getLevel() + 1);

            xr.putChild(xn.getName(), xn);
            parse(node, xn);
        }
    }
}
