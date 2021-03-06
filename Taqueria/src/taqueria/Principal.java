/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taqueria;

import Reportes.inventarioEmpaquetadosPDF;
import Reportes.inventarioMueblesPDF;
import SQL.ConexionMySQL;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author el dios
 */
public class Principal extends javax.swing.JFrame {

    public static int usr;
    public String usrNombre;

    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
        initDiaTacos();
        setExtendedState(6);
    }

    public Principal(String usrn) {
        this.setUndecorated(true);
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/LOGO.png")).getImage());
        this.setSize(700, 500);
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        usrNombre = usrn;
        //usr=getUsuario(usrNombre);
        initDiaTacos();
        initDiaSodas();
        setExtendedState(6);
        jLabel1.setSize((int)screenSize.getWidth(), (int)screenSize.getHeight());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu10 = new javax.swing.JMenu();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenu11 = new javax.swing.JMenu();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenu12 = new javax.swing.JMenu();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("Agregar Inventario Tacos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(20, 20, 210, 80);

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setText("Agregar Inventario Sodas");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(20, 110, 210, 80);

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton3.setText("Cierre de Caja");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3);
        jButton3.setBounds(20, 200, 210, 80);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondotacos2.png"))); // NOI18N
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 500, 320);

        jMenuBar1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jMenu1.setText("Archivo");
        jMenu1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jMenu4.setText("Usuarios");
        jMenu4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem3.setText("Nuevo Usuario");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem3);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jMenuItem4.setText("Editar Usuarios");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem4);

        jMenu1.add(jMenu4);

        jMenuItem16.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem16.setText("Numero de Mesas");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem16);

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jMenuItem9.setText("Salir");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem9);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Productos");
        jMenu2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jMenu5.setText("Productos");
        jMenu5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem1.setText("Nuevo");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem2.setText("Modificar");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem2);

        jMenu2.add(jMenu5);

        jMenu6.setText("Productos Empaquetados");
        jMenu6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem5.setText("Nuevo");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem5);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem6.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem6.setText("Modificar");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem6);

        jMenu2.add(jMenu6);

        jMenu7.setText("Menus");
        jMenu7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem7.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem7.setText("Nuevo");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem7);

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem8.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem8.setText("Modificar");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem8);

        jMenu2.add(jMenu7);

        jMenuBar1.add(jMenu2);

        jMenu10.setText("Agrear a Inventarios");
        jMenu10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jMenuItem12.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jMenuItem12.setText("Inventario Tacos");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem12);

        jMenuItem13.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jMenuItem13.setText("Inventario sodas");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem13);

        jMenuItem14.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jMenuItem14.setText("Inventario Empaquetados");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem14);

        jMenuBar1.add(jMenu10);

        jMenu11.setText("Moviliario");
        jMenu11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jMenuItem18.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jMenuItem18.setText("Nuevo Mueble");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem18);

        jMenuItem19.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jMenuItem19.setText("Modificar Muebles");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem19);

        jMenuBar1.add(jMenu11);

        jMenu12.setText("Gastos");
        jMenu12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jMenuItem21.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jMenuItem21.setText("Ingresar Gasto");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem21);

        jMenuBar1.add(jMenu12);

        jMenu3.setText("Reportes");
        jMenu3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jMenu8.setText("Ventas");
        jMenu8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        jMenuItem15.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem15.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem15.setText("Reporte de Ventas");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem15);

        jMenuItem20.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem20.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem20.setText("Reporte Mensual");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem20);

        jMenu3.add(jMenu8);

        jMenu9.setText("Inventario");
        jMenu9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        jMenuItem10.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem10.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem10.setText("Tacos/Sodas");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem10);

        jMenuItem17.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem17.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem17.setText("Productos Empaquetados");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem17);

        jMenuItem11.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem11.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jMenuItem11.setText("Mobiliario");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem11);

        jMenu3.add(jMenu9);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed

        new NuevoUser().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed

        new modiUsers().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        new NuevoProducto().setVisible(true);
// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed

        new modiProd().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed

        new NuevoProductoEmpaquetado().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed

        new modiProdEmpaquetados().setVisible(true);
// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed

        new NuevoMenu().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed

        new modiMenu().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed

        new InventarioDiarioTacos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed

        new InventarioDiarioSodas().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        new InventarioDiarioTacos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        new InventarioDiarioSodas().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed

        new AddEmpaquetados().setVisible(true);
// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        new MesasN().setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed

        new reporteDiario().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        new reporteDiario().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed

        new inventarioTacosMes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed

        new NuevoMueble().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed

        new modiMuebles().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed

        try {
            new inventarioMueblesPDF("Inventario Moviliario","Inventario Moviliario.pdf");
            // TODO add your handling code here:
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed

        new ReporteGananciasMes().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed

        try {
            new inventarioEmpaquetadosPDF("Inventario Empaquetados","Inventario Empaquetados.pdf");
            // TODO add your handling code here:
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed

        System.exit(0);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed

        new gastos().setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem21ActionPerformed

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
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    public static void initDiaTacos() {
        ArrayList<Integer> a = new ArrayList<Integer>();
        ConexionMySQL conect = new ConexionMySQL();
        String consulta = "select * from productos where categoria='TACOS'";
        int n = 0, can = 0;
        try {
            ResultSet rs = conect.Consulta(consulta);

            while (rs.next()) {
                a.add(rs.getInt("id"));
            }
        } catch (SQLException ex) {
            System.out.println("error: " + ex);
        }

        consulta = "select * from invtacos where fecha='" + getFecha() + "'";
        try {
            ResultSet rs = conect.Consulta(consulta);

            while (rs.next()) {
                can++;
                //System.out.println("can; "+can);
            }
        } catch (SQLException ex) {
            System.out.println("error: " + ex);
        }

        if (can < a.size()) {
            ConexionMySQL mysql = new ConexionMySQL();
            Connection cn = mysql.Conectar();
            PreparedStatement pst;
            int errores = 0;
            String sSQL = "";
            String mensaje = "";

            /*
                 
             */
            sSQL = "INSERT INTO  invtacos (idProd,entrada,salida,fecha)"
                    + "VALUES(?, ?, ?, ?)";

            for (int i = can; i < a.size(); i++) {

                try {
                    pst = cn.prepareStatement(sSQL);

                    pst.setInt(1, a.get(i));
                    pst.setFloat(2, 0.0f);
                    pst.setFloat(3, 0.0f);
                    pst.setString(4, getFecha());
                    n = pst.executeUpdate();
                    if (n > 0) {
                        //JOptionPane.showMessageDialog(null, mensaje);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }

            }
        }
    }

    public static void initDiaSodas() {
        ArrayList<Integer> a = new ArrayList<Integer>();
        ConexionMySQL conect = new ConexionMySQL();
        String consulta = "select * from productos where categoria='SODAS'";
        int n = 0, can = 0;
        try {
            ResultSet rs = conect.Consulta(consulta);

            while (rs.next()) {
                a.add(rs.getInt("id"));
            }
        } catch (SQLException ex) {
            System.out.println("error: " + ex);
        }

        consulta = "select * from invSodas where fecha='" + getFecha() + "'";
        try {
            ResultSet rs = conect.Consulta(consulta);

            while (rs.next()) {
                can++;
                //System.out.println("can; "+can);
            }
        } catch (SQLException ex) {
            System.out.println("error: " + ex);
        }

        if (can < a.size()) {
            ConexionMySQL mysql = new ConexionMySQL();
            Connection cn = mysql.Conectar();
            PreparedStatement pst;
            int errores = 0;
            String sSQL = "";
            String mensaje = "";

            /*
                 
             */
            sSQL = "INSERT INTO  invSodas (idProd,entrada,salida,fecha)"
                    + "VALUES(?, ?, ?, ?)";

            for (int i = can; i < a.size(); i++) {

                try {
                    pst = cn.prepareStatement(sSQL);

                    pst.setInt(1, a.get(i));
                    pst.setFloat(2, 0.0f);
                    pst.setFloat(3, 0.0f);
                    pst.setString(4, getFecha());
                    n = pst.executeUpdate();
                    if (n > 0) {
                        //JOptionPane.showMessageDialog(null, mensaje);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }

            }
        }
    }

    public static String getFecha() {

        Calendar fecha = new GregorianCalendar();
        fecha.add(Calendar.HOUR_OF_DAY, -5);
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        String fech = anio + "-" + mes + "-" + dia;
        return fech;

    }

    public static String getFechaAyer() {

        Calendar fecha = new GregorianCalendar();
        fecha.add(Calendar.HOUR, -5);
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        String fech = anio + "-" + mes + "-" + dia;

        return fech;

    }

    public static String getHora() {

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

    public static void limpiarTabla(JTable a) {

        DefaultTableModel m = (DefaultTableModel) a.getModel();
        for (int i = 0; i < a.getRowCount(); i++) {
            m.removeRow(i);
            i -= 1;
        }
    }

    public static ArrayList<String> getProdInfo(int id) {
        ArrayList<String> prod = new ArrayList<String>();
        String comp = "";
        ConexionMySQL conect = new ConexionMySQL();
        String consulta = "select * from productos where id=" + id;
        ResultSet rs = conect.Consulta(consulta);

        try {
            while (rs.next()) {
                comp = rs.getString("nombre");
                prod.add(comp);
                comp = rs.getString("unidad");
                prod.add(comp);
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println("error: " + ex);
        }
        return prod;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu12;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    // End of variables declaration//GEN-END:variables
}
