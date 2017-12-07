import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class RoomChecker extends HttpServlet{
	
	private String room;
	private Gson gson ;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("RoomChecker ");
		System.out.println(room);
		if(gson== null) {
			gson = new Gson();
		}
		ServletContext context = req.getSession().getServletContext();
		room = (String) context.getAttribute("room");
		if(room==null) {
			
			System.out.println(room);
			room="private-online-"+Math.round((Math.random()*10000000));
			context.setAttribute("room", room);	
		}
		
		getServletContext().getRequestDispatcher("/Jsp/Play.jsp").forward(req, resp);
		
	}
}
