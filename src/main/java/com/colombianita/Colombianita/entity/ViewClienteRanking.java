package com.colombianita.Colombianita.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vw_clientes_ranking")
@Immutable
public class ViewClienteRanking {

    @Id
    private Long idCliente;
    private String nombres;
    private String celular;
    private Integer totalVisitas;
    private BigDecimal gastoTotal;
    private BigDecimal ticketPromedio;
    private LocalDateTime ultimaVisita;
    private String segmento;
    private Integer rankingGasto;

    public Long getIdCliente() { return idCliente; }
    public String getNombres() { return nombres; }
    public String getCelular() { return celular; }
    public Integer getTotalVisitas() { return totalVisitas; }
    public BigDecimal getGastoTotal() { return gastoTotal; }
    public BigDecimal getTicketPromedio() { return ticketPromedio; }
    public LocalDateTime getUltimaVisita() { return ultimaVisita; }
    public String getSegmento() { return segmento; }
    public Integer getRankingGasto() { return rankingGasto; }
}