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
import static com.lowagie.text.pdf.BidiOrder.R;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import taqueria.reporteDiario;

/**
 *
 * @author el dios
 */
public class PDFVentasDia {

    private String strNombreDelPDF;

    private Font fuenteNegra10 = new Font(Font.TIMES_ROMAN, 10, Font.BOLD, Color.BLACK);
    private Font fuente8 = new Font(Font.TIMES_ROMAN, 9, Font.NORMAL, Color.BLACK);
    private Font fuenteAzul25 = new Font(Font.TIMES_ROMAN, 25, Font.BOLD, Color.BLUE);

    Color grisClaro = new Color(230, 230, 230);
    Color azulClaro = new Color(124, 195, 255);

    //############# VARIABLES PARA MANEJO DE BASE DE DATOS ########################
    //Guarda la consulta operacion a realizar
    ConexionMySQL mysql = new ConexionMySQL();

    String strConsultaSQL, strConsultaSQL2;
    //Apuntador a la conexion
    Connection conn = mysql.Conectar();
    //Para ejecutar operaciones SQL
    Statement estSQL1, estSQL2, estSQL3;
    //Para guardar los resultados de una operacion SELECT
    ResultSet rs, rs2, rs3;
    Document document;
    PdfWriter writer;
    String strRotuloPDF;
    public String fech;
    public String fech2;
    public float monto = 0, gastos=0, total=0;

    //Metodo principal del ejemplo
    public PDFVentasDia(String titulo, String nomPDF, String Fecha) throws SQLException {
        fech = Fecha;

        this.estSQL1 = conn.createStatement();
        this.estSQL2 = conn.createStatement();
        this.estSQL3 = conn.createStatement();
        strRotuloPDF = titulo;
        strNombreDelPDF = System.getProperty("user.home") + "/" + nomPDF;
        try {       //Hoja tamanio carta, rotarla (cambiar a horizontal)
            document = new Document(PageSize.LETTER, 15, 15, 5, 5);

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

/*            document.newPage();
            agregarGrafica(document, "graficoTacos.png");
            document.newPage();
            agregarGrafica(document, "graficoFuente de Sodas.png");
            document.newPage();
            agregarGrafica(document, "graficoPaquetes.png");
            */

            document.close();

            System.out.println("Se ha generado el PDF: " + strNombreDelPDF);
            abrirPDF();
        } catch (java.io.FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "El archivo \"" + strNombreDelPDF + "\" Se encuentra en uso\nCierre el archivo y vuelva a intentar.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //agrega el contenido del documento; para este ejemplo agrega una tabla con datos y una imagen
    //Espera como entrada el documento donde agregara el contenido
    private void agregarContenido(Document document) throws DocumentException {
        Paragraph ParrafoHoja = new Paragraph();

        // Agregamos una linea en blanco al principio del documento
        agregarLineasEnBlanco(ParrafoHoja, 1);
        // Colocar un encabezado (en mayusculas)
        ParrafoHoja.add(new Paragraph(strRotuloPDF.toUpperCase(), fuenteAzul25));

        agregarTabla(ParrafoHoja);
        agregarLineasEnBlanco(ParrafoHoja, 2);
        agregarTabla2(ParrafoHoja);
        agregarLineasEnBlanco(ParrafoHoja, 2);
        ParrafoHoja.add(new Paragraph("Total de Ventas: $" + String.format("%.2f", monto), fuenteNegra10));
        ParrafoHoja.add(new Paragraph("Total de Gastos: $" + String.format("%.2f", gastos), fuenteNegra10));
        total=monto-gastos;
        ParrafoHoja.add(new Paragraph("Total: $" + String.format("%.2f", total), fuenteNegra10));
        agregarLineasEnBlanco(ParrafoHoja, 1);

        agregarTabla3(ParrafoHoja);
        agregarTabla4(ParrafoHoja);
        agregarTabla5(ParrafoHoja);
        agregarTablaMermas(ParrafoHoja);

        document.add(ParrafoHoja);

    }

    //Se conecta al DBMS MySQL , obtiene los datos de la tabla (SELECT) y los acomoda en una tabla JTable de iText.
    // Espera como entrada el parrafo donde agregara la tabla
    private void agregarTabla(Paragraph parrafo) throws BadElementException {
        //Anchos de las columnas son 13 columnas
        //ventas
        //id, id_Empleado, id_platillo,cantidad, monto_venta, propina, Fecha_hora
        String cons = "";
        float anchosFilas[] = {.5f, 1f, .8f, .8f, 1.8f, 1f};
        PdfPTable tabla = new PdfPTable(anchosFilas);
        String rotulosColumnas[] = {"No. Cliente", "Mesa", "H. Llegada", "H. Salida", "Consumo", "$ Total"};

        // Porcentaje que ocupa a lo ancho de la pagina del PDF
        tabla.setWidthPercentage(100);
        //Alineacion horizontal centrada
        tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
        //agregar celda que ocupa las 9 columnas de los rotulos
        PdfPCell cell = new PdfPCell(new Paragraph("Ventas " + fech));
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
            strConsultaSQL = "select * from venta where fecha ='" + fech + "'";
            //ejecutar consulta

            rs = estSQL1.executeQuery(strConsultaSQL);

            //Iterar Mientras haya una fila siguiente
            //empleados
            //Apellido_Pat, Apellido_Mat, Nombre, calle, colonia, Municipio, C_P, telefono, Email, Cargo, sueldo
            //ventas
            //id, id_Empleado, id_platillo,cantidad, monto_venta, propina, Fecha_hora
            while (rs.next()) {           //Agregar 9 celdas

                cell = new PdfPCell(new Paragraph(rs.getString("id"), fuente8));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(rs.getString("nMesa"), fuente8));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(rs.getString("hEntrada"), fuente8));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(rs.getString("hSalida"), fuente8));
                tabla.addCell(cell);

                String strConsultaSQL2 = "select * from detalleVenta where idventa=" + rs.getInt("id");
                rs2 = estSQL2.executeQuery(strConsultaSQL2);
                cons = "";
                while (rs2.next()) {

                    cons = cons + rs2.getString("cantidad") + "  " + getPlatoNombre(rs2.getInt("idMenu")) + "\n";

                }
                cell = new PdfPCell(new Paragraph(cons, fuente8));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph("$ " + rs.getFloat("total"), fuente8));
                tabla.addCell(cell);

                monto = monto + (rs.getFloat("Total"));
            }

        } catch (Exception e) {
            System.out.println("Excepcion al ejecutar CONSULTA!!!");
            //Mostrar la traza de la pila
            e.printStackTrace();
        }
        //Agregar la tabla con los datos al parrafo que nos llego como entrada
        parrafo.add(tabla);
    }  //Fin del metodo que crea la tabla

    //Agrega las lineas en blanco  especificadas a un parrafo especificado
    ////////////////////////
    /////////////consu
    private void agregarTabla2(Paragraph parrafo) throws BadElementException {
        //Anchos de las columnas son 13 columnas
        //ventas
        //id, id_Empleado, id_platillo,cantidad, monto_venta, propina, Fecha_hora

        float anchosFilas[] = {.5f, 1f, 2f, 1f, 1F, 1F};
        PdfPTable tabla = new PdfPTable(anchosFilas);
        String rotulosColumnas[] = {"ID", "Concepto", "Detalles", "Fecha", "Hora", "$ Costo"};

        // Porcentaje que ocupa a lo ancho de la pagina del PDF
        tabla.setWidthPercentage(100);
        //Alineacion horizontal centrada
        tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
        //agregar celda que ocupa las 9 columnas de los rotulos
        PdfPCell cell = new PdfPCell(new Paragraph("Gastos"));
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
            strConsultaSQL = "select * from gastos where fecha ='" + fech + "'";
            //ejecutar consulta

            rs = estSQL1.executeQuery(strConsultaSQL);

            while (rs.next()) {           //Agregar 9 celdas

                cell = new PdfPCell(new Paragraph(rs.getString("id"), fuente8));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(rs.getString("concepto"), fuente8));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(rs.getString("detalles"), fuente8));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(rs.getString("fecha"), fuente8));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(rs.getString("hora"), fuente8));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph("$ " + rs.getFloat("costo"), fuente8));
                tabla.addCell(cell);
                gastos = gastos + (rs.getFloat("costo"));
            }

            //Cerrar los objetos de manejo de BD
            //Connection
            //Fin de if que comprueba si se pudo conectar
        } catch (Exception e) {
            System.out.println("Excepcion al ejecutar CONSULTA!!!");
            //Mostrar la traza de la pila
            e.printStackTrace();
        }
        //Agregar la tabla con los datos al parrafo que nos llego como entrada
        parrafo.add(tabla);
    }

    private void agregarTabla3(Paragraph parrafo) throws BadElementException {

        //ArrayList<objToGrafica> objetos = new ArrayList<>();

        float anchosFilas[] = {.5f, 1f, .8f};
        PdfPTable tabla = new PdfPTable(anchosFilas);
        String rotulosColumnas[] = {"ID", "Nombre", "Cantidad"};

        tabla.setWidthPercentage(50);
        tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell cell = new PdfPCell(new Paragraph("Conteo de Tacos"));
        cell.setColspan(15);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
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
            strConsultaSQL = "select * from menu where categoria='TACOS' and status ='SI'";
            //ejecutar consulta

            rs = estSQL1.executeQuery(strConsultaSQL);

            int tacosN = 0;
            while (rs.next()) {           //Agregar 9 celdas

                cell = new PdfPCell(new Paragraph("" + rs.getInt("id"), fuente8));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(rs.getString("nombre"), fuente8));
                tabla.addCell(cell);
                strConsultaSQL2 = "select sum(cantidad) as totalidad from detalleventa where idMenu=" + rs.getInt("id") + " and fecha='" + fech + "' and status!='MERMADO'";
                rs2 = estSQL2.executeQuery(strConsultaSQL2);
                while (rs2.next()) {
                    cell = new PdfPCell(new Paragraph("" + rs2.getInt("totalidad"), fuente8));
                    tabla.addCell(cell);
                    tacosN += rs2.getInt("totalidad");
                    //objetos.add(new objToGrafica(rs2.getInt("totalidad"), rs.getString("nombre")));
                }

            }
            cell = new PdfPCell(new Paragraph("Total", fuenteNegra10));
            tabla.addCell(cell);

            cell = new PdfPCell(new Paragraph("", fuente8));
            tabla.addCell(cell);

            cell = new PdfPCell(new Paragraph("" + tacosN, fuenteNegra10));
            tabla.addCell(cell);

            //generaGrafica(objetos, "Tacos");
            //Cerrar los objetos de manejo de BD
            //Connection
            //Fin de if que comprueba si se pudo conectar

        } catch (Exception e) {
            System.out.println("Excepcion al ejecutar CONSULTA!!!");
            //Mostrar la traza de la pila
            e.printStackTrace();
        }
        //Agregar la tabla con los datos al parrafo que nos llego como entrada
        parrafo.add(tabla);
    }

    private void agregarTabla4(Paragraph parrafo) throws BadElementException {
        //ArrayList<objToGrafica> objetos = new ArrayList<>();

        float anchosFilas[] = {.5f, 1f, .8f};
        PdfPTable tabla = new PdfPTable(anchosFilas);
        String rotulosColumnas[] = {"ID", "Nombre", "Cantidad"};

        // Porcentaje que ocupa a lo ancho de la pagina del PDF
        tabla.setWidthPercentage(50);
        //Alineacion horizontal centrada
        tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
        //agregar celda que ocupa las 9 columnas de los rotulos
        PdfPCell cell = new PdfPCell(new Paragraph("Conteo de Sodas"));
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
            strConsultaSQL = "select * from menu where categoria='FUENTE DE SODAS' and status ='SI'";
            //ejecutar consulta

            rs = estSQL1.executeQuery(strConsultaSQL);

            int tacosN = 0;
            while (rs.next()) {           //Agregar 9 celdas
                cell = new PdfPCell(new Paragraph("" + rs.getInt("id"), fuente8));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(rs.getString("nombre"), fuente8));
                tabla.addCell(cell);
                strConsultaSQL2 = "select sum(cantidad) as totalidad from detalleventa where idMenu=" + rs.getInt("id") + " and fecha='" + fech + "' and status!='MERMADO'";
                rs2 = estSQL2.executeQuery(strConsultaSQL2);
                while (rs2.next()) {
                    cell = new PdfPCell(new Paragraph("" + rs2.getInt("totalidad"), fuente8));
                    tabla.addCell(cell);
                    tacosN += rs2.getInt("totalidad");
          //          objetos.add(new objToGrafica(rs2.getInt("totalidad"), rs.getString("nombre")));
                }
            }
            cell = new PdfPCell(new Paragraph("Total", fuenteNegra10));
            tabla.addCell(cell);

            cell = new PdfPCell(new Paragraph("", fuente8));
            tabla.addCell(cell);

            cell = new PdfPCell(new Paragraph("" + tacosN, fuenteNegra10));
            tabla.addCell(cell);

            //generaGrafica(objetos, "Fuente de Sodas");
            //Cerrar los objetos de manejo de BD
            //Connection
            //Fin de if que comprueba si se pudo conectar
        } catch (Exception e) {
            System.out.println("Excepcion al ejecutar CONSULTA!!!");
            //Mostrar la traza de la pila
            e.printStackTrace();
        }
        //Agregar la tabla con los datos al parrafo que nos llego como entrada
        parrafo.add(tabla);
    }

    private void agregarTabla5(Paragraph parrafo) throws BadElementException {
        //ArrayList<objToGrafica> objetos = new ArrayList<>();

        float anchosFilas[] = {.5f, 1f, .8f};
        PdfPTable tabla = new PdfPTable(anchosFilas);
        String rotulosColumnas[] = {"ID", "Nombre", "Cantidad"};

        // Porcentaje que ocupa a lo ancho de la pagina del PDF
        tabla.setWidthPercentage(50);
        //Alineacion horizontal centrada
        tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
        //agregar celda que ocupa las 9 columnas de los rotulos
        PdfPCell cell = new PdfPCell(new Paragraph("Conteo de Paquetes"));
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
            strConsultaSQL = "select * from menu where categoria='PAQUETE' and status ='SI'";
            //ejecutar consulta

            rs = estSQL1.executeQuery(strConsultaSQL);

            int tacosN = 0;
            while (rs.next()) {           //Agregar 9 celdas

                cell = new PdfPCell(new Paragraph("" + rs.getInt("id"), fuente8));
                tabla.addCell(cell);
                cell = new PdfPCell(new Paragraph(rs.getString("nombre"), fuente8));
                tabla.addCell(cell);
                strConsultaSQL2 = "select sum(cantidad) as totalidad from detalleventa where idMenu=" + rs.getInt("id") + " and fecha='" + fech + "' and status!='MERMADO'";
                rs2 = estSQL2.executeQuery(strConsultaSQL2);
                while (rs2.next()) {
                    cell = new PdfPCell(new Paragraph("" + rs2.getInt("totalidad"), fuente8));
                    tabla.addCell(cell);
                    tacosN += rs2.getInt("totalidad");
          //          objetos.add(new objToGrafica(rs2.getInt("totalidad"), rs.getString("nombre")));
                }
            }
            cell = new PdfPCell(new Paragraph("Total", fuenteNegra10));
            tabla.addCell(cell);

            cell = new PdfPCell(new Paragraph("", fuente8));
            tabla.addCell(cell);

            cell = new PdfPCell(new Paragraph("" + tacosN, fuenteNegra10));
            tabla.addCell(cell);

            //generaGrafica(objetos, "Paquetes");
            //Cerrar los objetos de manejo de BD
            //Connection
            //Fin de if que comprueba si se pudo conectar
        } catch (Exception e) {
            System.out.println("Excepcion al ejecutar CONSULTA!!!");
            //Mostrar la traza de la pila
            e.printStackTrace();
        }
        //Agregar la tabla con los datos al parrafo que nos llego como entrada
        parrafo.add(tabla);
    }

    private void agregarTablaMermas(Paragraph parrafo) throws BadElementException {
        //ArrayList<objToGrafica> objetos = new ArrayList<>();

        float anchosFilas[] = {.5f, 1f, .8f};
        PdfPTable tabla = new PdfPTable(anchosFilas);
        String rotulosColumnas[] = {"ID", "Nombre", "Cantidad"};

        // Porcentaje que ocupa a lo ancho de la pagina del PDF
        tabla.setWidthPercentage(50);
        //Alineacion horizontal centrada
        tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
        //agregar celda que ocupa las 9 columnas de los rotulos
        PdfPCell cell = new PdfPCell(new Paragraph("Mermas"));
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

            strConsultaSQL2 = "select * from detalleventa where status='MERMADO' and fecha='" + fech + "'";
            rs2 = estSQL2.executeQuery(strConsultaSQL2);
            while (rs2.next()) {
                cell = new PdfPCell(new Paragraph(""+rs2.getInt("idMenu"), fuenteNegra10));
                tabla.addCell(cell);

                cell = new PdfPCell(new Paragraph(getPlatoNombre(rs2.getInt("idMenu")), fuente8));
                tabla.addCell(cell);

                cell = new PdfPCell(new Paragraph("" + rs2.getInt("cantidad"), fuenteNegra10));
                tabla.addCell(cell);
            }

        } catch (Exception e) {
            System.out.println("Excepcion al ejecutar CONSULTA!!!");
            //Mostrar la traza de la pila
            e.printStackTrace();
        }
        //Agregar la tabla con los datos al parrafo que nos llego como entrada
        parrafo.add(tabla);
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

    public static String getPlatoNombre(int n) {
        String nombre = "";
        ConexionMySQL conect = new ConexionMySQL();
        String consulta = "select * from menu where id=" + n;
        ResultSet rs = conect.Consulta(consulta);

        try {
            while (rs.next()) {
                nombre = rs.getString("nombre");
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println("error: " + ex);
//JOptionPane.showMessageDialog(rootPane, "Error al cargar Clientes!!!");
        }

        return nombre;
    }

    public static String getPlatoCategoria(int n) {
        String nombre = "";
        ConexionMySQL conect = new ConexionMySQL();
        String consulta = "select * from menu where id=" + n;
        ResultSet rs = conect.Consulta(consulta);

        try {
            while (rs.next()) {
                nombre = rs.getString("categoria");
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println("error: " + ex);
//JOptionPane.showMessageDialog(rootPane, "Error al cargar Clientes!!!");
        }

        return nombre;
    }

    public void generaGrafica(ArrayList<objToGrafica> objeto, String cat) {
        try {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (int i = 0; i < objeto.size(); i++) {
                dataset.setValue(objeto.get(i).getCant(), objeto.get(i).getNombre(), cat);

            }
            JFreeChart chart = ChartFactory.createBarChart3D("Vendidos", "Producto", "Categoria",
                    dataset, PlotOrientation.VERTICAL, true, true, false);
            chart.setBackgroundPaint(Color.white);
            chart.getTitle().setPaint(Color.black);
            CategoryPlot p = chart.getCategoryPlot();
            p.setRangeGridlinePaint(Color.BLUE);
            ChartUtilities.saveChartAsPNG(new File("grafico" + cat + ".png"), chart, 500, 500);
        } catch (IOException ex) {
            Logger.getLogger(PDFVentasDia.class.getName()).log(Level.SEVERE, null, ex);
        }

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

    private void agregarGrafica(Document document, String nombre) {
        Image image = null;
        try {
            image = Image.getInstance(nombre);

            image.scaleToFit(600, 500);
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

    private void agregarImagenDeFondo(Document document) throws DocumentException {
        Image image = null;
        try {
            image = Image.getInstance("Imagenes/marco2.png");
        } catch (BadElementException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
//Logger.getLogger(TicketPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error2: " + ex);
//Logger.getLogger(TicketPDF.class.getName()).log(Level.SEVERE, null, ex);
        }

        image.scaleToFit(PageSize.LETTER.getWidth(), PageSize.LETTER.getHeight());
        image.setAbsolutePosition(0, 0);

        document.add(image);

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

}

class Membrete extends PdfPageEventHelper {

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        try {
            Image image = null;
            try {
                image = Image.getInstance("Imagenes/Marco5.jpg");
            } catch (BadElementException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex);
//Logger.getLogger(TicketPDF.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error2: " + ex);
//Logger.getLogger(TicketPDF.class.getName()).log(Level.SEVERE, null, ex);
            }

            image.scaleToFit(PageSize.LETTER.getWidth(), PageSize.LETTER.getHeight() + 50);
            image.setAbsolutePosition(0, 0);

            document.add(image);

            //////////////////////////////////////
            try {
                image = Image.getInstance("Imagenes/LOGO.png");
            } catch (BadElementException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex);
//Logger.getLogger(TicketPDF.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error2: " + ex);
//Logger.getLogger(TicketPDF.class.getName()).log(Level.SEVERE, null, ex);
            }

            image.scaleToFit(PageSize.LETTER.getWidth() / 4, PageSize.LETTER.getHeight() / 4);
            image.setAlignment(Element.ALIGN_CENTER);

            document.add(image);
        } catch (DocumentException ex) {
            Logger.getLogger(Membrete.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
