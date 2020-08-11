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
public class CallData {

    @JacksonXmlProperty(localName = "CallId")
    private String callId;
    @JacksonXmlProperty(localName = "CalledParty")
    private String calledParty;
    @JacksonXmlProperty(localName = "CallingParty")
    private String callingParty;
    @JacksonXmlProperty(localName = "Customer")
    private String customer;
    @JacksonXmlProperty(localName = "DNIS")
    private String dNIS;
    @JacksonXmlProperty(localName = "Service")
    private String service;
    @JacksonXmlProperty(localName = "Disposition")
    private String disposition;

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public String getCalledParty() {
        return calledParty;
    }

    public void setCalledParty(String calledParty) {
        this.calledParty = calledParty;
    }

    public String getCallingParty() {
        return callingParty;
    }

    public void setCallingParty(String callingParty) {
        this.callingParty = callingParty;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getdNIS() {
        return dNIS;
    }

    public void setdNIS(String dNIS) {
        this.dNIS = dNIS;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDisposition() {
        return disposition;
    }

    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }
    
}
