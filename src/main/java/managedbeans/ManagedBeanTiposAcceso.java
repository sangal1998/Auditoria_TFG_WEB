/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import Entidades.TiposAcceso;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import sessionbeans.TiposAccesoFacadeLocal;

/**
 *
 * @author Santiago
 */
@Named(value = "managedBeanTiposAcceso")
@SessionScoped
public class ManagedBeanTiposAcceso implements Serializable {

    /**
     * Creates a new instance of ManagedBeanTiposAcceso
     */
    
    @EJB
    private TiposAccesoFacadeLocal TiposAccesoFacade;
    
    private int idacceso;
    private String acceso;       
    private List<TiposAcceso> listaAcceso;
    private List<SelectItem> itemsAcceso;
    
     ManagedBeanTiposAcceso() {
    }

    public int getIdacceso() {
        return idacceso;
    }

    public void setIdacceso(int idacceso) {
        this.idacceso = idacceso;
    }

    public String getAcceso() {
        return acceso;
    }

    public void setAcceso(String acceso) {
        this.acceso = acceso;
    }
    
        public List<TiposAcceso> getListaAcceso() {
        return listaAcceso;
    }


    public void setListaAcceso(List<TiposAcceso> listaAcceso) {
        this.listaAcceso = listaAcceso;
    }


    public List<SelectItem> getItemsAcceso() {
        return itemsAcceso;
    }


    public void setItemsAcceso(List<SelectItem> itemsAcceso) {
        this.itemsAcceso = itemsAcceso;
    }


    public List<SelectItem> seleccionarItem() {
        listaAcceso = TiposAccesoFacade.findAll();


        this.itemsAcceso = new ArrayList<SelectItem>();
        for (int i = 0; i <= listaAcceso.size() - 1; i++) {
            SelectItem nivelItem = new SelectItem(listaAcceso.get(i).getIdAcceso(),listaAcceso.get(i).getAcceso());
            this.itemsAcceso.add(nivelItem);
        }
        return itemsAcceso;
    }
}
