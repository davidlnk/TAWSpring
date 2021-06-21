/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.taw.sampletaw.dto;

import es.taw.sampletaw.entity.Estudio;

import java.util.Date;

/**
 *
 * @author danie
 */
public class EstudioDTO {
    private Integer id;
    private Integer analista;
    private String descripcion;
    private Date fecha;
    private String busqueda;

    public EstudioDTO (UsuarioDTO usuarioDTO) {
        id = 0;
        analista = usuarioDTO.getId();
        descripcion = "";
        fecha = new Date();
        busqueda = "-,-,-,-,-,-,-,-,-,-,-,-,-,-,-";
    }

    public EstudioDTO () {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getAnalista() {
        return analista;
    }

    public void setAnalista(Integer analista) {
        this.analista = analista;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }
    
}
