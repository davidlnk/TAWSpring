/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.taw.sampletaw.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import es.taw.sampletaw.dao.UsuarioDeEventosRepository;
import es.taw.sampletaw.dao.UsuarioRepository;
import es.taw.sampletaw.dto.UsuarioDTO;
import es.taw.sampletaw.dto.UsuarioDeEventosDTO;
import es.taw.sampletaw.entity.Usuario;
import es.taw.sampletaw.entity.UsuarioDeEventos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ivan
 */
@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    private UsuarioDeEventosRepository usuarioDeEventosRepository;

    @Autowired
    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Autowired
    public void setUsuarioDeEventosRepository(UsuarioDeEventosRepository usuarioDeEventosRepository) {
        this.usuarioDeEventosRepository = usuarioDeEventosRepository;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public UsuarioDTO comprobarCredenciales(String strNick, String strClave) {
        Usuario usuario = usuarioRepository.findByNickAndPassword(strNick, strClave);
        if (usuario != null) {
            return usuario.getDTO();
        } else {
            return null;
        }
    }

    public Usuario comprobarCredencialesUsuario(String strNick, String strClave) {
        Usuario usuario = usuarioRepository.findByNickAndPassword(strNick, strClave);
        if (usuario != null) {
            return usuario;
        } else {
            return null;
        }
    }

    protected List<UsuarioDTO> convertirAListaDTO(List<Usuario> lista) {
        if (lista != null) {
            List<UsuarioDTO> listaDTO = new ArrayList<UsuarioDTO>();
            for (Usuario usuario : lista) {
                listaDTO.add(usuario.getDTO());
            }
            return listaDTO;
        } else {
            return null;
        }
    }

    public Boolean esNickUnico(String nick) {
        return usuarioRepository.esNickUnico(nick) == null;
    }

    public List<UsuarioDTO> filtroNick(String nick) {
        List<Usuario> usuarios = usuarioRepository.findBySimilarNick(nick);
        return convertirAListaDTO(usuarios);
    }

    public List<UsuarioDTO> filtroContrasena(String contra) {
        List<Usuario> usuarios = usuarioRepository.findBySimilarContrasena(contra);
        return convertirAListaDTO(usuarios);
    }

    public List<UsuarioDTO> filtroTipoUsuario(String tipo) {
        List<Usuario> usuarios = usuarioRepository.findBySimilarTipoUsuario(tipo);
        return convertirAListaDTO(usuarios);
    }

    public List<UsuarioDTO> TodosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return convertirAListaDTO(usuarios);
    }

    public UsuarioDTO buscarUsuario(Integer id) {
        Optional<Usuario> usuario = this.usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return usuario.get().getDTO();
        } else {
            return null;
        }
    }

    public UsuarioDTO buscarUsuarioNick(String nick) {
        Usuario usuario = this.usuarioRepository.findByNick(nick);
        if (usuario != null) {
            return usuario.getDTO();
        } else {
            return null;
        }
    }

    public UsuarioDeEventosDTO getUsuarioDeEventos(UsuarioDTO user) {
        Optional<Usuario> usuario = usuarioRepository.findById(user.getId());
        return usuario.get().getUsuarioDeEventos().getDTO();
    }

    public void borrarUsuario(Integer id) {
        Optional<Usuario> usuario = this.usuarioRepository.findById(id);
        this.usuarioRepository.delete(usuario.get());
    }

    public void edit(UsuarioDTO e) {
        Optional<Usuario> usuario = usuarioRepository.findById(e.getId());
        usuarioRepository.save(usuario.get());
    }

    public void guardarUsuario(String id, String nickname, String contrasena,
            String tipoUsuario) {
        Optional<Usuario> usuarioOpt;
        Usuario usuario;

        if (id == null || id.isEmpty()) { // Crear nuevo cliente
            usuario = new Usuario();
        } else { // Editar cliente existente
            usuarioOpt = this.usuarioRepository.findById(new Integer(id));
            usuario = usuarioOpt.get();
        }
        usuario.setNickname(nickname);
        usuario.setContrasena(contrasena);
        usuario.setTipoUsuario(tipoUsuario);

        if (id == null || id.isEmpty()) { // Crear nuevo cliente
            this.usuarioRepository.save(usuario);
        } else { // Editar cliente existente
            this.usuarioRepository.save(usuario);
        }
    }

    public void guardarUsuario(String id, String nickname, String contrasena,
            String tipoUsuario, String nombre, String apellidos, String correoElectronico, String ciudad, String sexo, Date fechaNacimiento) {

        UsuarioDeEventos usuarioEventos = new UsuarioDeEventos();
        usuarioEventos.setNombre(nombre);
        usuarioEventos.setApellidos(apellidos);
        usuarioEventos.setCorreo(correoElectronico);
        usuarioEventos.setCiudad(ciudad);
        usuarioEventos.setSexo(sexo);
        usuarioEventos.setFechaNacimiento(fechaNacimiento);
        usuarioDeEventosRepository.save(usuarioEventos);


        Usuario usuario;

        if (id == null || id.isEmpty()) { // Crear nuevo cliente
            usuario = new Usuario();
        } else { // Editar cliente existente
            usuario = this.usuarioRepository.findById(new Integer(id)).get();
        }
        usuario.setNickname(nickname);
        usuario.setContrasena(contrasena);
        usuario.setTipoUsuario(tipoUsuario);
        usuario.setUsuarioDeEventos(usuarioEventos);



        if (id == null || id.isEmpty()) { // Crear nuevo cliente
            this.usuarioRepository.save(usuario);
        } else { // Editar cliente existente
            this.usuarioRepository.save(usuario);
        }
    }

    public void editarUsuario(String id, String nickname, String contrasena,
            String tipoUsuario, String nombre, String apellidos, String correoElectronico, String ciudad, String sexo, Date fechaNacimiento) {

        UsuarioDeEventos usuarioEventos = this.usuarioRepository.findById(new Integer(id)).get().getUsuarioDeEventos();
        usuarioEventos.setNombre(nombre);
        usuarioEventos.setApellidos(apellidos);
        usuarioEventos.setCorreo(correoElectronico);
        usuarioEventos.setCiudad(ciudad);
        usuarioEventos.setSexo(sexo);
        usuarioEventos.setFechaNacimiento(fechaNacimiento);
        usuarioDeEventosRepository.save(usuarioEventos);

        Usuario usuario = this.usuarioRepository.findById(new Integer(id)).get();

        usuario.setNickname(nickname);
        usuario.setContrasena(contrasena);
        usuario.setTipoUsuario(tipoUsuario);
        usuario.setUsuarioDeEventos(usuarioEventos);
        this.usuarioRepository.save(usuario);
    }
}
