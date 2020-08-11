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
public class AudioSegment {
    
    @JacksonXmlProperty(localName = "channel_id")
    private String chanelId;
    @JacksonXmlProperty(localName = "recording_order")
    private String recordingOrder;
    @JacksonXmlProperty(localName = "audio_url")
    private String audioUrl;
    @JacksonXmlProperty(localName = "StartTime")
    private String startTime;
    @JacksonXmlProperty(localName = "Duration")
    private String duration;

    public String getChanelId() {
        return chanelId;
    }

    public void setChanelId(String chanelId) {
        this.chanelId = chanelId;
    }

    public String getRecordingOrder() {
        return recordingOrder;
    }

    public void setRecordingOrder(String recordingOrder) {
        this.recordingOrder = recordingOrder;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
    
}
