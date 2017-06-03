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
public class inventarioMueblesPDF {

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
    public inventarioMueblesPDF(String titulo, String nomPDF) throws SQLException {
        
        this.estSQL1 = conn.createStatement();
        strRotuloPDF = titulo;
        strNombreDelPDF = System.getProperty("user.home") + "/" + nomPDF;
        try {       //Hoja tamanio carta, rotarla (cambiar a horizontal)
            document = new Document(PageSize.LETTER);

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
        agregarLineasEnBlanco(ParrafoHoja, 2);
        
        
        agregarLineasEnBlanco(ParrafoHoja, 2);

        document.add(ParrafoHoja);

    }

    //Se conecta al DBMS MySQL , obtiene los datos de la tabla (SELECT) y los acomoda en una tabla JTable de iText.
    // Espera como entrada el parrafo donde agregara la tabla
    private void agregarTabla(Paragraph parrafo) throws BadElementException {
        //Anchos de las columnas son 13 columnas
        //ventas
        //id, id_Empleado, id_platillo,cantidad, monto_venta, propina, Fecha_hora
        float anchosFilas[] = {.5f, 1.2f, 2f, 1f, 1f};
        PdfPTable tabla = new PdfPTable(anchosFilas);
        String rotulosColumnas[] = {"ID", "Nombre", "Descripcion", "Cantidad", "Costo $"};

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

            
            //Consultar la tabla empleados
            strConsultaSQL = "select * from moviliario";
            //ejecutar consulta

            rs = estSQL1.executeQuery(strConsultaSQL);

            while (rs.next()) {
                cell = new PdfPCell(new Paragraph(""+rs.getInt("id"), fuente9));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(rs.getString("nombre"), fuente9));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(rs.getString("descripcion"), fuente9));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(""+rs.getInt("cantidad"), fuente9));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(""+rs.getFloat("costo"), fuente9));
                tabla.addCell(cell);
            }
            parrafo.add(tabla);

        } catch (Exception e) {
            System.out.println("Excepcion al ejecutar CONSULTA!!!");
            //Mostrar la traza de la pila
            e.printStackTrace();
        }

    }  //Fin tabla
    
   
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
