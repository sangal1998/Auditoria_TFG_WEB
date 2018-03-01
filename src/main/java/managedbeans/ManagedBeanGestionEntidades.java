/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import Entidades.Auditorias;
import Entidades.Fichero;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import sessionbeans.AuditoriasFacadeLocal;
import sessionbeans.FicheroFacadeLocal;

/**
 *
 * @author Santiago
 */
@Named(value = "managedBeanGestionEntidades")
@SessionScoped
public class ManagedBeanGestionEntidades implements Serializable {

    @EJB
    private AuditoriasFacadeLocal auditoriasFacade;
    @EJB
    private FicheroFacadeLocal ejbFacadeFichero;

    private Auditorias a = new Auditorias();

    private Date fecha;
    private String fechastring;
    private int idFichero;
    private String nombreFichero;

    private List<Auditorias> filteredaud;
    
    private List<Fichero> listaficheros;

    /**
     * Creates a new instance of ManagedBeanCategorias
     */
    public ManagedBeanGestionEntidades() {

    }

    public Auditorias getA() {
        return a;
    }

    public void setA(Auditorias a) {
        this.a = a;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFechastring() {
        return fechastring;
    }

    public void setFechastring(String fechastring) {
        this.fechastring = fechastring;
    }

    public int getIdFichero() {
        return idFichero;
    }

    public void setIdFichero(int idFichero) {
        this.idFichero = idFichero;
    }

    public String getNombreFichero() {
        return nombreFichero;
    }

    public void setNombreFichero(String nombreFichero) {
        this.nombreFichero = nombreFichero;
    }

    public List<Auditorias> getFilteredaud() {
        return filteredaud;
    }

    public void setFilteredaud(List<Auditorias> filteredaud) {
        this.filteredaud = filteredaud;
    }

    public List<Auditorias> findAll() {
        List<Auditorias> listaauditorias = this.auditoriasFacade.findAll();
        //antes de devolver tenemos que rellenar el campo nombre
        for(Auditorias aud: listaauditorias){
            //buscamos los nombres
            Fichero f = ejbFacadeFichero.find(aud.getAuditoriasPK().getIdFichero());
            aud.getAuditoriasPK().setNombre(f.getNombreFichero());
        }
        return listaauditorias;
    }
    
    public void delete(Auditorias a) {
        this.auditoriasFacade.remove(a);
        this.auditoriasFacade.findAll();
    }

    public List<Fichero> getListaficheros() {
        if(listaficheros==null || listaficheros.isEmpty()){
            return auditoriasFacade.findAllFicheros();
        }
        return listaficheros;
    }

    public void setListaficheros(List<Fichero> listaficheros) {
        this.listaficheros = listaficheros;
    }
    
    
 
}
