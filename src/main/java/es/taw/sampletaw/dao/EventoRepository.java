package es.taw.sampletaw.dao;

import es.taw.sampletaw.entity.Etiqueta;
import es.taw.sampletaw.entity.Evento;
import es.taw.sampletaw.entity.UsuarioDeEventos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Integer> {

    @Query("SELECT max(e.id) FROM Evento e")
    public int findIdMasAlta ();

    @Query("SELECT c FROM Evento c WHERE UPPER(c.titulo) LIKE UPPER(CONCAT('%', :filtro, '%'))")
    public List<Evento> findByTitulo (String filtro);

    @Query("SELECT c FROM Evento c WHERE UPPER(c.titulo) LIKE UPPER(CONCAT('%', :filtro, '%'))")
    public List<Evento> findBySimilarNombre (String filtro);

    @Query("SELECT c FROM Evento c WHERE UPPER(c.descripcion) LIKE UPPER(CONCAT('%', :filtro, '%'))")
    public List<Evento> findBySimilarDescripcion (String filtro);

    @Query("SELECT c FROM Evento c WHERE UPPER(c.imagen) LIKE UPPER(CONCAT('%', :filtro, '%'))")
    public List<Evento> findBySimilarImagen (String filtro);

    @Query("SELECT c FROM Evento c WHERE c.fecha = :filtro")
    public List<Evento> findBySimilarFecha (Date filtro);

    @Query("SELECT c FROM Evento c WHERE c.fechaLimEntradas = :filtro")
    public List<Evento> findBySimilarFechaEntrada (Date filtro);

    //TODO: Cambiar para que el filtro que se le pasa en el controller/servlet sea un Integer
    @Query("SELECT c FROM Evento c WHERE c.precioEntrada = :filtro")
    public List<Evento> findBySimilarPrecio (Integer filtro);

    //TODO: Cambiar para que el filtro que se le pasa en el controller/servlet sea un Integer
    @Query("SELECT c FROM Evento c WHERE c.aforoMax = :filtro")
    public List<Evento> findBySimilarAforo (Integer filtro);

    //TODO: Cambiar para que el filtro que se le pasa en el controller/servlet sea un Integer
    @Query("SELECT c FROM Evento c WHERE c.maxEntradasPorUsuario = :filtro")
    public List<Evento> findBySimilarMaxEntradasUsuario (Integer filtro);

    //TODO: Cambiar para que el filtro que se le pasa en el controller/servlet sea un boolean
    //boolean asignados = filtro.equals("Si");
    @Query("SELECT c FROM Evento c WHERE c.asientosAsignados = :filtro")
    public List<Evento> findBySimilarAsientosAsignados (boolean filtro);

    //TODO: Cambiar para que el filtro que se le pasa en el controller/servlet sea un Integer
    @Query("SELECT c FROM Evento c WHERE c.numFilas = :filtro")
    public List<Evento> findBySimilarNumFilas (Integer filtro);

    //TODO: Cambiar para que el filtro que se le pasa en el controller/servlet sea un Integer
    @Query("SELECT c FROM Evento c WHERE c.asientosPorFila = :filtro")
    public List<Evento> findBySimilarAsientosFila (Integer filtro);

    @Query("SELECT c FROM Evento c WHERE UPPER(c.usuario.nickname) LIKE UPPER(CONCAT('%', :filtro, '%'))")
    public List<Evento> findBySimilarCreador (String filtro);

    @Query("SELECT e FROM Evento e WHERE :etiqueta MEMBER OF e.etiquetaList")
    public List<Evento> findByEtiqueta(Etiqueta etiqueta);

    //TODO: Pasarle un new Date()
    //TODO: Controlar la lista devuelta para limitarla a 7 elementos
    @Query("SELECT e FROM Evento e WHERE :date < e.fechaLimEntradas AND size(e.publicoList) < e.aforoMax AND :etiqueta MEMBER OF e.etiquetaList")
    public List<Evento> findByDisponiblesEtiqueta(Etiqueta etiqueta, Date date);

    //TODO: Pasarle un new Date()
    //TODO: Controlar la lista devuelta para limitarla a 7 elementos
    @Query("SELECT e FROM Evento e WHERE :date < e.fechaLimEntradas AND size(e.publicoList) < e.aforoMax ORDER BY size(e.publicoList) DESC")
    public List<Evento> findByDisponiblesMasPopulares(Date date);

    //TODO: Pasarle un new Date()
    //TODO: Controlar la lista devuelta para limitarla a 7 elementos
    @Query("SELECT e FROM Evento e WHERE :date < e.fechaLimEntradas AND size(e.publicoList) < e.aforoMax ORDER BY e.fechaLimEntradas ASC")
    public List<Evento> findByDisponiblesMasCercanos(Date date);

    // ======= FILTROS EXPLORAR =========

    //TODO: Pasarle un new Date()
    @Query("SELECT e FROM Evento e WHERE UPPER(e.titulo) LIKE UPPER(:tit) AND :date > e.fechaLimEntradas AND :user IN (SELECT p.usuarioDeEventos.id FROM Publico p WHERE p.evento = e)")
    public List<Evento> findByTituloHistorial(String filtro, UsuarioDeEventos user, Date date);

    //TODO: Pasarle un new Date()
    @Query("SELECT e FROM Evento e WHERE UPPER(e.titulo) LIKE UPPER(:tit) AND :date < e.fechaLimEntradas AND :user IN (SELECT p.usuarioDeEventos.id FROM Publico p WHERE p.evento = e)")
    public List<Evento> findByTituloReserva(String filtro, UsuarioDeEventos user, Date date);

    //TODO: Pasarle un new Date()
    @Query("SELECT e FROM Evento e WHERE :etiqueta MEMBER OF e.etiquetaList AND :date > e.fechaLimEntradas AND :user IN (SELECT p.usuarioDeEventos.id FROM Publico p WHERE p.evento = e)")
    public List<Evento> findByEtiquetaHistorial(Etiqueta etiqueta, UsuarioDeEventos user, Date date);

    //TODO: Pasarle un new Date()
    @Query("SELECT e FROM Evento e WHERE :etiqueta MEMBER OF e.etiquetaList AND :date < e.fechaLimEntradas AND :user IN (SELECT p.usuarioDeEventos.id FROM Publico p WHERE p.evento = e)")
    public List<Evento> findByEtiquetaReserva(Etiqueta etiqueta, UsuarioDeEventos user, Date date);

    //TODO: Pasarle un new Date()
    @Query("SELECT e FROM Evento e WHERE :date > e.fechaLimEntradas AND :user IN (SELECT p.usuarioDeEventos.id FROM Publico p WHERE p.evento = e)")
    public List<Evento> findAllHistorial(UsuarioDeEventos user, Date date);

    //TODO: Pasarle un new Date()
    @Query("SELECT e FROM Evento e WHERE :date < e.fechaLimEntradas AND :user IN (SELECT p.usuarioDeEventos.id FROM Publico p WHERE p.evento = e)")
    public List<Evento> findAllReserva(UsuarioDeEventos user, Date date);
}
