import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ReplayHandler extends HttpServlet{
private Gson gson = new GsonBuilder().create();
	
	private TypeReference<Map<String, String>> typeReference =
	      new TypeReference<Map<String, String>>() {};
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String body = CharStreams.readLines(req.getReader()).toString();
	    String json = body.replaceFirst("^\\[", "").replaceFirst("\\]$", "");
	    Map<String, String> data = gson.fromJson(json, typeReference.getType());
	    String divId = data.get("divId");
	    ServletContext context = req.getSession().getServletContext();
	    
	    if(divId.contains("Live")) {
	    	HashMap<String,String[]> GameIds = (HashMap<String,String[]>) context.getAttribute("GameIds");
	    	HashMap<String,String> feedsDivId = (HashMap<String, String>) context.getAttribute("feedsDivId");
	    	String gameId = feedsDivId.get(divId.split("Live")[0]);
	    	req.setAttribute("LiveId",divId);
	    	getServletContext().getRequestDispatcher("/Jsp/LiveReplay.jsp").forward(req, resp);
	    	
	    }else {
	    	
	    }
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().write("No page Found");
	}
}
