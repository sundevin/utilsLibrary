package com.devin.util;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Base64Utils {


    public static String encodeFile(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            return Base64.encodeToString(data, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void decodeToFile(String base64String, File outputFile) {
        try {
            byte[] data = Base64.decode(base64String, Base64.DEFAULT);
            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(data);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String encode(String input) {
        byte[] data = input.getBytes();
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    public static String decode(String input) {
        byte[] data = Base64.decode(input, Base64.DEFAULT);
        return new String(data);
    }

}
