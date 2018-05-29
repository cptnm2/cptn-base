package cptn.xml;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import cptn.util.JsonUtil;
import cptn.util.StrUtil;

public class XmlNode implements Serializable {
	private static final long serialVersionUID = 1L;

	private int level;
	
	private String id;
	private String name;
	private String text;

	private Map<String, String> attrMap;
	private Map<String, XmlNode> childMap;

	public XmlNode() {
		init();
	}
	
	public XmlNode(String name, String text) {
		setName(name);
		setText(text);
		
		init();
	}
	
	private void init() {
		attrMap = new LinkedHashMap<String, String>();
		childMap = new LinkedHashMap<String, XmlNode>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = StrUtil.INSTANCE.safeTrim2(name);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = StrUtil.INSTANCE.safeTrim2(text);
	}

	public Map<String, String> getAttrMap() {
		return attrMap;
	}

	public Map<String, XmlNode> getChildMap() {
		return childMap;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void putAttr(String key, String value) {
		attrMap.put(key, value);
	}
	
	public String getAttr(String key) {
		return attrMap.get(key);
	}
	
	public void removeAttr(String key) {
		attrMap.remove(key);
	}
	
	public void putChild(String key, XmlNode node) {
		childMap.put(key, node);
	}
	
	public XmlNode getChild(String key) {
		return childMap.get(key);
	}
	
	public void removeChild(String key) {
		childMap.remove(key);
	}
	
	@Override
	public String toString() {
		String prefix = "";

		for (int i = 0; i < level; i++) {
			prefix += "\t";
		}
		
		StringBuffer buff = new StringBuffer();
		buff.append(String.format("%s>>> %s: %s\n", prefix, name, text));
		
		if (!attrMap.isEmpty()) {
			buff.append(prefix);
			buff.append("\tAttr: ");
			buff.append(JsonUtil.mapToJson(attrMap));
			buff.append("\n");
		}
		
		if (!childMap.isEmpty()) {
			for (Iterator<Entry<String, XmlNode>> it = childMap.entrySet().iterator(); it.hasNext(); ) {
				Entry<String, XmlNode> en = it.next();
				
				buff.append(en.getValue().toString());
			}
		}
		
		return buff.toString();
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
