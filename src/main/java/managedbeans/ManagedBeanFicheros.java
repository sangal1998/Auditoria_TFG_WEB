/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import Entidades.Fichero;
import Entidades.NivelFichero;
import Entidades.TipoFichero;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import sessionbeans.FicheroFacadeLocal;

/**
 *
 * @author Santiago
 */
@Named(value = "managedBeanFicheros")
@SessionScoped
public class ManagedBeanFicheros implements Serializable {

    @EJB
    private FicheroFacadeLocal ficheroFacade;

    
    
    private Fichero f = new Fichero();
    private int idfichero;
    private String nombrefichero;
    private List<Fichero> filteredfic;
//    private List<NivelFichero> selectNivelFichero;
//    private List<TipoFichero> selectTipoFichero;
    private String idtipofichero;
    private String tipofichero;
    private String idnivelfichero;
    private String nivelfichero;
   
    /**
     * Creates a new instance of ManagedBeanFicheros
     */
    
    public ManagedBeanFicheros() {
    
    }

    public Fichero getF() {
        return f;
    }

    public void setF(Fichero f) {
        this.f = f;
    }

    public int getIdfichero() {
        return idfichero;
    }

    public void setIdfichero(int idfichero) {
        this.idfichero = idfichero;
    }

    public String getNombrefichero() {
        return nombrefichero;
    }

    public void setNombrefichero(String nombrefichero) {
        this.nombrefichero = nombrefichero;
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

    public List<Fichero> getFilteredfic() {
        return filteredfic;
    }

    public void setFilteredfic(List<Fichero> filteredfic) {
        this.filteredfic = filteredfic;
    }
    
    public List<Fichero> findAll() {
        return this.ficheroFacade.findAll();
    }

    public void delete(Fichero f) {
        this.ficheroFacade.remove(f);
        this.ficheroFacade.findAll();
    }


    public String edit(Fichero f) {
        this.f = f;
        return "edit";
    }
    public String edit() {
        TipoFichero fa = new TipoFichero(idtipofichero, "");
        this.f.setTipoFichero(fa);
        NivelFichero na = new NivelFichero(idnivelfichero);
        this.f.setNivelFichero(na);

        this.ficheroFacade.edit(this.f);
        this.ficheroFacade.findAll();
        this.f = new Fichero();
        return "ficheros";
    }
    public String add() {
        TipoFichero fa = new TipoFichero(idtipofichero, "");
        this.f.setTipoFichero(fa);
        NivelFichero na = new NivelFichero(idnivelfichero);
        this.f.setNivelFichero(na);
        this.ficheroFacade.create(this.f);
        this.f = new Fichero();
        return "ficheros";
    }  
}
