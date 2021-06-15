/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.taw.sampletaw.dto;

/**
 *
 * @author David
 */
public class PublicoDTO {
    private Integer id;
    private Integer fila;
    private Integer asiento;
    private Integer evento;
    private Integer usuarioDeEventos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFila() {
        return fila;
    }

    public void setFila(Integer fila) {
        this.fila = fila;
    }

    public Integer getAsiento() {
        return asiento;
    }

    public void setAsiento(Integer asiento) {
        this.asiento = asiento;
    }

    public Integer getEvento() {
        return evento;
    }

    public void setEvento(Integer evento) {
        this.evento = evento;
    }

    public Integer getUsuarioDeEventos() {
        return usuarioDeEventos;
    }

    public void setUsuarioDeEventos(Integer usuarioDeEventos) {
        this.usuarioDeEventos = usuarioDeEventos;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PublicoDTO)) {
            return false;
        }
        PublicoDTO other = (PublicoDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
