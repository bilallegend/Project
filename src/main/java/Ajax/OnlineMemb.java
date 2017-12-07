package Ajax;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OnlineMemb extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		URI thisUri =null;
		try {
			thisUri = new URI(req.getRequestURL().toString());
			
			resp.getWriter().write(thisUri.getScheme()+"://"+thisUri.getHost()+":"+thisUri.getPort()+"/onlineMembers");//scheme,host,path,fragment
			System.out.println(thisUri.getScheme()+"://"+thisUri.getHost()+":"+thisUri.getPort()+"/onlineMembers");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			resp.getWriter().write(null+"");
		}
	}
	
}
