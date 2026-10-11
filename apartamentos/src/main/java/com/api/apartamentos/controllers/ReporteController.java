package com.api.apartamentos.controllers;

import org.springframework.web.bind.annotation.*;
import com.api.apartamentos.services.PagosService;
import com.api.apartamentos.services.ReservaService;
import com.api.apartamentos.models.PagosModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final PagosService pagosService;
    private final ReservaService reservaService;

    public ReporteController(PagosService pagosService, ReservaService reservaService) {
        this.pagosService = pagosService;
        this.reservaService = reservaService;
    }

    @GetMapping("/ocupacion")
    public Map<String, Object> reporteOcupacion() {
        long reservasActivas = reservaService.findAll().stream()
                .filter(r -> !"CANCELADA".equals(r.getEstado()))
                .count();
        return Map.of("total_reservas_activas", reservasActivas);
    }

    @GetMapping("/ingresos")
    public Map<String, Object> reporteIngresos() {
        BigDecimal totalIngresos = pagosService.findAll().stream()
                .filter(p -> p.getEstado() == PagosModel.EstadoPago.PAGADO)
                .map(PagosModel::getImporte)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return Map.of("ingresos_totales", totalIngresos);
    }

    @GetMapping("/morosidad")
    public List<PagosModel> reporteMorosidad() {
        return pagosService.findAll().stream()
                .filter(p -> p.getEstado() == PagosModel.EstadoPago.VENCIDO || 
                            (p.getEstado() == PagosModel.EstadoPago.PENDIENTE && p.getFechaVencimiento().isBefore(LocalDate.now())))
                .collect(Collectors.toList());
    }
}