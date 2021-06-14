package es.taw.sampletaw.dao;

import es.taw.sampletaw.entity.Evento;
import es.taw.sampletaw.entity.Publico;
import es.taw.sampletaw.entity.UsuarioDeEventos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PublicoRepository extends JpaRepository<Publico, Integer> {

    @Query("SELECT p FROM Publico p WHERE p.usuarioDeEventos = :usuario AND p.evento = :evento")
    public List<Publico> findByUsuarioYEvento(UsuarioDeEventos usuario, Evento evento);

    @Query("SELECT p FROM Publico p WHERE p.evento = :evento AND p.fila = :fila AND p.asiento = :asiento")
    public Publico findByFilaAsientoEvento(Integer fila, Integer asiento, Evento evento);
}
