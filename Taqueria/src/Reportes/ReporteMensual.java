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
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author el dios
 */
public class ReporteMensual {

    private String strNombreDelPDF;

    private Font fuenteNegra11 = new Font(Font.TIMES_ROMAN, 11, Font.BOLD, Color.BLACK);
    private Font fuente10 = new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.BLACK);
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
    public String fech, fech2, mes;
    public static int anio, ultimoDia, diaActual, numeroMes = 0;
    public int totalCliente;
    public float totalVentas;
    public float totalCompras;


    //Metodo principal del ejemplo

    public ReporteMensual(String titulo, String nomPDF, String mes, int anio) throws SQLException {
        this.mes = mes;
        this.anio = anio;
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
            document.newPage();
            agregarGrafica(document,"graficoNumero de Clientes.png");
            document.newPage();
            agregarGrafica(document,"graficoTotales de Venta.png");
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
        
        
        document.add(ParrafoHoja);
    }

    //Se conecta al DBMS MySQL , obtiene los datos de la tabla (SELECT) y los acomoda en una tabla JTable de iText.
    // Espera como entrada el parrafo donde agregara la tabla
    private void agregarTabla(Paragraph parrafo) throws BadElementException {

        ArrayList<objToGrafica> objetos = new ArrayList<>();
        ArrayList<objToGrafica> objetos2 = new ArrayList<>();
        

        float anchosFilas[] = {.8f, 2f, 1f, 1f};
        PdfPTable tabla = new PdfPTable(anchosFilas);
        String rotulosColumnas[] = {"NP", "Fecha", "Numero de Clientes", "Total$"};

        // Porcentaje que ocupa a lo ancho de la pagina del PDF
        tabla.setWidthPercentage(100);
        //Alineacion horizontal centrada
        tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
        //agregar celda que ocupa las 9 columnas de los rotulos
        PdfPCell cell = new PdfPCell(new Paragraph("Ventas"));
        cell.setColspan(15);
        //Centrar contenido de celda
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        //Color de fondo de la celda
        cell.setBackgroundColor(azulClaro);
        tabla.addCell(cell);

        try {

            // Mostrar los rotulos de las columnas
            for (int i = 0; i < rotulosColumnas.length; i++) {
                cell = new PdfPCell(new Paragraph(rotulosColumnas[i], fuenteNegra11));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(grisClaro);
                tabla.addCell(cell);
            }

            for (int i = 1; i <= ultimoDia; i++) {
                String strConsultaSQL2 = "";
                String fechacons = anio + "-" + (numeroMes + 1) + "-" + i;
                strConsultaSQL2 = "select sum(total) as totalidad, count(id) as contador from venta where fecha='" + fechacons + "'";

                rs2 = estSQL2.executeQuery(strConsultaSQL2);
                while (rs2.next()) {
                    tabla.addCell(getNuevaCelda(new Paragraph("" + i, fuente10)));
                    tabla.addCell(getNuevaCelda(new Paragraph("" + fechacons, fuente10)));
                    tabla.addCell(getNuevaCelda(new Paragraph("" + rs2.getInt("contador"), fuente10)));
                    tabla.addCell(getNuevaCelda(new Paragraph("$ " + String.format("%.2f", rs2.getFloat("totalidad")), fuente10)));
                    totalVentas += rs2.getFloat("totalidad");
                    totalCliente += rs2.getInt("contador");
                    objetos.add(new objToGrafica(rs2.getInt("contador"), fechacons));
                    objetos2.add(new objToGrafica((int)rs2.getFloat("totalidad"), fechacons));
                }
            }
            generaGrafica(objetos, "Numero de Clientes");
            generaGrafica(objetos2, "Totales de Venta");

            tabla.addCell(getNuevaCelda(new Paragraph("Total", fuenteNegra11)));
            tabla.addCell(getNuevaCelda(new Paragraph("", fuente10)));
            tabla.addCell(getNuevaCelda(new Paragraph("" + totalCliente, fuenteNegra11)));
            tabla.addCell(getNuevaCelda(new Paragraph("$ " + String.format("%.2f",totalVentas), fuenteNegra11)));

        } catch (Exception e) {
            System.out.println("Excepcion al ejecutar CONSULTA!!!");
            //Mostrar la traza de la pila
            e.printStackTrace();
        }
        //Agregar la tabla con los datos al parrafo que nos llego como entrada
        parrafo.add(tabla);

    }  //Fin tabla
    
    private void agregarTabla2(Paragraph parrafo) throws BadElementException {

        ArrayList<objToGrafica> objetos = new ArrayList<>();
        
        ImageIcon imag = null;
        Blob blob = null;
        BufferedImage img = null;
        float anchosFilas[] = {.8f, 2f, 1f};
        PdfPTable tabla = new PdfPTable(anchosFilas);
        String rotulosColumnas[] = {"NP", "Fecha", "Total de compra$"};

        // Porcentaje que ocupa a lo ancho de la pagina del PDF
        tabla.setWidthPercentage(100);
        //Alineacion horizontal centrada
        tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
        //agregar celda que ocupa las 9 columnas de los rotulos
        PdfPCell cell = new PdfPCell(new Paragraph("Compras"));
        cell.setColspan(15);
        //Centrar contenido de celda
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        //Color de fondo de la celda
        cell.setBackgroundColor(azulClaro);
        tabla.addCell(cell);

        try {

            // Mostrar los rotulos de las columnas
            for (int i = 0; i < rotulosColumnas.length; i++) {
                cell = new PdfPCell(new Paragraph(rotulosColumnas[i], fuenteNegra11));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(grisClaro);
                tabla.addCell(cell);
            }

            for (int i = 1; i <= ultimoDia; i++) {
                String strConsultaSQL2 = "";
                String fechacons = anio + "-" + (numeroMes + 1) + "-" + i;
                strConsultaSQL2 = "select sum(Total) as totalidad from ingreso where fecha='" + fechacons + "'";

                rs2 = estSQL2.executeQuery(strConsultaSQL2);
                while (rs2.next()) {
                    tabla.addCell(getNuevaCelda(new Paragraph("" + i, fuente10)));
                    tabla.addCell(getNuevaCelda(new Paragraph("" + fechacons, fuente10)));
                    tabla.addCell(getNuevaCelda(new Paragraph("$ " + String.format("%.2f", rs2.getFloat("totalidad")), fuente10)));
                    totalCompras += rs2.getFloat("totalidad");
                    objetos.add(new objToGrafica(rs2.getInt("totalidad"), fechacons));
                    
                }
            }

            generaGrafica(objetos, "Totales de Compra");
            
            tabla.addCell(getNuevaCelda(new Paragraph("Total", fuenteNegra11)));
            tabla.addCell(getNuevaCelda(new Paragraph("", fuente10)));
            tabla.addCell(getNuevaCelda(new Paragraph("$ " + String.format("%.2f",totalCompras), fuenteNegra11)));

        } catch (Exception e) {
            System.out.println("Excepcion al ejecutar CONSULTA!!!");
            //Mostrar la traza de la pila
            e.printStackTrace();
        }
        //Agregar la tabla con los datos al parrafo que nos llego como entrada
        parrafo.add(tabla);

    }  //Fin tabla

    public PdfPCell getNuevaCelda(Paragraph texto) {
        PdfPCell a = new PdfPCell(texto);
        a.setVerticalAlignment(Element.ALIGN_MIDDLE);
        a.setHorizontalAlignment(Element.ALIGN_CENTER);
        return a;
    }

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
    
    
    public void generaGrafica(ArrayList<objToGrafica> objeto, String cat) {
        try {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (int i = 0; i < objeto.size(); i++) {
                dataset.setValue(objeto.get(i).getCant(), objeto.get(i).getNombre(), cat);
            }
            JFreeChart chart = ChartFactory.createBarChart3D(cat, "Dia", "Cantidad",
                    dataset, PlotOrientation.VERTICAL, true, true, false);
            
            
            chart.setBackgroundPaint(Color.white);
            chart.getTitle().setPaint(Color.black);
            CategoryPlot p = chart.getCategoryPlot();
            p.setRangeGridlinePaint(Color.BLUE);
            ChartUtilities.saveChartAsPNG(new File("grafico" + cat + ".png"), chart, 500, 500);
        } catch (IOException ex) {
            Logger.getLogger(ReporteMensual.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    private void agregarGrafica(Document document, String nombre) {
        Image image = null;
        try {
            image = Image.getInstance(nombre);
        
        image.scaleToFit(500, 700);
        //image.setAbsolutePosition(320, 370);
        image.setAlignment(Element.ALIGN_CENTER);
        document.add(image);
        } catch (BadElementException ex) {
            Logger.getLogger(PDFVentasDia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PDFVentasDia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(PDFVentasDia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}

class objToGrafica {

        private int cant;
        private String nombre;

        public objToGrafica(int n, String s) {
            cant = n;
            nombre = s;
        }

        public int getCant() {
            return cant;
        }

        public void setCant(int cant) {
            this.cant = cant;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

    }

