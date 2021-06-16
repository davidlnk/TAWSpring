package es.taw.sampletaw.controller;

import es.taw.sampletaw.dto.*;
import es.taw.sampletaw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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
    private PublicoService publicoService;


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

    @Autowired
    public void setPublicoService(PublicoService publicoService) {
        this.publicoService = publicoService;
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
            nombre = new String(nombre.getBytes("ISO-8859-1"), "UTF-8");
            apellidos = new String(apellidos.getBytes("ISO-8859-1"), "UTF-8");
            ciudad = new String(ciudad.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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

    @GetMapping("/explorar/{pagina}")
    public String doMostrarExplorar(@RequestParam(required = false) String busqueda, @PathVariable("pagina") String pagina, Model model, HttpSession session) {

        Set<EventoDTO> setEventos = new HashSet<>();
        List<EventoDTO> listaEventos;

        if (busqueda != null) {
            try {
                busqueda = new String(busqueda.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            model.addAttribute("ultimaBusqueda", busqueda);
            String palabra;
            EtiquetaDTO etiqueta;
            try (Scanner sc = new Scanner(busqueda)){
                while (sc.hasNext()) {
                    palabra = sc.next();
                    setEventos.addAll(eventoService.findByTitulo(palabra));
                    etiqueta = etiquetaService.findByNombreExacto(palabra);
                    if (etiqueta != null) {
                        setEventos.addAll(eventoService.findByEventosDeEtiqueta(etiqueta));
                    }
                }
            }
            listaEventos = new ArrayList<>(setEventos);
        } else {
            listaEventos = eventoService.findAll();
        }

        Integer pageid;
        if (pagina == null) {
            pageid = 1;
        } else {
            pageid = new Integer(pagina);
        }

        model.addAttribute("pagina", pageid);
        if ((pageid - 1) * 9 + 9 < listaEventos.size()) {
            List<EventoDTO> listaEventosPagina = listaEventos.subList((pageid - 1) * 9, (pageid - 1) * 9 + 9);
            Map<EventoDTO, List<PublicoDTO>> map = eventoService.construirMap(listaEventosPagina);
            model.addAttribute("listaEventosPagina", map);
            model.addAttribute("pagfinal", false);
        } else {
            List<EventoDTO> listaEventosPagina = listaEventos.subList((pageid - 1) * 9, listaEventos.size());
            Map<EventoDTO, List<PublicoDTO>> map = eventoService.construirMap(listaEventosPagina);
            model.addAttribute("listaEventosPagina", map);
            model.addAttribute("pagfinal", true);
        }
        return "busquedaHomeUsuarioDeEventos";
    }

    @GetMapping("/explorar/reservas/{pagina}")
    public String doMostrarReservas(@RequestParam(required = false) String busqueda, @PathVariable("pagina") String pagina, Model model, HttpSession session) {

        UsuarioDeEventosDTO usuarioDeEventos = usuarioService.getUsuarioDeEventos((UsuarioDTO)session.getAttribute("usuario"));
        Set<EventoDTO> setEventos = new HashSet<>();
        List<EventoDTO> listaEventos;

        if (busqueda != null) {
            try {
                busqueda = new String(busqueda.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            model.addAttribute("ultimaBusqueda", busqueda);
            String palabra;
            EtiquetaDTO etiqueta;
            try (Scanner sc = new Scanner(busqueda)){
                while (sc.hasNext()) {
                    palabra = sc.next();
                    setEventos.addAll(eventoService.findByTituloReserva(palabra, usuarioDeEventos));
                    etiqueta = etiquetaService.findByNombreExacto(palabra);
                    if (etiqueta != null) {
                        setEventos.addAll(eventoService.findByEtiquetaReserva(etiqueta, usuarioDeEventos));
                    }
                }
            }
            listaEventos = new ArrayList<>(setEventos);
        } else {
            listaEventos = this.eventoService.findAllReserva(usuarioDeEventos);
        }

        Integer pageid;
        if (pagina == null) {
            pageid = 1;
        } else {
            pageid = new Integer(pagina);
        }

        model.addAttribute("pagina", pageid);
        if ((pageid - 1) * 9 + 9 < listaEventos.size()) {
            List<EventoDTO> listaEventosPagina = listaEventos.subList((pageid - 1) * 9, (pageid - 1) * 9 + 9);
            Map<EventoDTO, List<PublicoDTO>> map = eventoService.construirMap(listaEventosPagina);
            model.addAttribute("listaEventosPagina", map);
            model.addAttribute("pagfinal", false);
        } else {
            List<EventoDTO> listaEventosPagina = listaEventos.subList((pageid - 1) * 9, listaEventos.size());
            Map<EventoDTO, List<PublicoDTO>> map = eventoService.construirMap(listaEventosPagina);
            model.addAttribute("listaEventosPagina", map);
            model.addAttribute("pagfinal", true);
        }
        return "busquedaReservasUsuarioDeEventos";
    }

    @GetMapping("/explorar/historial/{pagina}")
    public String doMostrarHistorial(@RequestParam(required = false) String busqueda, @PathVariable("pagina") String pagina, Model model, HttpSession session) {

        UsuarioDeEventosDTO usuarioDeEventos = usuarioService.getUsuarioDeEventos((UsuarioDTO)session.getAttribute("usuario"));
        Set<EventoDTO> setEventos = new HashSet<>();
        List<EventoDTO> listaEventos;

        if (busqueda != null) {
            try {
                busqueda = new String(busqueda.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            model.addAttribute("ultimaBusqueda", busqueda);
            String palabra;
            EtiquetaDTO etiqueta;
            try (Scanner sc = new Scanner(busqueda)){
                while (sc.hasNext()) {
                    palabra = sc.next();
                    setEventos.addAll(eventoService.findByTituloHistorial(palabra, usuarioDeEventos));
                    etiqueta = etiquetaService.findByNombreExacto(palabra);
                    if (etiqueta != null) {
                        setEventos.addAll(eventoService.findByEtiquetaHistorial(etiqueta, usuarioDeEventos));
                    }
                }
            }
            listaEventos = new ArrayList<>(setEventos);
        } else {
            listaEventos = this.eventoService.findAllHistorial(usuarioDeEventos);
        }

        Integer pageid;
        if (pagina == null) {
            pageid = 1;
        } else {
            pageid = new Integer(pagina);
        }

        model.addAttribute("pagina", pageid);
        if ((pageid - 1) * 9 + 9 < listaEventos.size()) {
            List<EventoDTO> listaEventosPagina = listaEventos.subList((pageid - 1) * 9, (pageid - 1) * 9 + 9);
            Map<EventoDTO, List<PublicoDTO>> map = eventoService.construirMap(listaEventosPagina);
            model.addAttribute("listaEventosPagina", map);
            model.addAttribute("pagfinal", false);
        } else {
            List<EventoDTO> listaEventosPagina = listaEventos.subList((pageid - 1) * 9, listaEventos.size());
            Map<EventoDTO, List<PublicoDTO>> map = eventoService.construirMap(listaEventosPagina);
            model.addAttribute("listaEventosPagina", map);
            model.addAttribute("pagfinal", true);
        }
        return "busquedaHistorialUsuarioDeEventos";
    }

    @GetMapping("/reserva/{id_evento}")
    public String doMostrarEvento(@PathVariable("id_evento") String id_evento, Model model, HttpSession session) {

        EventoDTO evento = eventoService.find(new Integer(id_evento));
        model.addAttribute("evento", evento);
        List<PublicoDTO> publicos = publicoService.findByUsuario(usuarioService.getUsuarioDeEventos((UsuarioDTO)session.getAttribute("usuario")));
        model.addAttribute("publicosUs", publicos);
        List<PublicoDTO> publicoDeEvento = publicoService.findByEvento(evento);
        model.addAttribute("publicosEv", publicoDeEvento);


        boolean [][] ocupados = null;
        if (evento.getAsientosAsignados()) {
            ocupados = new boolean[evento.getNumFilas()][evento.getAsientosPorFila()];
            for (int i = 0; i < evento.getNumFilas(); i++) {
                for (int j = 0; j < evento.getAsientosPorFila(); j++) {
                    ocupados[i][j] = buscarOcupado(publicoDeEvento, i, j);
                }
            }
        }
        model.addAttribute("ocupados", ocupados);
        return "reservaEvento";
    }

    @PostMapping("/solicitud")
    public String doGuardarReserva(@RequestParam("id_evento") String id_evento,
                                   @RequestParam(required = false) String fila, @RequestParam(required = false) String asiento,
                                   Model model, HttpSession session) {

        EventoDTO evento = eventoService.find(new Integer(id_evento));
        UsuarioDeEventosDTO usuarioDeEventos = usuarioService.getUsuarioDeEventos((UsuarioDTO)session.getAttribute("usuario"));

        if (evento.getAsientosAsignados()) {
            publicoService.createAsientosAsignados(evento, usuarioDeEventos, new Integer(fila) - 1, new Integer(asiento) - 1);

        } else {
            publicoService.createSinAsientos(evento, usuarioDeEventos);
        }
        return "redirect:/usuario/reserva/" + id_evento;
    }

    @GetMapping("/cancelar/{id_entrada}")
    public String doCancelarReserva(@PathVariable("id_entrada") String id_entrada, Model model, HttpSession session) {
        PublicoDTO publico = publicoService.findByID(new Integer(id_entrada));
        EventoDTO evento = publicoService.getEvento(publico);
        publicoService.borrar(publico);
        return "redirect:/usuario/reserva/" + evento.getId();
    }

    private boolean buscarOcupado(List<PublicoDTO> publicoList, int i, int j) {
        for (PublicoDTO pub : publicoList) {
            if (pub.getFila() == i && pub.getAsiento() == j) {
                return true;
            }
        }
        return false;
    }
}
