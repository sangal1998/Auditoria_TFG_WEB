/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import Entidades.Reglamento;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import sessionbeans.ReglamentoFacadeLocal;

/**
 *
 * @author Santiago
 */
@Named(value = "managedBeanReglamento")
@SessionScoped
public class ManagedBeanReglamento implements Serializable {

    @EJB
    private ReglamentoFacadeLocal reglamentoFacade;

    private List<Reglamento> filteredReg;
    /**
     * Creates a new instance of ManagedBeanReglamento
     */
    public ManagedBeanReglamento() {
    }

    public List<Reglamento> getFilteredReg() {
        return filteredReg;
    }

    public void setFilteredReg(List<Reglamento> filteredReg) {
        this.filteredReg = filteredReg;
    }
    
    public List<Reglamento> findAll() {
        return this.reglamentoFacade.findAll();
    }
    
    
}
