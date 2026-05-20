package com.colombianita.Colombianita.service;

import com.colombianita.Colombianita.entity.CierreCaja;
import com.colombianita.Colombianita.repository.CierreCajaRepository;
import com.colombianita.Colombianita.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CierreCajaService {

    @Autowired
    private CierreCajaRepository cierreCajaRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Transactional
    public CierreCaja recalcularTotales(Long idCierre) {
        CierreCaja caja = cierreCajaRepository.findById(idCierre)
                .orElseThrow(() -> new RuntimeException("Cierre de caja no encontrado: " + idCierre));

        caja.setTotalEfectivo(pagoRepository.sumarMontoByMetodo(idCierre, "EFECTIVO"));
        caja.setTotalTarjetas(pagoRepository.sumarMontoByMetodo(idCierre, "TARJETA"));

        BigDecimal totalTransferencias = pagoRepository.sumarMontoByMetodo(idCierre, "TRANSFERENCIA")
                .add(pagoRepository.sumarMontoByMetodo(idCierre, "NEQUI"))
                .add(pagoRepository.sumarMontoByMetodo(idCierre, "DAVIPLATA"));
        caja.setTotalTransferencias(totalTransferencias);

        return cierreCajaRepository.save(caja);
    }

    @Transactional
    public CierreCaja procesarCierre(Long idCierre, BigDecimal efectivoDeclarado, String observaciones) {
        CierreCaja caja = recalcularTotales(idCierre);

        caja.setFechaCierre(LocalDateTime.now());
        caja.setEfectivoDeclarado(efectivoDeclarado);
        caja.setDiferencia(efectivoDeclarado.subtract(caja.getTotalEfectivo()));
        caja.setEstado("CERRADA");
        caja.setObservaciones(observaciones);

        return cierreCajaRepository.save(caja);
    }
}
