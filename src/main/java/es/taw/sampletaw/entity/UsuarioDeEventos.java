/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.taw.sampletaw.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import es.taw.sampletaw.dto.UsuarioDeEventosDTO;

/**
 *
 * @author David
 */
@Entity
@Table(name = "USUARIO_DE_EVENTOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioDeEventos.findAll", query = "SELECT u FROM UsuarioDeEventos u")
    , @NamedQuery(name = "UsuarioDeEventos.findById", query = "SELECT u FROM UsuarioDeEventos u WHERE u.id = :id")
    , @NamedQuery(name = "UsuarioDeEventos.findByNombre", query = "SELECT u FROM UsuarioDeEventos u WHERE u.nombre = :nombre")
    , @NamedQuery(name = "UsuarioDeEventos.findByApellidos", query = "SELECT u FROM UsuarioDeEventos u WHERE u.apellidos = :apellidos")
    , @NamedQuery(name = "UsuarioDeEventos.findByCorreo", query = "SELECT u FROM UsuarioDeEventos u WHERE u.correo = :correo")
    , @NamedQuery(name = "UsuarioDeEventos.findByCiudad", query = "SELECT u FROM UsuarioDeEventos u WHERE u.ciudad = :ciudad")
    , @NamedQuery(name = "UsuarioDeEventos.findByFechaNacimiento", query = "SELECT u FROM UsuarioDeEventos u WHERE u.fechaNacimiento = :fechaNacimiento")
    , @NamedQuery(name = "UsuarioDeEventos.findBySexo", query = "SELECT u FROM UsuarioDeEventos u WHERE u.sexo = :sexo")})
public class UsuarioDeEventos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;
    @Basic(optional = false)
    @Column(name = "APELLIDOS", nullable = false, length = 50)
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "CORREO", nullable = false, length = 50)
    private String correo;
    @Column(name = "CIUDAD", length = 50)
    private String ciudad;
    @Column(name = "FECHA_NACIMIENTO")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Column(name = "SEXO", length = 20)
    private String sexo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioDeEventos")
    private List<Publico> publicoList;
    @OneToMany(mappedBy = "usuarioDeEventos")
    private List<Usuario> usuarioList;

    public UsuarioDeEventos() {
    }

    public UsuarioDeEventos(Integer id) {
        this.id = id;
    }

    public UsuarioDeEventos(Integer id, String nombre, String apellidos, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
    }

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

    @XmlTransient
    public List<Publico> getPublicoList() {
        return publicoList;
    }

    public void setPublicoList(List<Publico> publicoList) {
        this.publicoList = publicoList;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
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
        if (!(object instanceof UsuarioDeEventos)) {
            return false;
        }
        UsuarioDeEventos other = (UsuarioDeEventos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tawevents.entity.UsuarioDeEventos[ id=" + id + " ]";
    }
    
    public UsuarioDeEventosDTO getDTO() {
        UsuarioDeEventosDTO dto = new UsuarioDeEventosDTO();
        dto.setId(id);
        dto.setNombre(nombre);
        dto.setApellidos(apellidos);
        dto.setCorreo(correo);
        dto.setCiudad(ciudad);
        dto.setFechaNacimiento(fechaNacimiento);
        dto.setSexo(sexo);
                
        if(publicoList != null){
            List<Integer> publicos = new ArrayList<>();
            for(Publico p : publicoList) {
                publicos.add(p.getId());
            }
            dto.setPublicoList(publicos);
        }
        
        if(usuarioList != null){
            List<Integer> usuarios = new ArrayList<>();
            for(Usuario u : usuarioList) {
                usuarios.add(u.getId());
            }
            dto.setUsuarioList(usuarios);
        }
        
        return dto;
    }
    
}
