/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.taw.sampletaw.service;

import es.taw.sampletaw.dao.EstudioRepository;
import es.taw.sampletaw.dao.EventoRepository;
import es.taw.sampletaw.dao.UsuarioDeEventosRepository;
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
import java.util.*;
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
    private UsuarioDeEventosRepository usuarioDeEventosRepository;

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
    @Autowired
    public void setUsuarioDeEventosRepository(UsuarioDeEventosRepository usuarioDeEventosRepository) {
        this.usuarioDeEventosRepository = usuarioDeEventosRepository;
    }

    public List<EstudioDTO> listar(FiltroEstudios filtro, UsuarioDTO usuario) {
        if (filtro.getHastaFecha().before(filtro.getHastaFecha())) {
            Date aux = filtro.getHastaFecha();
            filtro.setHastaFecha(filtro.getDesdeFecha());
            filtro.setDesdeFecha(aux);
        }

        List<Estudio> listaEstudio;
        List<EstudioDTO> lista = new ArrayList<>();
        ;

        if (filtro.getOrdenporfecha().equals("a")) { // ordenar desde antiguos a nuevos
            listaEstudio = this.estudioRepository.findEstudioByAntiguo(filtro.getDescripcion(), filtro.getDesdeFecha(), filtro.getHastaFecha(), usuario.getId());
        } else { // ordenar desde nuevos a antiguos
            listaEstudio = this.estudioRepository.findEstudioByReciente(filtro.getDescripcion(), filtro.getDesdeFecha(), filtro.getHastaFecha(), usuario.getId());
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
        datos.set(1, (datos.get(1).equals("-")) ? "" : datos.get(1));
        datos.set(2, (datos.get(2).equals("-")) ? "" : datos.get(2));
        datos.set(3, (datos.get(3).equals("-")) ? "OFF" : "checked");
        datos.set(4, (datos.get(4).equals("-")) ? "OFF" : "checked");
        datos.set(5, ""); //
        datos.set(6, datos.get(6).equals("-") ? "" : datos.get(6));
        datos.set(7, datos.get(7).equals("-") ? "" : datos.get(7));
        datos.set(8, (datos.get(8).equals("-")) ? "" : datos.get(8));
        datos.set(9, (datos.get(9).equals("-")) ? "" : datos.get(9));
        datos.set(10, (datos.get(10).equals("-")) ? "" : datos.get(10));
        datos.set(11, (datos.get(11).equals("-")) ? "" : datos.get(11));
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
        datos.set(8, (((datos.get(8) == null || datos.get(8).isEmpty()) ? "-" : (datos.get(8)))));
        datos.set(9, ((datos.get(9) == null || datos.get(9).isEmpty()) ? "-" : new String(datos.get(9).getBytes("ISO8859-1"), "UTF8")));
        datos.set(10, ((datos.get(10) == null || datos.get(10).isEmpty()) ? "-" : datos.get(10)));
        datos.set(11, ((datos.get(11) == null || datos.get(11).isEmpty()) ? "-" : datos.get(11)));
        datos.set(12, ((datos.get(12) == null || datos.get(12).isEmpty()) ? "-" : new String(datos.get(12).getBytes("ISO8859-1"), "UTF8")));
        datos.set(13, ((datos.get(13) == null || datos.get(13).isEmpty()) ? "-" : new String(datos.get(13).getBytes("ISO8859-1"), "UTF8")));
        datos.set(14, ((datos.get(14) == null || datos.get(14).isEmpty()) ? "-" : datos.get(14)));
        datos.set(15, datos.get(15));

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
        if (!datos.get(6).equals("-") && Integer.parseInt(datos.get(6)) < 0) {
            datos.set(6, "-");
        }
        if (!datos.get(7).equals("-") && Integer.parseInt(datos.get(7)) < 0) {
            datos.set(7, "-");
        }
        if (!datos.get(6).equals("-") && !datos.get(7).equals("-")) {
            if (Integer.parseInt(datos.get(6)) > Integer.parseInt(datos.get(7))) {
                String aux = datos.get(6);
                datos.set(6, datos.get(7));
                datos.set(7, aux);
            }
        }

        // Compruebo y arreglo las edades mínima y máxima
        if (!datos.get(10).equals("-") && Integer.parseInt(datos.get(10)) < 0) {
            datos.set(10, "-");
        }
        if (!datos.get(11).equals("-") && Integer.parseInt(datos.get(11)) < 0) {
            datos.set(11, "-");
        }
        if (!datos.get(10).equals("-") && !datos.get(11).equals("-")) {
            if (Integer.parseInt(datos.get(10)) > Integer.parseInt(datos.get(11))) {
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
        Usuario analista = this.usuarioRepository.findById(usuarioID).get();
        estudio.setAnalista(analista);
        estudio.setFecha(new Date());
        estudio.setDescripcion(datos.get(15));
        estudio.setBusqueda(busqueda.toString());

        this.estudioRepository.save(estudio);
    }

    public Object[] relizarEstudio(List<String> datos) {
        List<UsuarioDTO> listaUsuarios = new ArrayList<>();
        List<UsuarioDeEventosDTO> listaUdE = new ArrayList<>();

        boolean addUsuario;
        boolean soloUsuariosDeEvento = datos.get(14).equals("usuariodeeventos");

        for (Usuario u : this.usuarioRepository.findAll()) {
            UsuarioDeEventos ude = u.getUsuarioDeEventos();

            if (datos.get(14).isEmpty() || datos.get(14).equals(u.getTipoUsuario())) {
                addUsuario = true;

                // Se filtra y si falla alguno se pone addUsuario a false;
                addUsuario = analizarPorFiltrosDeUsuario(u, ude, datos);
                addUsuario = addUsuario ? analizarPorFiltrosDeEvento(u, ude, datos) : false;

                if (addUsuario) {
                    listaUsuarios.add(u.getDTO());
                    if (ude == null) {
                        listaUdE.add(null);
                    } else {
                        listaUdE.add(ude.getDTO());
                    }
                }
            }
        }

        Object[] resultados = new Object[2];
        resultados[0] = listaUsuarios;
        resultados[1] = listaUdE;
        return resultados;
    }

    private boolean analizarPorFiltrosDeUsuario(Usuario u, UsuarioDeEventos ude, List<String> datos) {
        if (ude != null) {
            if (!datos.get(8).isEmpty() && !ude.getSexo().contains(datos.get(8))) {
                return false;
            }
            if (!datos.get(9).isEmpty() && !ude.getCiudad().equals(datos.get(9))) {
                return false;
            }
            if (!edadEnRango(ude.getFechaNacimiento(), datos.get(10), datos.get(11))) {
                return false;
            }
            if (!datos.get(12).isEmpty() && !ude.getNombre().toLowerCase(Locale.ROOT).contains(datos.get(12).toLowerCase(Locale.ROOT))) {
                return false;
            }
            if (!datos.get(13).isEmpty() && !ude.getApellidos().toLowerCase(Locale.ROOT).contains(datos.get(13).toLowerCase(Locale.ROOT))) {
                return false;
            }
        }
        return true;
    }

    private boolean analizarPorFiltrosDeEvento(Usuario u, UsuarioDeEventos ude, List<String> datos) {
        if (ude == null || noSeUsanFiltrosDeEvento(datos)) {
            return true;
        }
        Iterator<Publico> it = u.getUsuarioDeEventos().getPublicoList().iterator();
        Date hoy = new Date();

        boolean noFiltroFecha = datos.get(3).equals("OFF");
        boolean noFiltroAsientosAsignados = datos.get(4).equals("OFF");

        while (it.hasNext()) { // Itero entre todos los eventos a los que está apuntado el usuario de eventos
            Evento evento = it.next().getEvento();

            if (datos.get(0).isEmpty() || evento.getTitulo().toLowerCase(Locale.ROOT).contains(datos.get(0).toLowerCase(Locale.ROOT))) { // Compruebo el título
                SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
                Date desdeFecha = null;
                Date hastaFecha = null;
                try {
                    desdeFecha = (datos.get(1).isEmpty()) ? formatoFecha.parse("1970-01-01") : formatoFecha.parse(datos.get(1));
                    hastaFecha = (datos.get(2).isEmpty()) ? formatoFecha.parse("3000-01-01") : formatoFecha.parse(datos.get(2));
                } catch (ParseException ex) {
                    Logger.getLogger(EstudioService.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (desdeFecha.before(evento.getFecha()) && evento.getFecha().before(hastaFecha)) { // Compruebo la fecha de evento
                    // No se filtra por eventos finalizados O el evento ha finalizado
                    if (noFiltroFecha || evento.getFecha().before(hoy)) {
                        // No se filtra por eventos asignados O los asientos están asignados
                        if (noFiltroAsientosAsignados || evento.getAsientosAsignados()) {
                            if (datos.get(6).isEmpty() || evento.getAforoMax() > Integer.parseInt(datos.get(6))) { // Compruebo el aforo
                                if (datos.get(7).isEmpty() || evento.getAforoMax() < Integer.parseInt(datos.get(7))) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean noSeUsanFiltrosDeEvento(List<String> datos) {
        if (datos.get(0).isEmpty() && datos.get(1).isEmpty() && datos.get(2).isEmpty()
                && datos.get(3).equals("OFF") && datos.get(4).equals("OFF")
                && datos.get(6).isEmpty() && datos.get(7).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean edadEnRango(Date fechaNacimiento, String edad_min, String edad_max) {
        double diff_Fechas = new Date().getTime() - fechaNacimiento.getTime();
        double edad = (diff_Fechas / (1000l * 60 * 60 * 24 * 365));
        if (edad_min.isEmpty() || edad > Integer.parseInt(edad_min)) {
            if (edad_max.isEmpty() || edad < Integer.parseInt(edad_max)) {
                return true;
            }
        }
        return false;
    }

    public void eliminarEstudio(String strID) {
        Integer id = Integer.parseInt(strID);
        Optional<Estudio> optionalEstudio = this.estudioRepository.findById(id);
        if (optionalEstudio.isPresent()) {
            Estudio estudio = optionalEstudio.get();
            this.estudioRepository.delete(estudio);
        }
    }

    public Set<String> buscarCiudades() {
        Set<String> ciudades = new HashSet<>();
        for (UsuarioDeEventos ude : this.usuarioDeEventosRepository.findAll()) {
            if (!ciudades.contains(ude.getCiudad())) {
                ciudades.add(ude.getCiudad());
            }
        }
        return ciudades;
    }
}
