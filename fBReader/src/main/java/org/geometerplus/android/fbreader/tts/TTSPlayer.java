package org.geometerplus.android.fbreader.tts;

import android.content.Context;
import android.util.Log;

import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizerListener;

import org.geometerplus.android.fbreader.tts.util.TimeUtils;
import org.geometerplus.fbreader.book.Book;
import org.geometerplus.fbreader.fbreader.FBReaderApp;
import org.geometerplus.fbreader.fbreader.FBView;
import org.geometerplus.zlibrary.text.model.ZLTextModel;

import java.util.LinkedHashMap;
import java.util.LinkedList;
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
     * 是否在播放
     */
    private boolean isPlaying = false;

    private FBReaderApp mFBReaderApp;

    private LinkedList<TTSPlayerCallback> callbacks = new LinkedList<>();

    private TTSPlayer() {

    }

    public static TTSPlayer getInstance() {
        return Holder.instance;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public void init(Context context, FBReaderApp fbReaderApp) {
        this.mFBReaderApp = fbReaderApp;
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
                for (TTSPlayerCallback callback : callbacks) {
                    if (callback != null) {
                        // UI进度更新（外部回调）
                        callback.onProgressUpdate(TimeUtils.getTimeMillis(currentPosition, TTSProvider.SPEED),
                                TimeUtils.getTimeMillis(ttsHelper.getTotalCount(), TTSProvider.SPEED));
                    }
                }

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
     * 语音合成
     */
    private void synthesise() {
        LinkedHashMap<String, String> map = ttsHelper.getCurrentTextMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            ttsProvider.mSpeechSynthesizer.speak(entry.getValue(), entry.getKey());
        }
    }

    public Book getBook() {
        return mFBReaderApp.Model.Book;
    }

    /**
     * 处理文本内容
     */
    public void process() {
        ttsHelper.processText();
    }

    @Override
    public int getDuration() {
        return TimeUtils.getTimeMillis(currentPosition, TTSProvider.SPEED);
    }

    @Override
    public int getCurrentPosition() {
        return TimeUtils.getTimeMillis(ttsHelper.getTotalCount(), TTSProvider.SPEED);
    }

    @Override
    public void start() {
        ttsProvider.mSpeechSynthesizer.resume();
    }

    @Override
    public void pause() {
        ttsProvider.mSpeechSynthesizer.pause();
    }

    @Override
    public void stop() {
        ttsProvider.mSpeechSynthesizer.stop();
    }

    @Override
    public String getSpeed() {
        return String.valueOf(TTSProvider.SPEED);
    }

    @Override
    public void setSpeed(String speed) {
        ttsProvider.setSpeed(speed);
    }

    public void addPlayCallback(TTSPlayerCallback callback) {
        callbacks.add(callback);
    }

    public String getName() {
        FBView view = mFBReaderApp.getTextView();
        String tocText = view.getTOCText(view.getStartCursor());
        return tocText == null ? "" : tocText;
    }

    private static class Holder {
        public static final TTSPlayer instance = new TTSPlayer();
    }
}
