package com.colombianita.Colombianita.dto;

import java.math.BigDecimal;

public interface TopClienteDTO {
    Long getIdCliente();
    String getNombres();
    String getCelular();
    BigDecimal getTotalGastado();
}