/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import SQL.ConexionMySQL;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author el dios
 */
public class inventarioPDF {

    private String strNombreDelPDF;

    private Font fuenteNegra10 = new Font(Font.TIMES_ROMAN, 10, Font.BOLD, Color.BLACK);
    private Font fuente9 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL, Color.BLACK);
    private Font fuenteNegra7 = new Font(Font.TIMES_ROMAN, 7, Font.BOLD, Color.BLACK);
    
    private Font fuenteAzul25 = new Font(Font.TIMES_ROMAN, 25, Font.BOLD, Color.BLUE);

    Color grisClaro = new Color(230, 230, 230);
    Color azulClaro = new Color(124, 195, 255);

    //############# VARIABLES PARA MANEJO DE BASE DE DATOS ########################
    //Guarda la consulta operacion a realizar
    ConexionMySQL mysql = new ConexionMySQL();

    String strConsultaSQL;
    //Apuntador a la conexion
    Connection conn = mysql.Conectar();
    //Para ejecutar operaciones SQL
    Statement estSQL1, estSQL2, estSQL3;
    //Para guardar los resultados de una operacion SELECT
    ResultSet rs, rs2, rs3;
    Document document;
    PdfWriter writer;
    String strRotuloPDF;
    public String fech, fech2, mes, cat;
    public static int anio, ultimoDia, diaActual, numeroMes = 0;

    //Metodo principal del ejemplo
    public inventarioPDF(String titulo, String nomPDF, String mes, int anio, String categoria) throws SQLException {
        this.mes = mes;
        this.anio = anio;
        this.cat = categoria;
        fech = getPrimerDiaDelMes(getNMes(mes), anio);
        fech2 = getUltimoDiaDelMes(getNMes(mes), anio);

        this.estSQL1 = conn.createStatement();
        this.estSQL2 = conn.createStatement();
        this.estSQL3 = conn.createStatement();
        strRotuloPDF = titulo;
        strNombreDelPDF = System.getProperty("user.home") + "/" + nomPDF;
        try {       //Hoja tamanio carta, rotarla (cambiar a horizontal)
            document = new Document(PageSize.LETTER,15,15,5,5);

            writer = PdfWriter.getInstance(
                    // that listens to the document
                    document,
                    // direccionar el PDF-stream a un archivo
                    new FileOutputStream(strNombreDelPDF));
            Membrete header = new Membrete();
                //Asignamos el manejador de eventos al escritor.
            writer.setPageEvent(header);
            document.open();

            agregarMetaDatos(document);
            agregarContenido(document);

            document.close();

            System.out.println("Se ha generado el PDF: " + strNombreDelPDF);
            abrirPDF();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //agrega el contenido del documento; para este ejemplo agrega una tabla con datos y una imagen
    //Espera como entrada el documento donde agregara el contenido
    private void agregarContenido(Document document) throws DocumentException {
        Paragraph ParrafoHoja = new Paragraph();

        // Agregamos una linea en blanco al principio del documento
        //agregarLineasEnBlanco(ParrafoHoja, 1);
        // Colocar un encabezado (en mayusculas)
        //ParrafoHoja.add(new Paragraph(strRotuloPDF.toUpperCase(), fuenteAzul25));
        //agregarLineasEnBlanco(ParrafoHoja, 1);
        // 1.- AGREGAMOS LA TABLA
        //String suc = "Flower Shoes";
        //ParrafoHoja.add(new Paragraph(suc.toUpperCase(), fuenteNegra10));
        agregarTabla(ParrafoHoja);
        agregarLineasEnBlanco(ParrafoHoja, 1);
        agregarTabla2(ParrafoHoja);
        agregarLineasEnBlanco(ParrafoHoja, 1);
        agregarTabla3(ParrafoHoja);
        agregarLineasEnBlanco(ParrafoHoja, 1);
        agregarTabla4(ParrafoHoja);
        
        

        document.add(ParrafoHoja);

    }

    //Se conecta al DBMS MySQL , obtiene los datos de la tabla (SELECT) y los acomoda en una tabla JTable de iText.
    // Espera como entrada el parrafo donde agregara la tabla
    private void agregarTabla(Paragraph parrafo) throws BadElementException {
        //Anchos de las columnas son 13 columnas
        //ventas
        //id, id_Empleado, id_platillo,cantidad, monto_venta, propina, Fecha_hora
        float anchosFilas[] = {1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f};
        PdfPTable tabla = new PdfPTable(anchosFilas);
        String rotulosColumnas[] = {"  ", "1/" + (numeroMes+1) + "/" + anio, "2/" + (numeroMes+1) + "/" + anio, "3/" + (numeroMes+1) + "/" + anio, "4/" + (numeroMes+1) + "/" + anio, "5/" + (numeroMes+1) + "/" + anio, "6/" + (numeroMes+1) + "/" + anio, "7/" + (numeroMes+1) + "/" + anio, "8/" + (numeroMes+1) + "/" + anio};

        // Porcentaje que ocupa a lo ancho de la pagina del PDF
        tabla.setWidthPercentage(100);
        //Alineacion horizontal centrada
        tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
        //agregar celda que ocupa las 9 columnas de los rotulos
        PdfPCell cell = new PdfPCell(new Paragraph(strRotuloPDF));
        cell.setColspan(15);
        //Centrar contenido de celda
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        //Color de fondo de la celda
        cell.setBackgroundColor(azulClaro);
        tabla.addCell(cell);

        try {

            // Mostrar los rotulos de las columnas
            for (int i = 0; i < rotulosColumnas.length; i++) {
                cell = new PdfPCell(new Paragraph(rotulosColumnas[i], fuenteNegra10));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(grisClaro);
                tabla.addCell(cell);
            }

            parrafo.add(tabla);

            float anchosFilas2[] = {1f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f};
            PdfPTable tabla2 = new PdfPTable(anchosFilas2);
            tabla2.setWidthPercentage(100);
            tabla2.setHorizontalAlignment(Element.ALIGN_CENTER);
        
            cell = new PdfPCell(new Paragraph("Producto", fuente9));
            tabla2.addCell(cell);
            for (int i = 0; i < 8; i++) {
                cell = new PdfPCell(new Paragraph("Entrada", fuenteNegra7));
                tabla2.addCell(cell);
                cell = new PdfPCell(new Paragraph("Salida", fuenteNegra7));
                tabla2.addCell(cell);

            }

            //Consultar la tabla empleados
            strConsultaSQL = "select * from productos where  categoria='" + cat + "'";
            //ejecutar consulta

            rs = estSQL1.executeQuery(strConsultaSQL);

            while (rs.next()) {
                cell = new PdfPCell(new Paragraph(rs.getString("Nombre") + " (" + rs.getString("unidad") + ")", fuente9));
                tabla2.addCell(cell);

                for (int i = 1; i <= 8; i++) {
                    String strConsultaSQL2 = "";
                    String fechacons=anio+"-"+(numeroMes+1)+"-"+i;
                    if (cat.equals("Tacos")) {
                        strConsultaSQL2 = "select * from invtacos where idProd=" + rs.getInt("id") + " and fecha='" + fechacons + "'";
                    } else if(cat.equals("Sodas")){
                        strConsultaSQL2 = "select * from invSodas where idProd=" + rs.getInt("id") + " and fecha='" + fechacons + "'";
                    }

                    rs2 = estSQL2.executeQuery(strConsultaSQL2);
                    int p = 0;
                    while (rs2.next()) {
                        cell = new PdfPCell(new Paragraph("" + rs2.getFloat("entrada"), fuente9));
                        tabla2.addCell(cell);
                        cell = new PdfPCell(new Paragraph("" + rs2.getFloat("salida"), fuente9));
                        tabla2.addCell(cell);
                        p++;
                    }
                    if (p == 0) {
                        cell = new PdfPCell(new Paragraph("0", fuente9));
                        tabla2.addCell(cell);
                        cell = new PdfPCell(new Paragraph("0", fuente9));
                        tabla2.addCell(cell);
                    }
                }
            }
            parrafo.add(tabla2);

        } catch (Exception e) {
            System.out.println("Excepcion al ejecutar CONSULTA!!!");
            //Mostrar la traza de la pila
            e.printStackTrace();
        }

    }  //Fin tabla
    
    
    private void agregarTabla2(Paragraph parrafo) throws BadElementException {
        //Anchos de las columnas son 13 columnas
        //ventas
        //id, id_Empleado, id_platillo,cantidad, monto_venta, propina, Fecha_hora
        float anchosFilas[] = {1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f};
        PdfPTable tabla = new PdfPTable(anchosFilas);
        String rotulosColumnas[] = {"  ",
            "9/" + (numeroMes+1) + "/" + anio,
            "10/" + (numeroMes+1) + "/" + anio,
            "11/" + (numeroMes+1) + "/" + anio,
            "12/" + (numeroMes+1) + "/" + anio,
            "13/" + (numeroMes+1) + "/" + anio,
            "14/" + (numeroMes+1) + "/" + anio,
            "15/" + (numeroMes+1) + "/" + anio,
            "16/" + (numeroMes+1) + "/" + anio};

        // Porcentaje que ocupa a lo ancho de la pagina del PDF
        tabla.setWidthPercentage(100);
        //Alineacion horizontal centrada
        tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
        //agregar celda que ocupa las 9 columnas de los rotulos
        PdfPCell cell = new PdfPCell(new Paragraph(strRotuloPDF));
        cell.setColspan(15);
        //Centrar contenido de celda
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        //Color de fondo de la celda
        cell.setBackgroundColor(azulClaro);
        tabla.addCell(cell);

        try {

            // Mostrar los rotulos de las columnas
            for (int i = 0; i < rotulosColumnas.length; i++) {
                cell = new PdfPCell(new Paragraph(rotulosColumnas[i], fuenteNegra10));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(grisClaro);
                tabla.addCell(cell);
            }

            parrafo.add(tabla);

            float anchosFilas2[] = {1f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f};
            PdfPTable tabla2 = new PdfPTable(anchosFilas2);
            tabla2.setWidthPercentage(100);
            tabla2.setHorizontalAlignment(Element.ALIGN_CENTER);
        
            cell = new PdfPCell(new Paragraph("Producto", fuente9));
            tabla2.addCell(cell);
            for (int i = 0; i < 8; i++) {
                cell = new PdfPCell(new Paragraph("Entrada", fuenteNegra7));
                tabla2.addCell(cell);
                cell = new PdfPCell(new Paragraph("Salida", fuenteNegra7));
                tabla2.addCell(cell);

            }

            //Consultar la tabla empleados
            strConsultaSQL = "select * from productos where  categoria='" + cat + "'";
            //ejecutar consulta

            rs = estSQL1.executeQuery(strConsultaSQL);

            while (rs.next()) {
                cell = new PdfPCell(new Paragraph(rs.getString("Nombre") + " (" + rs.getString("unidad") + ")", fuente9));
                tabla2.addCell(cell);

                for (int i = 9; i <= 16; i++) {
                    String strConsultaSQL2 = "";
                    String fechacons=anio+"-"+(numeroMes+1)+"-"+i;
                    if (cat.equals("Tacos")) {
                        strConsultaSQL2 = "select * from invtacos where idProd=" + rs.getInt("id") + " and fecha='" + fechacons + "'";
                    } else if(cat.equals("Sodas")){
                        strConsultaSQL2 = "select * from invSodas where idProd=" + rs.getInt("id") + " and fecha='" + fechacons + "'";
                    }

                    rs2 = estSQL2.executeQuery(strConsultaSQL2);
                    int p = 0;
                    while (rs2.next()) {
                        cell = new PdfPCell(new Paragraph("" + rs2.getFloat("entrada"), fuente9));
                        tabla2.addCell(cell);
                        cell = new PdfPCell(new Paragraph("" + rs2.getFloat("salida"), fuente9));
                        tabla2.addCell(cell);
                        p++;
                    }
                    if (p == 0) {
                        cell = new PdfPCell(new Paragraph("0", fuente9));
                        tabla2.addCell(cell);
                        cell = new PdfPCell(new Paragraph("0", fuente9));
                        tabla2.addCell(cell);
                    }
                }
            }
            parrafo.add(tabla2);

        } catch (Exception e) {
            System.out.println("Excepcion al ejecutar CONSULTA!!!");
            //Mostrar la traza de la pila
            e.printStackTrace();
        }

    }  //Fin tabla 2
    
    private void agregarTabla3(Paragraph parrafo) throws BadElementException {
        //Anchos de las columnas son 13 columnas
        //ventas
        //id, id_Empleado, id_platillo,cantidad, monto_venta, propina, Fecha_hora
        float anchosFilas[] = {1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f};
        PdfPTable tabla = new PdfPTable(anchosFilas);
        String rotulosColumnas[] = {"  ",
            "17/" + (numeroMes+1) + "/" + anio,
            "18/" + (numeroMes+1) + "/" + anio,
            "19/" + (numeroMes+1) + "/" + anio,
            "20/" + (numeroMes+1) + "/" + anio,
            "21/" + (numeroMes+1) + "/" + anio,
            "22/" + (numeroMes+1) + "/" + anio,
            "23/" + (numeroMes+1) + "/" + anio,
            "24/" + (numeroMes+1) + "/" + anio};

        // Porcentaje que ocupa a lo ancho de la pagina del PDF
        tabla.setWidthPercentage(100);
        //Alineacion horizontal centrada
        tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
        //agregar celda que ocupa las 9 columnas de los rotulos
        PdfPCell cell = new PdfPCell(new Paragraph(strRotuloPDF));
        cell.setColspan(15);
        //Centrar contenido de celda
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        //Color de fondo de la celda
        cell.setBackgroundColor(azulClaro);
        tabla.addCell(cell);

        try {

            // Mostrar los rotulos de las columnas
            for (int i = 0; i < rotulosColumnas.length; i++) {
                cell = new PdfPCell(new Paragraph(rotulosColumnas[i], fuenteNegra10));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(grisClaro);
                tabla.addCell(cell);
            }

            parrafo.add(tabla);

            float anchosFilas2[] = {1f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f};
            PdfPTable tabla2 = new PdfPTable(anchosFilas2);
            tabla2.setWidthPercentage(100);
            tabla2.setHorizontalAlignment(Element.ALIGN_CENTER);
        
            cell = new PdfPCell(new Paragraph("Producto", fuente9));
            tabla2.addCell(cell);
            for (int i = 0; i < 8; i++) {
                cell = new PdfPCell(new Paragraph("Entrada", fuenteNegra7));
                tabla2.addCell(cell);
                cell = new PdfPCell(new Paragraph("Salida", fuenteNegra7));
                tabla2.addCell(cell);

            }

            //Consultar la tabla empleados
            strConsultaSQL = "select * from productos where  categoria='" + cat + "'";
            //ejecutar consulta

            rs = estSQL1.executeQuery(strConsultaSQL);

            while (rs.next()) {
                cell = new PdfPCell(new Paragraph(rs.getString("Nombre") + " (" + rs.getString("unidad") + ")", fuente9));
                tabla2.addCell(cell);

                for (int i = 17; i <= 24; i++) {
                    String strConsultaSQL2 = "";
                    String fechacons=anio+"-"+(numeroMes+1)+"-"+i;
                    if (cat.equals("Tacos")) {
                        strConsultaSQL2 = "select * from invtacos where idProd=" + rs.getInt("id") + " and fecha='" + fechacons + "'";
                    } else if(cat.equals("Sodas")){
                        strConsultaSQL2 = "select * from invSodas where idProd=" + rs.getInt("id") + " and fecha='" + fechacons + "'";
                    }

                    rs2 = estSQL2.executeQuery(strConsultaSQL2);
                    int p = 0;
                    while (rs2.next()) {
                        cell = new PdfPCell(new Paragraph("" + rs2.getFloat("entrada"), fuente9));
                        tabla2.addCell(cell);
                        cell = new PdfPCell(new Paragraph("" + rs2.getFloat("salida"), fuente9));
                        tabla2.addCell(cell);
                        p++;
                    }
                    if (p == 0) {
                        cell = new PdfPCell(new Paragraph("0", fuente9));
                        tabla2.addCell(cell);
                        cell = new PdfPCell(new Paragraph("0", fuente9));
                        tabla2.addCell(cell);
                    }
                }
            }
            parrafo.add(tabla2);

        } catch (Exception e) {
            System.out.println("Excepcion al ejecutar CONSULTA!!!");
            //Mostrar la traza de la pila
            e.printStackTrace();
        }

    }  //Fin tabla 3
    
    
    private void agregarTabla4(Paragraph parrafo) throws BadElementException {
        //Anchos de las columnas son 13 columnas
        //ventas
        //id, id_Empleado, id_platillo,cantidad, monto_venta, propina, Fecha_hora
        float anchosFilas[] = {1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f};
        PdfPTable tabla = new PdfPTable(anchosFilas);
        String rotulosColumnas[] = null;
        String rotulosColumnas31[] = {"  ",
            "25/" + (numeroMes+1) + "/" + anio,
            "26/" + (numeroMes+1) + "/" + anio,
            "27/" + (numeroMes+1) + "/" + anio,
            "28/" + (numeroMes+1) + "/" + anio,
            "29/" + (numeroMes+1) + "/" + anio,
            "30/" + (numeroMes+1) + "/" + anio,
            "31/" + (numeroMes+1) + "/" + anio,
            " "};
        String rotulosColumnas30[] = {"  ",
            "25/" + (numeroMes+1) + "/" + anio,
            "26/" + (numeroMes+1) + "/" + anio,
            "27/" + (numeroMes+1) + "/" + anio,
            "28/" + (numeroMes+1) + "/" + anio,
            "29/" + (numeroMes+1) + "/" + anio,
            "30/" + (numeroMes+1) + "/" + anio,
            " ",
            " "};
        String rotulosColumnas29[] = {"  ",
            "25/" + (numeroMes+1) + "/" + anio,
            "26/" + (numeroMes+1) + "/" + anio,
            "27/" + (numeroMes+1) + "/" + anio,
            "28/" + (numeroMes+1) + "/" + anio,
            "29/" + (numeroMes+1) + "/" + anio,
            " ",
            " ",
            " "};
        String rotulosColumnas28[] = {"  ",
            "25/" + (numeroMes+1) + "/" + anio,
            "26/" + (numeroMes+1) + "/" + anio,
            "27/" + (numeroMes+1) + "/" + anio,
            "28/" + (numeroMes+1) + "/" + anio,
            " ",
            " ",
            " ",
            " "};
        
        switch(ultimoDia){
            case 28:
                rotulosColumnas=rotulosColumnas28;
                break;
            case 29:
                rotulosColumnas=rotulosColumnas29;
                break;
            case 30:
                rotulosColumnas=rotulosColumnas30;
                break;
            case 31:
                rotulosColumnas=rotulosColumnas31;
                break;
        };


        // Porcentaje que ocupa a lo ancho de la pagina del PDF
        tabla.setWidthPercentage(100);
        //Alineacion horizontal centrada
        tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
        //agregar celda que ocupa las 9 columnas de los rotulos
        PdfPCell cell = new PdfPCell(new Paragraph(strRotuloPDF));
        cell.setColspan(15);
        //Centrar contenido de celda
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        //Color de fondo de la celda
        cell.setBackgroundColor(azulClaro);
        tabla.addCell(cell);

        try {

            // Mostrar los rotulos de las columnas
            for (int i = 0; i < rotulosColumnas.length; i++) {
                cell = new PdfPCell(new Paragraph(rotulosColumnas[i], fuenteNegra10));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(grisClaro);
                tabla.addCell(cell);
            }

            parrafo.add(tabla);

            float anchosFilas2[] = {1f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f, .5f};
            PdfPTable tabla2 = new PdfPTable(anchosFilas2);
            tabla2.setWidthPercentage(100);
            tabla2.setHorizontalAlignment(Element.ALIGN_CENTER);
        
            cell = new PdfPCell(new Paragraph("Producto", fuente9));
            tabla2.addCell(cell);
            for (int i = 0; i < 8; i++) {
                cell = new PdfPCell(new Paragraph("Entrada", fuenteNegra7));
                tabla2.addCell(cell);
                cell = new PdfPCell(new Paragraph("Salida", fuenteNegra7));
                tabla2.addCell(cell);

            }

            //Consultar la tabla empleados
            strConsultaSQL = "select * from productos where  categoria='" + cat + "'";
            //ejecutar consulta

            rs = estSQL1.executeQuery(strConsultaSQL);

            while (rs.next()) {
                cell = new PdfPCell(new Paragraph(rs.getString("Nombre") + " (" + rs.getString("unidad") + ")", fuente9));
                tabla2.addCell(cell);

                for (int i = 25; i <= ultimoDia; i++) {
                    String strConsultaSQL2 = "";
                    String fechacons=anio+"-"+(numeroMes+1)+"-"+i;
                    if (cat.equals("Tacos")) {
                        strConsultaSQL2 = "select * from invtacos where idProd=" + rs.getInt("id") + " and fecha='" + fechacons + "'";
                    } else if(cat.equals("Sodas")){
                        strConsultaSQL2 = "select * from invSodas where idProd=" + rs.getInt("id") + " and fecha='" + fechacons + "'";
                    }

                    rs2 = estSQL2.executeQuery(strConsultaSQL2);
                    int p = 0;
                    while (rs2.next()) {
                        cell = new PdfPCell(new Paragraph("" + rs2.getFloat("entrada"), fuente9));
                        tabla2.addCell(cell);
                        cell = new PdfPCell(new Paragraph("" + rs2.getFloat("salida"), fuente9));
                        tabla2.addCell(cell);
                        p++;
                    }
                    if (p == 0) {
                        cell = new PdfPCell(new Paragraph("0", fuente9));
                        tabla2.addCell(cell);
                        cell = new PdfPCell(new Paragraph("0", fuente9));
                        tabla2.addCell(cell);
                    }
                }
                for (int i = 0; i < 32-ultimoDia; i++) {
                    cell = new PdfPCell(new Paragraph(" ", fuente9));
                    tabla2.addCell(cell);
                    cell = new PdfPCell(new Paragraph(" ", fuente9));
                    tabla2.addCell(cell);
                }
            }
            parrafo.add(tabla2);

        } catch (Exception e) {
            System.out.println("Excepcion al ejecutar CONSULTA!!!");
            //Mostrar la traza de la pila
            e.printStackTrace();
        }

    }  //Fin tabla 4

    private static void agregarLineasEnBlanco(Paragraph parrafo, int nLineas) {
        for (int i = 0; i < nLineas; i++) {
            parrafo.add(new Paragraph(" "));
        }
    }

    //Agrega los metadatos del documento, los metadatos a asignar son
    //Titulo, Asunto, Palabras clave, Autor y el sw o org con el cual fue generado
    private static void agregarMetaDatos(Document document) {
        document.addTitle("Asistencia");
        document.addSubject("Usando iText y MySQL");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Gonzalez Tesillo Daniel");
        document.addCreator("UAEM");
    }

    //devuelve true en caso de que si se pudo conectar
    //devuelve false sino se logro conectar
    //Abre el documento PDF
    public void abrirPDF() {
        /* Abrir el archivo PDF */
        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + strNombreDelPDF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getNMes(String mes) {
        int nmes = 0;
        switch (mes) {
            case "Enero":
                nmes = 0;
                break;
            case "Febrero":
                nmes = 1;
                break;
            case "Marzo":
                nmes = 2;
                break;
            case "Abril":
                nmes = 3;
                break;
            case "Mayo":
                nmes = 4;
                break;
            case "Junio":
                nmes = 5;
                break;
            case "Julio":
                nmes = 6;
                break;
            case "Agosto":
                nmes = 7;
                break;
            case "Septiembre":
                nmes = 8;
                break;
            case "Octubre":
                nmes = 9;
                break;
            case "Noviembre":
                nmes = 10;
                break;
            case "Diciembre":
                nmes = 11;
                break;

        };
        numeroMes = nmes;
        return nmes;
    }

    public static String getPrimerDiaDelMes(int mes, int anio) {
        return anio + "-" + (mes + 1) + "-" + 1;
    }

    public static String getUltimoDiaDelMes(int mes, int anio) {
        Calendar cal = Calendar.getInstance();
        cal.set(anio, mes, 1, cal.getMaximum(Calendar.HOUR_OF_DAY), cal.getMaximum(Calendar.MINUTE), cal.getMaximum(Calendar.SECOND));
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMaximum(Calendar.DAY_OF_MONTH), cal.getMaximum(Calendar.HOUR_OF_DAY), cal.getMaximum(Calendar.MINUTE), cal.getMaximum(Calendar.SECOND));
        ultimoDia = cal.get(Calendar.DAY_OF_MONTH);
        return anio + "-" + (mes + 1) + "-" + (cal.get(Calendar.DAY_OF_MONTH));
    }

}
