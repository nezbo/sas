/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import Controller.ControllerFactory;
import Model.User;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
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
    @ManagedProperty(value="#{InoformationBean}")
    private InformationBean informationBean; 

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
    
    /**
     * returns the html for listing and addding users as friends
     * @return 
     */
    public String getHTMLFriendUsers()
    {
        List<User> users =null;
        //List<User> users =null;
        if(securityBean.isLoggedIn())
        {
             users = ControllerFactory.getController().getAllUsers();
             if(users!=null)
                 informationBean.setCurrentListOfUsers(users);
             
        }
        else
            return "not logged in";//error meesage
        String html="";
        html+="<div class='jumbotron' class='jumbotron'>";
         html+="<div class='list-group' id='"+users.size()+"'>";       
         html+="<table style='width=100%'><tr>";
          for(int i =0; i<users.size();i++)
          {
              html+="<tr><h:form>";
              
              if(users.get(i).getName()==null || users.get(i).getName().equals(""))
              {
                                html+="<td>No name</td>"; //insert user with no name
              }
              else

              {html+="<td>"+escapeHtml4(users.get(i).getName())+"</td>";} //insert user          with name


              html+="<td><h:commandButton class='btn btn-lg btn-primary' id='addFriendBtn' value='AddFriend' action=''>"+"Add friend"+"</h:commandButton></td>";//add addfreind biutton //hash the number and use it as a lookupvalue for the actual value
              html+="</tr> </h:form>";
          }
         html+="</table>";
        /* html+="   <a href='#' class='list-group-item active'>";
         html+="      Cras justo odio";
        html+="     </a>";
         html+="    <a href='#' class='list-group-item'>Dapibus ac facilisis in</a>";
        html+="    <a href='#' class='list-group-item'>Morbi leo risus</a>";
        html+="    <a href='#' class='list-group-item'>Porta ac consectetur ac</a>";
        html+="    <a href='#' class='list-group-item'>Vestibulum at eros</a>";*/
          html+="</div>";
        html+="</div><!-- /.col-sm-4 -->";
        
       
        
        return html;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
