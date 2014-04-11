/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import Controller.ControllerFactory;
import Model.RelationshipType;
import Model.User;
import java.util.List;
import java.util.HashMap;
import javax.el.MethodExpression;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;
/**
 *
 * @author dst
 */

@Named("HTMLBean")
@RequestScoped
public class HTMLBean implements java.io.Serializable {

    @ManagedProperty(value="#{SecurityBean}")
    private SecurityBean securityBean; 
    @ManagedProperty(value="#{InformationBean}")
    private InformationBean informationBean; 
    private HashMap<String, Integer> relationTypes = new HashMap<String,Integer>();

    public HashMap<String, Integer> getRelationTypes() {
        return relationTypes;
    }


    public InformationBean getInformationBean() {
        return informationBean;
    }

    public void setInformationBean(InformationBean informationBean) {
        this.informationBean = informationBean;
    }
    private String HTMLFriendUsers;
    
    public SecurityBean getSecurityBean() {
        return securityBean;
    }

    public void setSecurityBean(SecurityBean securityBean) {
        this.securityBean = securityBean;
    }
    public String getHTMLRelationshipTypeList()
    {
        List<RelationshipType> relationTypesLocal = ControllerFactory.getController().getRelationShipTypes();
        String html ="";
        /* Add dropdown
        html+="<h:selectOneMenu value='#{InformationBean.addFriendRelationshipType}'>";
        
        html+="</h:selectOneMenu>";
        */
        return html;
    }
	
}
    
        

