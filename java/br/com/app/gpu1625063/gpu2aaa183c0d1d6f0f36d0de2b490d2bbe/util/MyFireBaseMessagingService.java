package br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.util;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by JoHN on 12/06/2018.
 */


public class MyFireBaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getData().size() > 0){
            if(remoteMessage.getData().get("content")!=null) {
                Log.d("dblog_content", remoteMessage.getData().get("content"));
            }
        }

    }
}
