/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import Entidades.Categorias;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import sessionbeans.CategoriasFacadeLocal;

/**
 *
 * @author Santiago
 */
@Named(value = "managedBeanCategorias")
@SessionScoped
public class ManagedBeanCategorias implements Serializable {

    @EJB
    private CategoriasFacadeLocal categoriasFacade;

    private Categorias c = new Categorias();

    private String idcategoria;
    private String categoria;
    private List<Categorias> filteredcat;

    /**
     * Creates a new instance of ManagedBeanCategorias
     */
    public ManagedBeanCategorias() {
    }

    public Categorias getC() {
        return c;
    }

    public void setC(Categorias c) {
        this.c = c;
    }

    public String getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(String idcategoria) {
        this.idcategoria = idcategoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<Categorias> getFilteredcat() {
        return filteredcat;
    }

    public void setFilteredcat(List<Categorias> filteredcat) {
        this.filteredcat = filteredcat;
    }

    public List<Categorias> findAll() {
        return this.categoriasFacade.findAll();
    }
    
    public void delete(Categorias c) {
        this.categoriasFacade.remove(c);
        this.categoriasFacade.findAll();
    }

    public String edit(Categorias c) {
        this.c = c;
        return "edit";
    } 
    
        public String edit() {
        this.categoriasFacade.edit(this.c);
        this.categoriasFacade.findAll();
        this.c = new Categorias();
        return "categorias";
    }
        
        
    public String add() {
        this.categoriasFacade.create(this.c);
        this.c = new Categorias();
        return "categorias";
    }
}
