import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import HelperClasses.Cooky;
import HelperClasses.Redirecter;

public class profileInfo extends HttpServlet{
 @Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// TODO Auto-generated method stub
	String name = Cooky.getContextName("gc_account", req.getCookies(),"cookie", req);
	if(name == null) {
		resp.sendRedirect(Redirecter.giveUrlFor(req,"/home"));
	}else {
		
	}
}
}
