package com.api.apartamentos.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import com.api.apartamentos.models.PropiedadModel;

public interface IPropiedadRepository extends JpaRepository<PropiedadModel, Long> {
    
    @Override
    @EntityGraph(attributePaths = {"propietario"})
    List<PropiedadModel> findAll();
    List<PropiedadModel> findByTipo(String tipo);
    
    List<PropiedadModel> findByPropietario_Id(Long id);
}