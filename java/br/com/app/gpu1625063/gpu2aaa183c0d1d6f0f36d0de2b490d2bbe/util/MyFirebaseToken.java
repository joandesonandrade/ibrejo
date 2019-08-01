package br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.util;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by JoHN on 12/06/2018.
 */

public class MyFirebaseToken extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();

        Log.d("dblog_token",token);

        sendRegistrationToServer(token);

    }

    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
    }
}
