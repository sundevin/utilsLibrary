package com.devin.util;

import android.content.Context;
import android.content.res.AssetManager;

import com.devin.UtilManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class AssetUtils {

    /**
     * 读取Asset文件的内容
     * @param fileName
     * @return
     */
    public static String readAssetText(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = UtilManager.getContext().getAssets();
            InputStream inputStream = assetManager.open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    /**
     * 复制 Asset 文件到指定路径
     * @param context
     * @param assetFileName
     * @param destinationPath
     * @return
     */
    public static File copyAssetFile(Context context, String assetFileName, String destinationPath) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = UtilManager.getContext().getAssets().open(assetFileName);
            outputStream = new FileOutputStream(destinationPath);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            return new File(destinationPath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
