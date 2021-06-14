package es.taw.sampletaw.dao;

import es.taw.sampletaw.entity.UsuarioDeEventos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioDeEventosRepository extends JpaRepository<UsuarioDeEventos, Integer> {

    @Query("SELECT a FROM UsuarioDeEventos a WHERE a.correo = :correo")
    public Boolean esCorreoUnico(String correo);
}
