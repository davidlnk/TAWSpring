/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.taw.sampletaw.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import es.taw.sampletaw.dto.PublicoDTO;

/**
 *
 * @author David
 */
@Entity
@Table(name = "PUBLICO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Publico.findAll", query = "SELECT p FROM Publico p")
    , @NamedQuery(name = "Publico.findById", query = "SELECT p FROM Publico p WHERE p.id = :id")
    , @NamedQuery(name = "Publico.findByFila", query = "SELECT p FROM Publico p WHERE p.fila = :fila")
    , @NamedQuery(name = "Publico.findByAsiento", query = "SELECT p FROM Publico p WHERE p.asiento = :asiento")})
public class Publico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "FILA")
    private Integer fila;
    @Column(name = "ASIENTO")
    private Integer asiento;
    @JoinColumn(name = "EVENTO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Evento evento;
    @JoinColumn(name = "USUARIO_DE_EVENTOS", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private UsuarioDeEventos usuarioDeEventos;

    public Publico() {
    }

    public Publico(Integer id) {
        this.id = id;
    }

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

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public UsuarioDeEventos getUsuarioDeEventos() {
        return usuarioDeEventos;
    }

    public void setUsuarioDeEventos(UsuarioDeEventos usuarioDeEventos) {
        this.usuarioDeEventos = usuarioDeEventos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Publico)) {
            return false;
        }
        Publico other = (Publico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tawevents.entity.Publico[ id=" + id + " ]";
    }
    
    public PublicoDTO getDTO() {
        PublicoDTO dto = new PublicoDTO();
        dto.setId(id);
        dto.setFila(fila);
        dto.setAsiento(asiento);
        dto.setEvento(evento.getId());
        dto.setUsuarioDeEventos(usuarioDeEventos.getId());
        
        return dto;
    }
    
}
