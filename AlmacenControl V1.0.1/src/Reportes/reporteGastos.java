/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import SQL.ConexionMySQL;
import almacencontrol.principalAlmacen;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
/*Librerías para trabajar con archivos excel*/
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author el dios
 */
public class reporteGastos {

    String rutaArchivo;
    File archivoXLS;
    Workbook libro;
    FileOutputStream archivo;
    Sheet hoja;
    Sheet hoja2;
    Sheet hoja3;
    String fecha1, fecha2;
    ArrayList<Unidad> unidades;

    public reporteGastos(String nombre, String fecha1, String fecha2) {
        this.fecha1 = fecha1;
        this.fecha2 = fecha2;
        rutaArchivo = System.getProperty("user.home") + "/Gastos" + nombre + ".xls";
        archivoXLS = new File(rutaArchivo);
        if (archivoXLS.exists()) {
            archivoXLS.delete();
        }

        try {
            archivoXLS.createNewFile();
            libro = new HSSFWorkbook();
            archivo = new FileOutputStream(archivoXLS);
            hoja = libro.createSheet("Servicios");
            contenido(true);
            hoja2 = libro.createSheet("Servcios Foraneos");
            contenido2(true);
            hoja3 = libro.createSheet("Compra de Refacciones");
            contenido3(true);
            libro.write(archivo);
            archivo.close();
            abreArchivo();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Favor de cerrar el archivo antes de generar uno nuevo");
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    public reporteGastos(String nombre, String fecha1, String fecha2, ArrayList<Unidad> a) {
        this.fecha1 = fecha1;
        this.fecha2 = fecha2;
        this.unidades = a;
        rutaArchivo = System.getProperty("user.home") + "/Gastos " + nombre + ".xls";
        archivoXLS = new File(rutaArchivo);
        if (archivoXLS.exists()) {
            archivoXLS.delete();
        }

        try {
            archivoXLS.createNewFile();
            libro = new HSSFWorkbook();
            archivo = new FileOutputStream(archivoXLS);
            hoja = libro.createSheet("Servicios");
            contenido(false);
            hoja2 = libro.createSheet("Servcios Foraneos");
            contenido2(false);
            hoja3 = libro.createSheet("Compra de Refacciones");
            contenido3(false);
            libro.write(archivo);
            archivo.close();
            abreArchivo();
        } catch (IOException ex) {
            Logger.getLogger(reporteGastos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) throws Exception {
        reporteGastos a = new reporteGastos(principalAlmacen.GetFecha(), principalAlmacen.GetFecha(), principalAlmacen.GetFecha());
    }

    public void contenido(boolean d) {

        int servicio = 0;
        String destino = "";
        Cell celda;
        Row fila;
        int n = 0;
        int c = 0;
        
        String concepto="",fecha,hora,realzo,caja;
        int noSol=0,nocaja,kilo;
        float total;
        
        
        fila = hoja.createRow(n++);
        celda = fila.createCell(0);
        celda.setCellValue("Servicios");
        fila = hoja.createRow(n++);
        celda = fila.createCell(c++);
        celda.setCellValue("No. Solicitud");
        celda = fila.createCell(c++);
        celda.setCellValue("Concepto");
        celda = fila.createCell(c++);
        celda.setCellValue("Fecha");
        celda = fila.createCell(c++);
        celda.setCellValue("Hora");
        celda = fila.createCell(c++);
        celda.setCellValue("Quien Realizo");
        celda = fila.createCell(c++);
        celda.setCellValue("Tracto/Caja");
        celda = fila.createCell(c++);
        celda.setCellValue("Numero Economico");
        celda = fila.createCell(c++);
        celda.setCellValue("Refaccion");
        celda = fila.createCell(c++);
        celda.setCellValue("Cantidad");
        celda = fila.createCell(c++);
        celda.setCellValue("Importe");
        celda = fila.createCell(c++);
        celda.setCellValue("Total");
        celda = fila.createCell(c++);
        celda.setCellValue("Kilometraje");

        ConexionMySQL conect = new ConexionMySQL();
        if (d) {
            String consulta = "select * from servicio where fecha BETWEEN  '" + fecha1 + "' AND '" + fecha2 + "'";

            try {
                ResultSet rs = conect.Consulta(consulta);
                ResultSet rs2;
                
                
                while (rs.next()) {

                    servicio = rs.getInt("id");
                    noSol=rs.getInt("solicitud");
                    concepto = rs.getString("concepto");
                    fecha = rs.getString("fecha");
                    hora=rs.getString("hora");
                    realzo=getRealizo(servicio);
                    caja=rs.getString("CaTra");
                    nocaja=rs.getInt("nCaTra");
                    total=rs.getFloat("total");
                    kilo=rs.getInt("kilo");
                    
                    
                    String consulta2 = "select * from consumo where idServ=" + servicio;
                    rs2 = conect.Consulta(consulta2);
                    while (rs2.next()) {
                        c = 0;
                        fila = hoja.createRow(n++);
                        celda = fila.createCell(c++);
                        celda.setCellValue(noSol);
                        celda = fila.createCell(c++);
                        celda.setCellValue(concepto);
                        celda = fila.createCell(c++);
                        celda.setCellValue(fecha);
                        celda = fila.createCell(c++);
                        celda.setCellValue(hora);
                        celda = fila.createCell(c++);
                        celda.setCellValue(realzo);

                        celda = fila.createCell(c++);
                        celda.setCellValue(caja);
                        celda = fila.createCell(c++);
                        celda.setCellValue(nocaja);

                        celda = fila.createCell(c++);
                        celda.setCellValue(getRefNombre(rs2.getInt("idRef")));
                        celda = fila.createCell(c++);
                        celda.setCellValue(rs2.getInt("cantidad"));
                        celda = fila.createCell(c++);
                        celda.setCellValue(rs2.getFloat("costo"));

                        celda = fila.createCell(c++);
                        celda.setCellValue(total);
                        celda = fila.createCell(c++);
                        celda.setCellValue(kilo);

                    }
                }
            } catch (SQLException ex) {
                //System.out.println("error: " + ex);
                JOptionPane.showMessageDialog(null, "Error: "+ex);
            }
        } else {
            for (int i = 0; i < unidades.size(); i++) {//and CaTra='" + unidades.get(i).Catra + "' and nCaTra=" + unidades.get(i).num
                String consulta = "select * from servicio where CaTra='" + unidades.get(i).Catra + "' and nCaTra=" + unidades.get(i).num +" and fecha BETWEEN  '" + fecha1 + "' AND '" + fecha2 + "'" ;

                try {
                    ResultSet rs = conect.Consulta(consulta);
                    ResultSet rs2;
                    while (rs.next()) {

                        servicio = rs.getInt("id");
                        String consulta2 = "select * from consumo where idServ=" + servicio;
                        rs2 = conect.Consulta(consulta2);
                        while (rs2.next()) {
                            c = 0;
                            fila = hoja.createRow(n++);
                            celda = fila.createCell(c++);
                            celda.setCellValue(rs.getInt("solicitud"));
                            celda = fila.createCell(c++);
                            celda.setCellValue(rs.getString("concepto"));
                            celda = fila.createCell(c++);
                            celda.setCellValue(rs.getString("fecha"));
                            celda = fila.createCell(c++);
                            celda.setCellValue(rs.getString("hora"));

                            celda = fila.createCell(c++);
                            celda.setCellValue(getRealizo(servicio));

                            celda = fila.createCell(c++);
                            celda.setCellValue(rs.getString("CaTra"));
                            celda = fila.createCell(c++);
                            celda.setCellValue(rs.getInt("nCaTra"));

                            celda = fila.createCell(c++);
                            celda.setCellValue(getRefNombre(rs2.getInt("idRef")));
                            celda = fila.createCell(c++);
                            celda.setCellValue(rs2.getInt("cantidad"));
                            celda = fila.createCell(c++);
                            celda.setCellValue(rs2.getFloat("costo"));

                            celda = fila.createCell(c++);
                            celda.setCellValue(rs.getFloat("total"));
                            celda = fila.createCell(c++);
                            celda.setCellValue(rs.getInt("kilo"));

                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("error: " + ex);
//JOptionPane.showMessageDialog(rootPane, "Error al cargar Clientes!!!");
                }
            }
        }

    }

    public void contenido2(boolean d) {

        int servicio = 0;
        String destino = "";
        Cell celda;
        Row fila;
        int n = 0;
        int c = 0;
        fila = hoja2.createRow(n++);
        celda = fila.createCell(0);
        celda.setCellValue("Servicios Foraneos");
        fila = hoja2.createRow(n++);
        celda = fila.createCell(c++);
        celda.setCellValue("Remision");
        celda = fila.createCell(c++);
        celda.setCellValue("Concepto");
        celda = fila.createCell(c++);
        celda.setCellValue("Fecha");
        celda = fila.createCell(c++);
        celda.setCellValue("Hora");
        celda = fila.createCell(c++);
        celda.setCellValue("Tracto/Caja");
        celda = fila.createCell(c++);
        celda.setCellValue("Numero Economico");
        celda = fila.createCell(c++);
        celda.setCellValue("Total");

        ConexionMySQL conect = new ConexionMySQL();

        if (d) {
            String consulta = "select * from servicioForaneo where fecha BETWEEN  '" + fecha1 + "' AND '" + fecha2 + "'";

            try {
                ResultSet rs = conect.Consulta(consulta);
                while (rs.next()) {

                    c = 0;
                    fila = hoja2.createRow(n++);
                    celda = fila.createCell(c++);
                    celda.setCellValue(rs.getInt("remison"));
                    celda = fila.createCell(c++);
                    celda.setCellValue(rs.getString("concepto"));
                    celda = fila.createCell(c++);
                    celda.setCellValue(rs.getString("fecha"));
                    celda = fila.createCell(c++);
                    celda.setCellValue(rs.getString("hora"));

                    
                    celda = fila.createCell(c++);
                    celda.setCellValue(rs.getString("CaTra"));
                    celda = fila.createCell(c++);
                    celda.setCellValue(rs.getInt("nCaTra"));

                    celda = fila.createCell(c++);
                    celda.setCellValue(rs.getFloat("total"));
                    System.out.println("asdasdas");

                }

            } catch (SQLException ex) {
                //System.out.println("error: " + ex);
                JOptionPane.showMessageDialog(null, "Error: "+ex);
            }
        } else {
            for (int i = 0; i < unidades.size(); i++) {// and CaTra='" + unidades.get(i).Catra + "' and nCaTra=" + unidades.get(i).num;
                String consulta = "select * from servicioForaneo where fecha BETWEEN  '" + fecha1 + "' AND '" + fecha2 + "'  and CaTra='" + unidades.get(i).Catra + "' and nCaTra=" + unidades.get(i).num;

                try {
                    ResultSet rs = conect.Consulta(consulta);
                    while (rs.next()) {

                        c = 0;
                        fila = hoja2.createRow(n++);
                        celda = fila.createCell(c++);
                        celda.setCellValue(rs.getInt("remison"));
                        celda = fila.createCell(c++);
                        celda.setCellValue(rs.getString("concepto"));
                        celda = fila.createCell(c++);
                        celda.setCellValue(rs.getString("fecha"));
                        celda = fila.createCell(c++);
                        celda.setCellValue(rs.getString("hora"));

                        

                        celda = fila.createCell(c++);
                        celda.setCellValue(rs.getString("CaTra"));
                        celda = fila.createCell(c++);
                        celda.setCellValue(rs.getInt("nCaTra"));

                        celda = fila.createCell(c++);
                        celda.setCellValue(rs.getFloat("total"));
                        

                    }

                } catch (SQLException ex) {
                    //System.out.println("error: " + ex);
                    JOptionPane.showMessageDialog(null, "Error: "+ex);
                }
            }

        }

    }

    public void contenido3(boolean d) {

        String destino = "";
        Cell celda;
        Row fila;
        int n = 0;
        int c = 0;
        fila = hoja3.createRow(n++);
        celda = fila.createCell(0);
        celda.setCellValue("Compra de Refacciones");
        fila = hoja3.createRow(n++);
        celda = fila.createCell(c++);
        celda.setCellValue("Remisión");
        celda = fila.createCell(c++);
        celda.setCellValue("Fecha");
        celda = fila.createCell(c++);
        celda.setCellValue("Destino");
        celda = fila.createCell(c++);
        celda.setCellValue("Refaccion");
        celda = fila.createCell(c++);
        celda.setCellValue("Cantidad");
        celda = fila.createCell(c++);
        celda.setCellValue("Importe");
        celda = fila.createCell(c++);
        celda.setCellValue("Total Remisión");

        ConexionMySQL conect = new ConexionMySQL();
        if (d) {
            String consulta = "select * from compra where fecha BETWEEN  '" + fecha1 + "' AND '" + fecha2 + "'";

            try {
                ResultSet rs = conect.Consulta(consulta);
                while (rs.next()) {
                    c = 0;
                    fila = hoja3.createRow(n++);
                    celda = fila.createCell(c++);
                    celda.setCellValue(rs.getInt("remison"));
                    celda = fila.createCell(c++);
                    celda.setCellValue(rs.getString("fecha"));
                    celda = fila.createCell(c++);
                    destino = rs.getString("destino");
                    if (destino.equals("Almacen")) {
                        celda.setCellValue(destino);
                    } else {
                        celda.setCellValue(destino + " " + rs.getInt("nCaTra"));
                    }
                    celda = fila.createCell(c++);
                    celda.setCellValue(getRefNombre(rs.getInt("idRef")));
                    celda = fila.createCell(c++);
                    celda.setCellValue(rs.getInt("cantidad"));
                    celda = fila.createCell(c++);
                    celda.setCellValue(rs.getInt("total"));
                    celda = fila.createCell(c++);
                    celda.setCellValue(rs.getInt("totalRem"));

                }
            } catch (SQLException ex) {
                System.out.println("error: " + ex);
//JOptionPane.showMessageDialog(rootPane, "Error al cargar Clientes!!!");
            }
        } else {
            for (int i = 0; i < unidades.size(); i++) {// and CaTra='" + unidades.get(i).Catra + "' and nCaTra=" + unidades.get(i).num;
                String consulta = "select * from compra where  destino='" + unidades.get(i).Catra + "' and nCaTra=" + unidades.get(i).num+" and fecha BETWEEN  '" + fecha1 + "' AND '" + fecha2 + "'";

                try {
                    ResultSet rs = conect.Consulta(consulta);
                    while (rs.next()) {
                        c = 0;
                        fila = hoja3.createRow(n++);
                        celda = fila.createCell(c++);
                        celda.setCellValue(rs.getInt("remison"));
                        celda = fila.createCell(c++);
                        celda.setCellValue(rs.getString("fecha"));
                        celda = fila.createCell(c++);
                        destino = rs.getString("destino");
                        if (destino.equals("Almacen")) {
                            celda.setCellValue(destino);
                        } else {
                            celda.setCellValue(destino + " " + rs.getInt("nCaTra"));
                        }
                        celda = fila.createCell(c++);
                        celda.setCellValue(getRefNombre(rs.getInt("idRef")));
                        celda = fila.createCell(c++);
                        celda.setCellValue(rs.getInt("cantidad"));
                        celda = fila.createCell(c++);
                        celda.setCellValue(rs.getInt("total"));
                        celda = fila.createCell(c++);
                        celda.setCellValue(rs.getInt("totalRem"));

                    }
                } catch (SQLException ex) {
                    //System.out.println("error: " + ex);
                    JOptionPane.showMessageDialog(null, "Error al cargar Clientes!!!");
                }
            }
        }

    }

    public String getRefNombre(int id) {
        String comp = "";
        ConexionMySQL conect = new ConexionMySQL();
        String consulta = "select * from refacciones where id=" + id;
        ResultSet rs = conect.Consulta(consulta);

        try {
            while (rs.next()) {
                comp = rs.getString("nombre");
            }
            rs.close();
        } catch (SQLException ex) {
            //System.out.println("error: " + ex);
            JOptionPane.showMessageDialog(null, "Error: "+ex);
        }

        return comp;
    }

    public String getRealizo(int id) {
        String comp = "";
        ConexionMySQL conect = new ConexionMySQL();
        ArrayList f = getidMec(id);
        
        for (int i = 0; i < f.size(); i++) {
            
        
        String consulta = "select * from operador where id="+(int)f.get(i);
        ResultSet rs = conect.Consulta(consulta);

        try {
            while (rs.next()) {
                comp = comp+rs.getString("nombre") + ",";
            }
            rs.close();
        } catch (SQLException ex) {
            //System.out.println("error: " + ex);
            JOptionPane.showMessageDialog(null, "Error: "+ex);
        }
        }
        
        if(comp.length()>0)
        comp=comp.substring(0, (comp.length()-1));
        System.out.println("***"+comp);

        return comp;
    }
    
    public ArrayList getidMec(int id) {
        int comp = 0;
        ArrayList f = new ArrayList();
        ConexionMySQL conect = new ConexionMySQL();
        String consulta = "select * from mantenimiento where idServ=" + id;
        ResultSet rs = conect.Consulta(consulta);

        try {
            while (rs.next()) {
                f.add(rs.getInt("idMec"));
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println("error: " + ex);
//JOptionPane.showMessageDialog(rootPane, "Error al cargar Clientes!!!");
        }

        return f;
    }

    public void abreArchivo() {
        int respuesta = JOptionPane.showConfirmDialog(null, "Se ha generado el documento " + rutaArchivo + ", ¿Desea abrirlo?", "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        //Si la respuesta es SI, abrirlo
        if (respuesta == JOptionPane.YES_OPTION) {

            try {
                Desktop.getDesktop().open(archivoXLS);
            } catch (IOException ex) {
                Logger.getLogger(reporteGastos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
