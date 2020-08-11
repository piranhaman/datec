/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package separar.xmls.types.destino;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 *
 * @author hugo
 */
@JsonIgnoreProperties(ignoreUnknown = true) 
public class Data {
    
    @JacksonXmlProperty(localName = "audio")
    private Audio audio;
    @JacksonXmlProperty(localName = "direction")
    private String direction;
    @JacksonXmlProperty(localName = "number_of_holds")
    private String numberOfHolds;
    @JacksonXmlProperty(localName = "number_of_conferences")
    private String numberOfConferences;
    @JacksonXmlProperty(localName = "number_of_transfers")
    private String numberOfTransfers;
    @JacksonXmlProperty(localName = "total_hold_time")
    private String totalHoldTime;
    @JacksonXmlProperty(localName = "extension")
    private String extension;
    @JacksonXmlProperty(localName = "time_offset")
    private String timeOffset;
    @JacksonXmlProperty(localName = "pbx_login_id")
    private String pbxLoginId;
    @JacksonXmlProperty(localName = "switch_name")
    private String switchName;
    @JacksonXmlProperty(localName = "agent_name")
    private String agentName;
    @JacksonXmlProperty(localName = "group_name")
    private String groupName;
    @JacksonXmlProperty(localName = "ani")
    private String ani;
    @JacksonXmlProperty(localName = "dnis")
    private String dnis;
    @JacksonXmlProperty(localName = "unique_identifier")
    private String uniqueIdentifier;

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getNumberOfHolds() {
        return numberOfHolds;
    }

    public void setNumberOfHolds(String numberOfHolds) {
        this.numberOfHolds = numberOfHolds;
    }

    public String getNumberOfConferences() {
        return numberOfConferences;
    }

    public void setNumberOfConferences(String numberOfConferences) {
        this.numberOfConferences = numberOfConferences;
    }

    public String getNumberOfTransfers() {
        return numberOfTransfers;
    }

    public void setNumberOfTransfers(String numberOfTransfers) {
        this.numberOfTransfers = numberOfTransfers;
    }

    public String getTotalHoldTime() {
        return totalHoldTime;
    }

    public void setTotalHoldTime(String totalHoldTime) {
        this.totalHoldTime = totalHoldTime;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getTimeOffset() {
        return timeOffset;
    }

    public void setTimeOffset(String timeOffset) {
        this.timeOffset = timeOffset;
    }

    public String getPbxLoginId() {
        return pbxLoginId;
    }

    public void setPbxLoginId(String pbxLoginId) {
        this.pbxLoginId = pbxLoginId;
    }

    public String getSwitchName() {
        return switchName;
    }

    public void setSwitchName(String switchName) {
        this.switchName = switchName;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAni() {
        return ani;
    }

    public void setAni(String ani) {
        this.ani = ani;
    }

    public String getDnis() {
        return dnis;
    }

    public void setDnis(String dnis) {
        this.dnis = dnis;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }
    
}
