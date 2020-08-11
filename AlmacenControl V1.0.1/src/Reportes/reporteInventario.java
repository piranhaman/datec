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
public class reporteInventario {

    String rutaArchivo;
    File archivoXLS;
    Workbook libro;
    FileOutputStream archivo;
    Sheet hoja;

    public reporteInventario(String nombre) {
        rutaArchivo = System.getProperty("user.home") + "/Inventario" + nombre + ".xls";
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
            Logger.getLogger(reporteInventario.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) throws Exception {
        reporteInventario a = new reporteInventario(principalAlmacen.GetFecha());
    }

    public void contenido() {
        
        ConexionMySQL conect = new ConexionMySQL();
        String consulta = "select * from refacciones";
        Cell celda;
        Row fila;
        int n=0;
        int c=0;
        
                fila = hoja.createRow(n++);
                celda = fila.createCell(c++);
                celda.setCellValue("Codigo");
                celda = fila.createCell(c++);
                celda.setCellValue("Nombre");
                celda = fila.createCell(c++);
                celda.setCellValue("Cantidad");
                celda = fila.createCell(c++);
                celda.setCellValue("PU");
                
        try {
            ResultSet rs = conect.Consulta(consulta);
            while (rs.next()) {
                c=0;
                fila = hoja.createRow(n++);
                celda = fila.createCell(c++);
                celda.setCellValue(rs.getString("codigo"));
                celda = fila.createCell(c++);
                celda.setCellValue(rs.getString("nombre"));
                celda = fila.createCell(c++);
                celda.setCellValue(rs.getInt("cantidad"));
                celda = fila.createCell(c++);
                celda.setCellValue(rs.getFloat("pu"));
                               
            }
        } catch (SQLException ex) {
            System.out.println("error: "+ex);
//JOptionPane.showMessageDialog(rootPane, "Error al cargar Clientes!!!");
        }
        
                
                
        
    }

    public void abreArchivo() {
        int respuesta = JOptionPane.showConfirmDialog(null, "Se ha generado el documento " + rutaArchivo + ", ¿Desea abrirlo?", "Pregunta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        //Si la respuesta es SI, abrirlo
        if (respuesta == JOptionPane.YES_OPTION) {

            try {
                Desktop.getDesktop().open(archivoXLS);
            } catch (IOException ex) {
                Logger.getLogger(reporteInventario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
