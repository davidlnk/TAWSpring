package es.taw.sampletaw.vo;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FiltroEstudios {
    private Date desdeFecha;
    private Date hastaFecha;
    private String ordenporfecha;
    private String descripcion;

    public FiltroEstudios() {
        Calendar calendario = Calendar.getInstance();
        calendario.add(Calendar.MONTH, 3);
        hastaFecha = calendario.getTime(); // Dentro de 3 meses

        calendario.add(Calendar.MONTH, -6);
        desdeFecha = calendario.getTime(); // Hace 3 mes

        descripcion = "";
        ordenporfecha = "a";
    }

    public Date getDesdeFecha() {
        return desdeFecha;
    }

    public void setDesdeFecha(Date desdeFecha) {
        this.desdeFecha = desdeFecha;
    }

    public Date getHastaFecha() {
        return hastaFecha;
    }

    public void setHastaFecha(Date hastaFecha) {
        this.hastaFecha = hastaFecha;
    }

    public String getOrdenporfecha() {
        return ordenporfecha;
    }

    public void setOrdenporfecha(String ordenporfecha) {
        this.ordenporfecha = ordenporfecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
