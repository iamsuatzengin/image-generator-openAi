package com.suatzengin.imagegeneratorwithopenai.util;

import static com.suatzengin.imagegeneratorwithopenai.util.Constants.CHANNEL_ID;
import static com.suatzengin.imagegeneratorwithopenai.util.Constants.NOTIFICATION_ID;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.suatzengin.imagegeneratorwithopenai.R;

public final class StatusNotification {


    public static void makeStatusNotification(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_save)
                .setContentTitle("Image Generator")
                .setContentText("Saved image!")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "channel_name";
            String channelDescription = "channel_description";
            int channelImportance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel =
                    new NotificationChannel(CHANNEL_ID, channelName, channelImportance);
            channel.setDescription(channelDescription);
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.createNotificationChannel(channel);

        }

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build());

    }
}
