/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.datec.soadre.core.enums;

import com.datec.soadre.core.exceptions.BusinessException;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author Piranhaman
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = Perfil.EnumDeserializer.class)
public enum Perfil {
    ADMINISTRADOR("Administrador"),
    CAJERO("Cajero"),
    TAQUERO("Taquero"),
    FUENTE_DE_SODAS("Fuente de Sodas"),
    MESERO("Mesero");

    private final String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    Perfil(String descripcion) {
        this.descripcion = descripcion;
    }

    public static class EnumDeserializer extends StdDeserializer<Perfil> {

        public EnumDeserializer() {
            super(Perfil.class);
        }

        @Override
        public Perfil deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
            String descripcion = jp.readValueAsTree().get("descripcion").toString().replace("\"", "");
            return Arrays.asList(Perfil.values())
                    .stream()
                    .filter(m -> m.getDescripcion()
                    .equals(descripcion)).findFirst().get();
        }
    }

    public static Perfil buscarTipoUsuarioPorDescripcion(String descripcion) {
        for (Perfil value : Perfil.values()) {
            if (value.getDescripcion().equals(descripcion)) {
                return value;
            }
        }
        throw new BusinessException("No existe el tipo de usuario con la descripcion " + descripcion);
    }

}
