package org.geometerplus.android.fbreader.tts;

import android.content.Context;

import com.baidu.tts.chainofresponsibility.logger.LoggerProxy;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.TtsMode;

/**
 * 语音合成
 */
public class TTSProvider {

    private final String AppId = "16237949";
    private final String AppKey = "BAn6205aD5Xb9TXgGbGGsxvH";
    private final String AppSecret = "M2Luu4AGngce7W4tBeio2ZrEBRK123ek";

    public SpeechSynthesizer mSpeechSynthesizer = SpeechSynthesizer.getInstance();

    public TTSProvider(Context context) {
        LoggerProxy.printable(true);
        mSpeechSynthesizer.setAppId(AppId);
        mSpeechSynthesizer.setApiKey(AppKey, AppSecret);
        mSpeechSynthesizer.setContext(context);
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "3");
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEED, "10");
        mSpeechSynthesizer.initTts(TtsMode.MIX);
    }
}
