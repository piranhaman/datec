/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taqueria;

import SQL.ConexionMySQL;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import org.jvnet.substance.skin.SubstanceDustLookAndFeel;

/**
 * This class implements java socket client
 *
 * @author pankaj
 *
 */
public class SocketCliente extends Thread {

    private String nombre;
    public Socket socket = null;
    terminalCliente terc;
    public SocketCliente(String nombre) {
        this.nombre=nombre;
    }
    

    
    public void run() {
        try {
            //get the localhost IP address, if server is running on some other IP, you need to use that
            String ip = ConexionMySQL.getDirIP();
            socket = new Socket(ip, 9876);
            //write to socket using ObjectOutputStream

            terc = new terminalCliente(socket,nombre);

        } catch (UnknownHostException ex) {
            //Logger.getLogger(SocketCliente.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al conectar con el servidor!!!: \n"+ex);
            System.exit(0);
            
        } catch (IOException ex) {
            //Logger.getLogger(SocketCliente.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al conectar con el servidor!!!: \n"+ex);
            System.exit(0);
            
        }
    }
    public void terminarSesion() throws IOException{
        this.socket.close();
        terc.env.cerrarSocket();
        terc.rec.cerrarSocket();
    }
}


class terminalCliente {

    ClienteEnvia env;
    ClienteRecibe rec;

    terminalCliente(Socket socket,String tipo) {

        env = new ClienteEnvia(socket,tipo);
        rec = new ClienteRecibe(socket);
        env.start();
        rec.start();
    }

}

class ClienteEnvia extends Thread {

    Socket destino;
    ObjectOutputStream oos;
    String tipo;
    ClienteEnvia(Socket s, String tipo) {
        this.tipo=tipo;
        try {
            destino = s;
            oos = new ObjectOutputStream(destino.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClienteEnvia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void cerrarSocket(){
        try {
            this.oos.close();
        } catch (IOException ex) {
            Logger.getLogger(ClienteRecibe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        int n = 0;
        
            try {
                oos.writeObject(tipo);
            } catch (IOException ex) {
                Logger.getLogger(ClienteEnvia.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
}

class ClienteRecibe extends Thread {

    ObjectInputStream ois;

    ClienteRecibe(Socket s) {
        try {
            
            ois = new ObjectInputStream(s.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClienteRecibe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void cerrarSocket(){
        try {
            this.ois.close();
        } catch (IOException ex) {
            Logger.getLogger(ClienteRecibe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run() {
        int n = 0;
        while (true) {
            ordenAEnviar message = null;
            try {
                message = (ordenAEnviar) ois.readObject();
                //System.out.println("mensage recibido");
                agregaOrden(message);
            } catch (ClassNotFoundException ex) {
                
                Logger.getLogger(SocketServidor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                if(terminalProduccion.status=="ACTIVO"){
                    JOptionPane.showConfirmDialog(null, "Conección perdida\nFavor de reconectar el Servidor");
                }
                System.exit(0);
                Logger.getLogger(ClienteRecibe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void agregaOrden(ordenAEnviar or) {
        
        DefaultTableModel m = null;
        m= (DefaultTableModel) terminalProduccion.jTable1.getModel();
        String mesa = or.mesa;
        ArrayList<String> ar = or.productos;
        for (int i = 0; i < ar.size(); i++) {
            if (i == 0) {
                m.addRow(new Object[]{mesa, ar.get(i)});
            } else {
                m.addRow(new Object[]{"", ar.get(i)});
            }
        }
        terminalProduccion.ordenes.add(or);
        terminalProduccion.ReproducirSonido(terminalProduccion.clip1);
    }
}
