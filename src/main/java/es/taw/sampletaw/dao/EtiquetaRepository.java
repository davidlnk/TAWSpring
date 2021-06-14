package es.taw.sampletaw.dao;

import es.taw.sampletaw.entity.Etiqueta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EtiquetaRepository extends JpaRepository<Etiqueta, Integer> {

    @Query("SELECT e FROM Etiqueta e WHERE e.nombre = :filtro")
    public Etiqueta findByNombre(String filtro);

    @Query("SELECT e FROM Etiqueta e WHERE UPPER(e.nombre) LIKE UPPER(CONCAT('%', :filtro, '%'))")
    public Etiqueta findBySimilarNombre (String filtro);

    @Query("SELECT e FROM Etiqueta e WHERE UPPER(e.nombre) = UPPER(:filtro)")
    public Etiqueta findBySimilarNombreI (String filtro);

    @Query("SELECT e FROM Etiqueta e WHERE UPPER(e.nombre) = UPPER(:filtro)")
    public Etiqueta findByNombreExacto (String filtro);
}
