package laba1;


import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Client extends UnicastRemoteObject implements IClient {

    RMI server;
    final static ObservableList<Phone> data = FXCollections.observableArrayList();

    public Client() throws RemoteException {
        super();
    }
    public void con(Client client) throws RemoteException, NotBoundException {
        String objectName = "server";
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
        server = (RMI) registry.lookup(objectName);
        server.register(client);
    }
    public List ReadXML() throws RemoteException {
        List data1;
        data1 = server.ReadXML();
        return data1;
    }
    public void Addph(Phone ph1) {
        try {
            server.Addph(ph1);
        } catch (RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ArrayList Search(String ser, int mode) throws RemoteException {
        ArrayList lis;
        lis = server.search(ser, mode);
        return lis;
    }
    public Boolean DeleteData(int num) throws RemoteException {
        return server.DeleteData(num);
    }

    public void DeleteKol() throws RemoteException {
        server.DeleteKol();
    }
    public void ReadXL() throws RemoteException {
        server.ReadXL();
    }
    public void aregistry(IClient client) throws RemoteException{
        server.aregster(client);
    }
    public void regAll() throws RemoteException {
        server.registerAll();
    }
    public int update() throws RemoteException {
        ArrayList<Phone> data1 = new ArrayList<Phone>();
        try {
            data1 = (ArrayList<Phone>) server.ReadXML();
        } catch (RemoteException ex) {
            Logger.getLogger(InClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        data.clear();
        data.addAll(data1);
        return 0;
    }
    public ObservableList<Phone> print()throws RemoteException{
        return data; }
}



