/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import Entidades.TipoFichero;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import sessionbeans.TipoFicheroFacadeLocal;

/**
 *
 * @author Santiago
 */
@Named(value = "managedBeanTipoFichero")
@SessionScoped
public class ManagedBeanTipoFichero implements Serializable {

    @EJB
    private TipoFicheroFacadeLocal tipoFicheroFacade;

    private String idtipofichero;
    private String tipofichero;       
    private List<TipoFichero> listaTipoFichero;
    private List<SelectItem> itemsTipoFichero;
    /**
     * Creates a new instance of ManagedBeanTipoFichero
     */
   
       
    public ManagedBeanTipoFichero() {
    }

    public String getIdtipofichero() {
        return idtipofichero;
    }

    public void setIdtipofichero(String idtipofichero) {
        this.idtipofichero = idtipofichero;
    }

    public String getTipofichero() {
        return tipofichero;
    }

    public void setTipofichero(String tipofichero) {
        this.tipofichero = tipofichero;
    }

    public List<TipoFichero> getListaTipoFichero() {
        return listaTipoFichero;
    }

    public void setListaTipoFichero(List<TipoFichero> listaTipoFichero) {
        this.listaTipoFichero = listaTipoFichero;
    }

    public List<SelectItem> getItemsTipoFichero() {
        return itemsTipoFichero;
    }

    public void setItemsTipoFichero(List<SelectItem> itemsTipoFichero) {
        this.itemsTipoFichero = itemsTipoFichero;
    }
    
    public List<SelectItem> seleccionarItem() {
        listaTipoFichero = tipoFicheroFacade.findAll();


        this.itemsTipoFichero = new ArrayList<SelectItem>();
        for (int i = 0; i <= listaTipoFichero.size() - 1; i++) {
            SelectItem nivelItem = new SelectItem(listaTipoFichero.get(i).getIdTipoFichero(),listaTipoFichero.get(i).getTipoFichero());
            this.itemsTipoFichero.add(nivelItem);
        }
        return itemsTipoFichero;
    }    
}
