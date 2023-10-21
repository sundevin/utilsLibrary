package com.devin.util;

import android.content.Context;
import android.media.AudioManager;

/**
 * <p>Description:
 * <p>Company：
 * <p>Email:bjxm2013@163.com
 * <p>Created by Devin Sun on 2017/11/6.
 */
public class SpeakerUtils {


    /**
     * 扬声器开关
     * @param context
     * @param enableSpeaker 是否打开扬声器，true:打开，false:关闭
     */
    public static void switchSpeaker(Context context, boolean enableSpeaker) {
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            audioManager.setMode(AudioManager.MODE_IN_CALL);
            int currVolume = audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
            if (enableSpeaker) {
                //setSpeakerphoneOn() only work when audio mode set to MODE_IN_CALL.
                audioManager.setMode(AudioManager.MODE_IN_CALL);
                audioManager.setSpeakerphoneOn(true);
                audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,
                        audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL),
                        AudioManager.STREAM_VOICE_CALL);
            } else {
                audioManager.setSpeakerphoneOn(false);
                audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, currVolume,
                        AudioManager.STREAM_VOICE_CALL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取扬声器是否开启
     * @param context
     * @return  true:已打开，false:已关闭
     */
    public static boolean isOpenSpeaker(Context context) {
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            return audioManager.isSpeakerphoneOn();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
