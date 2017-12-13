import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import HelperClasses.ConnectionDatabase;

public class Replays extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ConnectionDatabase myDB =  new ConnectionDatabase();
		Connection conn = myDB.createConnection("gamecenter");
		ResultSet resultSet = myDB.selectUnCondition(conn,"game_list","game_id");
		try {
			while(resultSet.next()) {
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
