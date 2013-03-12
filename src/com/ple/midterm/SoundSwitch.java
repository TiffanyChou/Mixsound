package com.ple.midterm;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

public class SoundSwitch implements Runnable {

    // 傾聽器
    private OnReachedVolumeListener mListener;
    // 錄音中旗標
    private boolean isRecoding = true;
    // 取樣率
    private static final int SAMPLE_RATE = 8000;
    // 邊界音量
    private short mBorderVolume = 10000;

    // 設定臨界音量
    public void setBorderVolume(short volume) {
        mBorderVolume = volume;
    }

    // 取得臨界音量
    public short getBorderVolume() {
        return mBorderVolume;
    }

    // 停止錄音
    public void stop() {
        isRecoding = false;
    }

    // 設定OnReachedVolumeListener
    public void setOnVolumeReachedListener(
            OnReachedVolumeListener listener) {
        mListener = listener;
    }

    // 感應到臨界音量時 的傾聽器
    public interface OnReachedVolumeListener {
        // 當感應到超過臨界音量的聲音時
        // 會被呼叫的方法
        void onReachedVolume(short volume);
    }

    // 開始執行緒（開始錄音）
    public void run() {
        android.os.Process.setThreadPriority(
                android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
        int bufferSize = AudioRecord.getMinBufferSize(
                SAMPLE_RATE,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
        AudioRecord audioRecord = new AudioRecord(
                MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSize);
        short[] buffer = new short[bufferSize];
        audioRecord.startRecording();
        while(isRecoding) {
            audioRecord.read(buffer, 0, bufferSize);
            short max = 0;
            for (int i=0; i<bufferSize; i++) {
                // 計算最大音量
                max = (short)Math.max(max, buffer[i]);
                // 當最大音量超過臨界音量
                if (max > mBorderVolume) {
                    if (mListener != null) {
                        // 執行傾聽器
                        mListener.onReachedVolume(max);
                        break;
                    }
                }
            }
        }
        audioRecord.stop();
        audioRecord.release();
    }
}
