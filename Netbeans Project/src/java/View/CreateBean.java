/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;

import Controller.ControllerFactory;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Emil
 */
@Named("CreateBean")
@RequestScoped
public class CreateBean implements java.io.Serializable {
    
    private static final String RECAPTCHA_GLOBAL_PRIVATE_KEY = "6LeujfESAAAAACW-Khc5YcWLg47XOW1r4KQgbp8K";
    private static final String RECAPTCHA_PRIVATE_KEY = "6LeJQvESAAAAAAM4WMQUzf-3vBXCdHCx7chdu9YB";
    
    @ManagedProperty(value="#{loginBean}")
    private SecurityBean loginBean; // +setter
    
    private String username;
    private String password;
    private String password2;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
    
    public String createUser(){
        try{
            if(validateCaptcha()){
                boolean result = ControllerFactory.getController().createUser(username, password);
                if(result){
                    //loginBean.setUserName(username); //cannot do it this way
                    //loginBean.setPassword(password);
                    //loginBean.login();
                    return "index";
                }
            }
        }catch(IOException e){
            System.out.println(e.toString());
        }
        return "create";
    }
    
    private boolean validateCaptcha() throws IOException
    {
        HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();  
        String ip = httpServletRequest.getRemoteAddr();
        String challenge = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("recaptcha_challenge_field");
        String response = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("recaptcha_response_field");
 
        URL url = new URL("http://www.google.com/recaptcha/api/verify");
        URLConnection urlcon = url.openConnection();
        urlcon.setDoInput(true);
        urlcon.setDoOutput(true);
        urlcon.setUseCaches(false);
 
        DataOutputStream dos = new DataOutputStream(urlcon.getOutputStream());
        String content = "privatekey=" + RECAPTCHA_PRIVATE_KEY +
                         "&remoteip=" + ip +
                         "&challenge=" + URLEncoder.encode(challenge, "UTF-8") +
                         "&response=" + URLEncoder.encode(response, "UTF-8");
        dos.writeBytes(content);
        dos.flush();
        dos.close();
 
        BufferedReader br = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
        String result = br.readLine().toLowerCase();
        br.close();
       
        return result.startsWith("true");
    }
}
