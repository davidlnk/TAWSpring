/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.taw.sampletaw.dto;

import java.util.Date;
import java.util.List;

/**
 *
 * @author David
 */
public class UsuarioDeEventosDTO {
    private Integer id;
    private String nombre;
    private String apellidos;
    private String correo;
    private String ciudad;
    private Date fechaNacimiento;
    private String sexo;
    private List<Integer> publicoList;
    private List<Integer> usuarioList;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public List<Integer> getPublicoList() {
        return publicoList;
    }

    public void setPublicoList(List<Integer> publicoList) {
        this.publicoList = publicoList;
    }

    public List<Integer> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Integer> usuarioList) {
        this.usuarioList = usuarioList;
    }
    
}
