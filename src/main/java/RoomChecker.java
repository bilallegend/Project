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
		// TODO Auto-generated method stub
//		System.out.println("RoomChecker ");
//		System.out.println(room);
//		if(gson== null) {
//			gson = new Gson();
//		}
//		ServletContext context = req.getSession().getServletContext();
//		room = (String) context.getAttribute("room");
//		if(room==null) {
//			
//			System.out.println(room);
//			room="private-online-"+Math.round((Math.random()*10000000));
//			context.setAttribute("room", room);	
//		}
		ServletContext context = req.getSession().getServletContext();
		HashMap<String,String[]> GameIds = (HashMap<String, String[]>) context.getAttribute("GameIds");
		String cookievalue = Cooky.getCookieValue("gc_account",req.getCookies());
		if(GameIds != null && GameIds.containsValue(cookievalue)) {
			getServletContext().getRequestDispatcher("/home").forward(req, resp);
			return;
		}
		
//		(HashMap<String,HashMap<String,ArrayList<String>>>) context.getAttribute("PlayDetails");
		
		
		getServletContext().getRequestDispatcher("/Jsp/Play.jsp").forward(req, resp);
		
	}
}
