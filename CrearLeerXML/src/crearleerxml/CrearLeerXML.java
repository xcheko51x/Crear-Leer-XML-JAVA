/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crearleerxml;

import com.sun.istack.internal.logging.Logger;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import sun.util.logging.PlatformLogger;

/**
 *
 * @author xcheko51x
 */
public class CrearLeerXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String nomArchivo = "usuarios";
        
        List<Usuario> listaUsuarios = new ArrayList<Usuario>();
        
        listaUsuarios.add(new Usuario(1, "Sergio", "234242543543"));
        listaUsuarios.add(new Usuario(2, "Laura", "76865542424"));
        
        try {
            //crearXML(nomArchivo, listaUsuarios);
            leerXML();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void leerXML() {
        try {
            File archivo = new File("usuarios.xml");
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(archivo);
            
            document.getDocumentElement().normalize();
            
            System.out.println("Elemento raiz: " + document.getDocumentElement().getNodeName());
            
            NodeList listaUsuarios = document.getElementsByTagName("USUARIO");
            
            for(int i = 0 ; i < listaUsuarios.getLength() ; i++) {
                Node nodo = listaUsuarios.item(i);
                System.out.println("Elemento: " + nodo.getNodeName());
                
                if(nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodo;
                    System.out.println("ID: " + element.getElementsByTagName("ID").item(0).getTextContent());
                    System.out.println("Nombre: " + element.getElementsByTagName("NOMBRE").item(0).getTextContent());
                    System.out.println("Telefono: " + element.getElementsByTagName("TELEFONO").item(0).getTextContent());
                    
                    System.out.println("");
                }
            }
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void crearXML(String nomArchivo, List<Usuario> listaUsuarios) throws ParserConfigurationException, TransformerConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, nomArchivo, null);
            document.setXmlVersion("1.0");
            
            // NODO RAIZ
            Element raiz = document.getDocumentElement();
            
            for(int i = 0 ; i <listaUsuarios.size() ; i++) {
                Element itemNode = document.createElement("USUARIO");
                
                Element idNode = document.createElement("ID");
                Text nodeIdValue =document.createTextNode("" +  listaUsuarios.get(i).getIdUsuario());
                idNode.appendChild(nodeIdValue);
                
                Element nombreNode = document.createElement("NOMBRE");
                Text nodeNombreValue =document.createTextNode(listaUsuarios.get(i).getNombre());
                nombreNode.appendChild(nodeNombreValue);
                
                Element telefonoNode = document.createElement("TELEFONO");
                Text nodeTelefonoValue =document.createTextNode(listaUsuarios.get(i).getTelefono());
                telefonoNode.appendChild(nodeTelefonoValue);
                
                itemNode.appendChild(idNode);
                itemNode.appendChild(nombreNode);
                itemNode.appendChild(telefonoNode);
                
                raiz.appendChild(itemNode);
            }
            
            // GENERA XML
            Source source = new DOMSource(document);
            
            // DONDE SE GUARDARA
            Result result = new StreamResult(new java.io.File(nomArchivo + ".xml"));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
        
        } catch(ParserConfigurationException e) {
            
        }
    }
    
}
