import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.postgresql.jdbc.PgArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import HelperClasses.ConnectionDatabase;

public class ReplaysMovesGiver  extends HttpServlet{
	private Gson gson = new GsonBuilder().create();
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
				System.out.println(firstmove);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			HashMap<String,ArrayList<Object>> MoveIdandTimeMap = new HashMap<String,ArrayList<Object>>();
			HashMap<String,String> playerNames = new HashMap<>();
			
			try {
				ArrayList<Object> moveIds =new ArrayList<Object>(); 
				ArrayList<Object> Time = new ArrayList<Object>(); 
				while(MoveTimeResultSet.next()) {
					
					Array blackarray = MoveTimeResultSet.getArray("blackarray");
					Integer[] inte = (Integer[]) blackarray.getArray();
					moveIds.add(ALgiver(inte));
					System.out.println(Arrays.toString(inte));
					Array whitearray = MoveTimeResultSet.getArray("whitearray");
					Integer[] intw = (Integer[]) whitearray.getArray();
					moveIds.add(ALgiver(intw));
					Time.add(MoveTimeResultSet.getInt("time"));
				}
				MoveIdandTimeMap.put("moveIds", moveIds);
				MoveIdandTimeMap.put("Time",Time);
				
			} catch (SQLException e){
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
							nameAndPicSetter(
									MyDB.selectCondition(connection,"player_info","username,photo","'"+usernameSet.getString(playersIDs[add])+"'" , "player_id")
									,playerNames,"firstplayer");
							nameAndPicSetter(
									MyDB.selectCondition(connection,"player_info","username,photo","'"+usernameSet.getString(playersIDs[add+1])+"'" , "player_id")
									,playerNames,"nextplayer");
					
				}
			} catch (SQLException e) {
				
			}
			
			req.setAttribute("MoveIdandTimeMap", gson.toJson(MoveIdandTimeMap));
			req.setAttribute("playerNames", gson.toJson(playerNames));
			req.setAttribute("LiveId", id);
			System.out.println("Yes");
			System.out.println(playerNames);
			getServletContext().getRequestDispatcher("/Jsp/OldReplay.jsp").forward(req, resp);
			
	}
	
	private String nameAndPicSetter (ResultSet set,HashMap<String,String> playernames,String key) {
		try {
			while(set.next()) {
				playernames.put(key,set.getString("username"));
				playernames.put(set.getString("username"),set.getString("photo"));
				System.out.println(set.getString("photo"));
			}
		} catch (SQLException e) {
			e.printStackTrace();			
		}
		return null;
		
	}
	private ArrayList<Integer> ALgiver(Integer[] arr){
		ArrayList<Integer> rarr = new ArrayList<Integer>();
		for(Integer i : arr) {
			rarr.add(i);
		}
		return rarr;
	}
	
}
