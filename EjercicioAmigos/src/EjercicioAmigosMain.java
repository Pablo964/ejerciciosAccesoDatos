import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class EjercicioAmigosMain 
{
	public static void main(String[] args) throws ParserConfigurationException,
		SAXException, IOException, ParseException
	{
		List<Amigo> listaXML = new ArrayList<>();
		List<Amigo> listaJSON = new ArrayList<>();
		
		File inputFile = new File("amigos.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();
		
		NodeList nList = doc.getElementsByTagName("Amigo");
		for (int i = 0; i < nList.getLength(); i++) 
		{
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) 
			{
				Element eElement = (Element) nNode;
				String nombre = eElement.getElementsByTagName("Nombre").item(0).getTextContent();
				String edad = eElement.getElementsByTagName("Edad").item(0)
						.getTextContent();
				String telefono = eElement.getElementsByTagName("Telefono").item(0)
						.getTextContent();
				listaXML.add((new Amigo(nombre, edad, telefono)));
			}
		}
		
		JSONObject jo;
		jo = (JSONObject) new JSONParser().parse(new FileReader("amigos.json"));
		JSONArray jArray = (JSONArray) ((JSONObject) jo.get("Amigos")).get("Amigo");
		Iterator<Map.Entry> itr1;
		Iterator itr2 = jArray.iterator();
		
		String nombre = "";
		String edad = "";
		String telefono = "";
		while (itr2.hasNext()) 
		{
			itr1 = ((Map) itr2.next()).entrySet().iterator();
			while (itr1.hasNext()) 
			{
				Map.Entry pareja = itr1.next();
				if (pareja.getKey().equals("Nombre")) 
				{
					nombre = (String) pareja.getValue();
				}
				if (pareja.getKey().equals("Edad")) 
				{
					edad = (String)pareja.getValue();
				}
				if (pareja.getKey().equals("Telefono"))
				{
					telefono = (String)pareja.getValue();
				}
			}
			listaJSON.add(new Amigo(nombre, edad, telefono));
		}
		
		boolean todosIguales = true;
		int correctos = 0;
		for (Object ajson : listaJSON) 
		{
			for (Object axml: listaXML) 
			{
				if (!ajson.equals(axml)) 
				{
					todosIguales = false;
				}
				if (ajson.equals(axml)) 
				{
					correctos++;
					break;
				}
			}
		}
		
		int incorrectos = listaJSON.size()-correctos;
		JSONObject nuevoJson = new JSONObject();
		
		if (todosIguales) 
		{
			nuevoJson.put("iguales", true);
			nuevoJson.put("total ", listaJSON.size());
		}
		else
		{
			nuevoJson.put("iguales ", false);
			nuevoJson.put("correctos ", correctos);
			nuevoJson.put("incorrectos ", incorrectos);
		}
		
		System.out.println(nuevoJson);
	}
}
