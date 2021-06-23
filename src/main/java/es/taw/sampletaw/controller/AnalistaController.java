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
import java.io.UnsupportedEncodingException;
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
        Set<String> ciudades = null;

        if (strID.equals("nulo")) {
            estudio = new EstudioDTO((UsuarioDTO) session.getAttribute("usuario"));
        } else {
            estudio = this.estudioService.encontrarEstudioPorID(Integer.parseInt(strID));
        }
        datos = this.estudioService.separarDatos(estudio.getBusqueda());

        if (modo.equals("crear")) {
            ciudades = this.estudioService.buscarCiudades();
            model.addAttribute("ciudades", ciudades);
        } else if (modo.equals("ver")) {
            // Object[0] = List<UsuarioDTO> // Object[1] = List<UsuarioDeEventosDTO>
            Object[] resultados = this.estudioService.relizarEstudio(datos);
            model.addAttribute("resultados", resultados);
        }

        model.addAttribute("estudio", estudio);
        model.addAttribute("datos", datos);
        model.addAttribute("modo", modo);

        return "analistaEstudio";
    }

    @GetMapping("/eliminar/{confirmacion}/{strID}")
    public String doEliminar(@PathVariable("confirmacion") String confirmacion,
                             @PathVariable("strID") String strID,
                             Model model, HttpSession session) {

        if (confirmacion == null || strID == null || confirmacion.equals("n")) {
            // No elimino nada
            confirmacion = null;
            strID = null;

        } else if (confirmacion.equals("y")) {
            // Elimino estudio
            this.estudioService.eliminarEstudio(strID);
            confirmacion = null;
            strID = null;

        } else { //confirmacion = "idk";
            // Hay que confirmar si es seguro eliminar el estudio
        }

        model.addAttribute("confirmacion", confirmacion);
        model.addAttribute("strID", strID);

        return doInit(model, session);
    }

    @PostMapping("/guardar")
    public String doGuardar(Model model, HttpSession session,
                            @RequestParam(value = "descripcion") String descripcion,
                            @RequestParam(value = "titulo", required = false) String titulo,
                            @RequestParam(value = "desdeFecha", required = false) String desdeFecha,
                            @RequestParam(value = "hastaFecha", required = false) String hastaFecha,
                            @RequestParam(value = "event_fin", required = false) String event_fin,
                            @RequestParam(value = "asient_asig", required = false) String asient_asig,
                            @RequestParam(value = "aforo_min", required = false) String aforo_min,
                            @RequestParam(value = "aforo_max", required = false) String aforo_max,
                            @RequestParam(value = "sexo", required = false) String sexo,
                            @RequestParam(value = "ciudad", required = false) String ciudad,
                            @RequestParam(value = "edad_min", required = false) String edad_min,
                            @RequestParam(value = "edad_max", required = false) String edad_max,
                            @RequestParam(value = "nombre", required = false) String nombre,
                            @RequestParam(value = "apellidos", required = false) String apellidos,
                            @RequestParam(value = "tipousuario", required = false) String tipousuario) {

        List<String> datos = new ArrayList<>();
        datos.add(titulo); // 0
        datos.add(desdeFecha); // 1
        datos.add(hastaFecha); // 2
        datos.add(event_fin); // 3
        datos.add(asient_asig); // 4
        datos.add(""); // 5
        datos.add(aforo_min); // 6
        datos.add(aforo_max); // 7
        datos.add(sexo); // 8
        datos.add(ciudad); // 9
        datos.add(edad_min); // 10
        datos.add(edad_max); // 11
        datos.add(nombre); // 12
        datos.add(apellidos); // 13
        datos.add(tipousuario); // 14

        datos.add(descripcion); // 15

        try {
            this.estudioService.arreglarDatosYGuardarEstudio(((UsuarioDTO)session.getAttribute("usuario")).getId(), datos);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "redirect:/analista/";
    }
}