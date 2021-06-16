package es.taw.sampletaw.controller;

import es.taw.sampletaw.dto.EtiquetaDTO;
import es.taw.sampletaw.dto.EventoDTO;
import es.taw.sampletaw.dto.UsuarioDTO;
import es.taw.sampletaw.dto.UsuarioDeEventosDTO;
import es.taw.sampletaw.service.EtiquetaService;
import es.taw.sampletaw.service.EventoService;
import es.taw.sampletaw.service.UsuarioDeEventosService;
import es.taw.sampletaw.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("admin")
public class AdminController {

    private UsuarioService usuarioService;
    private EventoService eventoService;
    private EtiquetaService etiquetaService;
    private UsuarioDeEventosService usuarioDeEventosService;

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

    @GetMapping("/home")
    public String goHome(Model model, HttpSession session) {
        return "homeAdmin";
    }

    @GetMapping("/usuarios")
    public String goUsuarios(@RequestParam(value = "tipoFiltro", required = false) String tipoFiltro, @RequestParam(value = "filtro", required = false) String filtro,  Model model) {

        List<UsuarioDTO> lista;

        if(filtro != null){
            try {
                filtro = new String(filtro.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        if ((filtro != null && filtro.length()>0 )) {
            if(tipoFiltro.equals("fNick")){
                lista = this.usuarioService.filtroNick(filtro);
            }else if(tipoFiltro.equals("fContrasena")){
                lista = this.usuarioService.filtroContrasena(filtro);
            }else{
                lista = this.usuarioService.filtroTipoUsuario(filtro);
            }

        }else{
            lista = this.usuarioService.TodosUsuarios();
        }

        model.addAttribute("lista", lista);

        return "listaUsuarios";
    }

    @GetMapping("/eventos")
    public String goEventos(@RequestParam(value = "tipoFiltro", required = false) String tipoFiltro, @RequestParam(value = "filtroT", required = false) String filtroT, @RequestParam(value = "filtroN", required = false) String filtroN, @RequestParam(value = "filtroF", required = false) String filtroF, @RequestParam(value = "filtroC", required = false) String filtroC, Model model) {

        List<EventoDTO> lista;
        List<UsuarioDTO> l = this.usuarioService.TodosUsuarios();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        if(filtroT != null){
            try {
                filtroT = new String(filtroT.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        if ((filtroT != null && filtroT.length()>0 ) || (filtroN != null && filtroN.length()>0 ) || (filtroF != null && filtroF.length()>0 ) || (filtroC != null && filtroC.length()>0 )) {
            if(tipoFiltro.equals("fNombre")){
                lista = this.eventoService.filtroNombre(filtroT);
            }else if(tipoFiltro.equals("fDescripcion")){
                lista = this.eventoService.filtroDescripcion(filtroT);
            }else if(tipoFiltro.equals("fFecha")){
                Date fecha = null;
                try {
                    fecha = format.parse(filtroF);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                lista = this.eventoService.filtroFecha(fecha);
            }else if(tipoFiltro.equals("fFechaEntrada")){
                Date fecha = null;
                try {
                    fecha = format.parse(filtroF);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                lista = this.eventoService.filtroFechaEntrada(fecha);
            }else if(tipoFiltro.equals("fPrecio")){
                lista = this.eventoService.filtroPrecio(filtroN);
            }else if(tipoFiltro.equals("fAforo")){
                lista = this.eventoService.filtroAforo(filtroN);
            }else if(tipoFiltro.equals("fMaxEntradasUsuario")){
                lista = this.eventoService.filtroMaxEntradasUsuario(filtroN);
            }else if(tipoFiltro.equals("fAsientosAsignados")){
                lista = this.eventoService.filtroAsientosAsignados(filtroC);
            }else if(tipoFiltro.equals("fNumFilas")){
                lista = this.eventoService.filtroNumFilas(filtroN);
            }else if(tipoFiltro.equals("fAsientosFila")){
                lista = this.eventoService.filtroAsientosFila(filtroN);
            }else if(tipoFiltro.equals("fCreador")){
                lista = this.eventoService.filtroCreador(filtroT);
            }else if(tipoFiltro.equals("fEtiqueta")){
                EtiquetaDTO e = this.etiquetaService.filtroNombre(filtroT);

                if(e == null){
                    lista = new ArrayList();
                }else{
                    lista = this.eventoService.convertirAListaDTOdirectamente(e.getEventoList());
                }
            }else{
                lista = this.eventoService.filtroImagen(filtroT);
            }

        }else{
            lista = this.eventoService.TodosEventos();
        }

        model.addAttribute("listaEv", lista);
        model.addAttribute("listaUs", l);
        return "listaEventos";
    }

    @GetMapping("/borrarusuario/{id}")
    public String doBorrarUs (@PathVariable("id") String id, HttpSession session, Model model) {
        UsuarioDTO usuario = (UsuarioDTO)session.getAttribute("usuario");
        if (usuario == null) {
            model.addAttribute("errorRegistro", "Usuario no autenticado");
            return ("redirect:/");
        } else {

            UsuarioDTO elUsuario = this.usuarioService.buscarUsuario(Integer.parseInt(id));
            if(usuario.getNickname().equals(elUsuario.getNickname())){
                this.usuarioService.borrarUsuario(Integer.parseInt(id));
                session.invalidate();
                return("redirect:/");
            }else{
                this.usuarioService.borrarUsuario(Integer.parseInt(id));
                return ("redirect:/admin/usuarios");
            }
        }
    }

    @GetMapping("/editarusuario/{id}")
    public String doEditarUs (@PathVariable("id") String id, HttpSession session, Model model) {
        UsuarioDTO usuario = (UsuarioDTO)session.getAttribute("usuario");
        String strTo = "crearEditarUsuario";
        if (usuario == null) {
            model.addAttribute("errorRegistro", "Usuario no autenticado");
            return ("redirect:/");
        } else {

            UsuarioDTO us = this.usuarioService.buscarUsuario(Integer.parseInt(id));
            model.addAttribute("usuarioEditar", us);
            if(us.getTipoUsuario().equals("usuariodeeventos")){

                UsuarioDeEventosDTO usEv = this.usuarioDeEventosService.findById(us.getUsuarioDeEventos()).getDTO();
                model.addAttribute("usuarioEventoEditar", usEv);
                strTo = "editarUsuarioDeEventos";
            }



        }
        return strTo;
    }

    @GetMapping("/guardarusuario")
    public String doGuardarUs (@RequestParam(value = "id", required = false) String id, @RequestParam("nick") String strNick, @RequestParam("contrasena") String strClave, @RequestParam("confirmarContrasena") String strClaveConf, @RequestParam("tipoUsuario") String tipoUsuario, HttpSession session, Model model) {
        String strError = "";
        UsuarioDTO usuarioNuevo;


        if(strNick != null){
            try {
                strNick = new String(strNick.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        UsuarioDTO usuario = (UsuarioDTO)session.getAttribute("usuario");
        String strTo = "crearEditarUsuario";
        if (usuario == null) {
            model.addAttribute("errorRegistro", "Usuario no autenticado");
            return ("redirect:/");
        }
            if (strNick == null || strNick.isEmpty() ||
                    strClave == null || strClave.isEmpty()) {

                strError = "Error de creación: alguno de los valores está vacío";
                model.addAttribute("errorRegistro", strError);

                return ("crearEditarUsuario");

            } else {
                if (id == null || id.isEmpty()) { // Crear nuevo cliente

                    UsuarioDTO i = this.usuarioService.buscarUsuarioNick(strNick);
                    if (i == null) { // No existe usuario con ese nick
                        if(!strClave.equals(strClaveConf)){
                            strError = "Error de creación: las contraseñas son diferentes";
                            model.addAttribute("errorContra", strError);
                            return ("crearEditarUsuario");
                        }else{
                            this.usuarioService.guardarUsuario(id, strNick, strClave, tipoUsuario);
                            return("redirect:/admin/usuarios");
                        }


                    } else { // Existe nick con ese id
                        strError = "Error de creación: este nick está cogido";
                        model.addAttribute("errorNick", strError);
                        return ("crearEditarUsuario");
                    }

                } else { // Editar cliente existente
                    usuarioNuevo = this.usuarioService.buscarUsuario(Integer.parseInt(id));
                    if(!strClave.equals(strClaveConf)){
                        strError = "Error de creación: las contraseñas son diferentes";
                        model.addAttribute("errorContra", strError);
                        model.addAttribute("usuarioEditar", usuarioNuevo);
                        return ("crearEditarUsuario");
                    }else{
                        UsuarioDTO i = this.usuarioService.buscarUsuarioNick(strNick);
                        if(i == null || i.getId().equals(usuarioNuevo.getId())){
                            this.usuarioService.guardarUsuario(id, strNick, strClave, tipoUsuario);
                            return("redirect:/admin/usuarios");
                        }else{
                            strError = "Error de creación: este nick está cogido";
                            model.addAttribute("errorNick", strError);
                            model.addAttribute("usuarioEditar", usuarioNuevo);
                            return ("crearEditarUsuario");
                        }
                    }

                }
            }
        }

    @GetMapping("/crearusuario")
    public String doCrearUs ( HttpSession session, Model model) {
        UsuarioDTO usuario = (UsuarioDTO)session.getAttribute("usuario");
        if (usuario == null) {
            model.addAttribute("errorRegistro", "Usuario no autenticado");
            return ("redirect:/");
        } else {
            return "crearEditarUsuario";
        }
    }

    @GetMapping("/guardarusuarioeventos")
    public String doGuardarUsEv (@RequestParam("id") String id, @RequestParam("nick") String nick, @RequestParam("correoElectronico") String correoElectronico, @RequestParam("contrasena") String contrasena, @RequestParam("confirmarContrasena") String confirmarContrasena, @RequestParam("nombre") String nombre, @RequestParam("apellidos") String apellidos, @RequestParam("ciudad") String ciudad, @RequestParam("sexo") String sexo, @RequestParam("fechaNacimiento") String fechaNacimien, HttpSession session, Model model) {
        UsuarioDTO usuario = (UsuarioDTO)session.getAttribute("usuario");
        if (usuario == null) {
            model.addAttribute("errorRegistro", "Usuario no autenticado");
            return ("redirect:/");
        } else {
            String strErrorNick = "", strErrorCorreo = "", strErrorFormato = "", strErrorConfirmar = "", strTo = "";

            // ----------------------

            // Asignamos valores

            if(nick != null){
                try {
                    nick = new String(nick.getBytes("ISO-8859-1"), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            if(nombre != null){
                try {
                    nombre = new String(nombre.getBytes("ISO-8859-1"), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            if(apellidos != null){
                try {
                    apellidos = new String(apellidos.getBytes("ISO-8859-1"), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            if(ciudad != null){
                try {
                    ciudad = new String(ciudad.getBytes("ISO-8859-1"), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            Date fechaNacimiento = null;
            try {
                fechaNacimiento = new SimpleDateFormat("yyyy-MM-dd").parse(fechaNacimien);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // ----------------------

            // ----------------------

            Pattern regexPattern = Pattern.compile("^(.+)@(.+)$");
            Matcher regMatcher   = regexPattern.matcher(correoElectronico);

            UsuarioDTO usuarioBase = this.usuarioService.buscarUsuario(Integer.parseInt(id));
            UsuarioDeEventosDTO usuarioEventos = this.usuarioDeEventosService.findById(usuarioBase.getUsuarioDeEventos()).getDTO();

            Boolean nickUnico = usuarioService.esNickUnico(nick);
            Boolean correoUnico = usuarioDeEventosService.esCorreoUnico(correoElectronico);
            Boolean formatoCorreoValido = regMatcher.matches();
            Boolean coincidenContrasenas = contrasena.equals(confirmarContrasena);
            if(fechaNacimiento.after(new Date())){
                model.addAttribute("errorFecha", "Error de edición: fecha no válida");
                model.addAttribute("usuarioEventoEditar", usuarioEventos);
                model.addAttribute("usuarioEditar", usuarioBase);
                return ("editarUsuarioDeEventos");
            }else{
                if ((!nickUnico && !nick.equals(usuarioBase.getNickname())) || (!correoUnico && !correoElectronico.equals(usuarioEventos.getCorreo()))  || !formatoCorreoValido || !coincidenContrasenas) { // Si hay algun error en el formulario
                    model.addAttribute("usuarioEventoEditar", usuarioEventos);
                    model.addAttribute("usuarioEditar", usuarioBase);
                    strTo = "editarUsuarioDeEventos";
                    if (!nickUnico && !nick.equals(usuarioBase.getNickname())) { // Si ya hay un usuario con ese nick
                        strErrorNick = "Error de edición: el nick introducido ya está en uso";
                        model.addAttribute("errorNick", strErrorNick);
                    }
                    if (!correoUnico && !correoElectronico.equals(usuarioEventos.getCorreo())) { // Si ya hay un usuario con ese correo
                        strErrorCorreo = "Error de edición: el correo electrónico elegido ya está en uso";
                        model.addAttribute("errorCorreo", strErrorCorreo);
                    }
                    if (!formatoCorreoValido) { // Si el correo no tiene un formato valido
                        strErrorFormato = "Error de edición: el correo electrónico debe tener un formato válido";
                        model.addAttribute("errorFormato", strErrorFormato);
                    }
                    if (!coincidenContrasenas) { // Si la confirmacion de contrasena es diferente a la contrasena
                        strErrorConfirmar = "Error de edición: las contraseñas introducidas no coinciden";
                        model.addAttribute("errorConfirmar", strErrorConfirmar);
                    }
                    return(strTo);

                } else { // Si no hay errores
                    this.usuarioService.editarUsuario(usuarioBase.getId().toString(),nick,contrasena,usuarioBase.getTipoUsuario(), nombre, apellidos, correoElectronico, ciudad, sexo, fechaNacimiento);
                    return("redirect:/admin/usuarios");
                }
            }

        }
    }

    @GetMapping("/borrarevento/{idE}")
    public String doBorrarEv (@PathVariable("idE") String idE, HttpSession session, Model model) {
        UsuarioDTO usuario = (UsuarioDTO)session.getAttribute("usuario");
        if (usuario == null) {
            model.addAttribute("errorRegistro", "Usuario no autenticado");
            return ("redirect:/");
        } else {
            EventoDTO elEvento = this.eventoService.buscarEvento(Integer.parseInt(idE));
            this.eventoService.borrarEvento(elEvento);
            return ("redirect:/admin/eventos");
        }
    }

    @GetMapping("/editarevento/{idE}")
    public String doEditarEv (@PathVariable("idE") String idE, HttpSession session, Model model) {
        UsuarioDTO usuario = (UsuarioDTO)session.getAttribute("usuario");
        String strTo = "crearEditarUsuario";
        if (usuario == null) {
            model.addAttribute("errorRegistro", "Usuario no autenticado");
            return ("redirect:/");
        } else {
            if (idE != null) { // Es editar usuario
                EventoDTO us = this.eventoService.buscarEvento(Integer.parseInt(idE));
                model.addAttribute("eventoEditar", us);
                String etiquetas = this.eventoService.getEtiquetasToString(us);
                model.addAttribute("etiquetas", etiquetas);
            }
        }
        return ("editarEventoAdmin");
    }

    @GetMapping("/guardarevento")
    public String doGuardarEv (@RequestParam(value = "id", required = false) String eventoId, @RequestParam("titulo") String titulo, @RequestParam("descripcion") String descripcion, @RequestParam("precio") String precio, @RequestParam("imagen") String imagen, @RequestParam("etiquetas") String etiquetas, @RequestParam("fecha") String fech, @RequestParam("fecha_limite_entradas") String fechLim, @RequestParam("aforo_maximo") String aforo_maximo, @RequestParam("maximo_entradas_usuario") String maximo_entradas_usuario, @RequestParam("asientos_asignados") String asientos_asignados, @RequestParam("numero_filas") String numero_filas, @RequestParam("asientos_por_fila") String asientos_por_fila, HttpSession session, Model model) {
        UsuarioDTO usuario = (UsuarioDTO)session.getAttribute("usuario");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if(titulo != null){
            try {
                titulo = new String(titulo.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if(descripcion != null){
            try {
                descripcion = new String(descripcion.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        if(etiquetas != null){
            try {
                etiquetas = new String(etiquetas.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        Date fecha = null;
        try {
            fecha = format.parse(fech);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date fecha_limite_entradas = null;
        try {
            fecha_limite_entradas = format.parse(fechLim);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(eventoId != null){
            EventoDTO eventoEditar = eventoService.buscarEvento(Integer.parseInt(eventoId));
            if(fecha.before(fecha_limite_entradas)){
                model.addAttribute("eventoEditar", eventoEditar);
                String etiquetass = this.eventoService.getEtiquetasToString(eventoEditar);
                model.addAttribute("etiquetas", etiquetass);
                model.addAttribute("errorRegistro", "Error de edición: fechas no válidas");
                return("editarEventoAdmin");
            }else{
                eventoService.editarEvento(Integer.parseInt(eventoId), usuario.getId(), titulo, descripcion, etiquetas, Integer.parseInt(precio), imagen, fecha, fecha_limite_entradas, Integer.parseInt(aforo_maximo), Integer.parseInt(maximo_entradas_usuario), asientos_asignados, numero_filas, asientos_por_fila);
                return ("redirect:/admin/eventos");
            }

        }else{

            if(fecha.before(fecha_limite_entradas)){
                model.addAttribute("errorRegistro", "Error de creación: fechas no válidas");
                return("crearEventoAdmin");
            }else{
                eventoService.crearEvento(usuario.getId(), titulo, descripcion, etiquetas, Integer.parseInt(precio), imagen, fecha, fecha_limite_entradas, Integer.parseInt(aforo_maximo), Integer.parseInt(maximo_entradas_usuario), asientos_asignados, numero_filas, asientos_por_fila);
                return ("redirect:/admin/eventos");
            }

        }
    }

    @GetMapping("/crearevento")
    public String doCrearEv ( HttpSession session, Model model) {
        UsuarioDTO usuario = (UsuarioDTO)session.getAttribute("usuario");
        String strTo = "crearEditarUsuario";
        if (usuario == null) {
            model.addAttribute("errorRegistro", "Usuario no autenticado");
            return ("redirect:/");
        } else {
            return "crearEventoAdmin";
        }
    }

}


