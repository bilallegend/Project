package Ajax;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import HelperClasses.ConnectionDatabase;

public class upload extends HttpServlet {
	
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
    	System.out.println("fgfhgergyr4ruee");
        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
        List<BlobKey> blobKeys = blobs.get("photo");
        ConnectionDatabase psql = new ConnectionDatabase();
  		 Connection conn			=psql.createConnection("gamecenter");
  		 
  		Cookie[] cookies =  req.getCookies();

    	String userId = null;
    	for(Cookie cookie : cookies){
    	    if("gc_account".equals(cookie.getName())){
    	        userId = cookie.getValue();
    	        break;
    	    }
    	}
  		 
  		String name="";
   	 ServletContext context  =   req.getSession().getServletContext();
   	HashMap<String,String> users=(HashMap<String,String>)context.getAttribute("cookie");
   	for(String s:users.keySet()) {
	   		 
	   		 if(s.equals(userId)) {
	   			 name=users.get(s);
	   			 break;
	   		 }
	   	 }
   	System.out.println(name);
  		Statement stmt;
  		String src="";
        if (blobKeys == null || blobKeys.isEmpty()) {
            //res.sendRedirect("/");
        	try {
    			stmt = conn.createStatement();
    			System.out.println("Anu");
    			String Query="UPDATE player_info SET photo = '"+src+"' WHERE username = '"+name+"'";
    			
    			System.out.println(Query);
    			stmt.executeUpdate(Query);
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}//Statement class creates a object that can execute our query in the connected database in connection object
    		
                res.sendRedirect("http://localhost:8080/home");
        } else {
        	String a="helo";
        	res.getWriter().write("/serve?blob-key=" + blobKeys.get(0).getKeyString());
        	   src="/serve?blob-key=" + blobKeys.get(0).getKeyString();
        	
        	
        	
        	
		try {
			stmt = conn.createStatement();
			System.out.println("Anu");
			String Query="UPDATE player_info SET photo = '"+src+"' WHERE username = '"+name+"'";
			
			System.out.println(Query);
			stmt.executeUpdate(Query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//Statement class creates a object that can execute our query in the connected database in connection object
		
            res.sendRedirect("http://localhost:8080/home");
       }
    }
}