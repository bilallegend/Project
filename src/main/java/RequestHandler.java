import java.io.IOException;
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

import HelperClasses.Cooky;

public class RequestHandler extends HttpServlet{
	
	private Gson gson = new GsonBuilder().create();
	
	private TypeReference<Map<String, String>> typeReference =
		      new TypeReference<Map<String, String>>() {};
	@Override
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("RequestHandler");
		    String body = CharStreams.readLines(req.getReader()).toString();
		    System.out.println(body);
		    
		    String json = body.replaceFirst("^\\[", "").replaceFirst("\\]$", "");
		    Map<String, String> data = gson.fromJson(json, typeReference.getType());
		    System.out.println(json);
		
		ServletContext context = req.getSession().getServletContext();
		String ParentID = data.get("parentID");
		String name = Cooky.getContextName("gc_account",req.getCookies(),"cookie", req);
		HashMap<String, String[]> DivMap = Cooky.getContextValue("DivMap", req);
		
		HashMap<String,String[]> reqInfo = Cooky.getContextValue("reqInfo",req);
		if(reqInfo == null) {
			req.getSession().getServletContext().setAttribute("reqInfo", new HashMap<String, String[]>());
		}
		reqInfo = Cooky.getContextValue("reqInfo",req);
		
		String[] values = new  String[2]; 
		values[0]=name;
		for(String[] arr : DivMap.values()) {
			System.out.println(arr[0]+"  "+ParentID);
			if(arr[0].equals(ParentID)) {				
				values[1]=arr[3];
				System.out.println(Arrays.toString(arr));
				return;
				
			}
		}
		HashMap<String,ArrayList<String>> MultiTabs = (HashMap<String, ArrayList<String>>) context.getAttribute("MultiTabs");
		System.out.println("request handle");
		System.out.println(Arrays.toString(values));
		reqInfo.put(name, values);
		Map<String,String> messageData = new HashMap<String, String>();
		System.out.println(name);
		
		messageData.put("name", name);
		messageData.put("msg","<div class=\"noti-div\" style=\"transform: translateY(0px);\">\n" + 
				"        \n" + 
				"        <div class=\"con\">\n" + 
				"            <div class=\"img\">\n" + 
				"                <div></div>\n" + 
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
		HashMap<String,ArrayList<String>> MultiBrowser = (HashMap<String, ArrayList<String>>) context.getAttribute("MultiBrowser");
		ArrayList<String> receiverCookies= MultiBrowser.get(values[1]);
		String receivername = DivMap.get(receiverCookies.get(0))[3];
		ArrayList<String> receivingSockets= MultiTabs.get(receivername);
		for(String socketId : receivingSockets) {
			Result result =
			        PusherService.getDefaultInstance()
			            .trigger(
			                "private-MyNotification-"+socketId,
			                "GameReq", // name of event
			                messageData);
			
			System.out.println("private-MyNotification-"+socketId);
			System.out.println(result.toString());
			
			messageData.put("status", result.getStatus().name());
			messageData.remove("msg");messageData.remove("name");
		}
//		for(String Cookie : receiverCookies) {
//				Result result =
//				        PusherService.getDefaultInstance()
//				            .trigger(
//				                "private-MyNotification-"+DivMap.get(Cookie)[1],
//				                "GameReq", // name of event
//				                messageData);
//				
//				System.out.println("private-MyNotification-"+DivMap.get(Cookie)[1]);
//				System.out.println(result.toString());
//				
//				messageData.put("status", result.getStatus().name());
//				messageData.remove("msg");messageData.remove("name");
//			
//		}
		
		resp.getWriter().println(gson.toJson(messageData));
	}
}
