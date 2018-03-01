/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import Entidades.NivelFichero;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import sessionbeans.NivelFicheroFacadeLocal;

/**
 *
 * @author Santiago
 */
@Named(value = "managedBeanNivelFichero")
@SessionScoped
public class ManagedBeanNivelFichero implements Serializable {

    @EJB
    private NivelFicheroFacadeLocal nivelFicheroFacade;

    private String idnivelfichero;
    private String nivelfichero;       
    private List<NivelFichero> listaNivelFichero;
    private List<SelectItem> itemsNivelFichero;
    
    /**
     * Creates a new instance of ManagedBeanNivelFichero
     */
    public ManagedBeanNivelFichero() {
    }

    public String getIdnivelfichero() {
        return idnivelfichero;
    }

    public void setIdnivelfichero(String idnivelfichero) {
        this.idnivelfichero = idnivelfichero;
    }

    public String getNivelfichero() {
        return nivelfichero;
    }

    public void setNivelfichero(String nivelfichero) {
        this.nivelfichero = nivelfichero;
    }

    public List<NivelFichero> getListaNivelFichero() {
        return listaNivelFichero;
    }

    public void setListaNivelFichero(List<NivelFichero> listaNivelFichero) {
        this.listaNivelFichero = listaNivelFichero;
    }

    public List<SelectItem> getItemsNivelFichero() {
        return itemsNivelFichero;
    }

    public void setItemsNivelFichero(List<SelectItem> itemsNivelFichero) {
        this.itemsNivelFichero = itemsNivelFichero;
    }
    
    public List<SelectItem> seleccionarItem() {
        listaNivelFichero = nivelFicheroFacade.findAll();


        this.itemsNivelFichero = new ArrayList<SelectItem>();
        for (int i = 0; i <= listaNivelFichero.size() - 1; i++) {
            SelectItem nivelItem = new SelectItem(listaNivelFichero.get(i).getIdNivelFichero(),listaNivelFichero.get(i).getNivelFichero());
            this.itemsNivelFichero.add(nivelItem);
        }
        return itemsNivelFichero;
    }   
    
    
}
