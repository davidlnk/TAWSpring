package es.taw.sampletaw.controller;

import es.taw.sampletaw.dto.EstudioDTO;
import es.taw.sampletaw.dto.UsuarioDTO;
import es.taw.sampletaw.dto.UsuarioDeEventosDTO;
import es.taw.sampletaw.service.EstudioService;
import es.taw.sampletaw.vo.FiltroEstudios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("analista")
public class AnalistaController {
    private EstudioService estudioService;

    @Autowired
    public void setEstudioService(EstudioService estudioService) {
        this.estudioService = estudioService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) { // Arregla el form:input type Date
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true, 10));
    }

    @GetMapping("/")
    public String doInit(Model model, HttpSession session) {
        return doListar(model, session, new FiltroEstudios());
    }

    @PostMapping("/listar")
    public String doListar(Model model, HttpSession session, @ModelAttribute("filtro") FiltroEstudios filtro) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");

        if (usuario == null || !usuario.getTipoUsuario().equals("analistadeeventos")) {
            return "/autenticar";
        }

        model.addAttribute("lista", this.estudioService.listar(filtro, usuario));
        model.addAttribute("filtro", filtro);

        return "analistaVerEstudios";
    }

    @GetMapping("/ver/{modo}/{strID}")
    public String doVer(@PathVariable("modo") String modo,
                        @PathVariable("strID") String strID,
                        Model model, HttpSession session) {

        EstudioDTO estudio = null;
        List<String> datos = null;
        Set<String> ciudades = new HashSet<>();


        if (strID.equals("nulo")) {
            estudio = new EstudioDTO((UsuarioDTO) session.getAttribute("usuario"));
        } else {
            estudio = this.estudioService.encontrarEstudioPorID(Integer.parseInt(strID));
        }
        datos = this.estudioService.separarDatos(estudio.getBusqueda());

        if (modo.equals("crear")) {
            //ciudades = this.estudioService.encontrarCiudadesParaCrearEstudio();
            model.addAttribute("ciudades", ciudades);
        } else if (modo.equals("ver")) {
            // Object[0] = List<UsuarioDTO> // Object[1] = List<UsuarioDeEventosDTO>
            Object[] resultados = this.estudioService.analizarBaseDeDatos(datos);
            model.addAttribute("resultados", resultados);
        }

        model.addAttribute("estudio", estudio);
        model.addAttribute("datos", datos);
        model.addAttribute("modo", modo);

        return "analistaEstudio";
    }

    @PostMapping("/guardar")
    public String doGuardar(Model model, HttpSession session) {

        return doInit(model, session);
    }
}