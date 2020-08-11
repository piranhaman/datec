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
public class Audio {
    
    @JacksonXmlProperty(localName = "audio_segement")
    private AudioSegment audioSegment;
    

    public AudioSegment getAudioSegment() {
        return audioSegment;
    }

    public void setAudioSegment(AudioSegment audioSegment) {
        this.audioSegment = audioSegment;
    }

}
