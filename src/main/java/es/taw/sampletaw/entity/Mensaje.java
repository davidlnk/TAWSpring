/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.taw.sampletaw.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "MENSAJE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mensaje.findAll", query = "SELECT m FROM Mensaje m")
    , @NamedQuery(name = "Mensaje.findByContenido", query = "SELECT m FROM Mensaje m WHERE m.contenido = :contenido")
    , @NamedQuery(name = "Mensaje.findByFecha", query = "SELECT m FROM Mensaje m WHERE m.fecha = :fecha")
    , @NamedQuery(name = "Mensaje.findByMensaje", query = "SELECT m FROM Mensaje m WHERE m.mensaje = :mensaje")
    , @NamedQuery(name = "Mensaje.findByEmisor", query = "SELECT m FROM Mensaje m WHERE m.emisor = :emisor")})
public class Mensaje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "CONTENIDO", nullable = false, length = 1000)
    private String contenido;
    @Basic(optional = false)
    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MENSAJE")
    private Integer mensaje;
    @Basic(optional = false)
    @Column(name = "EMISOR", nullable = false)
    private int emisor;
    @JoinColumn(name = "CONVERSACION", referencedColumnName = "ID")
    @OneToOne(optional = false)
    private Conversacion conversacion;

    public Mensaje() {
    }

    public Mensaje(Integer mensaje) {
        this.mensaje = mensaje;
    }

    public Mensaje(Integer mensaje, String contenido, Date fecha, int emisor) {
        this.mensaje = mensaje;
        this.contenido = contenido;
        this.fecha = fecha;
        this.emisor = emisor;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getMensaje() {
        return mensaje;
    }

    public void setMensaje(Integer mensaje) {
        this.mensaje = mensaje;
    }

    public int getEmisor() {
        return emisor;
    }

    public void setEmisor(int emisor) {
        this.emisor = emisor;
    }

    public Conversacion getConversacion() {
        return conversacion;
    }

    public void setConversacion(Conversacion conversacion) {
        this.conversacion = conversacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mensaje != null ? mensaje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mensaje)) {
            return false;
        }
        Mensaje other = (Mensaje) object;
        if ((this.mensaje == null && other.mensaje != null) || (this.mensaje != null && !this.mensaje.equals(other.mensaje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tawevents.entity.Mensaje[ mensaje=" + mensaje + " ]";
    }
    
}
