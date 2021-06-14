/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.taw.sampletaw.dto;

import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import es.taw.sampletaw.entity.Evento;

/**
 *
 * @author rafar
 */
public class EtiquetaDTO {
    private Integer id;
    private String nombre;
    private List<Integer> eventoList;

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

    public List<Integer> getEventoList() {
        return eventoList;
    }

    public void setEventoList(List<Integer> eventoList) {
        this.eventoList = eventoList;
    }
    
    
}
