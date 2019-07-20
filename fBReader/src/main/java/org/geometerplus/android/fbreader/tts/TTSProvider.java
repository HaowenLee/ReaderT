package org.geometerplus.android.fbreader.tts;

import android.content.Context;

import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.TtsMode;

/**
 * 语音合成（百度语音合成）
 */
public class TTSProvider {

    private final String AppId = "app id";
    private final String AppKey = "app key";
    private final String AppSecret = "app secret";

    public SpeechSynthesizer mSpeechSynthesizer = SpeechSynthesizer.getInstance();

    public TTSProvider(Context context) {
        mSpeechSynthesizer.setAppId(AppId);
        mSpeechSynthesizer.setApiKey(AppKey, AppSecret);
        mSpeechSynthesizer.setContext(context);
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "3");
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "5");
        mSpeechSynthesizer.initTts(TtsMode.MIX);
    }
}
