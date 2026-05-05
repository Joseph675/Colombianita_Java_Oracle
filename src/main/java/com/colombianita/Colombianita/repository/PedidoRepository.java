package com.colombianita.Colombianita.repository;

import com.colombianita.Colombianita.dto.TopClienteDTO;
import com.colombianita.Colombianita.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Aquí puedes agregar métodos personalizados si los necesitas después, por ejemplo:
    // List<Pedido> findBySucursalIdSucursal(Long idSucursal);
     /**
     * Busca todos los pedidos activos (no pagados) para mesas, cargando
     * eficientemente sus detalles, la presentación del producto y el producto base.
     */
    @Query("SELECT p FROM Pedido p LEFT JOIN FETCH p.detalles dp LEFT JOIN FETCH dp.presentacion pr LEFT JOIN FETCH pr.producto WHERE p.estado <> 'PAGADO' AND p.tipoPedido = 'MESA'")
    List<Pedido> findPedidosActivosDeMesaConDetalles();

    /**
     * Busca el historial de pedidos para un ID de mesa específico, ordenados por fecha descendente.
     * Carga eficientemente los detalles, la presentación y el producto base.
     */
    @Query("SELECT p FROM Pedido p LEFT JOIN FETCH p.detalles dp LEFT JOIN FETCH dp.presentacion pr LEFT JOIN FETCH pr.producto WHERE p.idMesa = :idMesa ORDER BY p.fechaHora DESC")
    List<Pedido> findByIdMesaConDetalles(@Param("idMesa") Long idMesa);

    // El "Top 10" de Mejores Clientes (Histórico)
    @Query("SELECT c.idCliente as idCliente, c.nombres as nombres, c.celular as celular, SUM(p.total) as totalGastado " +
           "FROM Pedido p JOIN p.cliente c WHERE p.estado = 'PAGADO' GROUP BY c.idCliente, c.nombres, c.celular ORDER BY totalGastado DESC")
    List<TopClienteDTO> findTopClientes();
}