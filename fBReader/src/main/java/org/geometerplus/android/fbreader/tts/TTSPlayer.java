package org.geometerplus.android.fbreader.tts;

import android.content.Context;
import android.util.Log;

import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizerListener;

import org.geometerplus.android.fbreader.tts.util.TimeUtils;
import org.geometerplus.fbreader.fbreader.FBReaderApp;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 语音合成播放
 */
public class TTSPlayer implements IPlayer {

    public static final String TAG = "TTSPlayer";

    /**
     * 语音合成提供者
     */
    private TTSProvider ttsProvider;
    /**
     * TTSReader帮助类
     */
    private TTSHelper ttsHelper;
    /**
     * 当前位置
     */
    private int currentPosition;
    /**
     * 播放的回调
     */
    private TTSPlayerCallback mPlayCallback;

    public TTSPlayer(Context context, FBReaderApp fbReaderApp) {
        ttsHelper = new TTSHelper(fbReaderApp);
        init(context);
    }

    /**
     * 初始化相关操作
     *
     * @param context 上下文
     */
    private void init(Context context) {
        ttsProvider = new TTSProvider(context);

        ttsProvider.setTTSListener(new SpeechSynthesizerListener() {

            /**
             * 合成开始
             */
            @Override
            public void onSynthesizeStart(String utteranceId) {
            }

            /**
             * 合成过程中的数据回调接口
             */
            @Override
            public void onSynthesizeDataArrived(String utteranceId, byte[] audioData, int progress) {

            }

            /**
             * 合成结束
             */
            @Override
            public void onSynthesizeFinish(String utteranceId) {
            }

            /**
             * 播放开始
             */
            @Override
            public void onSpeechStart(String utteranceId) {
                ttsHelper.start(utteranceId);
            }

            /**
             * 播放过程中的回调
             *
             * @param progress 从0 到 “合成文本的字符数”
             */
            @Override
            public void onSpeechProgressChanged(String utteranceId, int progress) {
                // 更新进度
                updatePosition(progress);
                // UI进度更新（外部回调）
                mPlayCallback.onProgressUpdate(TimeUtils.getTimeMillis(currentPosition, TTSProvider.SPEED),
                        TimeUtils.getTimeMillis(ttsHelper.getTotalCount(), TTSProvider.SPEED));
                // 翻页
                ttsHelper.highlight(utteranceId);
            }

            /**
             * 播放结束
             */
            @Override
            public void onSpeechFinish(String utteranceId) {
                ttsHelper.finish(utteranceId);
            }

            /**
             * code：int，错误码。 具体错误码见“错误码及解决方法”一节
             * description： String, 具体的错误信息。
             */
            @Override
            public void onError(String utteranceId, SpeechError speechError) {
                Log.e(TAG, "语音合成发生错误：" + "错误码（" + speechError.code + "）" +
                        "错误描述（" + speechError.description + "）");
            }
        });

        ttsHelper.setOnProcessComplete(this::synthesise);
    }

    /**
     * 更新当前的进度
     *
     * @param progress 该句中的进度
     */
    private void updatePosition(int progress) {
        currentPosition = ttsHelper.getStartIndex() + progress;
    }

    /**
     * 处理文本内容
     */
    public void process() {
        ttsHelper.processText();
    }

    /**
     * 语音合成
     */
    private void synthesise() {
        LinkedHashMap<String, String> map = ttsHelper.getCurrentTextMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            ttsProvider.mSpeechSynthesizer.speak(entry.getValue(), entry.getKey());
        }
    }

    @Override
    public int getDuration() {
        return TimeUtils.getTimeMillis(currentPosition, TTSProvider.SPEED);
    }

    @Override
    public int getCurrentPosition() {
        return TimeUtils.getTimeMillis(ttsHelper.getTotalCount(), TTSProvider.SPEED);
    }

    public void setPlayCallback(TTSPlayerCallback callback) {
        this.mPlayCallback = callback;
    }
}
