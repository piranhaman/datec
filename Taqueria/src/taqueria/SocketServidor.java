/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taqueria;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements java Socket server
 *
 * @author pankaj
 *
 */
public class SocketServidor extends Thread {

    //static ServerSocket variable
    public ServerSocket server;
    //socket server port on which it will listen
    public int port = 9876;
    public terminal ter;
    public static ArrayList<terminal> terminales;

    public void run() {
        terminales = new ArrayList<>();
        try {
            //create the socket server object
            server = new ServerSocket(port);
            //keep listens indefinitely until receives 'exit' call or program terminates
            while (true) {

                System.out.println("Esperando cliente");
                //creating socket and waiting for client connection
                Socket socket = server.accept();
                ter = new terminal(socket);
                terminales.add(ter);

            }
        } catch (IOException ex) {
            Logger.getLogger(SocketServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        SocketServidor sk = new SocketServidor();
        sk.start();
    }

    public static void enviarOrdenTacos(ordenAEnviar or) {
        //System.out.println("enviar orden");
        //System.out.println("numero terminales: "+terminales.size());
        for (int i = 0; i < terminales.size(); i++) {
            terminal tmp = terminales.get(i);
            //System.out.println("nombre de teminal: "+tmp.tipo);
            if (tmp.tipo.equals("TACOS")) {
                try {
                    tmp.oos.writeObject(or);
                    //System.out.println("Se envio a tacos");
                } catch (IOException ex) {
                    Logger.getLogger(terminal.class.getName()).log(Level.SEVERE, null, ex);
                    try {
                        terminales.get(i).rec.origen.close();
                    } catch (IOException ex1) {
                        Logger.getLogger(SocketServidor.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    terminales.remove(i);
                }
            }

        }

    }

    public static void enviarOrdenSodas(ordenAEnviar or) {
        for (int i = 0; i < terminales.size(); i++) {
            terminal tmp = terminales.get(i);
            if (tmp.tipo.equals("FUENTE DE SODAS")) {
                try {
                    tmp.oos.writeObject(or);
                    //System.out.println("Se envio a sodas");
                } catch (IOException ex) {
                    try {
                        tmp.oos.writeObject(or);
                    } catch (IOException ex1) {
                        Logger.getLogger(SocketServidor.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    Logger.getLogger(terminal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}

class terminal {

    serverRecibe rec;
    ObjectOutputStream oos;
    String tipo = "";

    terminal(Socket socket) throws IOException {

        rec = new serverRecibe(socket, this);
        rec.start();
        oos = new ObjectOutputStream(socket.getOutputStream());

    }

}

class serverRecibe extends Thread {

    Socket origen;
    ObjectInputStream ois;
    terminal ter;

    serverRecibe(Socket s, terminal tr) {
        this.ter = tr;
        try {
            origen = s;
            ois = new ObjectInputStream(origen.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(serverRecibe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        int n = 0;
        try {
            while (true) {
                String message = null;
                try {
                    message = (String) ois.readObject();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(SocketServidor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    SocketServidor.terminales.remove(this.ter);
                    System.out.println("Cliente Eliminado!!");
                }
                System.out.println("Mensaje Recibido " + message);
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
                
                this.ter.tipo = message;
                
            }
        } catch (Exception ex) {

        }
    }
}

class ordenAEnviar implements Serializable {

    String mesa;
    int idVenta,nOrden;
    
    ArrayList<String> productos;
    ArrayList<Integer> idProd;
    ordenAEnviar(String mesa, int idventa, int norden) {
        this.mesa = mesa;
        this.idVenta=idventa;
        this.nOrden=norden;
        productos = new ArrayList<>();
        idProd = new ArrayList<>();
    }

    public void agregarAOrden(int id,int cant, String plato) {
        productos.add(cant + " - " + plato);
        idProd.add(id);
    }
}
