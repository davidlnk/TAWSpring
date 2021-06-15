/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.taw.sampletaw.service;

import java.util.ArrayList;
import java.util.List;

import es.taw.sampletaw.dao.UsuarioDeEventosRepository;
import es.taw.sampletaw.dto.UsuarioDeEventosDTO;
import es.taw.sampletaw.entity.Publico;
import es.taw.sampletaw.entity.UsuarioDeEventos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author David
 */
@Service
public class UsuarioDeEventosService {

    private UsuarioDeEventosRepository usuarioDeEventosRepository;

    @Autowired
    public void setUsuarioDeEventosRepository(UsuarioDeEventosRepository usuarioDeEventosRepository) {
        this.usuarioDeEventosRepository = usuarioDeEventosRepository;
    }

    protected List<UsuarioDeEventosDTO> convertirAListaDTO (List<UsuarioDeEventos> lista) {
        if (lista != null) {
            List<UsuarioDeEventosDTO> listaDTO = new ArrayList<UsuarioDeEventosDTO>();
            for (UsuarioDeEventos usuarioDeEventos:lista) {
                listaDTO.add(usuarioDeEventos.getDTO());
            }
            return listaDTO;
        } else {
            return null;
        }
    }

    public UsuarioDeEventos findById(Integer id) {
        return usuarioDeEventosRepository.findById(id).get();
    }

    public Boolean esCorreoUnico(String correo) {
        return usuarioDeEventosRepository.esCorreoUnico(correo) == null;
    }

    public void addPublico(UsuarioDeEventos usuarioDeEventos, Publico publico) {
        List<Publico> listaAsistencias = usuarioDeEventos.getPublicoList();
        listaAsistencias.add(publico);
        usuarioDeEventos.setPublicoList(listaAsistencias);
        usuarioDeEventosRepository.save(usuarioDeEventos);
    }

    public void removePublico(UsuarioDeEventos usuarioDeEventos, Publico publico) {
        List<Publico> listaAsistencias = usuarioDeEventos.getPublicoList();
        listaAsistencias.remove(publico);
        usuarioDeEventos.setPublicoList(listaAsistencias);
        usuarioDeEventosRepository.save(usuarioDeEventos);
    }
}
