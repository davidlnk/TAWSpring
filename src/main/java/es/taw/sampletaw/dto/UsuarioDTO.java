/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.taw.sampletaw.dto;

import java.util.List;

/**
 *
 * @author Ivan
 */
public class UsuarioDTO {
    private Integer id;
    private String nickname;
    private String contrasena;
    private String tipoUsuario;
    private Integer usuarioDeEventos;
    private List<Integer> eventoList;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Integer getUsuarioDeEventos() {
        return usuarioDeEventos;
    }

    public void setUsuarioDeEventos(Integer usuarioDeEventos) {
        this.usuarioDeEventos = usuarioDeEventos;
    }
    
    public List<Integer> getEventoList() {
        return eventoList;
    }

    public void setEventoList(List<Integer> eventoList) {
        this.eventoList = eventoList;
    }
}
