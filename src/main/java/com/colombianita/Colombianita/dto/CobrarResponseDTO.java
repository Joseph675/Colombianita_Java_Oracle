package com.colombianita.Colombianita.dto;

import com.colombianita.Colombianita.entity.Factura;
import com.colombianita.Colombianita.entity.Pago;

import java.util.List;

public class CobrarResponseDTO {

    private Factura factura;
    private List<Pago> pagos;

    public Factura getFactura() { return factura; }
    public void setFactura(Factura factura) { this.factura = factura; }

    public List<Pago> getPagos() { return pagos; }
    public void setPagos(List<Pago> pagos) { this.pagos = pagos; }
}
