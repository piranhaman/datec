/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taqueria;

import SQL.ConexionMySQL;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author el dios
 */
public class Orden extends javax.swing.JFrame {

    /**
     * Creates new form Orden
     */
    public String codigo;
    public static float total;
    agregPlatoOrden buscador;
    Control destino;

    public Orden(Control tab) {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/LOGO.png")).getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        destino = tab;
        jLabel3.setText(destino.NoMesa);
        jLabel11.setText("" + destino.numoreden);
        buscar();

        //jLabel11.setText("" + principalAlmacen.nServicio);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Orden");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/agregar.png"))); // NOI18N
        jButton2.setText("Añadir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Plato/Bebida", "PU", "Cantidad", "Total", "Destino"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jLabel8.setText("Total: $");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("0.00");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ACEPTAR.png"))); // NOI18N
        jButton3.setText("Terminar y Enviar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setText("1");

        jLabel2.setText("Mesa:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Mesa1");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Plato/Bebida", "Precio", "Categoria"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setRowSorter(new TableRowSorter(jTable3.getModel()));
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jTable3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable3KeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel11)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jLabel9)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        agrega();

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        DefaultTableModel m;
        DefaultTableModel cons = (DefaultTableModel) jTable2.getModel();
        ordenAEnviar orTacos = new ordenAEnviar(jLabel3.getText(), Integer.parseInt(destino.NoCliente.getText()), destino.numoreden);
        ordenAEnviar orSodas = new ordenAEnviar(jLabel3.getText(), Integer.parseInt(destino.NoCliente.getText()), destino.numoreden);

        m = (DefaultTableModel) destino.jTable1.getModel();
        for (int i = 0; i < cons.getRowCount(); i++) {
            m.addRow(new Object[]{Integer.parseInt(jLabel11.getText()), cons.getValueAt(i, 0), cons.getValueAt(i, 1), cons.getValueAt(i, 2), cons.getValueAt(i, 3), cons.getValueAt(i, 4), "PRODUCCION"});
            int cant = Integer.parseInt(cons.getValueAt(i, 3).toString());
            int id = Integer.parseInt(cons.getValueAt(i, 0).toString());
            String plato = cons.getValueAt(i, 1).toString();
            if (cons.getValueAt(i, 5).toString().equals("TACOS")) {
                orTacos.agregarAOrden(id,cant, plato);
            } else if (cons.getValueAt(i, 5).toString().equals("FUENTE DE SODAS")) {
                orSodas.agregarAOrden(id,cant, plato);
            } else {
                orTacos.agregarAOrden(id,cant, plato);
                orSodas.agregarAOrden(id,cant, plato);
            }
        }
        SocketServidor.enviarOrdenTacos(orTacos);
        SocketServidor.enviarOrdenSodas(orSodas);
        guardarPedido();
        destino.numoreden++;
        destino.CalcularTotales();
        
        this.dispose();

// TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked

        if ((evt.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {

        } else {
            try {
                int i = jTable2.getSelectedRow();
                DefaultTableModel m = (DefaultTableModel) jTable2.getModel();
                m.removeRow(i);
            } catch (Exception e) {

            }
            calcular();
        }

// TODO add your handling code here:
    }//GEN-LAST:event_jTable2MouseClicked

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        jTextField1.setText(jTextField1.getText().toUpperCase());
        buscar();
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked

        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            agrega();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable3MouseClicked

    private void jTable3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable3KeyReleased

        // TODO add your handling code here:
    }//GEN-LAST:event_jTable3KeyReleased

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
            java.util.logging.Logger.getLogger(Orden.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Orden.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Orden.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Orden.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Orden(null).setVisible(true);
            }
        });
    }

    public void guardarPedido() {

        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();
        PreparedStatement pst;

        String sSQL = "";
        String mensaje = "";
        String Articulo;
        String fech = destino.GetFecha();
        sSQL = "INSERT INTO  detalleVenta (idVenta,idMenu,cantidad,costo,total,status, fecha, nOrden)"
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        mensaje = "Los Datos se han insertado de manera satisfactoria";

        for (int i = 0; i < jTable2.getRowCount(); i++) {
            Articulo = jTable2.getValueAt(i, 1).toString();
            try {
                pst = cn.prepareStatement(sSQL);
                pst.setInt(1, Integer.parseInt(destino.NoCliente.getText()));
                pst.setInt(2, (int) jTable2.getValueAt(i, 0));
                pst.setInt(3, (int) jTable2.getValueAt(i, 3));
                pst.setFloat(4, (float) jTable2.getValueAt(i, 2));
                pst.setFloat(5, (float) jTable2.getValueAt(i, 4));
                pst.setString(6, "PRODUCCION");
                pst.setString(7, fech);
                pst.setInt(8, destino.numoreden);
                int n = pst.executeUpdate();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error detalle Venta: " + ex);
            }
        }
    }

    
    public static void calcular() {
        DefaultTableModel m = (DefaultTableModel) jTable2.getModel();
        int filas = jTable2.getRowCount();
        total = 0;
        for (int i = 0; i < filas; i++) {
            float pr = (Integer.parseInt(m.getValueAt(i, 3).toString())) * (Float.parseFloat(m.getValueAt(i, 2).toString()));
            m.setValueAt(pr, i, 4);
            total += pr;
        }
        jLabel9.setText(String.format("%.2f", total));
    }

    public void buscar() {
        ConexionMySQL conect = new ConexionMySQL();
        String nombre = jTextField1.getText();
        String consulta = "select * from menu where status='SI'";
        ResultSet rs = conect.Consulta(consulta);
        limpiarTabla(jTable3);
        DefaultTableModel m = (DefaultTableModel) jTable3.getModel();
        try {
            while (rs.next()) {
                if (rs.getString("nombre").contains(nombre)) {
                    m.addRow(new Object[]{rs.getInt("id"), rs.getString("nombre"), rs.getFloat("costo"), rs.getString("categoria")});
                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "Error en la busqueda!!!");
            System.out.println(ex);
        }
    }

    public int duplicado(String cod) {
        int a = -1;
        DefaultTableModel m = (DefaultTableModel) jTable2.getModel();
        for (int i = 0; i < jTable2.getRowCount(); i++) {
            if (m.getValueAt(i, 0).toString().equals(cod)) {
                a = i;
            }
        }
        return a;
    }

    public void limpiarTabla(JTable a) {

        DefaultTableModel m = (DefaultTableModel) a.getModel();
        for (int i = 0; i < a.getRowCount(); i++) {
            m.removeRow(i);
            i -= 1;
        }
    }

    public int duplicado(int cod) {
        int a = -1;
        DefaultTableModel m = (DefaultTableModel) Orden.jTable2.getModel();
        for (int i = 0; i < Orden.jTable2.getRowCount(); i++) {
            if (m.getValueAt(i, 0).toString().equals("" + cod)) {
                a = i;
            }
        }
        return a;
    }

    public void agrega() {
        // TODO add your handling code here:
        String codigo = "", descripcion = "", dest;
        int cantidad, id;
        float precio;
        String a = JOptionPane.showInputDialog("Cantidad: ");

        int fila = jTable3.getSelectedRow();

        if (fila >= 0) {
            try {
                cantidad = Integer.parseInt(a);
            } catch (java.lang.NumberFormatException ex) {
                cantidad = 1;
            }
            DefaultTableModel m = (DefaultTableModel) jTable3.getModel();
            id = (int) jTable3.getValueAt(fila, 0);
            descripcion = (String) jTable3.getValueAt(fila, 1);
            precio = (Float) jTable3.getValueAt(fila, 2);
            dest = (String) jTable3.getValueAt(fila, 3);
            DefaultTableModel m2 = (DefaultTableModel) Orden.jTable2.getModel();
            int dup = duplicado(id);
            if (dup == -1) {
                m2.addRow(new Object[]{id, descripcion, precio, cantidad, (cantidad * precio), dest});
            } else {
                m2.setValueAt((Integer.parseInt(m2.getValueAt(dup, 3).toString()) + cantidad), dup, 3);
            }

            Orden.calcular();
        } else {
            JOptionPane.showMessageDialog(null, "No has seleccionado ningun producto");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    public static javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public static javax.swing.JTable jTable2;
    public static javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
