package laba1;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface RMI extends Remote {

    public ArrayList<Phone> search(String ser, int mode) throws RemoteException;
    public boolean DeleteData(int kol) throws RemoteException;
    public void ReadXL()throws RemoteException;
    public void DeleteKol()throws RemoteException;
    public Boolean register(IClient client)throws RemoteException;
    public List<Phone> ReadXML()throws RemoteException;
    public void Addph(Phone phone) throws RemoteException;
    public void aregster(IClient client)throws RemoteException;
    public void registerAll()throws RemoteException;
}
