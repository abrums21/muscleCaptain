package com.chenglong.muscle.update;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ParseXmlService {

	public HashMap<String, String> parseXml(String file) throws Exception
	{
		HashMap<String, String> map = new HashMap<String, String>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		FileInputStream is = new FileInputStream(new File(file));
		Document document = builder.parse(is);
		
		Element root = document.getDocumentElement();
		NodeList list = root.getChildNodes();
		for (int i = 0; i < list.getLength(); i++)
		{
			Node node = list.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element element = (Element)node;
				if ("version".equals(element.getNodeName()))
				{
					String version = element.getFirstChild().getNodeValue();
					map.put("version", version);
				}
				else if ("name".equals(element.getNodeName()))
				{
					String name = element.getFirstChild().getNodeValue();
					map.put("name", name);
				}
				else if ("info".equals(element.getNodeName()))
				{
					String info = element.getFirstChild().getNodeValue();
					map.put("info", info);
				}
				else if ("url".equals(element.getNodeName()))
				{
					String url = element.getFirstChild().getNodeValue();
					map.put("url", url);
				}
			}
		}
		return map;
	}
}
