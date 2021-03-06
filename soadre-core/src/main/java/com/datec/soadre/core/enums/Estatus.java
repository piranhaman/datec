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
@JsonDeserialize(using = Estatus.EnumDeserializer.class)
public enum Estatus {
    ACTIVO("Activo"),
    INACTIVO("Inactivo");

    private final String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    Estatus(String descripcion) {
        this.descripcion = descripcion;
    }

    public static class EnumDeserializer extends StdDeserializer<Estatus> {

        public EnumDeserializer() {
            super(Estatus.class);
        }

        @Override
        public Estatus deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
            String descripcion = jp.readValueAsTree().get("descripcion").toString().replace("\"", "");
            return Arrays.asList(Estatus.values())
                    .stream()
                    .filter(m -> m.getDescripcion()
                    .equals(descripcion)).findFirst().get();
        }
    }

    public static Estatus buscarEstatusPorDescripcion(String descripcion) {
        for (Estatus value : Estatus.values()) {
            if (value.getDescripcion().equals(descripcion)) {
                return value;
            }
        }
        throw new BusinessException("No existe un estatus con la descripcion " + descripcion);
    }
}
