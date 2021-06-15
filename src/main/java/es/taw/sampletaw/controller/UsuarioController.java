package es.taw.sampletaw.controller;

import es.taw.sampletaw.dto.EtiquetaDTO;
import es.taw.sampletaw.dto.EventoDTO;
import es.taw.sampletaw.dto.UsuarioDTO;
import es.taw.sampletaw.service.EtiquetaService;
import es.taw.sampletaw.service.EventoService;
import es.taw.sampletaw.service.UsuarioDeEventosService;
import es.taw.sampletaw.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("usuario")
public class UsuarioController {

    private UsuarioDeEventosService usuarioDeEventosService;
    private EtiquetaService etiquetaService;
    private EventoService eventoService;
    private UsuarioService usuarioService;


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

    @Autowired
    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/registro")
    public String doCrearUsuario() {
        return "crearEditarUsuarioDeEventos";
    }

    @PostMapping("/registro/guardar")
    public String doGuardarUsuario(@RequestParam("nick") String nick, @RequestParam("correoElectronico") String correoElectronico,
                                   @RequestParam("contrasena") String contrasena, @RequestParam("confirmarContrasena") String confirmarContrasena,
                                   @RequestParam("nombre") String nombre, @RequestParam("apellidos") String apellidos,
                                   @RequestParam("ciudad") String ciudad, @RequestParam("sexo") String sexo,
                                   @RequestParam("fechaNacimiento") String fechaNacimientoString,Model model, HttpSession session) {
        Date currentDate = new Date();

        String strErrorNick = "", strErrorCorreo = "", strErrorFormato = "", strErrorConfirmar = "", strErrorNacimiento = "";
        Date fechaNacimiento = null;

        // ----------------------

        try {
            fechaNacimiento = new SimpleDateFormat("yyyy-MM-dd").parse(fechaNacimientoString);
        } catch (ParseException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // ----------------------

        // ----------------------

        Pattern regexPattern = Pattern.compile("^(.+)@(.+)$");
        Matcher regMatcher   = regexPattern.matcher(correoElectronico);

        Boolean nickUnico = usuarioService.esNickUnico(nick);
        Boolean correoUnico = usuarioDeEventosService.esCorreoUnico(correoElectronico);
        Boolean formatoCorreoValido = regMatcher.matches();
        Boolean coincidenContrasenas = contrasena.equals(confirmarContrasena);
        Boolean fechaNacimientoCorrecta = currentDate.after(fechaNacimiento);

        if (!nickUnico || !correoUnico || !formatoCorreoValido || !coincidenContrasenas || !fechaNacimientoCorrecta) { // Si hay algun error en el formulario
            if (!nickUnico) { // Si ya hay un usuario con ese nick
                strErrorNick = "El nick introducido ya está en uso";
                model.addAttribute("errorNick", strErrorNick);
            }
            if (!correoUnico) { // Si ya hay un usuario con ese correo
                strErrorCorreo = "El correo electrónico elegido ya está en uso";
                model.addAttribute("errorCorreo", strErrorCorreo);
            }
            if (!formatoCorreoValido) { // Si el correo no tiene un formato valido
                strErrorFormato = "El correo electrónico debe tener un formato válido";
                model.addAttribute("errorFormato", strErrorFormato);
            }
            if (!coincidenContrasenas) { // Si la confirmacion de contrasena es diferente a la contrasena
                strErrorConfirmar = "La contraseñas introducidas no coinciden";
                model.addAttribute("errorConfirmar", strErrorConfirmar);
            }
            if (!fechaNacimientoCorrecta) {
                strErrorNacimiento = "Fecha de nacimiento inválida";
                model.addAttribute("errorNacimiento", strErrorNacimiento);
            }

            return "crearEditarUsuarioDeEventos";

        } else { // Si no hay errores

            usuarioService.guardarUsuario(null, nick, contrasena, "usuariodeeventos", nombre, apellidos, correoElectronico, ciudad, sexo, fechaNacimiento);

            return "redirect:/";
        }
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
        return "busquedaHomeUsuarioDeEventos";
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
    public String doGuardarReserva(@PathVariable("id_evento") String id_evento, Model model, HttpSession session) {
        //TODO: ServletSolicitarEntrada
        return "redirect:/reserva/" + id_evento;
    }

    @GetMapping("/cancelar/{id_evento}")
    public String doCancelarReserva(@PathVariable("id_evento") String id_evento, Model model, HttpSession session) {
        //TODO: ServletCancelarEntrada
        return "redirect:/reserva/" + id_evento;
    }
}
