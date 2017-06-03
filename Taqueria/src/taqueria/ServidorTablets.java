/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taqueria;

import Reportes.PDFVentasDia;
import SQL.ConexionMySQL;
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Javi
 */
/*Clase del hilo*/
class hiloServidor implements Runnable {

    private Socket cli;
    /*Lista de clientes*/
    public static ArrayList<Socket> listaCli = new ArrayList<Socket>();
    private String[] datos;
    private DataInputStream Lectura;
    private DataOutputStream Escritura;
    /*Datos del cliente que se conecte*/
    private String Nombre;
    private String id;
    private ConexionMySQL mysql = new ConexionMySQL();

    public hiloServidor(Socket cli, ArrayList<Socket> lista) throws IOException {
        this.cli = cli;
        this.listaCli = lista;
        this.Lectura = new DataInputStream(cli.getInputStream());
        this.Escritura = new DataOutputStream(cli.getOutputStream());
    }

    public void run() {
        String texto = "";

        try {
            texto = Leer();
            datos = texto.split(":");
            this.id = datos[0];
            this.Nombre = datos[1];
            Escribir("Binvenido: " + this.Nombre);
        } catch (Exception e) {
            System.err.println("Error al recibir datos iniciales");
        }

        try {
            /*Escuchar lo que me envia el cliente*/
            while ((texto = Leer()) != "") {
                System.out.println("Clientes activos: " + listaCli.size());
                System.out.println("Lectura: " + texto);
                /*checar esta diagoonal porque cruza con un producto que tiene 1/2*/
                datos = texto.split("<");
                if (datos.length > 1) {
                    Acciones(datos[0], datos[1]);
                } else {
                    Acciones(datos[0], "");
                }

            }
        } catch (IOException ex) {
            System.err.println("Error al leer las peticioness");
        } finally {
            Desconectar();
        }
    }

    public void Acciones(String opc, String parametros) throws IOException {
        String[] auxArr;
        String aux = "";
        switch (Integer.parseInt(opc)) {
            case 0:
                /*Enviar mesas reservadas*/
                String Texto = MesasOcupadas();
                Escribir("0<" + Texto);
                break;
            case 1:
                /*Reservar Mesa*/
 /*NombreMesa,HoraInicio*/
                auxArr = parametros.split(";");
                ReservarMesa(auxArr);
                Caja.cargaOcupadaConNombre(auxArr[0]);
                break;
            case 2:
                /*Finalizar venta a la mesa*/
                auxArr = parametros.split(";");
                //id mesa, nombremesa
                finalizarVenta(auxArr[0], auxArr[1]);
                break;
            case 3:
                //Realizar pedido insertando en detalle venta
                auxArr = parametros.split(";");
                //idVenta, nMesa, productos 
                Pedido(auxArr[0], auxArr[1], auxArr[2]);
                break;
            case 4:
                /*Consultar pedidos en detalleventa en base al id*/
                String d = getDetalleVenta(parametros);
                Escribir("3<" + d);
                break;
            case 5:
                actualizarDispostivos("0<" + MesasOcupadas());
                break;

            case 6:
                auxArr = parametros.split(";");
                /*idVenta, nMesa, productos*/
                Pedido(auxArr[0], auxArr[1], auxArr[2]);
                finalizarVenta(auxArr[0], auxArr[1]);
                actualizarDispostivos("0<" + MesasOcupadas());
                break;
            case 7:
                Escribir("7<");
                Desconectar();
                break;
            default:
                System.out.println("Nada para hacer");
                break;
        }
    }

    /*Enviar Mesa Reservadas*/
    public static String MesasOcupadas() {
        String Texto = "";
        ConexionMySQL mysql = new ConexionMySQL();
        try {
            String fecha = Principal.getFecha();
            Connection con = mysql.Conectar();
            String sql = "SELECT * FROM venta WHERE status = 'Ocupado'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                /*id_nMesa_status_hEntrada*/
                Texto += rs.getString("id") + "_" + rs.getString("nMesa") + "_" + rs.getString("status") + "_" + rs.getString("hEntrada") + "!";
            }
            con.close();
            //System.err.println(Texto);
            return Texto;
        } catch (Exception e) {
            System.err.println("No se pudo obtener las mesas Reservadas");
        }
        return Texto;
    }

    /*Reservar Mesa*/
    public void ReservarMesa(String[] parametros) throws IOException {
        /*NombreMesa,HoraInicio*/
        try {
            Connection con = this.mysql.Conectar();
            /*Antes de abrir mesa checar que no haya sido reservada por la pc
            de ser así buscar actualizar dispositivos y mandar su id junto la consulta del pedido 
            sino abre la mesa normal*/
            if (mesaOcupada(con, parametros[0]) == true) {
                //obtener id
                String id = idMesaOcupada(con, parametros[0]);
                //actualizar dispostivos
                actualizarDispostivos("0<" + MesasOcupadas());
                //mandar menu con los producto que lleva en lista
                String venta = getDetalleVenta(id);
                Escribir("3<" + venta);
            } else {
                /*Reservar la mse sin problemas*/
                String sql = "INSERT INTO  venta (fecha,hEntrada,hSalida,nMesa,id_mesero,total,status)"
                        + "VALUES(?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(sql);
                String f = Principal.getFecha();
                String h = getHora();
                pst.setString(1, f);
                pst.setString(2, h);
                pst.setString(3, h);
                pst.setString(4, parametros[0]);
                pst.setInt(5, Integer.parseInt(this.id));
                pst.setFloat(6, 0.0f);
                pst.setString(7, "Ocupado");
                int val = pst.executeUpdate();
                if (val <= 0) {
                    System.err.println("No se pudo reservar la mesa");
                } else {
                    /*Obtener el id de la venta*/
                    String id = "";
                    sql = "SELECT * FROM venta WHERE fecha = '" + f + "' AND hEntrada = '" + h + "' AND"
                            + " nMesa = '" + parametros[0] + "' AND id_mesero = '" + this.id + "' AND status = 'Ocupado'";
                    try {
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(sql);
                        while (rs.next()) {
                            id = rs.getString("id");
                            /*Mandar la mesa y el id de venta*/
                        }
                        Escribir("4<" + id + ";" + parametros[0]);
                        //Avisar a los demas dispositivos conectados
                        //1<id_nMesa_hEntrada
                        EnviarClientes("1<" + id + ";" + parametros[0] + ";" + h);
                    } catch (Exception e) {
                        System.err.println("Error al consultar el id");
                    }
                }
                con.close();
            }
        } catch (Exception e) {
            System.err.println("Error al reservar la mesa");
        }
    }

    public boolean mesaOcupada(Connection con, String mesa) {
        boolean flag = false;
        try {
            String f = Principal.getFecha();
            String sql = "SELECT status FROM venta WHERE fecha = '" + f + "' AND nMesa = '" + mesa + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                if (rs.getString("status").equals("Ocupado")) {
                    return flag = true;
                }
            }
        } catch (Exception e) {
            System.err.println("Error en MesaOcupada");
        }
        return flag;
    }

    public String idMesaOcupada(Connection con, String nMesa) {
        String id = "";
        String f = Principal.getFecha();
        String sql = "SELECT id FROM venta WHERE fecha = '" + f + "' AND nMesa = '" + nMesa + "' AND status = 'Ocupado'";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                id = rs.getString("id");
                return id;
            }

        } catch (Exception e) {
            System.err.println("Error al obtener el id de la mesa");
        }
        return id;
    }

    public void Pedido(String idventa, String nMesa, String pedido) {
        /*Insertar pedido en detalleventa con el id de venta*/
        try {
            Connection con = mysql.Conectar();
            /*checar que la mesa siga abierta*/
            if (checarCabiosPC(con, idventa) == false) {
                String[] dat = pedido.split("¡");
                String[] val;
                val = dat[0].split("!");
                String f = Principal.getFecha();

                String cat;
                int cant;
                int idMenu;
                String plato;

                int numoreden = Integer.parseInt(val[5]);
                ordenAEnviar orTacos = new ordenAEnviar(nMesa, Integer.parseInt(idventa), numoreden);
                ordenAEnviar orSodas = new ordenAEnviar(nMesa, Integer.parseInt(idventa), numoreden);

                for (int i = 0; i < dat.length; i++) {
                    val = dat[i].split("!");
                    String sql = "INSERT INTO detalleventa (idVenta, idMenu, cantidad, costo, total, status, fecha, nOrden)"
                            + "VALUES (?,?,?,?,?,?,?,?)";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setInt(1, Integer.parseInt(idventa));   //idVenta
                    pst.setInt(2, Integer.parseInt(val[0]));    //idMenu
                    pst.setInt(3, Integer.parseInt(val[1]));    //cantidad
                    pst.setFloat(4, Float.parseFloat(val[2]));  //costo
                    pst.setFloat(5, Float.parseFloat(val[3]));  //total
                    pst.setString(6, val[4]);                   //status
                    pst.setString(7, f);                        //fecha
                    pst.setString(8, val[5]);                   //nOrden
                    int act = pst.executeUpdate();
                    if (act <= 0) {
                        System.err.println("Error al agregar pedido");
                        break;
                    } else {
                        cat = PDFVentasDia.getPlatoCategoria(Integer.parseInt(val[0]));
                        cant = Integer.parseInt(val[1]);
                        idMenu = Integer.parseInt(val[0]);
                        plato = PDFVentasDia.getPlatoNombre(Integer.parseInt(val[0]));

                        if (cat.equals("TACOS")) {
                            orTacos.agregarAOrden(idMenu, cant, plato);
                        } else if (cat.equals("FUENTE DE SODAS")) {
                            orSodas.agregarAOrden(idMenu, cant, plato);
                        } else {
                            orTacos.agregarAOrden(idMenu, cant, plato);
                            orSodas.agregarAOrden(idMenu, cant, plato);
                        }
                    }
                }
                SocketServidor.enviarOrdenTacos(orTacos);
                SocketServidor.enviarOrdenSodas(orSodas);
                /*---Realiza pedido con el metodo----------------------------------------------------------------------*/
                con.close();
            } else {
                /*sino reservar una mesa nueva*/
                String h = getHora();
                String n[] = {nMesa, h};
                ReservarMesa(n);
                /*actualizar dispositivos*/
                actualizarDispostivos("0<" + MesasOcupadas());
                /*obtener el id de la venta*/
                String id = getIdVenta(con, nMesa);
                /*Inserar el pedido nuevo*/
                Pedido(id, nMesa, pedido);
            }
        } catch (Exception e) {
            System.err.println("Error al realizar el pedido");
        }
    }

    public void finalizarVenta(String id, String nombre) {
        Connection con = mysql.Conectar();
        /*checar que aun este disponible y no alla sido modificado por la pc*/
        if (checarCabiosPC(con, id) == false) {
            String sql = "UPDATE venta SET hSalida = ?, total = ?, status = ? WHERE id = '" + id + "'";
            String val = validarventa(con, id);
            String h = getHora();
            if (val.equals("")) {
                //Eliminar la venta porque no hay datos
                ///////////////////////////////////////////le movi yo
                //eliminarVenta(con, id);
                try {
                    Caja.finalizarVentaConNombre(nombre);
                    Escribir("5<correcto");
                    EnviarClientes("2<" + nombre);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al cerra venta"+e);
                }
            } else if (val.contains(".")) {
                //finalizar la venta actualizando datos
                /*----------------------------------------------------------------------------*/
                try {
                    /*///////////////////////le movi yo
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, h);
                    pst.setFloat(2, Float.parseFloat(val));
                    pst.setString(3, "Terminado");
                    int op = pst.executeUpdate();
                    if (op <= 0) {
                        System.err.println("Error al actualizar la venta");
                    }
                    con.close();
                    */
                    Caja.finalizarVentaConNombre(nombre);
                    Escribir("5<correcto");
                    EnviarClientes("2<" + nombre);                    
                } catch (Exception e) {
                    System.err.println("Error al finalizar venta");
                }
            }
        } else {
            /*solo actualiza los dispositivos porque ya fue cerrada la venta*/
            actualizarDispostivos("0<" + MesasOcupadas());
        }

    }

    public void eliminarVenta(Connection con, String id) {
        String sql = "DELETE FROM venta WHERE id = '" + id + "'";
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            if (pst.executeUpdate() <= 0) {
                System.err.println("No se pudo eliminar la venta");
            }
            con.close();
        } catch (Exception e) {
            System.err.println("Error al cancelar la venta");
        }
    }

    public String validarventa(Connection con, String id) {
        String sql = "SELECT * FROM detalleventa WHERE idVenta = '" + id + "'";
        String flag = "cancelar";
        String dat = "";
        float val = 0.0f;
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                rs.getString("total");
                rs.getString("status");
                dat = rs.getString("status");
                val += Float.parseFloat(rs.getString("total"));
            }
            if (dat.equals("")) {
                return dat;
            }
            return Float.toString(val);
        } catch (Exception e) {
            System.err.println("Error al validar la venta");
        }
        return dat;
    }

    public String getDetalleVenta(String idVenta) {
        String menu = ConsultaProducto();
        String sql = "SELECT * FROM detalleventa WHERE idVenta = '" + idVenta + "'";
        String datos = "";
        try {
            Connection con = mysql.Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String nom = buscarProducto(rs.getString("idMenu"));
                datos += rs.getString("idMenu") + "!" + nom + "!" + rs.getString("cantidad") + "!"
                        + rs.getString("costo") + "!" + rs.getString("total") + "!" + rs.getString("status")
                        + "!" + rs.getString("nOrden") + "¡";
                /*principal*/
            }
            con.close();
            //System.err.println(""+datos);
            /*3<menu*/
            if (datos.equals("")) {
                return menu;
            }
        } catch (Exception e) {
            System.err.println("Error al consultar detalle venta");
        }
        return menu + ">" + datos;
    }

    public String buscarProducto(String id) {
        String dat = "";
        String sql = "SELECT * FROM menu WHERE id = '" + id + "'";
        try {
            Connection con = mysql.Conectar();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                dat = rs.getString("nombre");
                return dat;
            }
        } catch (Exception e) {
            System.err.println("Error al buscar producto");
        }
        return dat;
    }

    public String ConsultaProducto() {
        String dato[] = {"", "", "", "", ""};
        String dat = "";
        try {
            Connection con = mysql.Conectar();
            String sql = "SELECT * FROM menu WHERE status = 'SI'";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                dato[0] = rs.getString("id");
                dato[1] = rs.getString("nombre");
                dato[2] = rs.getString("costo");
                dato[3] = rs.getString("categoria");
                dat += dato[0] + ":" + dato[1] + ":" + dato[2] + ":" + dato[3] + ";";
            }
            con.close();
            return dat;
        } catch (Exception e) {
            System.err.println("Error al consultar producto");
        }
        return dat;
    }

    public boolean checarCabiosPC(Connection con, String idVenta) {
        try {
            String sql = "SELECT status FROM venta WHERE id = '" + idVenta + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                if (rs.getString("status").equals("Terminado")) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            System.err.println("Error al verificar el estado de la mesa");
        }
        return false;
    }

    public String getIdVenta(Connection con, String nMesa) {
        String id = "";
        try {
            String f = Principal.getFecha();
            String sql = "SELECT id FROM venta WHERE fecha = '" + f + "' AND nMesa = '" + nMesa + "' AND status = 'Ocupado'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                return id = rs.getString("id");
            }
        } catch (Exception e) {
            System.err.println("Error al obtener el id de la venta");
        }
        return id;
    }

    public String getHora() {
        String hora = "";
        Calendar fecha = new GregorianCalendar();
        int horas = fecha.get(Calendar.HOUR);
        int min = fecha.get(Calendar.MINUTE);
        if (horas < 10) {
            hora = "0" + horas + ":";
        } else {
            hora = horas + ":";
        }
        if (min < 10) {
            hora = hora + "0" + min + ":00";
        } else {
            hora = hora + min + ":00";
        }
        return hora;
    }

    public void EnviarClientes(String dato) {
        for (int i = 0; i < listaCli.size(); i++) {
            try {
                if (!cli.equals(listaCli.get(i))) {
                    new DataOutputStream(listaCli.get(i).getOutputStream()).writeUTF(dato);
                }
            } catch (Exception e) {
                /*si no se le puede enviar al cliente, se elimina de la lista*/
                listaCli.remove(i);
            }
        }
    }

    public static void actualizarDispostivos(String Texto) {
        for (int i = 0; i < listaCli.size(); i++) {
            try {
                new DataOutputStream(listaCli.get(i).getOutputStream()).writeUTF(Texto);
            } catch (Exception e) {
                /*si no se le puede enviar al cliente, se elimina de la lista*/
                listaCli.remove(i);
            }
        }
    }

    public String Leer() throws IOException {
        String texto = "";
        texto = this.Lectura.readUTF();
        return texto;
    }

    public void Escribir(String Texto) throws IOException {
        this.Escritura.writeUTF(Texto);
    }

    public void Desconectar() {
        /*Se desconecto el cliente*/
        for (int i = 0; i < listaCli.size(); i++) {
            if (listaCli.get(i).equals(cli)) {
                /*eliminar de la lista de clientes conectados y el socket*/
                try {
                    String dat = cli.getInetAddress().getHostName();
                    listaCli.remove(i);
                    Lectura.close();
                    Escritura.close();
                    //cli.close();
                    //System.out.println("Cliente " + dat + " desconectado");
                    break;
                } catch (Exception e) {
                    System.err.println("Error al cerrar el flujo");
                } finally {
                    System.out.println("Clientes activos: " + listaCli.size());
                    try {
                        this.finalize();
                        System.out.println("Hilo finalizado");
                    } catch (Throwable ex) {
                        System.err.println("Error al eliminar el hilo");
                    }
                }
            }
        }
    }
}

public class ServidorTablets extends Thread {

    public void run() {
        try {
            int puerto = 4242;
            ServerSocket server = new ServerSocket(puerto);
            ArrayList<Socket> lista = new ArrayList<Socket>();
            System.out.println("Servidor levantado...");
            while (true) {
                Socket cli = server.accept();
                System.out.println("Cliente conectado: " + cli.getInetAddress().getHostName());
                lista.add(cli);
                /*Ejecutar hilo*/
                Runnable run = new hiloServidor(cli, lista);
                new Thread(run).start();

            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorTablets.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
