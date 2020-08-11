/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package separar.xmls.types.destino;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 *
 * @author hugo
 */
@JacksonXmlRootElement(namespace = "http://www.w3.org/2001/XMLSchema-instance", localName = "Call")
@JsonIgnoreProperties(ignoreUnknown = true) 
public class Call {
    
    @JacksonXmlProperty(localName = "Data", isAttribute = true)
    private Data data;
    
    @JacksonXmlProperty(localName = "Custom_Data")
    private Custom_Data customData;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Custom_Data getCustomData() {
        return customData;
    }

    public void setCustomData(Custom_Data customData) {
        this.customData = customData;
    }
    
}
