package org.geometerplus.android.fbreader.tts;

import android.content.Context;
import android.util.Log;

import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizerListener;


/**
 * 语音合成播放
 */
public class TTSPlayer implements IPlayer {

    public static final String TAG = "TTSPlayer";

    /**
     * 总字符数
     */
    private long totalCount;
    /**
     * 起始的字符数（即该句之前的字符数）
     */
    private long startCount;
    /**
     * 当前已播放的字符数
     */
    private long currentCount;

    /**
     * 语音合成提供者
     */
    private TTSProvider ttsProvider;

    @Override
    public void start() {

    }

    @Override
    public void pause() {

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
                Log.i(TAG, "语音合成开始");
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
                Log.i(TAG, "语音合成结束");
            }

            /**
             * 播放开始
             */
            @Override
            public void onSpeechStart(String utteranceId) {
                Log.i(TAG, "语音播放开始");
            }

            /**
             * 播放过程中的回调
             *
             * @param progress 从0 到 “合成文本的字符数”
             */
            @Override
            public void onSpeechProgressChanged(String utteranceId, int progress) {
                currentCount = startCount + progress;

            }

            /**
             * 播放结束
             */
            @Override
            public void onSpeechFinish(String utteranceId) {
                Log.i(TAG, "语音播放结束");
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
    }
}
