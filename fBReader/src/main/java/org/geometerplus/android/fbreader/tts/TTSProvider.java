package org.geometerplus.android.fbreader.tts;

import android.content.Context;

import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

/**
 * 语音合成
 */
public class TTSProvider {

    /**
     * 语速
     */
    static final int SPEED = 10;

    private static final String AppId = "16237949";
    private static final String AppKey = "BAn6205aD5Xb9TXgGbGGsxvH";
    private static final String AppSecret = "M2Luu4AGngce7W4tBeio2ZrEBRK123ek";
    public SpeechSynthesizer mSpeechSynthesizer = SpeechSynthesizer.getInstance();

    public TTSProvider(Context context) {
        LoggerProxy.printable(false);
        mSpeechSynthesizer.setAppId(AppId);
        mSpeechSynthesizer.setApiKey(AppKey, AppSecret);
        mSpeechSynthesizer.setContext(context);
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, String.valueOf(SPEED));
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_HIGH_SPEED_NETWORK);
        mSpeechSynthesizer.initTts(TtsMode.ONLINE);
        mSpeechSynthesizer.auth(TtsMode.ONLINE);
    }

    void setTTSListener(SpeechSynthesizerListener listener) {
        mSpeechSynthesizer.setSpeechSynthesizerListener(listener);
    }
}