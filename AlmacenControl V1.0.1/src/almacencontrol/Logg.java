/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package almacencontrol;

import SQL.ConexionMySQL;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.jvnet.substance.api.skin.SubstanceMagellanLookAndFeel;
import org.jvnet.substance.skin.*;
/**
 *
 * @author AlbertC
 */
public class Logg extends javax.swing.JFrame {

    /**
     * Creates new form Logg
     */
    public static String IP="";
    public static String userDB="root";
    public static String passDB="";

    public Logg() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/LOGO.png")).getImage());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        getUsuarios();
        jPasswordField1.requestFocusInWindow();
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("/Imagenes/LOGO.png"));

        return retValue;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Usuario: ");

        jLabel2.setText("Contraseña: ");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/login.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPasswordField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPasswordField1KeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        jLabel3.setText("Ingreso al Sistema");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(79, 79, 79)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox1, 0, 129, Short.MAX_VALUE)
                            .addComponent(jPasswordField1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jLabel3)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel3)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ingresar();
// TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jPasswordField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordField1KeyReleased

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            ingresar();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField1KeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Logg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Logg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Logg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Logg.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    //SubstanceCremeCoffeeLookAndFeel()

                    UIManager.setLookAndFeel(new SubstanceDustLookAndFeel());
                } catch (Exception e) {
                    System.out.println("Substance Raven Graphite failed to initialize");
                }                
                new Logg().setVisible(true);
            }
        });
    }

    public void getUsuarios() {

        jComboBox1.removeAllItems();
        ConexionMySQL conect = new ConexionMySQL();
        String consulta = "select * from usuarios";

        int n = 0;
        try {
            ResultSet rs = conect.Consulta(consulta);
            while (rs.next()) {
                jComboBox1.addItem(rs.getString("nombre"));
                n++;
            }
        } catch (SQLException ex) {
            System.out.println("error: " + ex);
//JOptionPane.showMessageDialog(rootPane, "Error al cargar Clientes!!!");
        }
        if (n == 0) {
            JOptionPane.showMessageDialog(rootPane, "No hay usuarios, Registrar los datos del Administrador");
            new NuevoUser().setVisible(true);
            this.setExtendedState(JFrame.CROSSHAIR_CURSOR);

        }
    }

    public String getUser(String usr, String pass) {
        String comp = "";
        ConexionMySQL conect = new ConexionMySQL();
        String consulta = "select * from usuarios where nombre='" + usr + "' and pass='" + pass + "'";
        ResultSet rs = conect.Consulta(consulta);

        try {
            while (rs.next()) {
                comp = rs.getString("tipo");
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println("error: " + ex);
//JOptionPane.showMessageDialog(rootPane, "Error al cargar Clientes!!!");
        }

        return comp;
    }

    public void ingresar() {

        final String usr = jComboBox1.getSelectedItem().toString();
        String pas = jPasswordField1.getText();
        String com = getUser(usr, pas);

        if (com.equals("Empleado")) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new principalAlmacenEmpleado(usr).setVisible(true);
                }
            });
            this.dispose();
        } else if (com.equals("Administrador")) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new principalAlmacen(usr).setVisible(true);

                }
            });
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Contraseña incorrecta");
            jPasswordField1.setText("");
        }

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
                userDB=linea;
                linea = br.readLine();
                passDB=linea;
                System.out.println("ip: "+IP+"  usr:"+userDB+"  pass:"+passDB);
                
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    public static javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField jPasswordField1;
    // End of variables declaration//GEN-END:variables
}
