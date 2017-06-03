/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package taqueria;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;

/**
 *
 * @author el dios
 */
public class Ticket {
 
    static ArrayList<String> CabezaLineas = new ArrayList<String>();
    static ArrayList<String> subCabezaLineas = new ArrayList<String>();
    static ArrayList<String> items = new ArrayList<String>();
    static ArrayList<String> totales = new ArrayList<String>();
    static ArrayList<String> LineasPie = new ArrayList<String>();
    
    public Ticket(){
    
        Date date = new Date();
        SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat hora = new SimpleDateFormat("hh:mm:ss aa");
        
        this.AddCabecera("Taqueria \"Ke-Tacos\"");
        this.AddCabecera(this.DarEspacio());
        this.AddCabecera("Expedido en: Avenida Hidalgo #3");
        this.AddCabecera(this.DarEspacio());
        this.AddCabecera("San Juan Zumpango Centro");
        this.AddCabecera(this.DarEspacio());
        this.AddCabecera("Edo. de Mex. C.P. 55603");
        this.AddCabecera(this.DarEspacio());
        this.AddCabecera(this.DibujarLinea(29));
        this.AddSubCabecera(this.DarEspacio());
        this.AddSubCabecera("" + fecha.format(date) + " " + hora.format(date));
        this.AddSubCabecera(this.DarEspacio());
        this.AddSubCabecera(this.DibujarLinea(14));
        
        this.AddTotal("", this.DibujarLinea(29));
        this.AddTotal("", this.DarEspacio());
        this.AddPieLinea(this.DibujarLinea(29));
        this.AddPieLinea(this.DarEspacio());
        this.AddPieLinea(this.DarEspacio());
        this.AddPieLinea("Gracias por su preferencia!!\n\n\n");
        for (int i = 0; i < 5; i++) {
        this.AddPieLinea(this.DarEspacio());
        }
    
    }

    public static void AddCabecera(String line) {
        CabezaLineas.add(line);
    }

    public static void AddSubCabecera(String line) {
        subCabezaLineas.add(line);
    }

    public static void AddItem(String cantidad, String item, String price) {
        OrderItem newItem = new OrderItem(' ');
        items.add(newItem.GeneraItem(cantidad, item, price));
    }

    public static void AddTotal(String name, String price) {
        OrderTotal newTotal = new OrderTotal(' ');
        totales.add(newTotal.GeneraTotal(name, price));
    }

    public static void AddPieLinea(String line) {
        LineasPie.add(line);
    }

    public static String DibujarLinea(int valor) {
        String raya = "";
        for (int x = 0; x < valor; x++) {
            raya += "=";
        }
        return raya;
    }

    public static String DarEspacio() {
        return "\n";
    }

    public static void ImprimirDocumento() {
        String cadena = "";
        for (int cabecera = 0; cabecera < CabezaLineas.size(); cabecera++) {
            cadena += CabezaLineas.get(cabecera);
        }
        for (int subcabecera = 0; subcabecera < subCabezaLineas.size(); subcabecera++) {
            cadena += subCabezaLineas.get(subcabecera);
        }
        for (int ITEM = 0; ITEM < items.size(); ITEM++) {
            cadena += items.get(ITEM);
        }
        for (int total = 0; total < totales.size(); total++) {
            cadena += totales.get(total);
        }
        for (int pie = 0; pie < LineasPie.size(); pie++) {
            cadena += LineasPie.get(pie);
        }

        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintService service = PrintServiceLookup.lookupDefaultPrintService();
        DocPrintJob pj = service.createPrintJob();
        byte[] bytes = cadena.getBytes();
        Doc doc = new SimpleDoc(bytes, flavor, null);
        try {
            pj.print(doc, null);
            
        } catch (Exception e) {
        }
        
        CabezaLineas.clear();
        subCabezaLineas.clear();
        items.clear();
        totales.clear();
        LineasPie.clear();
        
    }

  
    public static void main(String[] args) {
        

    }
}

class OrderTotal {

    char[] temp = new char[]{' '};

    public OrderTotal(char delimit) {
        temp = new char[]{delimit};
    }

    public String GetTotalNombre(String totalItem) {
        String[] delimitado = totalItem.split("" + temp);
        return delimitado[0];
    }

    public String GetTotalCantidad(String totalItem) {
        String[] delimitado = totalItem.split("" + temp);
        return delimitado[1];
    }

    public String GeneraTotal(String Nombre, String precio) {
        return Nombre + temp[0] + precio;
    }
}

class OrderItem {

    char[] temp = new char[]{' '};

    public OrderItem(char delimit) {
        temp = new char[]{delimit};
    }

    public String GetItemCantidad(String orderItem) {
        String[] delimitado = orderItem.split("" + temp);
        return delimitado[0];
    }

    public String GetItemNombre(String orderItem) {
        String[] delimitado = orderItem.split("" + temp);
        return delimitado[1];
    }

    public String GetItemPrecio(String orderItem) {
        String[] delimitado = orderItem.split("" + temp);
        return delimitado[2];
    }

    public String GeneraItem(String cantidad, String nombre, String precio) {
        return cantidad + temp[0]+ temp[0] + nombre + temp[0]+ temp[0] + precio;
    }
}

