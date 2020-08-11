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
public class reporteCompras {

    String rutaArchivo;
    File archivoXLS;
    Workbook libro;
    FileOutputStream archivo;
    Sheet hoja;
    String fecha1, fecha2;

    public reporteCompras(String nombre, String fecha1, String fecha2) {
        this.fecha1 = fecha1;
        this.fecha2 = fecha2;
        rutaArchivo = System.getProperty("user.home") + "/Compras" + nombre + ".xls";
        archivoXLS = new File(rutaArchivo);
        if (archivoXLS.exists()) {
            archivoXLS.delete();
        }

        try {
            archivoXLS.createNewFile();
            libro = new HSSFWorkbook();
            archivo = new FileOutputStream(archivoXLS);
            hoja = libro.createSheet("hoja1");
            contenido();
            libro.write(archivo);
            archivo.close();
            abreArchivo();
        } catch (IOException ex) {
            Logger.getLogger(reporteCompras.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) throws Exception {
        reporteCompras a = new reporteCompras(principalAlmacen.GetFecha(), principalAlmacen.GetFecha(), principalAlmacen.GetFecha());
    }

    public void contenido() {

        ConexionMySQL conect = new ConexionMySQL();
        String consulta = "select * from compra where fecha BETWEEN  '"+fecha1+"' AND '"+fecha2+"'";
        String destino = "";
        Cell celda;
        Row fila;
        int n = 0;
        int c = 0;
        fila = hoja.createRow(n++);
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

        try {
            ResultSet rs = conect.Consulta(consulta);
            while (rs.next()) {
                c = 0;
                fila = hoja.createRow(n++);
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
            System.out.println("error: " + ex);
//JOptionPane.showMessageDialog(rootPane, "Error al cargar Clientes!!!");
        }

        return comp;
    }

    public void abreArchivo() {
        int respuesta = JOptionPane.showConfirmDialog(null, "Se ha generado el documento " + rutaArchivo + ", ¿Desea abrirlo?", "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        //Si la respuesta es SI, abrirlo
        if (respuesta == JOptionPane.YES_OPTION) {

            try {
                Desktop.getDesktop().open(archivoXLS);
            } catch (IOException ex) {
                Logger.getLogger(reporteCompras.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
