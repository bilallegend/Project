package Ajax;
import com.example.appengine.pusher.PusherService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pusher.rest.data.Result;

import HelperClasses.ConnectionDatabase;
import HelperClasses.Cooky;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CoinMoveCheck extends HttpServlet {

  private Gson gson = new GsonBuilder().create();
  private TypeReference<Map<String, String>> typeReference =
	      new TypeReference<Map<String, String>>() {};
  private static int x=0;
  
  private static  ArrayList<String> color=new ArrayList<String>();
  private static ArrayList<String> color1=new ArrayList<String>();
  
  private static  ArrayList<String> white=new ArrayList<String>();
  private static ArrayList<String> black=new ArrayList<String>();

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
	  
	  ServletContext context  =   request.getSession().getServletContext();
	  ConnectionDatabase psql = new ConnectionDatabase();
	  Connection conn			=psql.createConnection("gamecenter");
	
	  String body = CharStreams.readLines(request.getReader()).toString();
	  String json = body.replaceFirst("^\\[", "").replaceFirst("\\]$", "");
	  Map<String, String> data = gson.fromJson(json, typeReference.getType());
	  Map<String, String> messageData = new HashMap<>();

	  HashMap<String,HashMap<String,ArrayList<String>>> PlayDetails= (HashMap<String,HashMap<String,ArrayList<String>>>)context.getAttribute("PlayDetails");
	  HashMap<String,String[]> gameids= ( HashMap<String,String[]>)context.getAttribute("GameIds");
	  
	  Cookie[] cookie=request.getCookies();
	  String usercookie=Cooky.getCookieValue("gc_account", request.getCookies());
	  String gameid=data.get("gameid");
	  String id=data.get("gameid").split("_")[1];
	  String[] cookies=gameids.get(gameid);
	  String oppcookie="";
	  if(cookies[0].equals(usercookie)) {		  
		   oppcookie=cookies[1];
	  }
	  else {
		 oppcookie=cookies[0];
	  }
	  
	 System.out.println(usercookie);

	 System.out.println(PlayDetails.get(usercookie).get("status").get(0));
	 System.out.println(PlayDetails.get(oppcookie).get("status").get(0));
	 
	 int time=0;
	 
	    if(!data.get("message1").equals("")) {
		  time=Integer.parseInt(data.get("message1"));
	    }
	  
	  
	 HashMap<String,String> values=(HashMap<String,String> )context.getAttribute("cookie");
	 System.out.println(PlayDetails.get(usercookie));
	 if(PlayDetails.get(usercookie).get("status").get(0).equals("Playing")) {
  
			     HashMap<String,ArrayList<String>> result=new  HashMap<String,ArrayList<String>>(); 
			    

	 System.out.println(PlayDetails.get(usercookie).get("status").get(0));
	 System.out.println(PlayDetails.get(oppcookie).get("status").get(0));
	 if(PlayDetails.get(usercookie).get("status").get(0).equals("Playing")||data.get("message").equals("")) {		     
			     color1=PlayDetails.get(usercookie).get("coins");
			     System.out.println(color1);
			     color=PlayDetails.get(oppcookie).get("coins");
			     System.out.println(color);
			     System.out.println(data.get("message"));
			     
			     if(PlayDetails.get(usercookie).get("color").get(0).equals("White")) {		    	 
		     			white=color1;
		     			black=color;
		     	}
		     	else {
		     			white=color;
		     			black=color1;
		     	}
			    if(!(data.get("message").equals(""))) {
			     check1(data.get("message"));
			    }
		          
			     	if(PlayDetails.get(usercookie).get("color").get(0).equals("White")) {
			     			white=color1;
			     			black=color;
			    	 
			     	}
			     	else {	    	 
			     			white=color;
			     			black=color1;
			     	}
			     	
			     	  
                    
			    	 int[] whitearray=new int[white.size()];
			    	 int[] blackarray=new int[black.size()];
			    	 
			    	 for(int i=0;i<white.size();i++) {
			    		 whitearray[i]=Integer.parseInt(white.get(i));
			    	 }
			    	 for(int i=0;i<black.size();i++) {
			    		blackarray[i]=Integer.parseInt(black.get(i));
			    	 }
			    	 
			    	 if(whitearray.length==0) {
			    		 whitearray=new int[1];
			    	 }
			    	 if(blackarray.length==0) {
			    		 blackarray=new int[1];
			    	 }
			    	 
			    	 System.out.println(Arrays.toString(blackarray));
			    	 System.out.println(Arrays.toString(whitearray));
			     try{
					    Statement stmt = conn.createStatement();
						String Query="insert into game_info (game_id,whitearray,blackarray,time) values ("+Integer.parseInt(id)+",Array"+Arrays.toString(whitearray)+",Array"+Arrays.toString(blackarray)+","+time+")";//Query to be passed
						System.out.println(Query);
						stmt.executeUpdate(Query);//execution					
				    } catch (SQLException e) {
				      System.out.println(e+"");
				      
				    }
				  
			     
			         HashMap<String,ArrayList<String>> player=PlayDetails.get(usercookie);
	
				     
	
				    HashMap<String,ArrayList<String>> oppositeplayer=PlayDetails.get(oppcookie);
	
				      ArrayList<String> stat=new ArrayList<String>();
				      stat.add("Waiting");
				      player.put("status",stat);
				      player.put("coins",color1);
				      PlayDetails.put(usercookie, player);
                    
				    ArrayList<String> stat1=new ArrayList<String>();
				    stat1.add("Playing");
				    oppositeplayer.put("status",stat1);
				    oppositeplayer.put("coins",color);
				    PlayDetails.put(oppcookie, oppositeplayer);
				    context.setAttribute("PlayerDetails", PlayDetails);
				    System.out.println(PlayDetails);
				    
				  
			     ArrayList<String> s=new ArrayList<String>();
			     ArrayList<String> s1=new ArrayList<String>();
			     s.add(values.get(usercookie));
			     s1.add(values.get(oppcookie));
			     
			     ArrayList<String> photo=new ArrayList<String>();
			     ArrayList<String> photo1=new ArrayList<String>();
			     
			     try{
					    Statement stmt = conn.createStatement();
						String Query="select photo,username from player_info where username in('"+(values.get(usercookie))+"','"+(values.get(oppcookie))+"');";
						System.out.println(Query);
						ResultSet data_table=stmt.executeQuery(Query);
						
						while(data_table.next()) {
							if(data_table.getString("username").equals(values.get(usercookie))) {
								if(data_table.getString("photo")==null) {
									photo.add("../Images/pr.png");
								}
								else {
								photo.add(data_table.getString("photo"));
								}
							}
							else {
                                   if(data_table.getString("photo")==null) {
                                	   photo1.add("../Images/pr.png");
								}
								else {
								photo1.add(data_table.getString("photo"));
								}
							}
						}
						
				    } catch (SQLException e) {
				    	
				      System.out.println(e+"");
				      
				    }
			     try {
					  conn.close();
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
			     System.out.println(photo);
			     System.out.println(photo1);
			     
			     
			     ArrayList<String> sta=new ArrayList<String>();
			     sta.add("ok");
			     result.put("status",sta);
			     result.put("player1", s);
				 result.put("player2", s1);
				 result.put("player1photo",photo);
				 result.put("player2photo",photo1);
				 result.put("player1status", PlayDetails.get(usercookie).get("status"));
				 result.put("player2status", PlayDetails.get(oppcookie).get("status"));
			     result.put("black",black);
			     result.put("white",white);
			     result.put("color",PlayDetails.get(usercookie).get("color"));
			     result.put("oppcolor",PlayDetails.get(oppcookie).get("color"));
			    
		         System.out.println(result);
			     
			     Result result1 =
					        PusherService.getDefaultInstance()
					            .trigger(
					                data.get("channel_id"),
					                "colorchange", 
					                result
					                ); 
			     
			     Result Liveresult =
					        PusherService.getDefaultInstance()
					            .trigger(
					            		"presence-live-"+gameid,
					                "addNew", 
					                result
					                ); 
			     
	  }
	  else { 
		  
		  messageData.put("status","Invalid move");
  
	     response.getWriter().println(gson.toJson(messageData));
	  }
	 }
  
  }
  
  public void check1(String id) {
	  int a=Integer.parseInt(id);
	  System.out.println("a\t"+a);
	  System.out.println("white.indexOf(a)  \t"+white.indexOf(a));
	  System.out.println("black.indexOf(a) \t"+black.indexOf(a));
	  if (white.indexOf(a) == -1 && black.indexOf(a) == -1) {

		  int[] set= {-9, -8, -7, -1, 1, 7, 9, 8};
		  ArrayList<Integer>click1 = new ArrayList<Integer>();
	     
	       int ok = 0;
	        

	       int i = 0;
	       int j = 0;
	       
	        while (i < 8) {
	        	 
	            if (a % 8 == 0) {
	                if (set[i] == 1 || set[i] == -7 || set[i] == 9) {
	                    i++;
	                }
	            }
	            if (a % 8 == 1) {
	                if (set[i] == -1 || set[i] == -9 || set[i] == 7) {
	                    i++;
	                }
	            }
	            
	            if (color.indexOf((a + set[i])+"")!= -1) {
	                click1.add(set[i]);
	                ok = 1;
	            }
	            i++;
	        }
	        System.out.println("click     "+click1);
	      check2(a,click1);
          
          
      } else {
          System.out.println("Place your coin correctly");
      }
  }
  
  public void check2(int save,ArrayList<Integer> click1) {
	  
	  int final1 = 0;
      int t = save;
      int  j = 0;
      HashMap<Integer,Integer> confirm=new HashMap<Integer,Integer>();
       while (j < click1.size()) {

           t = (t + click1.get(j));
           if (color1.indexOf(t+"") != -1) {
               final1 = 1;

               confirm.put(click1.get(j),t);
               j++;
               t = save;

           } else if (color.indexOf(t+"") == -1) {
               j++;
               t = save;
           } else if (t % 8 == 0 || t % 8 == 1) {
               if(click1.get(j) != -8) {
                   if (click1.get(j) !=8) {

                       t = save;
                       j++;
                   }
               }

           } else if (t < 0 || t > 64) { 
             t = save;
               j++;
           }

       }
       System.out.println("confirm  "+confirm);
     colorchange(save,confirm);
	  
  }
  
  public void colorchange(int save,HashMap<Integer,Integer> confirm) {
	  
	  ArrayList<Integer> k=new ArrayList<Integer>();
	  
	  for(Integer i:confirm.keySet()) {
		  k.add(i);
	  }
	  
      int t = save;
      int i = 0;
      int temp;
      while (i < k.size()) {
       

          if (color1.indexOf(t+"") == -1) {
              color1.add(t+"");
          }

          if (color.indexOf(t+"") != -1) {
              temp = color.indexOf(t+"");
              color.remove(temp);
          }
          t = (t + (k.get(i)));
          if (t == confirm.get(k.get(i))) {
              i++;
              t = save;
          }
         
      }
     System.out.println(color);
     System.out.println(color1);	  
  }
}