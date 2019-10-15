package org.geometerplus.android.fbreader.tts;

import android.content.Context;

import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

/**
 * 语音合成
 */
public class TTSProvider {

    private static final String AppId = "16237949";
    private static final String AppKey = "BAn6205aD5Xb9TXgGbGGsxvH";
    private static final String AppSecret = "M2Luu4AGngce7W4tBeio2ZrEBRK123ek";

    public SpeechSynthesizer mSpeechSynthesizer = SpeechSynthesizer.getInstance();

    public TTSProvider(Context context) {
        mSpeechSynthesizer.setAppId(AppId);
        mSpeechSynthesizer.setApiKey(AppKey, AppSecret);
        mSpeechSynthesizer.setContext(context);
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "5");
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "15");
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_HIGH_SPEED_NETWORK);
        mSpeechSynthesizer.initTts(TtsMode.ONLINE);
    }

    public void setTTSListener(SpeechSynthesizerListener listener) {
        mSpeechSynthesizer.setSpeechSynthesizerListener(listener);
    }

    private void initTTSListener() {
        mSpeechSynthesizer.setSpeechSynthesizerListener(new SpeechSynthesizerListener() {

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

            }

            /**
             * 播放过程中的回调
             *
             * @param progress 从0 到 “合成文本的字符数”
             */
            @Override
            public void onSpeechProgressChanged(String utteranceId, int progress) {

            }

            /**
             * 播放结束
             */
            @Override
            public void onSpeechFinish(String utteranceId) {

            }

            /**
             * code：int，错误码。 具体错误码见“错误码及解决方法”一节
             * description： String, 具体的错误信息。
             */
            @Override
            public void onError(String utteranceId, SpeechError speechError) {

            }
        });
    }
}