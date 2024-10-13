package com.devin.utilscenter;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.devin.util.ImageLoader;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        textView.setText("具体请查看Java doc");
        String url="http://f.hiphotos.baidu.com/image/pic/item/a71ea8d3fd1f4134d244519d2b1f95cad0c85ee5.jpg";

        ImageLoader.loadBorderCircle(url,2, Color.WHITE,imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAccessibilityServiceEnabled(MainActivity.this, MyAccessibilityService.class)) {
                    openAccessibilitySettings(MainActivity.this);
                } else {
                    MyAccessibilityService.currentStep =MyAccessibilityService.STEP_START;
                    openWeChat();
                }

            }
        });

    }

    private void initView() {
        textView =  findViewById(R.id.textView);
        imageView =  findViewById(R.id.imageView);
    }


    public boolean isAccessibilityServiceEnabled(Context context, Class<? extends AccessibilityService> service) {
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        List<AccessibilityServiceInfo> enabledServices =
                am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);

        String expectedId = context.getPackageName() + "/" + service.getName();
        String alternativeExpectedId = context.getPackageName() + "/." + service.getSimpleName();

        for (AccessibilityServiceInfo enabledService : enabledServices) {
            if (enabledService.getId().equals(expectedId) || enabledService.getId().equals(alternativeExpectedId)) {
                return true;
            }
        }
        return false;
    }


    public void openAccessibilitySettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        context.startActivity(intent);
    }


    private void openWeChat() {
        // 获取微信的包名
        String weChatPackageName = "com.tencent.mm";

        // 尝试启动微信
        try {
            Intent intent = getPackageManager().getLaunchIntentForPackage(weChatPackageName);
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                // 如果微信未安装，可以处理提示
                Toast.makeText(this, "未安装微信", Toast.LENGTH_SHORT).show();
            }
//            Intent intent = new Intent();
//            intent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI"));
//            intent.addCategory("android.intent.category.LAUNCHER");
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}