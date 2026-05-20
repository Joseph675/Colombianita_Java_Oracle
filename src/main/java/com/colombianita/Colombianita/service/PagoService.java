package com.colombianita.Colombianita.service;

import com.colombianita.Colombianita.entity.Pago;
import com.colombianita.Colombianita.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    public List<Pago> listarTodos() {
        return pagoRepository.findAll();
    }

    public Optional<Pago> buscarPorId(Long id) {
        return pagoRepository.findById(id);
    }

    public Pago guardar(Pago pago) {
        return pagoRepository.save(pago);
    }

    public List<Pago> buscarPorPedido(Long idPedido) {
        return pagoRepository.findByPedidoIdPedido(idPedido);
    }

    public List<Pago> buscarPorFactura(Long idFactura) {
        return pagoRepository.findByFacturaIdFactura(idFactura);
    }

    public boolean existe(Long id) {
        return pagoRepository.existsById(id);
    }

    public void eliminar(Long id) {
        pagoRepository.deleteById(id);
    }
}
