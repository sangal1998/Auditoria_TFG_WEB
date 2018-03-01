/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import java.util.Calendar;
import java.util.Date;
import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author Santiago
 */
@Named(value = "managedBeanObtenerHora")
@Dependent
public class ManagedBeanObtenerHora {

    /**
     * Creates a new instance of ManagedBeanObtenerHora
     */
    public ManagedBeanObtenerHora() {
    }
//    public class ObtenerFechaHora {
 
 public static void main ( String args[] ) {
   
  Date fecha1 = new Date ();
   
  System.out.println(fecha1.toString());
   
  // Segun la versión utilizada de Java, los dos siguientes
  // metodos pueden estar deprecated
  System.out.println(fecha1.toLocaleString());
  System.out.println(fecha1.toGMTString());
   
  Calendar cal1 = Calendar.getInstance();
    System.out.println(""+cal1.get(Calendar.DATE)+"/"+cal1.get(Calendar.MONTH)
    +"/"+cal1.get(Calendar.YEAR)+" "+cal1.get(Calendar.HOUR)
    +":"+cal1.get(Calendar.MINUTE)+":"+cal1.get(Calendar.SECOND)
    +":"+cal1.get(Calendar.MILLISECOND));
   
 } 
}