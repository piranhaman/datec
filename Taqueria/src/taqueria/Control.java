/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package taqueria;

import static Reportes.PDFVentasDia.getPlatoNombre;
import SQL.ConexionMySQL;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import static taqueria.hiloServidor.actualizarDispostivos;

/**
 *
 * @author el dios
 */
public class Control extends javax.swing.JFrame {

    /**
     * Creates new form Control
     */
    public String NoMesa;
    public String horaI;
    public int HorasC;
    public int MinC;
    public int tarH = 8;
    public int tarM = 4;
    public int tarMin = 3;
    public String usr;
    public int numoreden = 1;
    JPopupMenu menucontextual;

    public Control(ImageIcon a, String m) {
        this.setUndecorated(true);
        initComponents();
        //cargarprod();
        TableColumnModel columnModel = jTable1.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(20);
        columnModel.getColumn(2).setPreferredWidth(60);
        columnModel.getColumn(3).setPreferredWidth(40);
        columnModel.getColumn(4).setPreferredWidth(40);
        columnModel.getColumn(5).setPreferredWidth(40);
        columnModel.getColumn(6).setPreferredWidth(60);
        a = new ImageIcon(a.getImage().getScaledInstance(Numero.getWidth(), -1, Image.SCALE_DEFAULT));
        Numero.setIcon(a);
        NoMesa = m;

        menucontextual = new JPopupMenu();
        JMenuItem item = new JMenuItem("Mermar/Desmermar");
        JMenuItem item2 = new JMenuItem("Eliminar");
        JMenuItem item3 = new JMenuItem("Cancelar");

        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int i = jTable1.getSelectedRow();
                int numOrden = 0;
                try {
                    numOrden = Integer.parseInt(jTable1.getValueAt(i, 0).toString());

                    int id = Integer.parseInt(jTable1.getValueAt(i, 1).toString());
                    int idVenta = Integer.parseInt(NoCliente.getText());
                    menucontextual.setVisible(false);
                    if (jTable1.getValueAt(i, 6).toString().equals("MERMADO")) {
                        desmermar(idVenta, numOrden, id);
                        jTable1.setValueAt("TERMINADO", i, 6);
                    } else {
                        mermar(idVenta, numOrden, id);
                        jTable1.setValueAt("MERMADO", i, 6);
                    }
                    CalcularTotales();
                } catch (Exception ex) {
                    System.out.println("Exception: " + ex);
                }
            }

            private void mermar(int idVenta, int numOrden, int id) {
                ConexionMySQL mysql = new ConexionMySQL();
                Connection cn = mysql.Conectar();
                PreparedStatement pst;
                String sSQL = "UPDATE detalleVenta SET  status= 'MERMADO' where idVenta =" + idVenta + " and idMenu=" + id + " and nOrden=" + numOrden;
                try {
                    pst = cn.prepareStatement(sSQL);
                    int n = pst.executeUpdate();
                    if (n > 0) {
                        JOptionPane.showMessageDialog(null, "Producto Mermado!!");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }

            private void desmermar(int idVenta, int numOrden, int id) {
                ConexionMySQL mysql = new ConexionMySQL();
                Connection cn = mysql.Conectar();
                PreparedStatement pst;
                String sSQL = "UPDATE detalleVenta SET  status= 'TERMINADO' where idVenta =" + idVenta + " and idMenu=" + id + " and nOrden=" + numOrden;
                try {
                    pst = cn.prepareStatement(sSQL);
                    int n = pst.executeUpdate();
                    if (n > 0) {
                        JOptionPane.showMessageDialog(null, "Producto Desmermado!!");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }

        });

        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int i = jTable1.getSelectedRow();
                int numOrden = 0;
                try {
                    numOrden = Integer.parseInt(jTable1.getValueAt(i, 0).toString());

                    int id = Integer.parseInt(jTable1.getValueAt(i, 1).toString());
                    int idVenta = Integer.parseInt(NoCliente.getText());
                    menucontextual.setVisible(false);
                    int confirmado = JOptionPane.showConfirmDialog(
                            Control.this,
                            "Deceas Eliminar el elemento de la venta??");
                    if (JOptionPane.OK_OPTION == confirmado) {

                        Eliminar(idVenta, numOrden, id);
                        ((DefaultTableModel)jTable1.getModel()).removeRow(i);
                        CalcularTotales();
                    }
                } catch (Exception ex) {
                    System.out.println("Exception: " + ex);
                }
            }

            private void Eliminar(int idVenta, int numOrden, int id) {
                ConexionMySQL mysql = new ConexionMySQL();
                Connection cn = mysql.Conectar();
                PreparedStatement pst;
                String sSQL = "DELETE FROM detalleVenta where idVenta =" + idVenta + " and idMenu=" + id + " and nOrden=" + numOrden;
                try {
                    pst = cn.prepareStatement(sSQL);
                    int n = pst.executeUpdate();
                    if (n > 0) {
                        JOptionPane.showMessageDialog(null, "Producto Eliminado!!");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
            }

        });

        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                menucontextual.setVisible(false);
            }
        });
        menucontextual.add(item);
        menucontextual.add(item2);
        menucontextual.add(item3);

        //setExtendedState(0);
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/LOGO.png")).getImage());
        setDefaultCloseOperation(0);
        setResizable(false);
        setVisible(false);
        setLocationRelativeTo(null);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Numero = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        NoCliente = new javax.swing.JLabel();
        hInicio = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        Numero.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Hora de inicio:");

        jLabel1.setText("No. Venta:");

        NoCliente.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        NoCliente.setText("0");

        hInicio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        hInicio.setText("00:00:00");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No. Orden", "ID", "Descripcion", "Precio $", "Cantidad", "Total $", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Float.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton2.setText("Terminar Venta");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Cerrar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel4.setText("TotalConsumo: $");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("0.0");

        jButton4.setText("Nueva Orden");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel3.setText("Mesero:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Salvador");

        jMenu1.setText("Archivo");

        jMenuItem1.setText("Cambio de Mesa");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(Numero, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(47, 47, 47)
                                        .addComponent(jLabel2)
                                        .addGap(45, 45, 45)
                                        .addComponent(hInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(61, 61, 61))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(101, 101, 101)
                                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(206, 206, 206)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(52, 52, 52))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(NoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(28, 28, 28))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(NoCliente))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6))
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(hInicio))
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Numero, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        this.setVisible(false);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        int confirmado = JOptionPane.showConfirmDialog(
                this,
                "Deceas Terminar la Venta????");
        if (JOptionPane.OK_OPTION == confirmado) {
            if (finalizarVenta()) {
                JOptionPane.showMessageDialog(this.getComponent(0), "Venta Terminada");
                hiloServidor.actualizarDispostivos("0<" + hiloServidor.MesasOcupadas());
            }
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        new cambiosDeMesa(this).setVisible(true);
        /*
        String mselect = "";
        int seleccion = JOptionPane.showOptionDialog(
                null, "Selecciona una mesa", "Cambio de Mesa", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Mesa1", "Mesa2", "Mesa3", "Mesa4", "Mesa5", "Maquina6", "Maquina7", "Maquina8", "Maquina9", "Maquina10", "Maquina11"}, "opcion 1");
        mselect = "Mesa" + (seleccion + 1);
        if (!Caja.Disponibilidad(mselect)) {

            ConexionMySQL mysql = new ConexionMySQL();
            Connection cn = mysql.Conectar();
            PreparedStatement pst;
            String sSQL = "UPDATE venta SET nMesa = '" + mselect + "' where id  =" + Integer.parseInt(NoCliente.getText());

            try {
                pst = cn.prepareStatement(sSQL);
                int n = pst.executeUpdate();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }

            Caja.cambioMesa(mselect);
            clear();
        } else {
            JOptionPane.showConfirmDialog(this, "La Mesa que Seleccionaste Esta Ocupada intenta con otra :D");
        }*/
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        if ((evt.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
            //System.out.println("Click con el botón izdo");
            menucontextual.setVisible(false);

        } else {
            //System.out.println("Click con el botón dcho");
            menucontextual.setVisible(true);
            menucontextual.setLocation(evt.getLocationOnScreen());

        }// TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        new Orden(this).setVisible(true);
// TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(Control.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Control.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Control.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Control.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Control(null, "").setVisible(true);
            }
        });
    }

    public void nuevaRenta(String usr) {
        this.usr = usr;
        numoreden = 1;
        limpiarTabla(jTable1);
        jLabel5.setText("0.0");
        hInicio.setText("00:00:00");

        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();
        PreparedStatement pst;

        String sSQL = "";
        String mensaje = "";
        String hora = getHora();
        String fech = GetFecha();

        sSQL = "INSERT INTO  venta (fecha,hEntrada,hSalida,nMesa,id_mesero,total,status)"
                + "VALUES(?, ?, ?, ?, ?, ?, ?)";
        mensaje = "Los Datos se han insertado de manera satisfactoria";


        /*venta(
         id int NOT NULL AUTO_INCREMENT,
         fecha date,
         hEntrada time,
         hSalida time,
         nMesa varchar(10),
         id_mesero int,
         total numeric(7,2),
         status varchar(20),
         primary key(id),
         foreign key(id_mesero)references usuarios(id));*/
        try {
            pst = cn.prepareStatement(sSQL);

            pst.setString(1, fech);
            pst.setString(2, hora);
            pst.setString(3, hora);
            pst.setString(4, NoMesa);
            pst.setInt(5, getIdMesero(usr));
            pst.setFloat(6, 0.0f);
            pst.setString(7, "Ocupado");

            int n = pst.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "asdsd" + ex);
        }

        hInicio.setText(hora);
        NoCliente.setText("" + getCliente());
        jLabel6.setText(usr);
        horaI = hora;

    }

    public void clear() {

        Caja.desocupaMesa(NoMesa);
        this.setVisible(false);
    }

    public void limpiarTabla(JTable a) {
        DefaultTableModel m = (DefaultTableModel) a.getModel();
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            m.removeRow(i);
            i -= 1;
        }
    }

    public void CalcularTotales() {
        float Total = 0;
        int TotalInt = 0;
        int filas = jTable1.getRowCount();
        int cant;
        float prec;
        float tot;

        for (int i = 0; i < filas; i++) {
            prec = (float) jTable1.getValueAt(i, 3);
            cant = (int) jTable1.getValueAt(i, 4);
            if (!jTable1.getValueAt(i, 6).equals("MERMADO")) {
                tot = prec * cant;
                jTable1.setValueAt(tot, i, 5);
                Total += tot;
            }
        }

        jLabel5.setText(""+Total);

    }

    public void CargarCuenta() {
        ConexionMySQL conect = new ConexionMySQL();
        String consulta = "select * from venta where nMesa='" + NoMesa + "' and status='Ocupado'";
        ResultSet rs = conect.Consulta(consulta);
        try {
            while (rs.next()) {
                horaI = rs.getString("hEntrada");
                hInicio.setText(horaI);
                NoCliente.setText("" + rs.getInt("id"));
                usr = getNomMesero(rs.getInt("id_mesero"));
                jLabel6.setText(usr);
                String consulta2 = "select * from detalleVenta where idVenta=" + rs.getInt("id");
                ResultSet rs2 = conect.Consulta(consulta2);
                DefaultTableModel m;
                m = (DefaultTableModel) jTable1.getModel();
                while (rs2.next()) {
                    m.addRow(new Object[]{rs2.getInt("nOrden"), rs2.getInt("idMenu"), getPlatoNombre(rs2.getInt("idMenu")), rs2.getFloat("costo"), rs2.getInt("cantidad"), rs2.getFloat("total"), rs2.getString("status")});
                    this.numoreden = rs2.getInt("nOrden") + 1;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void actualizaDatos() {

        limpiarTabla(jTable1);
        try {
            ConexionMySQL conect = new ConexionMySQL();

            String consulta2 = "select * from detalleVenta where idVenta=" + this.NoCliente.getText();
            ResultSet rs2 = conect.Consulta(consulta2);
            DefaultTableModel m;
            m = (DefaultTableModel) jTable1.getModel();
            while (rs2.next()) {
                m.addRow(new Object[]{rs2.getInt("nOrden"), rs2.getInt("idMenu"), getPlatoNombre(rs2.getInt("idMenu")), rs2.getFloat("costo"), rs2.getInt("cantidad"), rs2.getFloat("total"), rs2.getString("status")});
                this.numoreden = rs2.getInt("nOrden") + 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        CalcularTotales();
    }

    public int getCliente() {

        int cliente = 0;
        ConexionMySQL conect = new ConexionMySQL();
        String consulta = "select MAX(id) AS ultimoid from venta where nMesa='" + NoMesa + "'";
        ResultSet rs = conect.Consulta(consulta);
        try {
            while (rs.next()) {
                cliente = rs.getInt("ultimoid");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return cliente;
    }

    public int getIdMesero(String nom) {
        int id = 0;
        ConexionMySQL conect = new ConexionMySQL();
        String consulta = "select * from usuarios where nombre='" + nom + "'";
        ResultSet rs = conect.Consulta(consulta);
        try {
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return id;
    }

    public String getNomMesero(int id) {
        String nom = "";
        ConexionMySQL conect = new ConexionMySQL();
        String consulta = "select * from usuarios where id=" + id;
        ResultSet rs = conect.Consulta(consulta);
        try {
            while (rs.next()) {
                nom = rs.getString("nombre");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return nom;
    }

    public void reAbrirMesa() {
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();
        PreparedStatement pst;

        int NumClient = Integer.parseInt(NoCliente.getText());
        String sSQL = "UPDATE venta SET status = 'Ocupado' where id=" + NumClient;

        try {
            pst = cn.prepareStatement(sSQL);
            int n = pst.executeUpdate();
            if (n > 0) {
                Caja.cambioMesa(NoMesa);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public String getHora() {

        String hora = "";
        Calendar fecha = new GregorianCalendar();
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
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

    public String GetFecha() {

        Calendar fecha = new GregorianCalendar();
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int horas = fecha.get(Calendar.HOUR);
        int min = fecha.get(Calendar.MINUTE);
        String fech = anio + "-" + mes + "-" + dia;

        return fech;

    }

    public int getHorasC() {
        int h = 0;
        String hi = hInicio.getText();
        if (hi.length() == 8) {
            h = Integer.parseInt(hi.substring(0, 2));
        } else {
            h = Integer.parseInt(hi.substring(0, 1));
        }
        return h;
    }

    public int getMinC() {
        int m = 0;
        String hi = hInicio.getText();
        if (hi.length() == 8) {
            m = Integer.parseInt(hi.substring(3, 5));
        } else {
            m = Integer.parseInt(hi.substring(2, 4));
        }

        return m;
    }

    public void imprimirTicket() {
        Ticket a = new Ticket();
        a.AddSubCabecera("No. Cliente: " + NoCliente.getText());
        a.AddSubCabecera(a.DarEspacio());

        a.AddSubCabecera("Hora de Llegada: " + horaI);
        a.AddSubCabecera(a.DarEspacio());
        a.AddSubCabecera("-> " + NoMesa);
        a.AddSubCabecera(a.DarEspacio());
        a.AddSubCabecera("Le Atendio: " + jLabel6.getText());
        a.AddSubCabecera(a.DarEspacio());

        a.AddItem("", "", a.DarEspacio());
        a.AddItem("Cant.", "Descripcion", "Importe");
        a.AddItem("", "", a.DarEspacio());

        for (int i = 0; i < jTable1.getRowCount(); i++) {
            if (!jTable1.getValueAt(i, 6).toString().equals("MERMADO")) {
                a.AddItem(jTable1.getValueAt(i, 4).toString() + "  ", rellenaCadena(jTable1.getValueAt(i, 2).toString()), "$" + jTable1.getValueAt(i, 5).toString());
                a.AddItem("", "", a.DarEspacio());
            }
        }

        a.AddTotal("TOTAL:                $", jLabel5.getText());
        a.AddTotal("", a.DarEspacio());
        a.AddTotal("", a.DarEspacio());
        a.AddTotal("", a.DarEspacio());

        a.ImprimirDocumento();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel NoCliente;
    private javax.swing.JLabel Numero;
    private javax.swing.JLabel hInicio;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    public boolean finalizarVenta() {
        boolean ret = false;
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();
        PreparedStatement pst;

        String sSQL = "";
        String mensaje = "";
        String hora = getHora();
        String fech = GetFecha();

        float total = Float.parseFloat(jLabel5.getText());
        int NumClient = Integer.parseInt(NoCliente.getText());
        sSQL = "UPDATE venta SET hSalida='" + getHora() + "', total = " + total + ", status = 'Terminado' where id=" + NumClient;

        mensaje = "Venta Terminada\n";

        try {
            pst = cn.prepareStatement(sSQL);
            int n = pst.executeUpdate();
            if (n > 0) {
                ret = true;
                if (Caja.jCheckBox1.isSelected()) {
                    imprimirTicket();
                }

                clear();

            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

        numoreden = 1;

        return ret;
    }

    private String rellenaCadena(String toString) {
        while (toString.length() < 15) {
            toString += ' ';
        }
        return toString;
    }
}
