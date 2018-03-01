/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import Entidades.Usuario;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import sessionbeans.UsuarioFacadeLocal;

/**
 *
 * @author Santiago
 */
@Named(value = "managedBeanLoginUsuarios")
@SessionScoped
public class ManagedBeanLoginUsuarios implements Serializable {

    @EJB
    private UsuarioFacadeLocal usuarioFacade;

    /**
     * Creates a new instance of ManagedBeanLoginUsuarios
     */
    private String login;
    private String password; 
    private String acceso;

    public ManagedBeanLoginUsuarios() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAcceso() {
        return acceso;
    }

    public void setAcceso(String acceso) {
        this.acceso = acceso;
    }

    public String loginUsuario() {

        try {
            Usuario u = usuarioFacade.findByName(this.login);
            if (u.getPassword().equals(this.password)) {
               

                    return "ok";

                } else {
                    
                    return "User";
                    }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    
}
