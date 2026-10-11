package com.api.apartamentos.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.api.apartamentos.models.PropiedadModel;

public interface IPropiedadRepository extends JpaRepository<PropiedadModel, Long> {
    
    // Buscar propiedades por su tipo
    List<PropiedadModel> findByTipo(String tipo);
    
    // Buscar propiedades por el ID del cliente (propietario)
    List<PropiedadModel> findByPropietario_Id(Long id);
}