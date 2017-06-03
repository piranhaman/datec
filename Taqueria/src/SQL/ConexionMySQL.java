/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Piranhaman
 */
public class ConexionMySQL {
    
    public static String IP="";
    public static String userDB="root";
    public static String passDB="";
    public static String puerto="";
    String ip =getDirIP();
    public String db = "ktacos";//se debe asignar el nombre de la base de datos 
    public String url = "jdbc:mysql://"+IP+":"+puerto+"/"+db;//se asigna la ubicacion de la base de datos
    public String user = userDB;//se asigna el nombre de usuario
    public String pass = passDB;//se asigna el valor de la contrase�a
    

    public ConexionMySQL() {
    }
    ///creacion de metodo conectar para lograr la comunicacion con la red

    public Connection Conectar() {
        
        Connection link = null;
        try {
            //cargamos driver de mySQL
            Class.forName("org.gjt.mm.mysql.Driver");
            //creamos un enlace hacia la base de datos
            link = DriverManager.getConnection(this.url, this.user, this.pass);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar con la Base de datos: "+e);
            System.exit(0);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return link;
    }
    
    public ResultSet Consulta(String Cons){
        Statement estSQL1;
        ResultSet rs = null;
        try{
        String strConsultaSQL;
        //Apuntador a la conexion
        Connection conn = this.Conectar();
        //Para ejecutar operaciones SQL
        estSQL1 = conn.createStatement();
        strConsultaSQL = Cons;
        rs = estSQL1.executeQuery(strConsultaSQL);
        
        }catch(SQLException e){
           JOptionPane.showMessageDialog(null, e);
        }
       return rs;
    }
    
    public static String getDirIP() {
        String ip="";
        if(IP.equals("")){
        
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        try {
            archivo = new File("ip.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                ip=linea;
                IP=ip;
                linea = br.readLine();
                puerto=linea;
                linea = br.readLine();
                userDB=linea;
                linea = br.readLine();
                passDB=linea;
                System.out.println("ip: "+IP+"   Puerto:"+puerto+"  usr:"+userDB+"  pass:"+passDB);
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se encuentra el archivo de la direccion remota ");
            System.exit(0);
            
        } finally {
         // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        }else{
            return IP;
        }
        return ip;
    }
    
}
