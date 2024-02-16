package com.edgomesdev.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum Status {
    PENDING(0, "Pendente"), PROCESSING(1, "Processando"), SENT(2, "Enviado"), DELIVERED(3, "Entregue");

    private Integer code;
    private String description;

    public static Status toEnum(Integer cod) {
        if(cod == null) return null;

        for(Status x : Status.values()) {
            if(cod.equals(x.getCode())) return x;
        }

        throw new IllegalArgumentException("Status inválido");
    }
}
