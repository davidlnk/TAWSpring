/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.taw.sampletaw.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import es.taw.sampletaw.dto.UsuarioDTO;

/**
 *
 * @author David
 */
@Entity
@Table(name = "USUARIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findById", query = "SELECT u FROM Usuario u WHERE u.id = :id")
    , @NamedQuery(name = "Usuario.findByNickname", query = "SELECT u FROM Usuario u WHERE u.nickname = :nickname")
    , @NamedQuery(name = "Usuario.findByContrasena", query = "SELECT u FROM Usuario u WHERE u.contrasena = :contrasena")
    , @NamedQuery(name = "Usuario.findByTipoUsuario", query = "SELECT u FROM Usuario u WHERE u.tipoUsuario = :tipoUsuario")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NICKNAME", nullable = false, length = 20)
    private String nickname;
    @Basic(optional = false)
    @Column(name = "CONTRASENA", nullable = false, length = 30)
    private String contrasena;
    @Basic(optional = false)
    @Column(name = "TIPO_USUARIO", nullable = false, length = 20)
    private String tipoUsuario;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Conversacion conversacion;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "teleoperador")
    private Conversacion conversacion1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "analista")
    private List<Estudio> estudioList;
    @JoinColumn(name = "USUARIO_DE_EVENTOS", referencedColumnName = "ID")
    @ManyToOne(cascade = CascadeType.ALL)
    private UsuarioDeEventos usuarioDeEventos;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Evento> eventoList;

    public Usuario() {
    }

    public Usuario(Integer id) {
        this.id = id;
    }

    public Usuario(Integer id, String nickname, String contrasena, String tipoUsuario) {
        this.id = id;
        this.nickname = nickname;
        this.contrasena = contrasena;
        this.tipoUsuario = tipoUsuario;
    }

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

    public Conversacion getConversacion() {
        return conversacion;
    }

    public void setConversacion(Conversacion conversacion) {
        this.conversacion = conversacion;
    }

    public Conversacion getConversacion1() {
        return conversacion1;
    }

    public void setConversacion1(Conversacion conversacion1) {
        this.conversacion1 = conversacion1;
    }

    @XmlTransient
    public List<Estudio> getEstudioList() {
        return estudioList;
    }

    public void setEstudioList(List<Estudio> estudioList) {
        this.estudioList = estudioList;
    }

    public UsuarioDeEventos getUsuarioDeEventos() {
        return usuarioDeEventos;
    }

    public void setUsuarioDeEventos(UsuarioDeEventos usuarioDeEventos) {
        this.usuarioDeEventos = usuarioDeEventos;
    }

    @XmlTransient
    public List<Evento> getEventoList() {
        return eventoList;
    }

    public void setEventoList(List<Evento> eventoList) {
        this.eventoList = eventoList;
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
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tawevents.entity.Usuario[ id=" + id + " ]";
    }
    
    public UsuarioDTO getDTO() {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(id);
        dto.setNickname(nickname);
        dto.setContrasena(contrasena);
        dto.setTipoUsuario(tipoUsuario);
        if(usuarioDeEventos != null){
            dto.setUsuarioDeEventos(usuarioDeEventos.getId());
        }
        if(eventoList != null){
            List<Integer> eventos = new ArrayList<Integer>();
            for(Evento e: eventoList){
            eventos.add(e.getId());
        }
        dto.setEventoList(eventos);
        }
        return dto;
    }
    
}
