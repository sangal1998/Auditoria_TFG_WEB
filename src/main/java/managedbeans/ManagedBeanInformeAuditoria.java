/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import Entidades.Auditorias;
import Entidades.Fichero;
import Entidades.Reglamento;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import sessionbeans.AuditoriasFacadeLocal;
import sessionbeans.FicheroFacadeLocal;
import sessionbeans.ReglamentoFacadeLocal;
import utilidades.JsfUtil;


/**
 *
 * @author Santiago
 */
@Named(value = "managedBeanInformeAuditoria")
@SessionScoped
public class ManagedBeanInformeAuditoria implements Serializable {
  
    @EJB
    private FicheroFacadeLocal ejbFacadeFichero;
    
    @EJB
    private AuditoriasFacadeLocal ejbFacadeAuditorias;
    
    @EJB
    private ReglamentoFacadeLocal ejbFacadeReglamento;
    
    private List<Auditorias> listaauditorias = null;
    
    private List<Fichero> listaficheros = null;
    
    private Integer selectedfichero;
    
    private Fichero ficheroanalizado;
    
    private Auditorias auditoriaanalizada;

    private static final int MAX_PREGUNTAS=126;
   
    
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
        return "/vistas/listdepartamento/listado.xhtml";
    }
    
    
     public String generarInforme(Auditorias a){
        this.auditoriaanalizada = a;
        ficheroanalizado = ejbFacadeFichero.find(auditoriaanalizada.getAuditoriasPK().getIdFichero());
        return "/vistas/listdepartamento/informe.xhtml";
    }
     
     public String volverListado(){
         return "/vistas/listdepartamento/listado.xhtml";
     }
    

    public List<Auditorias> getListaauditorias() {
        return listaauditorias;
    }

    public void setListaauditorias(List<Auditorias> listaauditorias) {
        this.listaauditorias = listaauditorias;
    }

    public List<Fichero> getListaficheros() {
        return listaficheros;
    }

    public void setListaficheros(List<Fichero> listaficheros) {
        this.listaficheros = listaficheros;
    }

    public Auditorias getAuditoriaanalizada() {
        return auditoriaanalizada;
    }

    public void setAuditoriaanalizada(Auditorias auditoriaanalizada) {
        this.auditoriaanalizada = auditoriaanalizada;
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
    
    

    public Fichero getFicheroanalizado() {
        return ficheroanalizado;
    }

    public void setFicheroanalizado(Fichero ficheroanalizado) {
        this.ficheroanalizado = ficheroanalizado;
    }
  
    public String resetTest(){
        ficheroanalizado=null;
        selectedfichero=null;
        return "/vistas/listdepartamento.xhtml";
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
    
    public StreamedContent getObtenerFichero(){
        //vamos a crear el pdf
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String fecha = sdf.format(auditoriaanalizada.getAuditoriasPK().getFecha());
        String fileName = "Informe -" +  ficheroanalizado.getNombreFichero() + "-" + fecha + ".pdf";
        try {       
            Document pdf = new Document(PageSize.LETTER);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer;
            writer = PdfWriter.getInstance(pdf, baos);
            if (!pdf.isOpen()) {
                pdf.open();
            }

           //Adding content to pdf
            pdf.addTitle(fileName);
            Paragraph p = null;
            pdf.add(new Paragraph ("INFORME DE AUTOEVALUACIÓN SOBRE CUMPLIMIENTO DE LAS MEDIDAS DE SEGURIDAD",FontFactory.getFont("arial",14,Font.BOLDITALIC, Color.DARK_GRAY)));
            pdf.add(new Paragraph (" "));
            pdf.add(new Paragraph("INTRODUCCIÓN",FontFactory.getFont("arial",14,Font.BOLDITALIC, Color.DARK_GRAY)));
            p = new Paragraph();
            p.add("Con carácter general, la seguridad de la información puede ser definida como la preservación de la confidencialidad, integridad y disponibilidad de la información y se consigue implantando un conjunto adecuado de controles, que pueden ser políticas, prácticas, procedimientos, estructuras organizativas y funciones de software.\n" +
"Por lo que respecta a los datos de carácter personal, el artículo 9 de la Ley Orgánica 15/1999, de 13 de diciembre, de Protección de Datos de Carácter Personal (LOPD), establece en su punto 1 que ‘el responsable del fichero, y, en su caso, el encargado del tratamiento, deberán adoptar las medidas de índole técnica y organizativas necesarias que garanticen la seguridad de los datos de carácter personal y eviten su alteración, pérdida, tratamiento o acceso no autorizado, habida cuenta del estado de la tecnología, la naturaleza de los datos almacenados y los riesgos a que están expuestos, ya provengan de la acción humana o del medio físico o natural’.\n" +
"El Reglamento de desarrollo de la LOPD (RLOPD), aprobado por el Real Decreto 1720/2007, de 21 de diciembre, (BOE número 17, de 19 de enero de 2008), desarrolla las medidas de seguridad en el tratamiento de datos de carácter personal y tiene por objeto establecer las medidas de índole técnica y organizativa necesarias para garantizar la seguridad que deben reunir los ficheros, ya sean automatizados o manuales, los centros de tratamiento, locales, equipos, sistemas, programas y las personas que intervengan en el tratamiento de los datos de carácter personal.");
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            pdf.add(p);            
            pdf.add(new Paragraph (" ")); 
            pdf.add(new Paragraph ("TIPOS DE MEDIDAS DE SEGURIDAD A IMPLANTAR:",FontFactory.getFont("arial",14,Font.BOLDITALIC, Color.DARK_GRAY)));
            p = new Paragraph();
            p.add("La normativa sobre protección de datos, establece obligaciones distintas dependiendo de la naturaleza de los datos de carácter personal que se tenga previsto tratar, tales como, las formas de recabar el consentimiento o el nivel de medidas de seguridad a implantar en los ficheros.\n" +
"Así, las medidas de seguridad exigibles a los ficheros y tratamientos de datos personales se clasifican en tres niveles: BÁSICO, MEDIO y ALTO.");
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            pdf.add(p); 
            pdf.add(new Paragraph (" ")); 
            pdf.add(new Paragraph ("I. MEDIDAS DE NIVEL BÁSICO:",FontFactory.getFont("arial",14,Font.BOLDITALIC, Color.DARK_GRAY)));
            p = new Paragraph();
            p.add("Todos los ficheros o tratamientos de datos de carácter personal deberán adoptar las medidas de seguridad calificadas de nivel básico.\n" +
"\n" +
"En el caso de los ficheros automatizados, las medidas calificadas de nivel básico se refieren a:\n" +
"•	Se ha elaborado y se mantiene actualizado el documento de seguridad;\n" +
"•	Establecimiento de las funciones y obligaciones del personal;\n" +
"•	Establecimiento de un registro de incidencias;\n" +
"•	Establecimiento de procedimientos para el control de acceso;\n" +
"•	Se gestionan de forma adecuada los soportes y documentos;\n" +
"•	Se han establecido mecanismos que garanticen la correcta identificación y autenticación de los usuarios;\n" +
"•	Se han establecido procedimientos de actuación para la realización de copias de respaldo.");
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            pdf.add(p); 
            pdf.add(new Paragraph (" "));
            p = new Paragraph();
            p.add("En el caso de ficheros no automatizados, se deberán tener en cuenta que les será de aplicación las obligaciones comunes establecidas en el Reglamento de desarrollo de la LOPD en lo relativo a:\n" +
"•	Encargado de tratamiento;\n" +
"•	Prestaciones de servicios sin acceso a datos personales;\n" +
"•	Delegación de autorizaciones;\n" +
"•	Régimen de trabajo fuera de los locales le responsable del fichero o del encargado del tratamiento;\n" +
"•	Copias de trabajo de documentos;\n" +
"•	Documento de seguridad.\n" +
"\n" +
"Además, deberán asegurar que:\n" +
"•	El archivo de los documentos se realiza de forma adecuada;\n" +
"•	Se han establecido mecanismos de protección adecuado a los dispositivos de almacenamiento de los documentos;\n" +
"•	Se han establecido procedimientos adecuados para la custodia de la documentación cuando ésta no se encuentre archivada en los dispositivos de almacenamiento. ");
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            pdf.add(p);
            pdf.add(new Paragraph (" ")); 
            pdf.add(new Paragraph ("II. MEDIDAS DE NIVEL MEDIO:",FontFactory.getFont("arial",14,Font.BOLDITALIC, Color.DARK_GRAY)));
            p = new Paragraph();
            p.add("Además de las medidas calificadas de nivel básico, deberán implantar las medidas de nivel medio los ficheros o tratamientos relacionados con:\n" +
"•	La comisión de infracciones administrativas o penales;\n" +
"•	La prestación de servicios de solvencia patrimonial y crédito; Las Administraciones tributarias y que se relacionen con ejercicio de sus potestades tributarias;\n" +
"•	Las entidades financieras para las finalidades relacionadas con la prestación de servicios financieros;\n" +
"•	Las Entidades Gestoras y Servicios Comunes de Seguridad Social, que se relacionen con el ejercicio de sus competencias;\n" +
"•	Las mutuas de accidentes de trabajo y enfermedades profesionales de la Seguridad Social;\n" +
"•	Que ofrezcan una definición de la personalidad y permitan evaluar determinados aspectos de la misma o del comportamiento de las personas; y\n" +
"•	Los operadores de comunicaciones electrónicas, respecto de los datos de tráfico y localización (Para esta categoría de ficheros, además deberá disponerse de un registro de accesos).");
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            pdf.add(p); 
            pdf.add(new Paragraph (" "));
            p = new Paragraph();
            p.add("Tanto para ficheros automatizados como no automatizados, las medidas de nivel medio se refieren, además de las señaladas para el nivel básico, a:\n" +
"•	Se ha designado un responsable de seguridad;\n" +
"•	Se ha planificado la realización de una auditoria que verifique el cumplimiento de las medidas de seguridad.\n" +
"Además, en el caso de los ficheros automatizados se deberá tener en cuenta que:\n" +
"•	Se ha establecido la adecuada gestión de soportes y documentos;\n" +
"•	Se ha limitado el número de reintentos para acceder al sistema de información;\n" +
"•	Se han establecido procedimientos de control de acceso físico a los lugares donde se encuentre almacenada la información;\n" +
"•	Se han establecido los procedimientos de recuperación de la información en el registro de incidencias.");
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            pdf.add(p);           
            pdf.add(new Paragraph (" ")); 
            pdf.add(new Paragraph ("III. MEDIDAS DE NIVEL ALTO:",FontFactory.getFont("arial",14,Font.BOLDITALIC, Color.DARK_GRAY)));
            p = new Paragraph();
            p.add("Además de las medidas de nivel básico y medio, deberán implantarse las medidas de seguridad calificadas de nivel alto a los ficheros o tratamientos con datos:\n" +
"•	De ideología, afiliación sindical, religión, creencias, origen racial, salud o vida sexual y respecto de los que no se prevea la posibilidad de adoptar el nivel básico;\n" +
"•	Recabados con fines policiales sin consentimiento de las personas afectadas; y derivados de actos de violencia de género.");
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            pdf.add(p);
            p = new Paragraph();
            p.add("Si bien con carácter general los ficheros que incluyan datos especialmente protegidos deben adoptar las medidas de seguridad catalogadas como de nivel alto, la normativa sobre protección de datos establece determinados supuestos en los que es suficiente implantar las medidas de nivel básico. En concreto cuando:\n" +
"•	Los datos se utilicen con la única finalidad de realizar una transferencia dineraria a entidades de las que los afectados sean asociados o miembros;\n" +
"•	Se trate de ficheros o tratamientos no automatizados o sean tratamientos manuales de estos tipos de datos de forma incidental o accesoria, que no guarden relación con la finalidad del fichero; y\n" +
"•	En los ficheros o tratamientos que contengan datos de salud, que se refieran exclusivamente al grado o condición de discapacidad o la simple declaración de invalidez, con motivo del cumplimiento de deberes públicos.");
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            pdf.add(p);
            p = new Paragraph();
            p.add("En el caso de los ficheros automatizados, las medidas calificadas de nivel alto se refieren, además de las señaladas para el nivel básico y medio, a:\n" +
"•	Se han establecido los procedimientos adecuados para la gestión y distribución de soportes;\n" +
"•	Conservación de la copia de seguridad y los procedimientos de recuperación en lugar diferente;\n" +
"•	Se mantiene un registro de los accesos realizados al sistema de información;\n" +
"•	En caso de transmisión de datos de carácter personal a través de redes públicas o inalámbricas se han previsto mecanismos de cifrado.");
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            pdf.add(p);
            p = new Paragraph();
            p.add("En el caso de los ficheros no automatizados, las medidas calificadas de nivel alto se refieren, además de las señaladas para el nivel básico y medio a:\n" +
"•	Se han adoptado las medidas de adecuadas en relación con los armarios, archivadores en los que se almacenen los ficheros no automatizados;\n" +
"•	Se han establecido los procedimientos adecuados en relación con la generación de copias o la reproducción de los documentos;\n" +
"•	Se han establecido los procedimientos adecuados para limitar el acceso a la información exclusivamente al personal autorizado;\n" +
"•	Se han establecido los procedimientos adecuados para impedir el acceso no autorizado a la información durante el traslado de la misma;");
            p.setAlignment(Element.ALIGN_JUSTIFIED);           
            pdf.add(new Paragraph (" "));             
            pdf.add(new Paragraph("RESPUESTAS AL TEST DE CUMPLIMIENTO DEL FICHERO " + ficheroanalizado.getNombreFichero() + ":",FontFactory.getFont("arial",14,Font.BOLDITALIC, Color.DARK_GRAY)));
            pdf.add(new Paragraph (" ")); 
            for(int i=1; i <= MAX_PREGUNTAS; i++){
                try{
                    PropertyDescriptor pe = new PropertyDescriptor("p" + i, Auditorias.class);
                    Integer c = (Integer) pe.getReadMethod().invoke(auditoriaanalizada);
                    if(c!=null && c==0){
                        //hay que buscar dicha pregunta y poner lo que pone en el campo reglamento
                        Reglamento r = ejbFacadeReglamento.find(i);
                        if(r!=null && r.getIncumplimiento()!=null && !r.getIncumplimiento().isEmpty()){
                            p = new Paragraph();
                            p.add("- " + r.getIncumplimiento());
                            p.setAlignment(Element.ALIGN_JUSTIFIED);
                            pdf.add(p);
                        }
                    }

                }
                catch(Exception e){
                    System.out.println("Error:" + e.getMessage());
                }
            }
            pdf.close();
            InputStream stream = new ByteArrayInputStream(baos.toByteArray());
            StreamedContent file = new DefaultStreamedContent(stream, "application/pdf", fileName);
            return file;

        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Error: createPDF() " + e.getMessage());
        }
        return null;

    }

}
