package Ajax;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;

import HelperClasses.ConnectionDatabase;
import HelperClasses.Cooky;

public class ShowResult extends HttpServlet {
	private Gson gson = new GsonBuilder().create();
	  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		  
		  ServletContext context  =   request.getSession().getServletContext();
		  HashMap<String,HashMap<String,ArrayList<String>>> playdetails= (HashMap<String,HashMap<String,ArrayList<String>>>) context.getAttribute("PlayDetails");
		  String cookievalue=Cooky.getCookieValue("gc_account", request.getCookies());
		  if(cookievalue==null) {
			  response.sendRedirect("http://localhost:8080/home");
		  }
		  else {
			  System.out.println(cookievalue);
			  System.out.println((HashMap<String,String>)context.getAttribute("cookie"));
			 String name=((HashMap<String,String>)context.getAttribute("cookie")).get(cookievalue);
			 
			 ConnectionDatabase psql = new ConnectionDatabase();
			  Connection conn			=psql.createConnection("gamecenter");
			  
			  int playerid=0;
			  String playerphoto="";
			  String playerwin="";
			  String playerscore="";
				 
			  try{
				    Statement stmt = conn.createStatement();
				    String Query="select player_id,photo,win,score from player_info where username='"+name+"'";
					ResultSet data_table=stmt.executeQuery(Query);
					
					while(data_table.next()) {
						playerid=data_table.getInt("player_id");
						playerphoto=data_table.getString("photo");
						playerwin=data_table.getInt("win")+"";
						playerscore=data_table.getInt("score")+"";
					}
					
			    } catch (SQLException e) {
			    	
			        System.out.println(e+"");
			    }
			  
			  try{
				    Statement stmt = conn.createStatement();
				    String Query="select player_id,photo from player_info where username='"+name+"'";
					ResultSet data_table=stmt.executeQuery(Query);
					
					while(data_table.next()) {
						playerid=data_table.getInt("player_id");
						playerphoto=data_table.getString("photo");
					}
					
			    } catch (SQLException e) {
			    	
			        System.out.println(e+"");
			    }
			  
			  String status="";
			  int oppplayer=0;
			  String oppstatus="";
			  try{
				    Statement stmt = conn.createStatement();
				    String Query="select game_id,winner_id,player1_id,player2_id from game_list where player1_id='"+playerid+"' or player2_id='"+playerid+"'";
					ResultSet data_table=stmt.executeQuery(Query);
					
					while(data_table.next()) {
						System.out.println(data_table.getInt("winner_id"));
						System.out.println(playerid);
						System.out.println((data_table.getInt("winner_id")+"").equals(playerid));
						if(playerid==data_table.getInt("player1_id")) {
							
							oppplayer=data_table.getInt("player2_id");
						}
						else {
							oppplayer=data_table.getInt("player1_id");
						}
						
						if(data_table.getInt("winner_id")==playerid) {
							status="winner";
							
						}
						else {
						
							status="loser";
							
						}
						
					}
					
			    } catch (SQLException e) {
			    	
			        System.out.println(e+"");
			    }
			  
			  if(status.equals("winner")) {
				  oppstatus="loser";
			  }
			  else {
				  oppstatus="winner";
			  }
			  
			  String oppname="";
			  String oppphoto="";
			  String oppwin="";
			  String oppscore="";
			  try{
				    Statement stmt = conn.createStatement();
				    String Query="select username,photo,score,win from player_info where player_id='"+oppplayer+"'";
					ResultSet data_table=stmt.executeQuery(Query);
					
					while(data_table.next()) {
						oppname=data_table.getString("username");
						oppphoto=data_table.getString("photo");
						oppwin=data_table.getInt("win")+"";
						oppscore=data_table.getString("score");		
					}
					
			    } catch (SQLException e) {
			    	
			        System.out.println(e+"");
			    }
			  
			  
			  
    		  playdetails.remove(cookievalue);
    		  HashMap<String,String[]> gameid=(HashMap<String,String[]>) context.getAttribute("GameIds");
    		  
    		  String id="";
    		  
    		  for(String key:gameid.keySet()) {
    			  
    			  if(gameid.get(key)[0].equals(cookievalue)||gameid.get(key)[1].equals(cookievalue)) {
    				  
    				  id=key;
    				  
    			  }
	  
    		  }
    		  if(!id.equals("")) {
    		  gameid.remove(id);
    		  }
    		  
    		  String div="";
//    		   ArrayList<Integer> scores=new ArrayList<Integer>();
//    		  ArrayList<String>  names=new ArrayList<String>();
//    		  HashMap<String,String[]> scoredetails=new HashMap<String,String[]>();
    		  try{
				    Statement stmt = conn.createStatement();
				    String Query="select username,win,score,photo from player_info order by score desc";
					ResultSet data_table=stmt.executeQuery(Query);
					
					while(data_table.next()) {
//						scores.add(data_table.getInt("score"));
//						names.add(data_table.getString("username"));
//						String[] d= {data_table.getString("score")+"",data_table.getInt("win")+"",data_table.getString("photo")};
//						scoredetails.put(data_table.getString("username"), d);
		    			  String p=data_table.getString("photo");
		    			  if(p==null) {
		    				  p="../Images/pr.png";
		    			  }
		    			  String n=data_table.getString("username");
		    			  String w=data_table.getInt("win")+"";
		    			  String s=data_table.getString("score")+"";

						
						div+="<div class='scrdiv'><ul id='fir-ul'><li><div style=\"background-image: url('"+p+"');\"></div></li><li>"+n+"</li><li><img src='../Images/star1.png'><div>"+s+"</div></li><li><div><img src='../Images/cup.png'><div>"+w+"</div></div></li></ul></div><div class='scrdiv'>";
						
						
					}
					
			    } catch (SQLException e) {
			    	
			        System.out.println(e+"");
			    }
    		  
    		  try {
				  conn.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				
//    		  for(int i=0;i<scores.size();i++) {
//    			  for(int j=0;j<scores.size();j++) {
//    				  
//    				  if(scores.get(i)<scores.get(j)) {
//    					  
//    					  int temp=scores.get(i);
//    					  String tempname=names.get(i);
//    					  scores.set(i,scores.get(j));
//    					  scores.set(j,temp);
//    					  names.set(i,names.get(j));
//    					  names.set(j,tempname);
//    					  
//    				  }
//    			  }
//    		  }
//    		 
//    		  String div="";
//              
//    		  for(int k=0;k<names.size();k++) {
//    			  String p=scoredetails.get(names.get(k))[2];
//    			  if(p==null) {
//    				  p="../Images/pr.png";
//    			  }
//    			  String n=names.get(k);
//    			  String w=scoredetails.get(names.get(k))[1];
//    			  String s=scoredetails.get(names.get(k))[0];
//    			  div+="<div class='scrdiv'><ul id='fir-ul'><li><div style=\"background-image: url('"+p+"');\"></div></li><li>"+n+"</li><li><img src='../Images/star1.png'><div>"+s+"</div></li><li><div><img src='../Images/cup.png'><div>"+w+"</div></div></li></ul></div><div class='scrdiv'>";
//    		  }
    		  
			  HashMap<String,String> result=new HashMap<String,String>();
			  result.put("name", name);
			  result.put("status",status);
			  result.put("oppname",oppname);
			  result.put("oppstatus",oppstatus);
			  result.put("ranking",div);
			  result.put("win",playerwin);
			  result.put("score",playerscore);
			  result.put("win1",oppwin);
			  result.put("score1",oppscore);
			  if(playerphoto==null) {
            	  result.put("photo","../Images/pr.png");
              }
              else {
            	  result.put("photo","http://localhost:8080"+playerphoto);
              }
			  if(oppphoto==null) {
            	  result.put("photo1","../Images/pr.png");
              }
              else {
            	  result.put("photo1","http://localhost:8080"+oppphoto);
              }
			  
			  System.out.println(result);
			  response.getWriter().println(gson.toJson(result));
			  
		  }
	  }
}
