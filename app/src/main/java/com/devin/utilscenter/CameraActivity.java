package com.devin.utilscenter;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Size;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;

public class CameraActivity extends AppCompatActivity {

    private ImageCapture imageCapture;
    private final int REQUEST_CODE_PERMISSIONS = 1001;
    //    private final String[] REQUIRED_PERMISSIONS = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private final String[] REQUIRED_PERMISSIONS = new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // 申请相机权限
        if (allPermissionsGranted()) {
            startCamera(); // 权限通过后启动相机
        } else {
            requestPermissions(REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }
    }

    // 检查所有权限是否已授予
    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    // 启动 CameraX
    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

//                Preview preview = new Preview.Builder().build();
                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
//                CameraSelector cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA;

                imageCapture = new ImageCapture.Builder()
                        .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation())
                        .setTargetResolution(new Size(1369,1824))
                        .setJpegQuality(85)
                        .build();

//                PreviewView previewView = findViewById(R.id.viewFinder);
                // 设置预览输出
//                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                // 绑定生命周期与相机
                cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, imageCapture);

                // 自动抓拍
//                autoCapturePhoto();
                findViewById(R.id.btn_take).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        autoCapturePhoto();
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    // 自动抓拍并保存照片
    private void autoCapturePhoto() {
        File photoFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "IMG_" + System.currentTimeMillis() + ".jpg");

        ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        // 调用 takePicture 实现自动抓拍
        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        // 抓拍成功，图片已保存
                        Toast.makeText(CameraActivity.this, "图片已保存: " + photoFile.getAbsolutePath(),
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        // 处理错误
                        exception.printStackTrace();
                    }
                }
        );
    }

    // 处理权限申请结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera();
            } else {
                Toast.makeText(this, "未授予相机权限", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
