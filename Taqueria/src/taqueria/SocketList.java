/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taqueria;


/**
 *
 * @author JoseGuadalupe
 */


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SocketList {
    private List<ObjectOutputStream> obOutStrList;
    private List<ObjectInputStream> obInStrList;
    private List<Socket> sockList;

    private int maxUsers;
    private int currentUsers;

    public SocketList() {
        obOutStrList = Collections
                .synchronizedList(new ArrayList<ObjectOutputStream>());
        obInStrList = Collections
                .synchronizedList(new ArrayList<ObjectInputStream>());
        sockList = Collections.synchronizedList(new ArrayList<Socket>());

        maxUsers = 4;
        currentUsers = 0;
    }

    synchronized public void addSocket(Socket sock) {
        if (sockList.size() < 4) {
            sockList.add(sock);
            currentUsers++;
        } else {
            System.out.println("sockList: Blad max uzytkownikow");
        }
    }

    synchronized public void addObjOutStrm(Socket sock) {
        try {
            obOutStrList.add(new ObjectOutputStream(sock.getOutputStream()));
        } catch (IOException e) {
            System.out.println("Problem z uzyskaniem strumienia wyjsciowego dla " + sock.getInetAddress().getHostAddress().toString());
            e.printStackTrace();
        }
    }

    synchronized public void addObjInStrm(Socket sock) {
        try {
            obInStrList.add(new ObjectInputStream(sock.getInputStream()));
        } catch (IOException e) {
            System.out.println("Problem z uzyskaniem strumienia wejsciowego dla " + sock.getInetAddress().getHostAddress().toString());
            e.printStackTrace();
        }
    }

    synchronized public void removeSock(Socket s) {
        if (sockList.contains(s)) {
            System.out.println("SOCKETLIST: Usuwam " + s.toString());
            sockList.remove((Socket) s);
        }
    }

    synchronized public List<Socket> getSockList() {
        return sockList;
    }

    synchronized public List<ObjectOutputStream> getOOSList() {
        return obOutStrList;
    }

    synchronized public List<ObjectInputStream> getOISList() {
        return obInStrList;
    }

    public String toString() {
        return sockList.toString();
    }

    public String[] extractToString() {
        String[] retArr = new String[currentUsers];

        for (int i = 0; i < sockList.size(); i++)
            retArr[i] = sockList.get(i).getInetAddress().getHostAddress()
                    .toString();

        return retArr;
    }

}