package laba1;

import com.sun.org.apache.xerces.internal.dom.DeferredElementImpl;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.xml.stream.XMLStreamConstants.*;


public class Server extends UnicastRemoteObject implements RMI{

    private static final long serialVersionUID = 1L;
    File f1 = new File("dom.xml");
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public ArrayList<Phone> list;
    private Phone ph1;
    public ArrayList<IClient> clients = new ArrayList<IClient>();
    
    public Server() throws RemoteException {
        super();
    }

    public static ArrayList<Phone> arrayList = new ArrayList<Phone>();
    
    public void Addph(Phone ph1) throws RemoteException{
        arrayList.add(ph1);
        System.out.println("Объект записан");
        //---------------------------------
        f1.delete();
        File f1 = new File("dom.xml");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = docFactory.newDocumentBuilder();

            Document doc = dBuilder.newDocument();
            Element rootElement = doc.createElement(TAGS.CATALOG_TAG);
            doc.appendChild(rootElement);
            for(Phone item:arrayList) {
                Element phone = doc.createElement(TAGS.PHONE_TAG);
                rootElement.appendChild(phone);

                Attr attr = doc.createAttribute(TAGS.ID_TAG);
                attr.setValue(String.valueOf(item.getId()));
                phone.setAttributeNode(attr);

                Element surname = doc.createElement(TAGS.SURNAME_TAG);
                surname.appendChild(doc.createTextNode(item.getSurname()));
                phone.appendChild(surname);


                Element city = doc.createElement(TAGS.CITY_TAG);
                city.appendChild(doc.createTextNode(item.getCity()));
                phone.appendChild(city);

                Element code = doc.createElement(TAGS.CODE_TAG);
                code.appendChild(doc.createTextNode(String.valueOf(item.getCode())));
                phone.appendChild(code);

                Element number = doc.createElement(TAGS.NUMBER_TAG);
                number.appendChild(doc.createTextNode(String.valueOf(item.getNumber())));
                phone.appendChild(number);
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(getClass().getResource("dom.xml").getPath()));

            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }



    public boolean DeleteData(int kol) throws RemoteException{
        if (!arrayList.isEmpty()) {
            if (kol > 0 && kol < arrayList.size()){
                arrayList.remove(kol);
                f1.delete();
                File f1 = new File("dom.xml");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            try {
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
                Document doc = dBuilder.newDocument();
                Element rootElement = doc.createElement(TAGS.CATALOG_TAG);
                doc.appendChild(rootElement);
                for(Phone item:arrayList) {
                    Element phone = doc.createElement(TAGS.PHONE_TAG);
                    rootElement.appendChild(phone);

                    Attr attr = doc.createAttribute(TAGS.ID_TAG);
                    attr.setValue(String.valueOf(item.getId()));
                    phone.setAttributeNode(attr);

                    Element surname = doc.createElement(TAGS.SURNAME_TAG);
                    surname.appendChild(doc.createTextNode(item.getSurname()));
                    phone.appendChild(surname);


                    Element city = doc.createElement(TAGS.CITY_TAG);
                    city.appendChild(doc.createTextNode(item.getCity()));
                    phone.appendChild(city);

                    Element code = doc.createElement(TAGS.CODE_TAG);
                    code.appendChild(doc.createTextNode(String.valueOf(item.getCode())));
                    phone.appendChild(code);

                    Element number = doc.createElement(TAGS.NUMBER_TAG);
                    number.appendChild(doc.createTextNode(String.valueOf(item.getNumber())));
                    phone.appendChild(number);
                }
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(getClass().getResource("dom.xml").getPath()));

                transformer.transform(source, result);
                System.out.println("File saved!");

            }
            catch (ParserConfigurationException pce) {
                pce.printStackTrace();
            } catch (TransformerException tfe) {
                tfe.printStackTrace();
            }
            System.out.println("Объект удален");
            try {} catch (Exception ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }
        System.out.println("Объект не удален. Повторите попытку");
        return false;
    } else
    {
        System.out.println("Массив пуст");
    }
    return false;}
    

    public ArrayList<Phone> search(String ser, int mode)throws RemoteException{
        list=new ArrayList<Phone>();
        arrayList=new ArrayList<Phone>();
        System.out.println(arrayList);
        int i=0;
        System.out.println("Search");
        switch (mode){
            case 1:
                while (i<arrayList.size()){
                    if (arrayList.get(i).getSurname().equals(ser))
                    {
                        list.add(arrayList.get(i));
                    }
                    i++;
                }
                break;
            case 2:
                while (i<arrayList.size()){
                    if (arrayList.get(i).getCity().equals(ser))
                    {
                        list.add(arrayList.get(i));
                    }
                    i++;
                }
                break;
            case 3:
                while (i<arrayList.size()){
                    if (arrayList.get(i).getCode()==Integer.parseInt(ser))
                    {
                        list.add(arrayList.get(i));
                    }
                    i++;
                }
                break;
            case 4:
                while (i<arrayList.size()){
                    if (arrayList.get(i).getNumber()==Integer.parseInt(ser))
                    {
                        list.add(arrayList.get(i));
                    }
                    i++;
                }
                break;
        }
        System.out.println(list);
        return list;
    }
    
    public ArrayList<Phone> ReadXML()throws RemoteException{   // запись в файл
        ArrayList<Phone> arrayList1 = new ArrayList();
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(getClass().getResourceAsStream("dom.xml"));
            NodeList nList = doc.getElementsByTagName(TAGS.CATALOG_TAG);

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    DeferredElementImpl el = (DeferredElementImpl) nNode;
                    String surname = el.getElementsByTagName(TAGS.SURNAME_TAG).item(0).getTextContent();
                    int id = Integer.parseInt(el.getAttribute(TAGS.ID_TAG));
                    String city = el.getElementsByTagName(TAGS.CITY_TAG).item(0).getTextContent();
                    int code = Integer.parseInt(el.getElementsByTagName(TAGS.CODE_TAG).item(0).getTextContent());
                    int number = Integer.parseInt(el.getElementsByTagName(TAGS.NUMBER_TAG).item(0).getTextContent());
                    ph1 = new Phone(id, surname, city, code, number);
                }
                arrayList1.add(ph1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList1;
    }
    public void ReadXL()throws RemoteException{
        if (arrayList.isEmpty()) {
            try {

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(getClass().getResourceAsStream("dom.xml"));

                NodeList nList = doc.getElementsByTagName(TAGS.CATALOG_TAG);
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        DeferredElementImpl el = (DeferredElementImpl) nNode;
                        String surname = el.getElementsByTagName(TAGS.SURNAME_TAG).item(0).getTextContent();
                        int id = Integer.parseInt(el.getAttribute(TAGS.ID_TAG));
                        String city = el.getElementsByTagName(TAGS.CITY_TAG).item(0).getTextContent();
                        int code = Integer.parseInt(el.getElementsByTagName(TAGS.CODE_TAG).item(0).getTextContent());
                        int number = Integer.parseInt(el.getElementsByTagName(TAGS.NUMBER_TAG).item(0).getTextContent());
                        ph1 = new Phone(id, surname, city, code, number);
                    }
                    arrayList.add(ph1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } 
        }
        
    }
    public Boolean register(IClient client)throws RemoteException{
        clients.add(client);

        return true;
    }

    public void aregster(IClient client)throws RemoteException {

        clients.remove(client);


    }
    public void registerAll() throws RemoteException{
        for (IClient client:clients){
            client.update();

        }
    }

    public static void main(String args[]){
        try{
            Registry reg = LocateRegistry.createRegistry(1099);
            reg.rebind("server", new Server());
            System.out.println("Server started");
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void DeleteKol()throws RemoteException{
        arrayList = new ArrayList<Phone>();
    }

}
