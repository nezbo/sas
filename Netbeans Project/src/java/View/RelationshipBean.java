/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import Controller.ControllerFactory;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author dst
 */

@Named("RelationshipBean")
@SessionScoped
public class RelationshipBean implements java.io.Serializable {
    
     @ManagedProperty(value="#{SecurityBean}")
    private SecurityBean securityBean; 
     @ManagedProperty(value="#{InformationBean}")
    private InformationBean informationBean; 

    public SecurityBean getSecurityBean() {
        return securityBean;
    }

    public void setSecurityBean(SecurityBean securityBean) {
        this.securityBean = securityBean;
    }

    public InformationBean getInformationBean() {
        return informationBean;
    }

    public void setInformationBean(InformationBean informationBean) {
        this.informationBean = informationBean;
    }
    
    
    
    public String addFriend(int id)
    {
        //if(securityBean.isLoggedIn())
            //ControllerFactory.getController().addRelationShip(securityBean.getUserName(), informationBean.getCurrentListOfUsers().get(id), )
        return "true";
    }
}
