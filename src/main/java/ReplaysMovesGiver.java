import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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
			ResultSet MoveTimeResultSet = MyDB.selectCondition(connection,"game_info","whitearray,blackarray,time",id,"game_id");
			ResultSet gamelistResultSet = MyDB.selectCondition(connection,"game_list","firstmove",id,"game_id");
			String firstmove=null;
			try {
				while(gamelistResultSet.next()) {
					if(firstmove==null) {
						firstmove=""+gamelistResultSet.getInt("firstmove");
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			HashMap<String,ArrayList<Object>> MoveIdandTimeMap = new HashMap<String,ArrayList<Object>>();
			HashMap<String,String> playerNames = new HashMap<>();
			
			try {
				ArrayList<Object> moveIds =new ArrayList<Object>(); 
				ArrayList<Object> Time = new ArrayList<Object>(); 
				while(MoveTimeResultSet.next()) {
					System.out.print("Array type\t "+MoveTimeResultSet.getArray("blackarray").getClass().getSimpleName());
					moveIds.add(MoveTimeResultSet.getArray("blackarray"));
					moveIds.add(MoveTimeResultSet.getArray("whitearray"));
					Time.add(MoveTimeResultSet.getInt("time"));
					
				}
				MoveIdandTimeMap.put("moveIds", moveIds);
				MoveIdandTimeMap.put("Time",Time);
				
			} catch (SQLException e) {
				System.out.println("Problem in resultset in getting");
			}catch(Exception e) {
				e.printStackTrace();
			}
			ResultSet usernameSet = MyDB.selectCondition(connection,"game_list","player1_id,player2_id",id,"game_id");
			try {
				String[] playersIDs = new String[] {"player1_id","player2_id","player1_id"};
				int add=0;
				while(usernameSet.next()) {
					if(firstmove.equals(usernameSet.getString("player1_id"))) {
						add=0;
					}else {
						add=1;
					}
					playerNames.put(
							"firstplayer",nameGiver(
									MyDB.selectCondition(connection,"player_info","username","'"+usernameSet.getString(playersIDs[add])+"'" , "player_id")
									));
					playerNames.put(
							"nextplayer",nameGiver(
									MyDB.selectCondition(connection,"player_info","username","'"+usernameSet.getString(playersIDs[add+1])+"'" , "player_id")
									));
				}
			} catch (SQLException e) {
				
			}
			
			req.setAttribute("MoveIdandTimeMap", MoveIdandTimeMap);
			req.setAttribute("playerNames", playerNames);
			req.setAttribute("LiveId", id);
			getServletContext().getRequestDispatcher("/Jsp/OldReplay.jsp").forward(req, resp);
			
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
