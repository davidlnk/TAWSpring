/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.taw.sampletaw.service;

import java.util.ArrayList;
import java.util.List;
import es.taw.sampletaw.dto.EtiquetaDTO;
import es.taw.sampletaw.entity.Etiqueta;

/**
 *
 * @author rafar
 */
//@Stateless
public class EtiquetaService {
//     @EJB
//     private EtiquetaFacade etiquetaFacade;
//    // Add business logic below. (Right-click in editor and choose
//    // "Insert Code > Add Business Method")
//
//     public List<EtiquetaDTO> convertirAListaDTOdirectamente(List<Integer> lista){
//         return this.convertirAListaDTO(this.convertirAListaEtiqueta(lista));
//     }
//
//     public List<EtiquetaDTO> convertirAListaDTO(List<Etiqueta> lista){
//         if (lista != null) {
//            List<EtiquetaDTO> listaDTO = new ArrayList<>();
//            for (Etiqueta e : lista) {
//                listaDTO.add(e.getDTO());
//            }
//            return listaDTO;
//        } else {
//            return null;
//        }
//     }
//
//     public List<Etiqueta> convertirAListaEtiqueta(List<Integer> lista){
//         List<Etiqueta> listaEtiqueta = new ArrayList<>();
//         if(lista != null ){
//            for(int id : lista){
//             listaEtiqueta.add(etiquetaFacade.find(id));
//            }
//            return listaEtiqueta;
//         }else{
//             return null;
//         }
//
//     }
//
//     public EtiquetaDTO findByNombreExacto(String nombre) {
//         Etiqueta e = etiquetaFacade.findByNombreExacto(nombre);
//         return (e == null) ? null : e.getDTO();
//     }
//
//     public EtiquetaDTO findBySimilarNombre(String nombre){
//         Etiqueta et = etiquetaFacade.findBySimilarNombre(nombre);
//         return (et == null) ? null : et.getDTO();
//     }
//
//     public void remove(EtiquetaDTO e){
//         etiquetaFacade.remove(etiquetaFacade.find(e.getId()));
//     }
//
//     public void edit(EtiquetaDTO e){
//         etiquetaFacade.edit(etiquetaFacade.find(e.getId()));
//     }
//
//     public EtiquetaDTO filtroNombre(String nombre){
//        Etiqueta etiqueta = etiquetaFacade.findBySimilarNombreI(nombre);
//        return etiqueta.getDTO();
//    }
//
//     public EtiquetaDTO buscarEtiqueta (Integer id) {
//        Etiqueta etiqueta = this.etiquetaFacade.find(id);
//        if (etiqueta != null) {
//            return etiqueta.getDTO();
//        } else {
//            return null;
//        }
//    }
//
//     public int crearEtiqueta (String nombre) {
//        Etiqueta etiqueta = new Etiqueta();
//        etiqueta.setNombre(nombre);
//        this.etiquetaFacade.create(etiqueta);
//        return etiqueta.getId();
//    }
}
