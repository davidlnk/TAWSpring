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

    public FiltroEstudios() {
        Calendar calendario = Calendar.getInstance();
        hastaFecha = calendario.getTime(); // Hoy

        calendario.add(Calendar.MONTH, -6);
        desdeFecha = calendario.getTime(); // Hace 6 mes

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
}
