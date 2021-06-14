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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import es.taw.sampletaw.dto.EventoDTO;
import es.taw.sampletaw.dto.UsuarioDTO;

/**
 *
 * @author David
 */
@Entity
@Table(name = "EVENTO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evento.findAll", query = "SELECT e FROM Evento e")
    , @NamedQuery(name = "Evento.findById", query = "SELECT e FROM Evento e WHERE e.id = :id")
    , @NamedQuery(name = "Evento.findByTitulo", query = "SELECT e FROM Evento e WHERE e.titulo = :titulo")
    , @NamedQuery(name = "Evento.findByImagen", query = "SELECT e FROM Evento e WHERE e.imagen = :imagen")
    , @NamedQuery(name = "Evento.findByDescripcion", query = "SELECT e FROM Evento e WHERE e.descripcion = :descripcion")
    , @NamedQuery(name = "Evento.findByFecha", query = "SELECT e FROM Evento e WHERE e.fecha = :fecha")
    , @NamedQuery(name = "Evento.findByFechaLimEntradas", query = "SELECT e FROM Evento e WHERE e.fechaLimEntradas = :fechaLimEntradas")
    , @NamedQuery(name = "Evento.findByPrecioEntrada", query = "SELECT e FROM Evento e WHERE e.precioEntrada = :precioEntrada")
    , @NamedQuery(name = "Evento.findByAforoMax", query = "SELECT e FROM Evento e WHERE e.aforoMax = :aforoMax")
    , @NamedQuery(name = "Evento.findByMaxEntradasPorUsuario", query = "SELECT e FROM Evento e WHERE e.maxEntradasPorUsuario = :maxEntradasPorUsuario")
    , @NamedQuery(name = "Evento.findByAsientosAsignados", query = "SELECT e FROM Evento e WHERE e.asientosAsignados = :asientosAsignados")
    , @NamedQuery(name = "Evento.findByNumFilas", query = "SELECT e FROM Evento e WHERE e.numFilas = :numFilas")
    , @NamedQuery(name = "Evento.findByAsientosPorFila", query = "SELECT e FROM Evento e WHERE e.asientosPorFila = :asientosPorFila")})
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "TITULO", nullable = false, length = 50)
    private String titulo;
    @Column(name = "IMAGEN", length = 500)
    private String imagen;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION", nullable = false, length = 500)
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "FECHA", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "FECHA_LIM_ENTRADAS", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaLimEntradas;
    @Basic(optional = false)
    @Column(name = "PRECIO_ENTRADA", nullable = false)
    private int precioEntrada;
    @Basic(optional = false)
    @Column(name = "AFORO_MAX", nullable = false)
    private int aforoMax;
    @Basic(optional = false)
    @Column(name = "MAX_ENTRADAS_POR_USUARIO", nullable = false)
    private int maxEntradasPorUsuario;
    @Basic(optional = false)
    @Column(name = "ASIENTOS_ASIGNADOS", nullable = false)
    private Boolean asientosAsignados;
    @Column(name = "NUM_FILAS")
    private Integer numFilas;
    @Column(name = "ASIENTOS_POR_FILA")
    private Integer asientosPorFila;
    @JoinTable(name = "FILTRO", joinColumns = {
        @JoinColumn(name = "EVENTO", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "ETIQUETA", referencedColumnName = "ID")})
    @ManyToMany
    private List<Etiqueta> etiquetaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evento")
    private List<Publico> publicoList;
    @JoinColumn(name = "USUARIO", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Evento() {
    }

    public Evento(Integer id) {
        this.id = id;
    }

    public Evento(Integer id, String titulo, String descripcion, Date fecha, Date fechaLimEntradas, int precioEntrada, int aforoMax, int maxEntradasPorUsuario, Boolean asientosAsignados) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.fechaLimEntradas = fechaLimEntradas;
        this.precioEntrada = precioEntrada;
        this.aforoMax = aforoMax;
        this.maxEntradasPorUsuario = maxEntradasPorUsuario;
        this.asientosAsignados = asientosAsignados;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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

    public Date getFechaLimEntradas() {
        return fechaLimEntradas;
    }

    public void setFechaLimEntradas(Date fechaLimEntradas) {
        this.fechaLimEntradas = fechaLimEntradas;
    }

    public int getPrecioEntrada() {
        return precioEntrada;
    }

    public void setPrecioEntrada(int precioEntrada) {
        this.precioEntrada = precioEntrada;
    }

    public int getAforoMax() {
        return aforoMax;
    }

    public void setAforoMax(int aforoMax) {
        this.aforoMax = aforoMax;
    }

    public int getMaxEntradasPorUsuario() {
        return maxEntradasPorUsuario;
    }

    public void setMaxEntradasPorUsuario(int maxEntradasPorUsuario) {
        this.maxEntradasPorUsuario = maxEntradasPorUsuario;
    }

    public Boolean getAsientosAsignados() {
        return asientosAsignados;
    }

    public void setAsientosAsignados(Boolean asientosAsignados) {
        this.asientosAsignados = asientosAsignados;
    }

    public Integer getNumFilas() {
        return numFilas;
    }

    public void setNumFilas(Integer numFilas) {
        this.numFilas = numFilas;
    }

    public Integer getAsientosPorFila() {
        return asientosPorFila;
    }

    public void setAsientosPorFila(Integer asientosPorFila) {
        this.asientosPorFila = asientosPorFila;
    }

    @XmlTransient
    public List<Etiqueta> getEtiquetaList() {
        return etiquetaList;
    }

    public void setEtiquetaList(List<Etiqueta> etiquetaList) {
        this.etiquetaList = etiquetaList;
    }

    @XmlTransient
    public List<Publico> getPublicoList() {
        return publicoList;
    }

    public void setPublicoList(List<Publico> publicoList) {
        this.publicoList = publicoList;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    
     public String getEtiquetasToString() {
        String str = "";
        for(Etiqueta e : this.getEtiquetaList()){
            str = str + e.getNombre() + " ";
        }
        return str;
    }

     public String getEtiquetasToString1() {
        String str = "";
        for(Etiqueta e : this.getEtiquetaList()){
            str = str + e.getNombre() + " ";
        }
        return str;
    }
     
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        if (!(object instanceof Evento)) {
            return false;
        }
        Evento other = (Evento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tawevents.entity.Evento[ id=" + id + " ]";
    }
    
     public EventoDTO getDTO() {
        EventoDTO dto = new EventoDTO();
        dto.setId(id);
        dto.setTitulo(titulo);
        dto.setImagen(imagen);
        dto.setDescripcion(descripcion);
        dto.setFecha(fecha);
        dto.setFechaLimEntradas(fechaLimEntradas);
        dto.setPrecioEntrada(precioEntrada);
        dto.setAforoMax(aforoMax);
        dto.setMaxEntradasPorUsuario(maxEntradasPorUsuario);
        dto.setAsientosAsignados(asientosAsignados);
        dto.setNumFilas(numFilas);
        dto.setAsientosPorFila(asientosPorFila);
        
        List<Integer> etList = new ArrayList<>();
        for(Etiqueta e : etiquetaList){
            etList.add(e.getId());
        }
        dto.setEtiquetaList(etList);
        
        List<Integer> puList = new ArrayList<>();
        for(Publico p : publicoList){
            puList.add(p.getId());
        }
        dto.setPublicoList(puList);
        
        if(usuario != null){
            dto.setUsuario(usuario.getId());
        }
        
        return dto;
    }
    
}
