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
 * @author rafar
 */
public class EventoDTO {
    private Integer id;
    private String titulo;
    private String imagen;
    private String descripcion;
    private Date fecha;
    private Date fechaLimEntradas;
    private int precioEntrada;
    private int aforoMax;
    private int maxEntradasPorUsuario;
    private Boolean asientosAsignados;
    private Integer numFilas;
    private Integer asientosPorFila;
    private List<Integer> etiquetaList;
    private List<Integer> publicoList;
    private Integer usuario;

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

    public List<Integer> getEtiquetaList() {
        return etiquetaList;
    }

    public void setEtiquetaList(List<Integer> etiquetaList) {
        this.etiquetaList = etiquetaList;
    }

    public List<Integer> getPublicoList() {
        return publicoList;
    }

    public void setPublicoList(List<Integer> publicoList) {
        this.publicoList = publicoList;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }
}
