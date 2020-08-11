///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package separar.xmls;
//
//import separar.xmls.types.origen.Regordings;
//import separar.xmls.types.origen.Recording;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.dataformat.xml.XmlMapper;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
///**
// *
// * @author daniel
// */
//public class SepararXmls {
//
//    static XmlMapper xmlMapper = new XmlMapper();
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        Vista v= new Vista();
//    }
//    
////    File archivo = new File("/archivos/archivoXml.xml");
////        try (InputStream inputStream = new FileInputStream(archivo)) {
////            Regordings readValue = xmlMapper.readValue(inputStream, Regordings.class);
////            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
////            for (Recording recording : readValue.getRecording()) {
////                xmlMapper.writeValue(new File("/archivos/resultado_separacion/"+recording.getInfo().getFilename().replaceAll(".wav", "")+".xml"), recording);
////            }
////            System.out.println("echo : ");
////        } catch (IOException ex) {
////            System.out.println("error " + ex.getMessage());
////        }
//    
//}
