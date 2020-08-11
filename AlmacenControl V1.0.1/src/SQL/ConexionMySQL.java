/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;


import almacencontrol.Logg;
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
    
    //String ip="localhost";
    String ip=Logg.getDirIP();
    String userDB=Logg.userDB;
    String passDB=Logg.passDB;
    public String db = "almacen";//se debe asignar el nombre de la base de datos 
    public String url = "jdbc:mysql://"+ip+"/"+db;//se asigna la ubicacion de la base de datos
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
    
}
