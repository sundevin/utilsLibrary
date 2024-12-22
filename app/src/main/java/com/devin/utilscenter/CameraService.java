package com.devin.utilscenter;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.util.Size;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraService extends Service {

    private ImageCapture imageCapture;
    private ExecutorService cameraExecutor;

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化相机的执行器
        cameraExecutor = Executors.newSingleThreadExecutor();

        // 初始化前台服务通知
        startForegroundService();
        initCamera();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        takePicture();

        return super.onStartCommand(intent, flags, startId);

    }

    // 设置前台服务通知
    private void startForegroundService() {
        String channelId = "CameraServiceChannel";
        String channelName = "Camera Background Service";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle("Camera Service")
                .setContentText("Running camera capture in background")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        startForeground(1, notification);
    }

    // 初始化并配置相机
    private void initCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                // 配置 ImageCapture
                imageCapture = new ImageCapture.Builder()
                        .setTargetResolution(new Size(1280, 720))  // 指定分辨率
                        .setJpegQuality(85) // 设置图片质量
                        .build();

                // 绑定到生命周期
                cameraProvider.bindToLifecycle(null, cameraSelector, imageCapture);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    // 对外提供的抓拍接口
    public void takePicture() {
        if (imageCapture == null) {
            return;
        }

        File photoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "IMG_" + System.currentTimeMillis() + ".jpg");

        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        // 抓拍成功，图片已保存
                        Toast.makeText(getApplication(), "图片已保存: " + photoFile.getAbsolutePath(),
                                Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        exception.printStackTrace();
                    }
                }
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}

