import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.appengine.pusher.PusherService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pusher.rest.data.Result;

import HelperClasses.ConnectionDatabase;
import HelperClasses.Cooky;

public class RequestHandler extends HttpServlet{
	
	private Gson gson = new GsonBuilder().create();
	
	private TypeReference<Map<String, String>> typeReference =
		      new TypeReference<Map<String, String>>() {};
	@Override
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		    String body = CharStreams.readLines(req.getReader()).toString();
		    String json = body.replaceFirst("^\\[", "").replaceFirst("\\]$", "");
		    Map<String, String> data = gson.fromJson(json, typeReference.getType());
		ServletContext context = req.getSession().getServletContext();
		String ParentID = data.get("parentID");
		String socketID = data.get("socket_id");
		String name = Cooky.getContextName("gc_account",req.getCookies(),"cookie", req);
		HashMap<String, String[]> DivMap = Cooky.getContextValue("DivMap", req);
		HashMap<String,String[]> reqInfo = Cooky.getContextValue("reqInfo",req);
		if(reqInfo == null) {
			req.getSession().getServletContext().setAttribute("reqInfo", new HashMap<String, String[]>());//for check request info must be deleted after confirm
		}
		reqInfo = Cooky.getContextValue("reqInfo",req);
		String[] values = new  String[2]; 
		values[0]=socketID;    //setting requester socketId in values[0]
		for(String[] arr : DivMap.values()) {
			if(arr[0].equals(ParentID)) {				
				values[1]=arr[3]; //setting receiver name in vlaues[1]
				break;
				
			}
		}
		ConnectionDatabase DB = new ConnectionDatabase();
		Connection connection = DB.createConnection("gamecenter");
		ResultSet resultset =DB.selectCondition(connection,"player_info","photo","'"+name+"'","username");
		String pic="";
		try {
			while(resultset.next()) {
				pic=resultset.getString("photo");
			}
		} catch (SQLException e) {

		}
		//println("pic "+pic);
		HashMap<String,ArrayList<String>> MultiTabs = (HashMap<String, ArrayList<String>>) context.getAttribute("MultiTabs");
		reqInfo.put(name, values);//setting reqInfo {requestername:[requestersocketId,recivername]}
		Map<String,String> messageData = new HashMap<String, String>();
		messageData.put("name", name);
		messageData.put("msg","<div class=\"noti-div\" style=\"transform: translateY(0px);\">\n" + 
				"        \n" + 
				"        <div class=\"con\">\n" + 
				"            <div class=\"img\">\n" + 
				"                <div style='background:url(\""+pic+"\");background-size:cover'></div>\n" + 
				"            </div>\n" + 
				"           <div class=\"name-div\">\n" + 
				"               <p class=\"name\">"+name+"</p>\n" + 
				"               <p class=\"send\"><i>Send friend request for u.</i></p>\n" + 
				"                <div class=\"insi\">\n" + 
				"                 <button name='accept' class=\"yes\">Yes</button>\n" + 
				"                 <button name='accept' class=\"no\">No</button>\n" + 
				"             </div>\n" + 
				"           </div>\n" + 
				"       \n" + 
				"        </div>\n" + 
				"   </div>"	);
		String receivername =values[1];
		ArrayList<String> receivingSockets= MultiTabs.get(receivername);
		//println(receivingSockets);
		for(String socketId : receivingSockets) {
			Result result =
			        PusherService.getDefaultInstance()
			            .trigger(
			                "private-MyNotification-"+socketId,
			                "GameReq", // name of event
			                messageData);
			//println(result.getStatus()+"  for "+"private-MyNotification-"+socketId+"   ");
			messageData.put("status", result.getStatus().name());
			
		}
		
		messageData.remove("msg");messageData.remove("name");
		
		resp.getWriter().println(gson.toJson(messageData));
	}
}
