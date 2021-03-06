/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.httprpc.demo;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import org.httprpc.ResultHandler;
import org.httprpc.WebServiceProxy;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NotesApplication extends Application {
    private static WebServiceProxy serviceProxy;

    public static WebServiceProxy getServiceProxy() {
        return serviceProxy;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        URL serverURL;
        try {
            serverURL = new URL("http://10.0.2.2:8080");
        } catch (MalformedURLException exception) {
            throw new RuntimeException(exception);
        }

        serviceProxy = new WebServiceProxy(serverURL, Executors.newSingleThreadExecutor()) {
            private Handler handler = new Handler(Looper.getMainLooper());

            @Override
            protected void dispatchResult(Runnable command) {
                handler.post(command);
            }
        };
    }
}
