/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.taw.sampletaw.entity;

import com.sun.istack.NotNull;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author David
 */
@Entity
@Table(name = "CONVERSACION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Conversacion.findAll", query = "SELECT c FROM Conversacion c")
    , @NamedQuery(name = "Conversacion.findById", query = "SELECT c FROM Conversacion c WHERE c.id = :id")
    , @NamedQuery(name = "Conversacion.findByFechacreacion", query = "SELECT c FROM Conversacion c WHERE c.fechacreacion = :fechacreacion")})
public class Conversacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "FECHACREACION", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechacreacion;
    @JoinColumn(name = "USUARIO", referencedColumnName = "ID")
    @OneToOne(optional = false)
    private Usuario usuario;
    @JoinColumn(name = "TELEOPERADOR", referencedColumnName = "ID")
    @OneToOne(optional = false)
    private Usuario teleoperador;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "conversacion")
    private Mensaje mensaje;

    public Conversacion() {
    }

    public Conversacion(Integer id) {
        this.id = id;
    }

    public Conversacion(Integer id, Date fechacreacion) {
        this.id = id;
        this.fechacreacion = fechacreacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getTeleoperador() {
        return teleoperador;
    }

    public void setTeleoperador(Usuario teleoperador) {
        this.teleoperador = teleoperador;
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
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
        if (!(object instanceof Conversacion)) {
            return false;
        }
        Conversacion other = (Conversacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tawevents.entity.Conversacion[ id=" + id + " ]";
    }
    
}
