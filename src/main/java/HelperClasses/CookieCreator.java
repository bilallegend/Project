package HelperClasses;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;
public class CookieCreator{
    public void createContext(String cookie_name,String id,HttpServletRequest req,HttpServletResponse res){
       String random           =   ""+(int)(Math.random()*500000+100000);
       System.out.println(random);
       Cookie cookie           =   new Cookie(cookie_name,random);
       ServletContext context  =   req.getSession().getServletContext();
       HashMap<String,String> map;
       if(context.getAttribute("cookie")== null){
           map =  new HashMap<String,String>();
       }else{
           map         =   (HashMap<String,String>) context.getAttribute("cookie");
           
           while(map.containsKey(random)){
               random  =   ""+(int)(Math.random()*500000+100000);
           }
           
       }
       map.put(random, id);
       System.out.println(map);
       context.setAttribute("cookie",map);
       cookie.setPath("/");
       res.addCookie(cookie);
       
       
   }
}