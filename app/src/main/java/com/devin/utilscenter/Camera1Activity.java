package com.devin.utilscenter;

import android.app.AlertDialog;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Camera1Activity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    private int selectedCameraId = -1; // 用户选择的摄像头ID
    private List<Integer> availableCameraIds = new ArrayList<>();
    private Button btnCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera1);

        initView();
        surfaceView = findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(surfaceCallback);

        // 加载摄像头列表并弹窗选择
        loadCameraIds();
        showCameraSelectionDialog();

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });
    }

    // 加载设备支持的摄像头ID
    private void loadCameraIds() {
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            availableCameraIds.add(i);
        }
    }

    // 显示选择摄像头的弹窗
    private void showCameraSelectionDialog() {
        if (availableCameraIds.isEmpty()) {
            Toast.makeText(this, "No cameras available", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] cameraOptions = new String[availableCameraIds.size()];
        for (int i = 0; i < availableCameraIds.size(); i++) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(availableCameraIds.get(i), cameraInfo);

            String cameraType = cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK ?
                    "Back Camera" : "Front Camera";
            cameraOptions[i] = cameraType + " (ID: " + availableCameraIds.get(i) + ")";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Camera");
        builder.setItems(cameraOptions, (dialog, which) -> {
            selectedCameraId = availableCameraIds.get(which);
            openCamera(selectedCameraId); // 打开用户选择的摄像头
        });
        builder.setCancelable(false);
        builder.show();
    }

    // 打开摄像头
    private void openCamera(int cameraId) {
        try {
            camera = Camera.open(cameraId);
            camera.setDisplayOrientation(90); // 调整预览方向
            camera.setPreviewDisplay(surfaceHolder);
            startPreview();
        } catch (Exception e) {
            Log.e("CameraError", "Failed to open camera: " + e.getMessage());
            Toast.makeText(this, "Failed to open camera", Toast.LENGTH_SHORT).show();
        }
    }

    // 开始预览
    private void startPreview() {
        try {
            if (camera != null) {
                camera.startPreview();
            }
        } catch (Exception e) {
            Log.e("CameraError", "Failed to start preview: " + e.getMessage());
        }
    }

    // 释放摄像头资源
    private void releaseCamera() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    // SurfaceHolder Callback
    private final SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            // 此处等待用户选择摄像头
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (camera != null) {
                Camera.Parameters parameters = camera.getParameters();
                parameters.setPreviewSize(width, height);
                camera.setParameters(parameters);
                startPreview();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            releaseCamera();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }


    private void captureImage() {
        if (camera != null) {
            camera.takePicture(null, null, pictureCallback);
        }
    }

    private final Camera.PictureCallback pictureCallback = (data, camera) -> {
        File file = new File(getExternalFilesDir(null), "capture.jpg");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
            Toast.makeText(this, "Image saved: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        camera.startPreview(); // 重新启动预览
    };

    private void initView() {
        btnCapture = findViewById(R.id.btnCapture);
    }
}
