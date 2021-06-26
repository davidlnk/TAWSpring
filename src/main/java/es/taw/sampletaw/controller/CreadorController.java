package es.taw.sampletaw.controller;

import es.taw.sampletaw.dto.EtiquetaDTO;
import es.taw.sampletaw.dto.EventoDTO;
import es.taw.sampletaw.dto.UsuarioDTO;
import es.taw.sampletaw.service.EtiquetaService;
import es.taw.sampletaw.service.EventoService;
import es.taw.sampletaw.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("creador")
public class CreadorController {
    private EtiquetaService etiquetaService;
    private EventoService eventoService;
    private UsuarioService usuarioService;

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

    @GetMapping("/")
    public String doInit(Model model, HttpSession session) {
        return doListar(null, model, session);
    }

    @PostMapping("/listar")
    public String doListar(@RequestParam("filtro") String filtro, Model model, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");

        if(filtro != null && filtro.length() > 0){

            List <Integer> lista = eventoService.convertirALaInversa(eventoService.findByTitulo(filtro));
            lista.retainAll(usuario.getEventoList());
            // falta filtrar por etiquetas:  (+ el borrado bien hecho)
            List<EtiquetaDTO> etList = etiquetaService.findBySimilarNombreMuchas(filtro);
            if(!etList.isEmpty()){
                for(EtiquetaDTO e : etList){

                    System.out.println("Etiquetaaaa: " + e.getNombre());
                    for(int even : e.getEventoList()){
                        if(!lista.contains(even) && usuario.getEventoList().contains(even)){
                            lista.add(even);
                        }
                    }
                }
            }

            model.addAttribute("listaEventos", eventoService.convertirAListaDTOdirectamente(lista));

        }else{
            List <EventoDTO> lEventos = eventoService.convertirAListaDTOdirectamente(usuario.getEventoList());
            model.addAttribute("listaEventos", lEventos);
        }
        model.addAttribute("usuario", usuario);
        List<EtiquetaDTO> todasLasEtiquetas = etiquetaService.findAll();
        model.addAttribute("todasLasEtiquetas", todasLasEtiquetas);
        model.addAttribute("filtro", filtro);

        return "homeCreadorDeEventos";
    }

    @GetMapping("/crear")
    public String doEnviarACrear() {
        return "crearEvento";
    }

    @PostMapping("/guardar")
    public String doGuardar(Model model, HttpSession session,
                            @RequestParam("titulo") String titulo,
                            @RequestParam("descripcion") String descripcion,
                            @RequestParam("imagen") String imagen,
                            @RequestParam("aforo_maximo") String aforo_maximo,
                            @RequestParam("maximo_entradas_usuario") String maximo_entradas_usuario,
                            @RequestParam("asientos_asignados") String asientos_asignados,
                            @RequestParam("numero_filas") String numero_filas,
                            @RequestParam("asientos_por_fila") String asientos_por_fila,
                            @RequestParam("precio") String precio,
                            @RequestParam("etiquetas") String etiquetas,
                            @RequestParam("fecha") String fechaStr,
                            @RequestParam("fecha_limite_entradas") String fecha_limite_entradasStr,
                            @RequestParam("idEventoEditar") String idEventoEditarStr) throws ParseException {

        UsuarioDTO u = (UsuarioDTO) session.getAttribute("usuario");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = null;
        Date fecha_limite_entradas = null;
        EventoDTO evento = new EventoDTO();
        fecha = format.parse(fechaStr);
        fecha_limite_entradas = format.parse(fecha_limite_entradasStr);

        try{
            //evento.setId(0);
            evento.setTitulo(titulo);
            evento.setDescripcion(descripcion);
            evento.setImagen(imagen);
            evento.setPrecioEntrada(Integer.parseInt(precio));
            evento.setFecha(fecha);
            evento.setFechaLimEntradas(fecha_limite_entradas);
            evento.setAforoMax(Integer.parseInt(aforo_maximo));
            evento.setMaxEntradasPorUsuario(Integer.parseInt(maximo_entradas_usuario));

            if("Si".equals(asientos_asignados)){
                evento.setAsientosAsignados(true);
                if(Integer.parseInt(numero_filas) <= 0 || Integer.parseInt(numero_filas) > 10){
                    throw new Exception("Numero de filas no válido");
                }
                evento.setNumFilas(Integer.parseInt(numero_filas));
                if(Integer.parseInt(asientos_por_fila) <= 0 || Integer.parseInt(asientos_por_fila) > 10){
                    throw new Exception("Asientos por fila no válido");
                }
                evento.setAsientosPorFila(Integer.parseInt(asientos_por_fila));
            }else{
                evento.setAsientosAsignados(false);
            }
            evento.setUsuario(u.getId());

            List<Integer> etiquetaList = new ArrayList();
            String[] arrayEtiquetas = etiquetas.split(" ");
            for(String item : arrayEtiquetas){
                EtiquetaDTO buscada = etiquetaService.findBySimilarNombre(item);
                if(buscada==null){

                    int id = etiquetaService.crearEtiqueta(item);
                    etiquetaList.add(id);
                }else{
                    etiquetaList.add(buscada.getId());
                }
            }
            evento.setEtiquetaList(etiquetaList);

        }catch(Exception e){

            String mensajeError = "Error rellenando los datos del formulario. Intentelo de nuevo.";

            if(idEventoEditarStr == null || idEventoEditarStr == ""){ //estamos en guardar

                model.addAttribute("mensajeError", mensajeError);
                return "crearEvento";

            }else{ //estamos en editar
                int idEventoEditar = Integer.parseInt(idEventoEditarStr);
                EventoDTO eventoEditar = eventoService.find(idEventoEditar);
                model.addAttribute("evento", eventoEditar);
                model.addAttribute("mensajeError", mensajeError);
                return "editarEventoCreadorDeEventos";
            }
        }

        if(idEventoEditarStr == null || idEventoEditarStr == ""){ //estamos en guardar
            eventoService.crearEvento(u.getId(), titulo, descripcion, etiquetas, Integer.parseInt(precio), imagen, fecha, fecha_limite_entradas, Integer.parseInt(aforo_maximo), Integer.parseInt(maximo_entradas_usuario), asientos_asignados, numero_filas, asientos_por_fila);

            int idMasAlta = eventoService.findIdMasAlta();
          List <Integer> listaEventos = u.getEventoList();
            listaEventos.add(idMasAlta);
            u.setEventoList(listaEventos);
            usuarioService.edit(u);
        }else{ // estamos en editar

            int idEventoEditar = Integer.parseInt(idEventoEditarStr);
            eventoService.editarEvento2(idEventoEditar, u.getId(), titulo, descripcion, Integer.parseInt(precio), imagen, fecha, fecha_limite_entradas, Integer.parseInt(aforo_maximo), Integer.parseInt(maximo_entradas_usuario), asientos_asignados, numero_filas, asientos_por_fila);
        }
        return "redirect:/";
    }

    @GetMapping("/editar/{idE}")
    public String doEnviarAEditar(@PathVariable("idE") Integer idE, Model model) {
        EventoDTO evento = eventoService.buscarEvento2(idE);
        model.addAttribute("evento", evento);
        return "editarEventoCreadorDeEventos";
    }

    @GetMapping("/borrar/{idE}")
    public String doBorrar(@PathVariable("idE") Integer idE, HttpSession session) {
        UsuarioDTO usuario = (UsuarioDTO)session.getAttribute("usuario");
        EventoDTO eventoABorrar = eventoService.buscarEvento2(idE);
        for(int idEtiqueta : eventoABorrar.getEtiquetaList()){
            EtiquetaDTO etiqueta = etiquetaService.buscarEtiqueta(idEtiqueta);
            List<Integer> eventosDeLaEtiqueta = etiqueta.getEventoList();
            eventosDeLaEtiqueta.remove(eventoABorrar.getId());

            if(eventosDeLaEtiqueta.isEmpty()){
               // etiquetaService.remove2(idEtiqueta);
            }else{
                etiqueta.setEventoList(eventosDeLaEtiqueta);
                etiquetaService.edit(etiqueta);
            }
        }
        eventoService.borrarEvento(eventoABorrar);

        List<Integer> eventosList = usuario.getEventoList();
        eventosList.remove(eventoABorrar.getId());
        usuario.setEventoList(eventosList);
        usuarioService.edit(usuario);

        return "redirect:/";
    }

}
