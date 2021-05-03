package com.babu.adminbug;

import android.util.Log;

import com.babu.adminbug.notification.sendnotify.FirebaseApi;
import com.babu.adminbug.notification.sendnotify.FirebaseClient;
import com.babu.adminbug.notification.sendnotify.FirebaseMessage;
import com.babu.adminbug.notification.sendnotify.NotifyData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class Notication_send {
    public void sendNotifiy(String title,String message) {
        sendNotification("/topics/event",title,message);
    }

    private void sendNotification(String deviceId,String title,String message)
    {  FirebaseApi apiService =
            FirebaseClient.getClient().create(FirebaseApi.class);
        NotifyData notifydata = new NotifyData(title,message);
        Call<FirebaseMessage> call = apiService.sendMessage(new
                FirebaseMessage(deviceId, notifydata));
        call.enqueue(new Callback<FirebaseMessage>() {

            @Override
            public void onResponse(Call<FirebaseMessage> call,
                                   Response<FirebaseMessage> response) {
                Log.e("Message Response","Send");
            }
            @Override
            public void onFailure(Call<FirebaseMessage> call, Throwable t) {
                Log.e("Message Response","Fail");
            }
        });
    }
}
