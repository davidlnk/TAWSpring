/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.taw.sampletaw.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.taw.sampletaw.dao.EtiquetaRepository;
import es.taw.sampletaw.dao.EventoRepository;
import es.taw.sampletaw.dao.UsuarioDeEventosRepository;
import es.taw.sampletaw.dao.UsuarioRepository;
import es.taw.sampletaw.dto.EtiquetaDTO;
import es.taw.sampletaw.dto.EventoDTO;
import es.taw.sampletaw.dto.PublicoDTO;
import es.taw.sampletaw.dto.UsuarioDeEventosDTO;
import es.taw.sampletaw.entity.Etiqueta;
import es.taw.sampletaw.entity.Evento;
import es.taw.sampletaw.entity.Publico;
import es.taw.sampletaw.entity.UsuarioDeEventos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author rafar
 */
@Service
public class EventoService {

    private EtiquetaService etiquetaService;

    private EtiquetaRepository etiquetaRepository;

    private UsuarioRepository usuarioRepository;

    private EventoRepository eventoRepository;

    private UsuarioDeEventosRepository usuarioDeEventosRepository;

    @Autowired
    public void setEtiquetaRepository(EtiquetaRepository etiquetaRepository) {
        this.etiquetaRepository = etiquetaRepository;
    }

    @Autowired
    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Autowired
    public void setEventoRepository(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @Autowired
    public void setUsuarioDeEventosRepository(UsuarioDeEventosRepository usuarioDeEventosRepository) {
        this.usuarioDeEventosRepository = usuarioDeEventosRepository;
    }




    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")


     public List<EventoDTO> convertirAListaDTOdirectamente(List<Integer> lista){
         return this.convertirAListaDTO(this.convertirAListaEvento(lista));
     }

     public List<EventoDTO> convertirAListaDTO(List<Evento> lista){
         if (lista != null) {
            List<EventoDTO> listaDTO = new ArrayList<>();
            for (Evento e : lista) {
                listaDTO.add(e.getDTO());
            }
            return listaDTO;
        } else {
            return null;
        }
     }

     public List<Evento> convertirAListaEvento(List<Integer> lista){
         List<Evento> listaEvento = new ArrayList<>();
         if(lista != null ){
            for(int id : lista){
             listaEvento.add(eventoRepository.findById(id).get());
            }
            return listaEvento;
         }else{
             return null;
         }

     }

     public List<Integer> convertirALaInversa (List<EventoDTO> lista){
        List<Integer> res = new ArrayList<>();
        for(EventoDTO e : lista){
            res.add(e.getId());
        }
        return res;
    }

     public List<EventoDTO> findByEventosDeEtiqueta(EtiquetaDTO e) {
         Etiqueta et = etiquetaRepository.findById(e.getId()).get();
         return convertirAListaDTO(et.getEventoList());
     }

     public List<EventoDTO> findByTitulo(String titulo){
         List <Evento> lista = eventoRepository.findByTitulo(titulo);
         return convertirAListaDTO(lista);
     }

     public EventoDTO find(int id){
         return eventoRepository.findById(id).get().getDTO();
     }

     public List<EventoDTO> findAll(){
         return convertirAListaDTO(eventoRepository.findAll());
     }

     public List<EventoDTO> findByTituloHistorial(String palabra, UsuarioDeEventosDTO usuarioDeEventos){
         UsuarioDeEventos user = usuarioDeEventosRepository.findById(usuarioDeEventos.getId()).get();
         return convertirAListaDTO(eventoRepository.findByTituloHistorial(palabra, user, new Date()));
     }

     public List<EventoDTO> findByEtiquetaHistorial(EtiquetaDTO e, UsuarioDeEventosDTO usuarioDeEventos){
         UsuarioDeEventos user = usuarioDeEventosRepository.findById(usuarioDeEventos.getId()).get();
         Etiqueta et = etiquetaRepository.findById(e.getId()).get();
         return convertirAListaDTO(eventoRepository.findByEtiquetaHistorial(et, user, new Date()));
     }

     public List<EventoDTO> findAllHistorial(UsuarioDeEventosDTO usuarioDeEventos){
         UsuarioDeEventos user = usuarioDeEventosRepository.findById(usuarioDeEventos.getId()).get();
         return convertirAListaDTO(eventoRepository.findAllHistorial(user, new Date()));
     }

     public List<EventoDTO> findByTituloReserva(String palabra, UsuarioDeEventosDTO usuarioDeEventos){
         UsuarioDeEventos user = usuarioDeEventosRepository.findById(usuarioDeEventos.getId()).get();
         return convertirAListaDTO(eventoRepository.findByTituloReserva(palabra, user, new Date()));
     }

     public List<EventoDTO> findByEtiquetaReserva(EtiquetaDTO e, UsuarioDeEventosDTO usuarioDeEventos){
         UsuarioDeEventos user = usuarioDeEventosRepository.findById(usuarioDeEventos.getId()).get();
         Etiqueta et = etiquetaRepository.findById(e.getId()).get();
         return convertirAListaDTO(eventoRepository.findByEtiquetaReserva(et, user, new Date()));
     }

     public List<EventoDTO> findAllReserva(UsuarioDeEventosDTO usuarioDeEventos){
         UsuarioDeEventos user = usuarioDeEventosRepository.findById(usuarioDeEventos.getId()).get();
         return convertirAListaDTO(eventoRepository.findAllReserva(user, new Date()));
     }

     public void remove(EventoDTO e){
         eventoRepository.delete(eventoRepository.findById(e.getId()).get());
     }

     public List<EventoDTO> filtroNombre(String nombre){
        List<Evento> eventos = eventoRepository.findBySimilarNombre(nombre);
        return convertirAListaDTO(eventos);
    }

     public List<EventoDTO> filtroDescripcion(String descripcion){
        List<Evento> eventos = eventoRepository.findBySimilarDescripcion(descripcion);
        return convertirAListaDTO(eventos);
    }

     public List<EventoDTO> filtroFecha(Date fecha){
        List<Evento> eventos = eventoRepository.findBySimilarFecha(fecha);
        return convertirAListaDTO(eventos);
    }

     public List<EventoDTO> filtroFechaEntrada(Date fecha){
        List<Evento> eventos = eventoRepository.findBySimilarFechaEntrada(fecha);
        return convertirAListaDTO(eventos);
    }

     public List<EventoDTO> filtroPrecio(String precio){
        List<Evento> eventos = eventoRepository.findBySimilarPrecio(new Integer(precio));
        return convertirAListaDTO(eventos);
    }

     public List<EventoDTO> filtroAforo(String aforo){
        List<Evento> eventos = eventoRepository.findBySimilarAforo(new Integer(aforo));
        return convertirAListaDTO(eventos);
    }

     public List<EventoDTO> filtroMaxEntradasUsuario(String entradas){
        List<Evento> eventos = eventoRepository.findBySimilarMaxEntradasUsuario(new Integer(entradas));
        return convertirAListaDTO(eventos);
    }

     public List<EventoDTO> filtroAsientosAsignados(String asiento){
        List<Evento> eventos = eventoRepository.findBySimilarAsientosAsignados(asiento.equals("Si") ? true : false);
        return convertirAListaDTO(eventos);
    }

     public List<EventoDTO> filtroNumFilas(String numero){
        List<Evento> eventos = eventoRepository.findBySimilarNumFilas(new Integer(numero));
        return convertirAListaDTO(eventos);
    }

     public List<EventoDTO> filtroAsientosFila(String asientos){
        List<Evento> eventos = eventoRepository.findBySimilarAsientosFila(new Integer(asientos));
        return convertirAListaDTO(eventos);
    }

     public List<EventoDTO> filtroCreador(String creador){
        List<Evento> eventos = eventoRepository.findBySimilarCreador(creador);
        return convertirAListaDTO(eventos);
    }

     public List<EventoDTO> filtroImagen(String imagen){
        List<Evento> eventos = eventoRepository.findBySimilarImagen(imagen);
        return convertirAListaDTO(eventos);
    }

     public List<EventoDTO> TodosEventos(){
        List<Evento> eventos = eventoRepository.findAll();
        return convertirAListaDTO(eventos);
    }

    public List<EventoDTO> findByDisponiblesEtiqueta(EtiquetaDTO e) {
        Etiqueta et = etiquetaRepository.findById(e.getId()).get();
        List<EventoDTO> res = convertirAListaDTO(eventoRepository.findByDisponiblesEtiqueta(et, new Date()));
        if (res.size() >= 7) {
            return res.subList(0,7);
        } else {
            return res;
        }
    }

    public List<EventoDTO> findByDisponiblesMasPopulares() {
        List<EventoDTO> res = convertirAListaDTO(eventoRepository.findByDisponiblesMasPopulares(new Date()));
        if (res.size() >= 7) {
            return res.subList(0,7);
        } else {
            return res;
        }
    }

    public List<EventoDTO> findByDisponiblesMasCercanos() {
        List<EventoDTO> res = convertirAListaDTO(eventoRepository.findByDisponiblesMasCercanos(new Date()));
        if (res.size() >= 7) {
            return res.subList(0,7);
        } else {
            return res;
        }
    }

    public Map<EventoDTO, List<PublicoDTO>> construirMap(List<EventoDTO> lista) {
        Map<EventoDTO, List<PublicoDTO>> map = new HashMap<>();
        List<Evento> eventos = convertirAListaEvento(convertirALaInversa(lista));
        for (Evento e : eventos) {
            map.put(e.getDTO(), PublicoService.convertirAListaDTO(e.getPublicoList()));
        }
        return map;
    }

     public EventoDTO buscarEvento (Integer id) {
        Evento evento = this.eventoRepository.findById(id).get();
        if (evento != null) {
            return evento.getDTO();
        } else {
            return null;
        }
    }

     public void borrarEvento (EventoDTO evento) {
        Evento eventoF = this.eventoRepository.findById(evento.getId()).get();
        this.eventoRepository.delete(eventoF);
    }

     public String getEtiquetasToString(EventoDTO evento) {
        String str = "";
        for(Integer e : evento.getEtiquetaList()){
            str = str + this.etiquetaService.buscarEtiqueta(e).getNombre() + " ";
        }
        return str;
    }

     public void editarEvento(EventoDTO e){
         eventoRepository.save(eventoRepository.findById(e.getId()).get());
     }

     public void editarEvento (Integer eventoId, Integer idUsuario, String titulo, String descripcion, String etiquetas, Integer precio, String imagen, Date fecha, Date fecha_limite_entradas, Integer aforo_maximo, Integer maximo_entradas_usuario, String asientos_asignados, String numero_filas, String asientos_por_fila) {
        Evento eventoEditar = eventoRepository.findById(eventoId).get();
        eventoEditar.setTitulo(titulo);
        eventoEditar.setDescripcion(descripcion);
        eventoEditar.setPrecioEntrada(precio);
        eventoEditar.setImagen(imagen);
        eventoEditar.setFecha(fecha);
        eventoEditar.setFechaLimEntradas(fecha_limite_entradas);
        eventoEditar.setAforoMax(aforo_maximo);
        eventoEditar.setMaxEntradasPorUsuario(maximo_entradas_usuario);
        if("Si".equals(asientos_asignados)){
            eventoEditar.setAsientosAsignados(true);
            eventoEditar.setNumFilas(Integer.parseInt(numero_filas));
            eventoEditar.setAsientosPorFila(Integer.parseInt(asientos_por_fila));
        }else{
            eventoEditar.setAsientosAsignados(false);
            eventoEditar.setNumFilas(null);
            eventoEditar.setAsientosPorFila(null);
         }
        List<Etiqueta> etiquetaList = new ArrayList();
        String[] arrayEtiquetas = etiquetas.split(" ");
        for(String item : arrayEtiquetas){
            Etiqueta buscada = etiquetaRepository.findBySimilarNombreI(item);
            if(buscada==null){
                Etiqueta e = new Etiqueta();
                e.setNombre(item);
                etiquetaList.add(e);
                etiquetaRepository.save(e);
            }else{
                etiquetaList.add(buscada);
            }
        }
        eventoEditar.setEtiquetaList(etiquetaList);
        eventoRepository.save(eventoEditar);
     }

     public void crearEvento (Integer idUsuario, String titulo, String descripcion, String etiquetas, Integer precio, String imagen, Date fecha, Date fecha_limite_entradas, Integer aforo_maximo, Integer maximo_entradas_usuario, String asientos_asignados, String numero_filas, String asientos_por_fila) {
        Evento eventoEditar = new Evento();
        eventoEditar.setUsuario(usuarioRepository.findById(idUsuario).get());
        eventoEditar.setTitulo(titulo);
        eventoEditar.setDescripcion(descripcion);
        eventoEditar.setPrecioEntrada(precio);
        eventoEditar.setImagen(imagen);
        eventoEditar.setFecha(fecha);
        eventoEditar.setFechaLimEntradas(fecha_limite_entradas);
        eventoEditar.setAforoMax(aforo_maximo);
        eventoEditar.setMaxEntradasPorUsuario(maximo_entradas_usuario);
        if("Si".equals(asientos_asignados)){
            eventoEditar.setAsientosAsignados(true);
            eventoEditar.setNumFilas(Integer.parseInt(numero_filas));
            eventoEditar.setAsientosPorFila(Integer.parseInt(asientos_por_fila));
        }else{
            eventoEditar.setAsientosAsignados(false);
            eventoEditar.setNumFilas(null);
            eventoEditar.setAsientosPorFila(null);
         }
        List<Etiqueta> etiquetaList = new ArrayList();
        String[] arrayEtiquetas = etiquetas.split(" ");
        for(String item : arrayEtiquetas){
            Etiqueta buscada = etiquetaRepository.findBySimilarNombreI(item);
            if(buscada==null){
                Etiqueta e = new Etiqueta();
                e.setNombre(item);
                etiquetaList.add(e);
                etiquetaRepository.save(e);
            }else{
                etiquetaList.add(buscada);
            }
        }
        eventoEditar.setEtiquetaList(etiquetaList);

        eventoRepository.save(eventoEditar);

    }

     public void addPublico(Evento evento, Publico publico) {
        List<Publico> listaPublico = evento.getPublicoList();
        listaPublico.add(publico);
        evento.setPublicoList(listaPublico);
        eventoRepository.save(evento);
     }

     public void removePublico(Evento evento, Publico publico) {
        List<Publico> listaPublico = evento.getPublicoList();
        listaPublico.remove(publico);
        evento.setPublicoList(listaPublico);
        eventoRepository.save(evento);
     }
}
