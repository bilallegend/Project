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

import com.pusher.rest.Pusher;

public abstract class PusherService {

  public static final String APP_KEY = System.getenv("PUSHER_APP_KEY");
  public static final String CLUSTER = System.getenv("PUSHER_CLUSTER");

  private static final String APP_ID = System.getenv("PUSHER_APP_ID");
  private static final String APP_SECRET = System.getenv("PUSHER_APP_SECRET");

  private static Pusher instance;

  public static Pusher getDefaultInstance() {
	  System.out.println("Publicservice");
    if (instance != null) {
    	System.out.println("instance is not null");
      return instance;
    } // Instantiate a pusher
    Pusher pusher = new Pusher(APP_ID, APP_KEY, APP_SECRET);
    pusher.setCluster(CLUSTER); // required, if not default mt1 (us-east-1)
    pusher.setEncrypted(true); // optional, ensure subscriber also matches these settings
    
    instance = pusher;
    return pusher;
  }
}
