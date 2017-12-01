/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.appengine.pusher;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Homepage of chat application, redirects user to login page if not authorized. */
public class ChatServlet extends HttpServlet {

  public static String getUriWithChatRoom(HttpServletRequest request, String chatRoom) {
    try {
      String query = "";
      if (chatRoom != null) {
        query = "room=" + chatRoom;
      }
      System.out.println(request.getRequestURL().toString()+"  getRequestURL()");
      URI thisUri = new URI(request.getRequestURL().toString());
      System.out.println(thisUri+" thisUri");
      System.out.println(thisUri.getScheme()+" "+
              thisUri.getUserInfo()+" "+
              thisUri.getHost()+" "+
              thisUri.getPort());
      URI uriWithOptionalRoomParam =
          new URI(
              thisUri.getScheme(),
              thisUri.getUserInfo(),
              thisUri.getHost(),
              thisUri.getPort(),
              "/",
              query,
              "");
      System.out.println(uriWithOptionalRoomParam.toString());
      return uriWithOptionalRoomParam.toString();
      
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    final UserService userService = UserServiceFactory.getUserService();
    User currentUser = userService.getCurrentUser();
    System.out.println(currentUser+" currentUser");
    String room = req.getParameter("room");
    System.out.println(room+" room");
    // Show login link if user is not logged in.
    if (currentUser == null) {
      String loginUrl = userService.createLoginURL(getUriWithChatRoom(req, room));
      System.out.println(loginUrl+" loginUrl");
      resp.getWriter().println("<p>Please <a href=\"" + loginUrl + "\">sign in</a>.</p>");
      return;
    }
System.out.println("After if "+room);
    // user is already logged in
    if (room != null) {
      req.setAttribute("room", room);
    }
    getServletContext().getRequestDispatcher("/WEB-INF/view/chat.jsp").forward(req, resp);
  }
}
