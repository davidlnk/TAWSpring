/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.taw.sampletaw.service;

import java.util.ArrayList;
import java.util.List;

import es.taw.sampletaw.dao.EventoRepository;
import es.taw.sampletaw.dao.PublicoRepository;
import es.taw.sampletaw.dao.UsuarioDeEventosRepository;
import es.taw.sampletaw.dto.EventoDTO;
import es.taw.sampletaw.dto.PublicoDTO;
import es.taw.sampletaw.dto.UsuarioDeEventosDTO;
import es.taw.sampletaw.entity.Evento;
import es.taw.sampletaw.entity.Publico;
import es.taw.sampletaw.entity.UsuarioDeEventos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author David
 */
@Controller
public class PublicoService {


    private PublicoRepository publicoRepository;

    private UsuarioDeEventosRepository usuarioDeEventosRepository;

    private EventoRepository eventoRepository;

    @Autowired
    public void setPublicoRepository(PublicoRepository publicoRepository) {
        this.publicoRepository = publicoRepository;
    }

    @Autowired
    public void setUsuarioDeEventosRepository(UsuarioDeEventosRepository usuarioDeEventosRepository) {
        this.usuarioDeEventosRepository = usuarioDeEventosRepository;
    }

    @Autowired
    public void setEventoRepository(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    EventoService eventoService;

    UsuarioDeEventosService usuarioDeEventosService;



    public static List<PublicoDTO> convertirAListaDTO(List<Publico> lista) {
        if (lista != null) {
            List<PublicoDTO> listaDTO = new ArrayList<>();
            for (Publico publico : lista) {
                listaDTO.add(publico.getDTO());
            }
            return listaDTO;
        } else {
            return null;
        }
    }

    public PublicoDTO findByID(Integer id) {
        return publicoRepository.findById(id).get().getDTO();
    }

    public EventoDTO getEvento(PublicoDTO pub) {
        Publico publico = publicoRepository.findById(pub.getId()).get();
        return publico.getEvento().getDTO();
    }

    public List<PublicoDTO> findByUsuario(UsuarioDeEventosDTO us) {
        UsuarioDeEventos usuarioDeEventos = usuarioDeEventosRepository.findById(us.getId()).get();
        return convertirAListaDTO(usuarioDeEventos.getPublicoList());
    }

    public List<PublicoDTO> findByEvento(EventoDTO ev) {
        Evento evento = eventoRepository.findById(ev.getId()).get();
        return convertirAListaDTO(evento.getPublicoList());
    }

    public List<PublicoDTO> findByUsuarioYEvento(UsuarioDeEventosDTO us, EventoDTO ev) {
        UsuarioDeEventos usuarioDeEventos = usuarioDeEventosRepository.findById(us.getId()).get();
        Evento evento = eventoRepository.findById(ev.getId()).get();
        List<PublicoDTO> publicos = convertirAListaDTO(publicoRepository.findByUsuarioYEvento(usuarioDeEventos, evento));
        return publicos;
    }

    public void createAsientosAsignados(EventoDTO evento, UsuarioDeEventosDTO usuarioDeEventos, int fila, int asiento) {
        Publico publico = new Publico();
        Evento ev = eventoRepository.findById(evento.getId()).get();
        publico.setEvento(ev);
        UsuarioDeEventos us = usuarioDeEventosRepository.findById(usuarioDeEventos.getId()).get();
        publico.setUsuarioDeEventos(us);
        publico.setFila(fila);
        publico.setAsiento(asiento);
        publicoRepository.save(publico);
        eventoService.addPublico(ev, publico);
        usuarioDeEventosService.addPublico(us, publico);
    }

    public void createSinAsientos(EventoDTO evento, UsuarioDeEventosDTO usuarioDeEventos) {
        Publico publico = new Publico();
        Evento ev = eventoRepository.findById(evento.getId()).get();
        publico.setEvento(ev);
        UsuarioDeEventos us = usuarioDeEventosRepository.findById(usuarioDeEventos.getId()).get();
        publico.setUsuarioDeEventos(us);
        publicoRepository.save(publico);
        eventoService.addPublico(ev, publico);
        usuarioDeEventosService.addPublico(us, publico);
    }

    public void borrar(PublicoDTO pub) {
        Publico publico = publicoRepository.findById(pub.getId()).get();
        publicoRepository.delete(publico);
        eventoService.removePublico(publico.getEvento(), publico);
        usuarioDeEventosService.removePublico(publico.getUsuarioDeEventos(), publico);
    }
}
