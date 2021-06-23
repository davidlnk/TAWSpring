package es.taw.sampletaw.dao;

import es.taw.sampletaw.entity.Estudio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface EstudioRepository extends JpaRepository<Estudio, Integer> {

    @Query("select e from Estudio e where e.analista.id = :id")
    List<Estudio> findByAnalista(@Param("id") Integer id);

    @Query("select e from Estudio e where e.analista.id = :id and e.fecha BETWEEN :desdeFecha AND :hastaFecha AND lower(e.descripcion) LIKE lower(concat('%', :descripcion, '%')) ")
    List<Estudio> findEstudioByAntiguo(@Param("descripcion") String descripcion, @Param("desdeFecha") Date desdeFecha, @Param("hastaFecha") Date hastaFecha, @Param("id") Integer id);

    @Query("select e from Estudio e where e.analista.id = :id and e.fecha BETWEEN :desdeFecha AND :hastaFecha AND lower(e.descripcion) LIKE lower(concat('%', :descripcion, '%')) order by e.fecha desc")
    List<Estudio> findEstudioByReciente(@Param("descripcion") String descripcion, @Param("desdeFecha") Date desdeFecha, @Param("hastaFecha") Date hastaFecha, Integer id);
}
