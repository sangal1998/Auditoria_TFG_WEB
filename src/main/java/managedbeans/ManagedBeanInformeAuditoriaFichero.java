/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import Entidades.Auditorias;
import Entidades.Fichero;
import Entidades.Reglamento;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import java.awt.Color;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import sessionbeans.AuditoriasFacadeLocal;
import sessionbeans.FicheroFacadeLocal;
import sessionbeans.ReglamentoFacadeLocal;

/**
 *
 * @author Santiago
 */
@Named(value = "managedBeanInformeAuditoriaFichero")
@SessionScoped
public class ManagedBeanInformeAuditoriaFichero implements Serializable {
    
    @EJB
    private FicheroFacadeLocal ejbFacadeFichero;
    
    @EJB
    private AuditoriasFacadeLocal ejbFacadeAuditorias;
    
    @EJB
    private ReglamentoFacadeLocal ejbFacadeReglamento;
    
    private List<Fichero> listaficheros = null;
    
    private Integer selectedfichero;
    
    private Fichero ficheroanalizado;
      
    private Auditorias auditoriaanalizada;
    
    private List<Auditorias> listaauditorias;
    
    private static final int MAX_PREGUNTAS=126;
    
    /**
     * Con este método obtenemos todos los ficheros
     */
    public void obtenerFicheros(){
        listaficheros = ejbFacadeFichero.findAll();
    }

    public List<Fichero> getListaficheros() {
        return listaficheros;
    }

    public void setListaficheros(List<Fichero> listaficheros) {
        this.listaficheros = listaficheros;
    }

    public Integer getSelectedfichero() {
        return selectedfichero;
    }

    public void setSelectedfichero(Integer selectedfichero) {
        this.selectedfichero = selectedfichero;
    }
    
    public void resetFichero(){
        this.selectedfichero=null;
    }
    
    public String irAListado(){
        //buscamos el fichero seleccionado para tener sus datos
         //si llega aqui tenemos ir al menu de test
        //recuperamos el fichero y se lo pasamos al método
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        session.setAttribute("idfichero", selectedfichero);
        //vamos a buscar el fichero
        ficheroanalizado = ejbFacadeFichero.find(selectedfichero);
        
        //ahora buscamos la lista de auditorias para guardarla en el bean
        listaauditorias = ejbFacadeAuditorias.findById(ficheroanalizado);
        return "/vistas/listficheros/listado.xhtml";
    }
    
        /**
     * Con este método obtenemos todos los ficheros
     * @return 
     */
    public String obtenerAuditorias(){
        listaauditorias = ejbFacadeAuditorias.findAll();
        listaficheros = ejbFacadeFichero.findAll();
        
        for(Auditorias a: listaauditorias){
            //buscamos los nombres
            Fichero f = ejbFacadeFichero.find(a.getAuditoriasPK().getIdFichero());
            a.getAuditoriasPK().setNombre(f.getNombreFichero());
        }
        return "/vistas/listficheros/listado.xhtml";
    }
    
    

    public Fichero getFicheroanalizado() {
        return ficheroanalizado;
    }

    public void setFicheroanalizado(Fichero ficheroanalizado) {
        this.ficheroanalizado = ficheroanalizado;
    }

      
    
    public String resetTest(){
        ficheroanalizado=null;
        selectedfichero=null;
        listaauditorias=null;
        return "/vistas/listficheros.xhtml";
    }
     
    public String getMostrarResultados() {
        //debemos mostrar los resultados
        StringBuilder strb = new StringBuilder();
        if(auditoriaanalizada==null){
            return "No existe ningún informe para dicho informe";
        }
        for(int i=1; i <= MAX_PREGUNTAS; i++){
            try{
                PropertyDescriptor p = new PropertyDescriptor("p" + i, Auditorias.class);
                Integer c = (Integer) p.getReadMethod().invoke(auditoriaanalizada);
                if(c!=null && c==0){
                    //hay que buscar dicha pregunta y poner lo que pone en el campo reglamento
                    Reglamento r = ejbFacadeReglamento.find(i);
                    if(r!=null && r.getIncumplimiento()!=null && !r.getIncumplimiento().isEmpty()){
                        strb.append("<li>");
                        strb.append(r.getIncumplimiento());
                        strb.append("</li>");
                    }
                }
                
            }
            catch(Exception e){
                System.out.println("Error:" + e.getMessage());
            }
        }
        return strb.toString();
    }

    public void setAuditoriaanalizada(Auditorias auditoriaanalizada) {
        this.auditoriaanalizada = auditoriaanalizada;
    }

    public List<Auditorias> getListaauditorias() {
        return listaauditorias;
    }

    public void setListaauditorias(List<Auditorias> listaauditorias) {
        this.listaauditorias = listaauditorias;
    }
    
    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);
        //Añadimos un texto base en la exportacion
        pdf.add(new Paragraph ("LISTADO DE INFORMES REALIZADOS",FontFactory.getFont("arial",14,Font.BOLDITALIC, Color.DARK_GRAY)));
        pdf.add(new Paragraph (" "));
    }
   
}
