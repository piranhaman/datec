/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package separar.xmls.types.origen;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;

/**
 *
 * @author hugo
 */
public class Regordings {

    @JacksonXmlElementWrapper(useWrapping = false, localName = "Recordings")
    @JacksonXmlProperty(localName = "Recording")
    private List<Recording> recording;

    public List<Recording> getRecording() {
        return recording;
    }

    public void setRecording(List<Recording> recording) {
        this.recording = recording;
    }
    
}
