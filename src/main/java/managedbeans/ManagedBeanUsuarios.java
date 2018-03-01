/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import Entidades.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import sessionbeans.UsuarioFacadeLocal;
import Entidades.TiposAcceso;

/**
 *
 * @author Santiago
 */
@Named(value = "managedBeanUsuarios")
@SessionScoped
public class ManagedBeanUsuarios implements Serializable {


    @EJB
    private UsuarioFacadeLocal usuarioFacade;


    private Usuario u = new Usuario();


    private List<Usuario> filteredUsu;
    private List<TiposAcceso> selectTipoAcceso;
    private int idacceso;
    private String acceso;


    public ManagedBeanUsuarios() {
    }


    public List<Usuario> getFilteredUsu() {
        return filteredUsu;
    }


    public void setFilteredUsu(List<Usuario> filteredUsu) {
        this.filteredUsu = filteredUsu;
    }


    public List<Usuario> findAll() {
        return this.usuarioFacade.findAll();
    }


    public Usuario getU() {
        return u;
    }


    public void setU(Usuario u) {
        this.u = u;
    }


    public String getAcceso() {
        return acceso;
    }

    public int getIdacceso() {
        return idacceso;
    }

    public void setIdacceso(int idacceso) {
        this.idacceso = idacceso;
    }


    public void setacceso(String acceso) {
        this.acceso = acceso;
    }



    public String add() {
        TiposAcceso ta = new TiposAcceso(idacceso, "");
        this.u.setAcceso(ta);
        this.usuarioFacade.create(this.u);
        this.u = new Usuario();
        return "usuario";
    }
    
    public String edit() {
        TiposAcceso ta = new TiposAcceso(idacceso, "");
        this.u.setAcceso(ta);
        this.usuarioFacade.edit(this.u);
        this.usuarioFacade.findAll();
        this.u = new Usuario();
        return "usuario";
    }

    public void delete(Usuario u) {
        this.usuarioFacade.remove(u);
        this.usuarioFacade.findAll();
    }


    public String edit(Usuario u) {
        this.u = u;
        return "edit";
    }


    
    
}