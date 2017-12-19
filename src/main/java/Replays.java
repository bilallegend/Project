import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import HelperClasses.ConnectionDatabase;

public class Replays extends HttpServlet{
	private Gson gson = new GsonBuilder().create();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ConnectionDatabase myDB =  new ConnectionDatabase();
		Connection conn = myDB.createConnection("gamecenter");
		ResultSet resultSet = myDB.selectUnCondition(conn,"game_list","game_id,player1_id,player2_id,likes,views");
		HashMap<String,Object> data = new HashMap<String,Object>();
		
		try {
			ArrayList<HashMap<String,String>> OldReplaysList = new ArrayList<HashMap<String,String>>();
			while(resultSet.next()) {
				HashMap<String,String> gamedetails = new HashMap<String,String>();
				String player_1_name = nameGiver(myDB.selectCondition(conn,"player_info","username","'"+resultSet.getString("player1_id")+"'" , "player_id"));
				String player_2_name = nameGiver(myDB.selectCondition(conn,"player_info","username","'"+resultSet.getString("player2_id")+"'" , "player_id"));
				gamedetails.put("game_id",resultSet.getInt("game_id")+"Replay");
				gamedetails.put("player_1_name",player_1_name);
				gamedetails.put("player_2_name",player_2_name);
				gamedetails.put("likes",resultSet.getString("likes"));
				gamedetails.put("views",resultSet.getString("views"));
				OldReplaysList.add(gamedetails);
				
			}
			data.put("data", OldReplaysList);
			System.out.println(OldReplaysList);
			resp.getWriter().println(gson.toJson(data));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			  conn.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		
	}
	
	private String nameGiver (ResultSet set) {
		try {
			while(set.next()) {
				return set.getString("username");
			}
		} catch (SQLException e) {
			e.printStackTrace();			
		}
		return null;
		
	}
}
