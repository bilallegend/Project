import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import HelperClasses.ConnectionDatabase;

public class ReplaysMovesGiver  extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
			String id = req.getParameter("RId");
			ConnectionDatabase MyDB = new ConnectionDatabase();
			Connection connection = MyDB.createConnection("gamecenter");
			ResultSet resultset = MyDB.selectCondition(connection,"game_info","coin_move_id,time",id,"game_id");
			
	}
}
