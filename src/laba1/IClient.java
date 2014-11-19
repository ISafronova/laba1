package laba1;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClient extends Remote {
    public int update() throws RemoteException; // функция, обновляющая клиенты
}
