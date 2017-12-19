import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import HelperClasses.ConnectionDatabase;
import HelperClasses.Cooky;
import HelperClasses.Redirecter;

public class profileInfo extends HttpServlet{
	
	private Gson gson = new GsonBuilder().create();
 @Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// TODO Auto-generated method stub
	String name = Cooky.getContextName("gc_account", req.getCookies(),"cookie", req);
	if(name == null) {
		resp.sendRedirect(Redirecter.giveUrlFor(req,"/home"));
	}else {
		ConnectionDatabase DB = new ConnectionDatabase();
		Connection con = DB.createConnection("gamecenter");
		ResultSet resultset = DB.select(con, "player_info","*",name);
		HashMap<String,String> userDetailsMap = new HashMap<String,String>();
		try {
			while(resultset.next()) {
				userDetailsMap.put("victories", resultset.getInt("win")+"");
				userDetailsMap.put("defeats", resultset.getInt("loss")+"");
				userDetailsMap.put("total_match",(resultset.getInt("win")+resultset.getInt("loss")+""));
				userDetailsMap.put("name",name);
				userDetailsMap.put("score",resultset.getInt("score")+"");
				System.out.println(resultset.getString("photo")==null);
				if(resultset.getString("photo")==null) {
					userDetailsMap.put("photo","../Images/pr.png");
				}
				else {
				userDetailsMap.put("photo",resultset.getString("photo") );
				}
			}
			resp.getWriter().println(gson.toJson(userDetailsMap));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			  con.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
	}
}
}
