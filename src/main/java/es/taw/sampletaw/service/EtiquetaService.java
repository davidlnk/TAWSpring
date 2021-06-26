/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.taw.sampletaw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import es.taw.sampletaw.dao.EtiquetaRepository;
import es.taw.sampletaw.dto.EtiquetaDTO;
import es.taw.sampletaw.entity.Etiqueta;
import es.taw.sampletaw.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author rafar
 */
@Service
public class EtiquetaService {

    private EtiquetaRepository etiquetaRepository;

    @Autowired
    public void setEtiquetaRepository(EtiquetaRepository etiquetaRepository) {
        this.etiquetaRepository = etiquetaRepository;
    }


    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

     public List<EtiquetaDTO> convertirAListaDTOdirectamente(List<Integer> lista){
         return this.convertirAListaDTO(this.convertirAListaEtiqueta(lista));
     }

     public List<EtiquetaDTO> convertirAListaDTO(List<Etiqueta> lista){
         if (lista != null) {
            List<EtiquetaDTO> listaDTO = new ArrayList<>();
            for (Etiqueta e : lista) {
                listaDTO.add(e.getDTO());
            }
            return listaDTO;
        } else {
            return null;
        }
     }

     public List<Etiqueta> convertirAListaEtiqueta(List<Integer> lista){
         List<Etiqueta> listaEtiqueta = new ArrayList<>();
         if(lista != null ){
            for(int id : lista){
             listaEtiqueta.add(etiquetaRepository.findById(id).get());
            }
            return listaEtiqueta;
         }else{
             return null;
         }

     }

     public EtiquetaDTO findByNombreExacto(String nombre) {
         Etiqueta e = etiquetaRepository.findByNombreExacto(nombre);
         return (e == null) ? null : e.getDTO();
     }

     public EtiquetaDTO findBySimilarNombre(String nombre){
         Etiqueta et = etiquetaRepository.findBySimilarNombre(nombre);
         return (et == null) ? null : et.getDTO();
     }

     public void remove(EtiquetaDTO e){
         etiquetaRepository.delete(etiquetaRepository.findById(e.getId()).get());
     }

    public void remove2(Integer id){
        this.etiquetaRepository.deleteById(id);
    }

     public void edit(EtiquetaDTO e){
         etiquetaRepository.save(etiquetaRepository.findById(e.getId()).get());
     }

     public EtiquetaDTO filtroNombre(String nombre){
        Etiqueta etiqueta = etiquetaRepository.findBySimilarNombreI(nombre);
        return etiqueta.getDTO();
    }

     public EtiquetaDTO buscarEtiqueta (Integer id) {
        Etiqueta etiqueta = this.etiquetaRepository.findById(id).get();
        if (etiqueta != null) {
            return etiqueta.getDTO();
        } else {
            return null;
        }
    }

    public List<EtiquetaDTO> findBySimilarNombreMuchas(String nombre){
        List<Etiqueta> et = etiquetaRepository.findBySimilarNombreMuchas(nombre);
        return this.convertirAListaDTO(et);
    }

    public List<EtiquetaDTO> findAll(){
        return this.convertirAListaDTO(etiquetaRepository.findAll());
    }

     public int crearEtiqueta (String nombre) {
        Etiqueta etiqueta = new Etiqueta();
        etiqueta.setNombre(nombre);
        this.etiquetaRepository.save(etiqueta);
        return etiqueta.getId();
    }
}
