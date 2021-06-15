package es.taw.sampletaw.controller;

import es.taw.sampletaw.dto.EtiquetaDTO;
import es.taw.sampletaw.dto.EventoDTO;
import es.taw.sampletaw.dto.UsuarioDTO;
import es.taw.sampletaw.service.EtiquetaService;
import es.taw.sampletaw.service.EventoService;
import es.taw.sampletaw.service.UsuarioDeEventosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

    private UsuarioDeEventosService usuarioDeEventosService;
    private EtiquetaService etiquetaService;
    private EventoService eventoService;

    @Autowired
    public void setUsuarioDeEventosService(UsuarioDeEventosService usuarioDeEventosService) {
        this.usuarioDeEventosService = usuarioDeEventosService;
    }

    @Autowired
    public void setEtiquetaService(EtiquetaService etiquetaService) {
        this.etiquetaService = etiquetaService;
    }

    @Autowired
    public void setEventoService(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @GetMapping("/registro")
    public String doCrearUsuario() {
        return "crearEditarUsuarioDeEventos";
    }

    @PostMapping("/registro/guardar")
    public String doGuardarUsuario() {
        //TODO: ServletCrearUsuarioDeEventos
        return null;
    }

    @GetMapping("/home")
    public String doMostrarHome(Model model, HttpSession session) {
        EtiquetaDTO etiqueta = etiquetaService.findByNombreExacto(usuarioDeEventosService.findById(((UsuarioDTO)session.getAttribute("usuario")).getUsuarioDeEventos()).getCiudad());
        if (etiqueta != null) {
            List<EventoDTO> listaEnCiudad = this.eventoService.findByDisponiblesEtiqueta(etiqueta);
            model.addAttribute("listaEnCiudad", listaEnCiudad);
        } else {
            model.addAttribute("listaEnCiudad", null);
        }
        List<EventoDTO> listaPopulares = this.eventoService.findByDisponiblesMasPopulares();
        model.addAttribute("listaPopulares", listaPopulares);
        List<EventoDTO> listaProximos = this.eventoService.findByDisponiblesMasCercanos();
        model.addAttribute("listaProximos", listaProximos);
        return "homeUsuarioDeEventos";
    }

    @GetMapping("/explorar")
    public String doMostrarExplorar(Model model, HttpSession session) {
        //TODO: ServletPaginacionEventos
        return "BusquedaHomeUsuarioDeEventos";
    }

    @GetMapping("/explorar/reservas")
    public String doMostrarReservas(Model model, HttpSession session) {
        //TODO: ServletPaginacionReservas
        return "busquedaReservasUsuarioDeEventos";
    }

    @GetMapping("/explorar/historial")
    public String doMostrarHistorial(Model model, HttpSession session) {
        //TODO: ServletPaginacionHistorial
        return "busquedaHistorialUsuarioDeEventos";
    }

    @GetMapping("/reserva/{id_evento}")
    public String doMostrarEvento(Model model, HttpSession session) {
        //TODO: ServletUnirseEvento
        return "reservaEvento";
    }

    @PostMapping("/solicitud")
    public String doGuardarReserva(Model model, HttpSession session) {
        //TODO: ServletSolicitarEntrada
        return "redirect:/reserva/" + id_evento;
    }

    @GetMapping("/cancelar/{id_evento}")
    public String doCancelarReserva(Model model, HttpSession session) {
        //TODO: ServletCancelarEntrada
        return "redirect:/reserva/" + id_evento;
    }
}
