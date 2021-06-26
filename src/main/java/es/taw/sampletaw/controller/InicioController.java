package es.taw.sampletaw.controller;

import es.taw.sampletaw.dto.UsuarioDTO;
import es.taw.sampletaw.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


@Controller
public class InicioController {

    private UsuarioService usuarioService;

    @Autowired
    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }



    @GetMapping("/")
    public String doInit(HttpSession session) {
        String strTo = "inicioSesion";
        UsuarioDTO usuarioRegistrado = (UsuarioDTO) session.getAttribute("usuario");
        if(usuarioRegistrado != null){
            if (usuarioRegistrado.getTipoUsuario().equals("administrador")) {
                strTo = "redirect:admin/home";
            } else if (usuarioRegistrado.getTipoUsuario().equals("creadordeeventos")) {
                strTo = "redirect:creador/";
            } else if (usuarioRegistrado.getTipoUsuario().equals("teleoperador")) {

            } else if (usuarioRegistrado.getTipoUsuario().equals("analistadeeventos")) {
                strTo = "redirect:analista/";
            } else {
                strTo = "redirect:usuario/home";
            }
        }
        return strTo;
    }

    @PostMapping("/autenticar")
    public String doAutenticar(@RequestParam("nick") String nick, @RequestParam("contrasena") String contrasena, Model model, HttpSession session) {
        UsuarioDTO usuario;
        String strError = "", strTo = "";

        UsuarioDTO usuarioRegistrado = (UsuarioDTO) session.getAttribute("usuario");
        if (usuarioRegistrado == null) {
            if (nick == null || nick.isEmpty()
                    || contrasena == null || contrasena.isEmpty()) {  // Error de autenticación por email o clave
                // vacíos o nulos.
                strError = "Error de autenticación: alguno de los valores está vacío";
                model.addAttribute("error", strError);
                strTo = "inicioSesion";

            } else {
                usuario = usuarioService.comprobarCredenciales(nick, contrasena);
                if (usuario == null) { // No hay usuario en la BD
                    strError = "Error de autenticación: credenciales incorrectas";
                    model.addAttribute("error", strError);
                    strTo = "inicioSesion";
                } else { // El usuario está en la BD
                    session.setAttribute("usuario", usuario);
                    if (usuario.getTipoUsuario().equals("administrador")) {
                        strTo = "redirect:admin/home";
                    } else if (usuario.getTipoUsuario().equals("creadordeeventos")) {
                        strTo = "redirect:creador/";
                    } else if (usuario.getTipoUsuario().equals("teleoperador")) {

                    } else if (usuario.getTipoUsuario().equals("analistadeeventos")) {
                        strTo = "redirect:analista/";
                    } else {
                        strTo = "redirect:usuario/home";
                    }
                }
            }
        } else {
            if (usuarioRegistrado.getTipoUsuario().equals("administrador")) {
                strTo = "redirect:admin/home";
            } else if (usuarioRegistrado.getTipoUsuario().equals("creadordeeventos")) {
                strTo = "redirect:creador/";
            } else if (usuarioRegistrado.getTipoUsuario().equals("teleoperador")) {

            } else if (usuarioRegistrado.getTipoUsuario().equals("analistadeeventos")) {
                strTo = "redirect:analista/";
            } else {
                strTo = "redirect:usuario/home";
            }
        }
        return strTo;
    }

    @GetMapping("/salir")
    public String doExit (HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
