package com.babu.adminbug.notification.sendnotify;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FirebaseApi {
    @Headers({"Authorization: key=AAAAPQgSVHI:APA91bG1uuR6E6wDkli7aXqfrIchJC25tARgcfouK45LQrMXQPYyE_dZ49DdQYywdHkMu5TDEPFtTasGHmWTRGNCDxXLvzzrdu30DpcyWt2mSTvTQ4g_4IS40ChdqEbvo2li_Xm5sWKm",
            "Content-Type:application/json"})
    @POST("fcm/send")
    Call<FirebaseMessage> sendMessage(@Body FirebaseMessage message);
}
