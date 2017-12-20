import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import HelperClasses.Cooky;

public class RoomChecker extends HttpServlet{
	
	private String room;
	private Gson gson ;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext context = req.getSession().getServletContext();
		HashMap<String,String[]> GameIds = (HashMap<String, String[]>) context.getAttribute("GameIds");
		String cookievalue = Cooky.getCookieValue("gc_account",req.getCookies());
		HashMap<String,Object> playdetails = (HashMap<String, Object>) context.getAttribute("PlayDetails");
		
		if(GameIds != null && playdetails.containsKey(cookievalue)) {
			getServletContext().getRequestDispatcher("/home").forward(req, resp);
			return;
		}
		
//		(HashMap<String,HashMap<String,ArrayList<String>>>) context.getAttribute("PlayDetails");
		
		
		getServletContext().getRequestDispatcher("/Jsp/Play.jsp").forward(req, resp);
		
	}
}
