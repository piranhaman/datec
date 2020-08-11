/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package separar.xmls.types.origen;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 *
 * @author hugo
 */
@JsonIgnoreProperties(ignoreUnknown = true) 
public class Recording {
    
    @JacksonXmlProperty(localName = "Info")
    private Info info;
    
    @JacksonXmlProperty(localName = "CallData")
    private CallData callData;
    
    @JacksonXmlProperty(localName = "UserData")
    private UserData userData;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public CallData getCallData() {
        return callData;
    }

    public void setCallData(CallData callData) {
        this.callData = callData;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
    
}
