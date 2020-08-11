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
public class Info {

    @JacksonXmlProperty(localName = "RecordingId")
    private String recordingId;
    @JacksonXmlProperty(localName = "RecordingDateTime")
    private String recordingDateTime;
    @JacksonXmlProperty(localName = "Agent")
    private String agent;
    @JacksonXmlProperty(localName = "Recorder")
    private String recorder;
    @JacksonXmlProperty(localName = "Filename")
    private String filename;
    @JacksonXmlProperty(localName = "RecordingLength")
    private String recordingLength;
    @JacksonXmlProperty(localName = "RecordFileType")
    private String recordFileType;
    @JacksonXmlProperty(localName = "RecordFormat")
    private String recordFormat;
    @JacksonXmlProperty(localName = "RecordSampleRate")
    private String recordSampleRate;
    @JacksonXmlProperty(localName = "RecordBitsPerSample")
    private String recordBitsPerSample;

    public String getRecordingId() {
        return recordingId;
    }

    public void setRecordingId(String recordingId) {
        this.recordingId = recordingId;
    }

    public String getRecordingDateTime() {
        return recordingDateTime;
    }

    public void setRecordingDateTime(String recordingDateTime) {
        this.recordingDateTime = recordingDateTime;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getRecorder() {
        return recorder;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getRecordFileType() {
        return recordFileType;
    }

    public void setRecordFileType(String recordFileType) {
        this.recordFileType = recordFileType;
    }

    public String getRecordFormat() {
        return recordFormat;
    }

    public void setRecordFormat(String recordFormat) {
        this.recordFormat = recordFormat;
    }

    public String getRecordSampleRate() {
        return recordSampleRate;
    }

    public void setRecordSampleRate(String recordSampleRate) {
        this.recordSampleRate = recordSampleRate;
    }

    public String getRecordBitsPerSample() {
        return recordBitsPerSample;
    }

    public void setRecordBitsPerSample(String recordBitsPerSample) {
        this.recordBitsPerSample = recordBitsPerSample;
    }

    public String getRecordingLength() {
        return recordingLength;
    }

    public void setRecordingLength(String recordingLength) {
        this.recordingLength = recordingLength;
    }
    
}
