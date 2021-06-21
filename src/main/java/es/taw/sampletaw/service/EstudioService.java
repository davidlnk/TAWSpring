/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.taw.sampletaw.service;

import es.taw.sampletaw.dao.EstudioRepository;
import es.taw.sampletaw.dao.EventoRepository;
import es.taw.sampletaw.dao.UsuarioRepository;
import es.taw.sampletaw.dto.EstudioDTO;
import es.taw.sampletaw.dto.UsuarioDTO;
import es.taw.sampletaw.dto.UsuarioDeEventosDTO;
import es.taw.sampletaw.entity.*;
import es.taw.sampletaw.vo.FiltroEstudios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author danie
 */
@Service
public class EstudioService {
    private EstudioRepository estudioRepository;
    private UsuarioRepository usuarioRepository;
    private EventoRepository eventoRepository;

    @Autowired
    public void setEstudioRepository(EstudioRepository estudioRepository) {
        this.estudioRepository = estudioRepository;
    }
    @Autowired
    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    @Autowired
    public void setEventoRepository(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public List<EstudioDTO> listar(FiltroEstudios filtro, UsuarioDTO usuario) {
        if (filtro.getHastaFecha().before(filtro.getHastaFecha())) {
            Date aux = filtro.getHastaFecha();
            filtro.setHastaFecha(filtro.getDesdeFecha());
            filtro.setDesdeFecha(aux);
        }

        List<Estudio> listaEstudio;
        List<EstudioDTO> lista = new ArrayList<>();;

        if (filtro.getOrdenporfecha().equals("a")) { // ordenar desde antiguos a nuevos
            listaEstudio = this.estudioRepository.findEstudioByAntiguo(filtro.getDesdeFecha(), filtro.getHastaFecha(), usuario.getId());
        } else { // ordenar desde nuevos a antiguos
            listaEstudio = this.estudioRepository.findEstudioByReciente(filtro.getDesdeFecha(), filtro.getHastaFecha(), usuario.getId());
        }

        if (listaEstudio != null) {
            for (Estudio estudio : listaEstudio) {
                lista.add(estudio.getDTO());
            }
        }

        return lista;
    }

    public List<String> datosVacios() {
        List<String> datos = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            datos.add("");
        }
        return datos;
    }

    public EstudioDTO encontrarEstudioPorID(int id) {
        return this.estudioRepository.findById(id).get().getDTO();
    }

    public List<String> separarDatos(String busqueda) {
        List<String> datos = new ArrayList<>();
        datos.addAll(Arrays.asList(busqueda.split(",")));
        datos.set(0, (datos.get(0).equals("-")) ? "" : datos.get(0));
        datos.set(1, datos.get(1));
        datos.set(2, datos.get(2));
        datos.set(3, (datos.get(3).equals("-")) ? "OFF" : "checked");
        datos.set(4, (datos.get(4).equals("-")) ? "OFF" : "checked");
        datos.set(5, "-"); //
        datos.set(6, datos.get(6).equals("-") ? "" : datos.get(6));
        datos.set(7, datos.get(7).equals("-") ? "" : datos.get(7));
        datos.set(8, (datos.get(8).equals("-")) ? "" : datos.get(8));
        datos.set(9, (datos.get(9).equals("-")) ? "" : datos.get(9));
        datos.set(10, (datos.get(10).equals("-")) ? "0" : datos.get(10));
        datos.set(11, (datos.get(11).equals("-")) ? "0" : datos.get(11));
        datos.set(11, (datos.get(11).equals("0")) ? "-" : datos.get(11));
        datos.set(12, (datos.get(12).equals("-")) ? "" : datos.get(12));
        datos.set(13, (datos.get(13).equals("-")) ? "" : datos.get(13));
        datos.set(14, (datos.get(14).equals("-")) ? "" : datos.get(14));
        return datos;
    }

    public void arreglarDatosYGuardarEstudio(Integer usuarioID, List<String> datos) throws UnsupportedEncodingException {
        datos.set(0, ((datos.get(0).isEmpty()) ? "-" : new String(datos.get(0).getBytes("ISO8859-1"), "UTF8")));
        datos.set(1, ((datos.get(1).isEmpty()) ? "-" : datos.get(1)));
        datos.set(2, ((datos.get(2).isEmpty()) ? "-" : datos.get(2)));
        datos.set(3, ((datos.get(3) == null) ? "-" : datos.get(3)));
        datos.set(4, ((datos.get(4) == null) ? "-" : datos.get(4)));
        // datos.get(5) es el hueco de las etiquetas
        datos.set(6, (((datos.get(6).isEmpty()) ? "-" : (datos.get(6)))));
        datos.set(7, (((datos.get(7).isEmpty()) ? "-" : (datos.get(7)))));
        datos.set(8, (((datos.get(8) == null) ? "-" : (datos.get(8)))));
        datos.set(9, (((datos.get(9) == null) ? "" : new String(datos.get(9).getBytes("ISO8859-1"), "UTF8"))));
        datos.set(10, ((datos.get(10).isEmpty()) ? "-" : datos.get(10)));
        datos.set(11, ((datos.get(11).isEmpty()) ? "-" : datos.get(11)));
        datos.set(12, ((datos.get(12).isEmpty()) ? "-" : new String(datos.get(12).getBytes("ISO8859-1"), "UTF8")));
        datos.set(13, ((datos.get(13).isEmpty()) ? "-" : new String(datos.get(13).getBytes("ISO8859-1"), "UTF8")));
        datos.set(14, ((datos.get(14) == null) ? "-" : datos.get(14)));
        datos.set(15, new String(datos.get(15).getBytes("ISO8859-1"), "UTF8"));

        // Compruebo y arreglo las fechas
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        Date desdeF;
        Date hastaF;
        try {
            if (!datos.get(1).equals("-") && !datos.get(2).equals("-")) {
                desdeF = formatoFecha.parse(datos.get(1));
                hastaF = formatoFecha.parse(datos.get(2));
                if (desdeF.after(hastaF)) {
                    String aux = datos.get(2);
                    datos.set(2, datos.get(1));
                    datos.set(1, aux);
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(EstudioService.class.getName()).log(Level.SEVERE, "Error has formatoFecha.parse(String de fecha)", ex);
        }

        // Compruebo y arreglo los aforos mínimo y máximo
        if (!datos.get(6).equals("-") && new Integer(datos.get(6)) < 0) {
            datos.set(6, "-");
        }
        if (!datos.get(7).equals("-") && new Integer(datos.get(7)) < 0) {
            datos.set(7, "-");
        }
        if (!datos.get(6).equals("-") && !datos.get(7).equals("-")) {
            if (new Integer(datos.get(6)) > new Integer(datos.get(7))) {
                String aux = datos.get(6);
                datos.set(6, datos.get(7));
                datos.set(7, aux);
            }
        }

        // Compruebo y arreglo las edades mínima y máxima
        if (!datos.get(10).equals("-") && new Integer(datos.get(10)) < 0) {
            datos.set(10, "-");
        }
        if (!datos.get(11).equals("-") && new Integer(datos.get(11)) < 0) {
            datos.set(11, "-");
        }
        if (!datos.get(10).equals("-") && !datos.get(11).equals("-")) {
            if (new Integer(datos.get(10)) > new Integer(datos.get(11))) {
                String aux = datos.get(10);
                datos.set(10, datos.get(11));
                datos.set(11, aux);
            }
        }

        // Unir datos para guardarlos en la base de datos
        StringBuilder busqueda = new StringBuilder("");
        busqueda.append(datos.get(0)).append(",");
        busqueda.append(datos.get(1)).append(",");
        busqueda.append(datos.get(2)).append(",");
        busqueda.append(datos.get(3)).append(",");
        busqueda.append(datos.get(4)).append(",");
        busqueda.append("-,"); //
        busqueda.append(datos.get(6)).append(",");
        busqueda.append(datos.get(7)).append(",");
        busqueda.append(datos.get(8)).append(",");
        busqueda.append(datos.get(9)).append(",");
        busqueda.append(datos.get(10)).append(",");
        busqueda.append(datos.get(11)).append(",");
        busqueda.append(datos.get(12)).append(",");
        busqueda.append(datos.get(13)).append(",");
        busqueda.append(datos.get(14));

        // Creo e inserto el nuevo estudio
        Estudio estudio = new Estudio();
        Usuario analista = this.usuarioRepository.findById(new Integer(usuarioID)).get();
        estudio.setAnalista(analista);
        estudio.setFecha(new Date());
        estudio.setDescripcion(datos.get(15));
        estudio.setBusqueda(busqueda.toString());

        this.estudioRepository.save(estudio);
    }

    public Object[] analizarBaseDeDatos(List<String> datos) {
        List<UsuarioDTO> listaUsuarios = new ArrayList<>();
        List<UsuarioDeEventosDTO> listaUdE = new ArrayList<>();
        //for (Usuario u : filtrarUsuarios(datos)) { // filtrarUsuario filtra usuarios por tipo, nombre, apellidos, ciudad y sexo
        for (Usuario u : this.usuarioRepository.findAll()) {
            UsuarioDeEventos ude = u.getUsuarioDeEventos();
            if (ude != null && (datos.get(14).equals("usuariodeeventos") || datos.get(14).isEmpty())) {
                if (edadEnRango(ude.getFechaNacimiento(), datos.get(10), datos.get(11))) {
                    if (filtrosDeEvento(ude, datos)) {
                        listaUsuarios.add(u.getDTO());
                        listaUdE.add(ude.getDTO());
                    }
                }
            } else if (noSeUsanFiltrosDeEvento(datos) && !u.getTipoUsuario().equals("usuariodeeventos")) {
                listaUsuarios.add(u.getDTO());
                listaUdE.add(null);
            }
        }

        Object[] resultados = new Object[2];
        resultados[0] = listaUsuarios;
        resultados[1] = listaUdE;
        return resultados;
    }

    public boolean edadEnRango(Date fechaNacimiento, String edad_min, String edad_max) {
        long diff_Fechas = new Date().getTime() - fechaNacimiento.getTime();
        int edad = (int) (diff_Fechas / (1000l * 60 * 60 * 24 * 365));
        if (edad_min.equals("-") || edad_min.isEmpty() || edad > Integer.parseInt(edad_min)) {
            if (edad_max.equals("-") || edad_max.isEmpty() || edad < Integer.parseInt(edad_max)) {
                return true;
            }
        }
        return false;
    }

    public boolean filtrosDeEvento(UsuarioDeEventos ude, List<String> datos) {
        // Este IF comprueba si se usa algún filtro de evento
        //  si no se usa ninguno devuelve true
        if (noSeUsanFiltrosDeEvento(datos)) {
            return true;
        }

        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        Date desdeFecha = null;
        Date hastaFecha = null;
        try {
            desdeFecha = (datos.get(1).equals("-")) ? formatoFecha.parse("1970-01-01") : formatoFecha.parse(datos.get(1));
            hastaFecha = (datos.get(2).equals("-")) ? formatoFecha.parse("3000-01-01") : formatoFecha.parse(datos.get(2));
        } catch (ParseException ex) {
            Logger.getLogger(EstudioService.class.getName()).log(Level.SEVERE, null, ex);
        }

        // checked = ON
        // Solo se comprueba si los asientos están asignados o si el evento ha finalizado
        // cuando está checked, si no lo está no se comprueba
        boolean asientAsig = datos.get(4).equals("checked");
        boolean eventFinal = datos.get(3).equals("checked");
        Date hoy = new Date();
        List<Evento> listaEventos;
        //listaEventos = filtrarEventos(datos.get(0), desdeFecha, hastaFecha, datos.get(6), datos.get(7));
        listaEventos = this.eventoRepository.findAll();
        for (Publico publico : ude.getPublicoList()) {
            Evento evento = publico.getEvento();
            boolean checkAsientosAsig = asientAsig ? evento.getAsientosAsignados() : true;
            boolean checkEventFinalizado = eventFinal ? evento.getFecha().after(hoy) : true;
            if (checkAsientosAsig && checkEventFinalizado && listaEventos.contains(evento)) {
                return true;
            }
        }
        return false;
    }

    public boolean noSeUsanFiltrosDeEvento(List<String> datos) {
        if (datos.get(0).isEmpty() && datos.get(1).equals("-") && datos.get(2).equals("-")
                && datos.get(3).equals("OFF") && datos.get(4).equals("OFF")
                && datos.get(6).equals("") && datos.get(7).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

}
