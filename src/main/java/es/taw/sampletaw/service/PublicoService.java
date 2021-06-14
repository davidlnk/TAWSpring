/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.taw.sampletaw.service;

import java.util.ArrayList;
import java.util.List;
import es.taw.sampletaw.dto.EventoDTO;
import es.taw.sampletaw.dto.PublicoDTO;
import es.taw.sampletaw.dto.UsuarioDeEventosDTO;
import es.taw.sampletaw.entity.Evento;
import es.taw.sampletaw.entity.Publico;
import es.taw.sampletaw.entity.UsuarioDeEventos;

/**
 *
 * @author David
 */
//@Stateless
public class PublicoService {

//    @EJB
//    private PublicoFacade publicoFacade;
//
//    @EJB
//    private UsuarioDeEventosFacade usuarioDeEventosFacade;
//
//    @EJB
//    private EventoFacade eventoFacade;
//
//    @EJB
//    EventoService eventoService;
//
//    @EJB
//    UsuarioDeEventosService usuarioDeEventosService;
//
//    public static List<PublicoDTO> convertirAListaDTO(List<Publico> lista) {
//        if (lista != null) {
//            List<PublicoDTO> listaDTO = new ArrayList<>();
//            for (Publico publico : lista) {
//                listaDTO.add(publico.getDTO());
//            }
//            return listaDTO;
//        } else {
//            return null;
//        }
//    }
//
//    public PublicoDTO findByID(Integer id) {
//        return publicoFacade.findByID(id).getDTO();
//    }
//
//    public EventoDTO getEvento(PublicoDTO pub) {
//        Publico publico = publicoFacade.findByID(pub.getId());
//        return publico.getEvento().getDTO();
//    }
//
//    public List<PublicoDTO> findByUsuario(UsuarioDeEventosDTO us) {
//        UsuarioDeEventos usuarioDeEventos = usuarioDeEventosFacade.findById(us.getId());
//        return convertirAListaDTO(usuarioDeEventos.getPublicoList());
//    }
//
//    public List<PublicoDTO> findByEvento(EventoDTO ev) {
//        Evento evento = eventoFacade.findById(ev.getId());
//        return convertirAListaDTO(evento.getPublicoList());
//    }
//
//    public List<PublicoDTO> findByUsuarioYEvento(UsuarioDeEventosDTO us, EventoDTO ev) {
//        UsuarioDeEventos usuarioDeEventos = usuarioDeEventosFacade.findById(us.getId());
//        Evento evento = eventoFacade.findById(ev.getId());
//        List<PublicoDTO> publicos = convertirAListaDTO(publicoFacade.findByUsuarioYEvento(usuarioDeEventos, evento));
//        return publicos;
//    }
//
//    public void createAsientosAsignados(EventoDTO evento, UsuarioDeEventosDTO usuarioDeEventos, int fila, int asiento) {
//        Publico publico = new Publico();
//        Evento ev = eventoFacade.findById(evento.getId());
//        publico.setEvento(ev);
//        UsuarioDeEventos us = usuarioDeEventosFacade.findById(usuarioDeEventos.getId());
//        publico.setUsuarioDeEventos(us);
//        publico.setFila(fila);
//        publico.setAsiento(asiento);
//        publicoFacade.create(publico);
//        eventoService.addPublico(ev, publico);
//        usuarioDeEventosService.addPublico(us, publico);
//    }
//
//    public void createSinAsientos(EventoDTO evento, UsuarioDeEventosDTO usuarioDeEventos) {
//        Publico publico = new Publico();
//        Evento ev = eventoFacade.findById(evento.getId());
//        publico.setEvento(ev);
//        UsuarioDeEventos us = usuarioDeEventosFacade.findById(usuarioDeEventos.getId());
//        publico.setUsuarioDeEventos(us);
//        publicoFacade.create(publico);
//        eventoService.addPublico(ev, publico);
//        usuarioDeEventosService.addPublico(us, publico);
//    }
//
//    public void borrar(PublicoDTO pub) {
//        Publico publico = publicoFacade.findByID(pub.getId());
//        publicoFacade.remove(publico);
//        eventoService.removePublico(publico.getEvento(), publico);
//        usuarioDeEventosService.removePublico(publico.getUsuarioDeEventos(), publico);
//    }
}
