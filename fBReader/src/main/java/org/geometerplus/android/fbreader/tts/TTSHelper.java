package org.geometerplus.android.fbreader.tts;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import org.geometerplus.fbreader.fbreader.ActionCode;
import org.geometerplus.fbreader.fbreader.FBReaderApp;
import org.geometerplus.zlibrary.text.view.ZLTextElement;
import org.geometerplus.zlibrary.text.view.ZLTextFixedPosition;
import org.geometerplus.zlibrary.text.view.ZLTextParagraphCursor;
import org.geometerplus.zlibrary.text.view.ZLTextWord;
import org.geometerplus.zlibrary.text.view.ZLTextWordCursor;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * ================================================
 * 作    者：Herve、Li
 * 创建日期：2019/10/22
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class TTSHelper implements TTSReader {

    /**
     * 文本内容（断句好了的）
     */
    private HashMap<String, Pair<String, Boolean>> textMap = new HashMap<>();
    /**
     * 文本内容（断句好了的）
     */
    private LinkedHashMap<String, String> currentTextMap = new LinkedHashMap<>();
    /**
     * 章节文字
     */
    private StringBuilder sectionBuilder = new StringBuilder();
    /**
     * 总字符数
     */
    private int totalCount;
    private int totalWordCount;
    /**
     * 下一个章节的起始段落索引
     */
    private int lastParagraphIndex;
    /**
     * FBReaderApp对象
     */
    private FBReaderApp myFBReaderApp;
    /**
     * 章节结束tag
     */
    private String lastTag = null;

    /**
     * 开始时间
     */
    private long startTime = 0;
    private int totalTime = 0;
    private OnProcessComplete onProcessComplete;

    /**
     * 构造函数
     *
     * @param myFBReaderApp FBReaderApp
     */
    TTSHelper(FBReaderApp myFBReaderApp) {
        this.myFBReaderApp = myFBReaderApp;
    }

    public void processText() {
        processText(-1);
    }

    /**
     * 处理文字内容
     */
    public void processText(int paragraphIndex) {
        // 无效判断
        if (myFBReaderApp == null || myFBReaderApp.getCurrentTOCElement() == null ||
                myFBReaderApp.getCurrentTOCElement().getReference() == null || myFBReaderApp.Model == null ||
                myFBReaderApp.Model.getTextModel() == null || myFBReaderApp.getTextView() == null ||
                myFBReaderApp.getTextView().getStartCursor() == null) {
            return;
        }

        sectionBuilder.setLength(0);

        // 章节的起始段落索引
        if (paragraphIndex == -1) {
            paragraphIndex = myFBReaderApp.getCurrentTOCElement().getReference().ParagraphIndex;
        }

        // 起始段索引，和元素索引
        int currentPIndex = myFBReaderApp.getTextView().getStartCursor().getParagraphIndex();
        int currentEIndex = myFBReaderApp.getTextView().getStartCursor().getElementIndex();

        // 段落索引，起始元素索引，结束元素索引
        int pIndex, startEIndex, endEIndex;

        // 是否断句了，断句则重置起始信息
        boolean isPunctuation = false;

        // 清空内容
        textMap.clear();
        currentTextMap.clear();

        // 语句的Builder
        StringBuilder builder = new StringBuilder();
        // 全章节的文字内容
        StringBuilder paragraphBuilder = new StringBuilder();
        // 段落游标
        ZLTextParagraphCursor zlTextParagraphCursor = new ZLTextParagraphCursor(myFBReaderApp.Model.getTextModel(), paragraphIndex);
        // 如果不是章节结束就循环读取游标
        while (!zlTextParagraphCursor.isEndOfSection()) {
            // 文字游标
            final ZLTextWordCursor cursor = new ZLTextWordCursor(zlTextParagraphCursor);
            // 段落索引
            pIndex = zlTextParagraphCursor.Index;
            // 开始元素位置索引
            startEIndex = cursor.getElementIndex();

            builder.setLength(0);

            // 如果不是段落最后
            while (!cursor.isEndOfParagraph()) {
                // 元素
                ZLTextElement element = cursor.getElement();
                // 如果是文字元素
                if (element instanceof ZLTextWord) {
                    // 该页面之前的元素都不记录
                    if (pIndex <= currentPIndex && startEIndex < currentEIndex) {
                        builder.setLength(0);
                        // 游标右移
                        cursor.nextWord();
                        // 记录起始元素索引
                        startEIndex = cursor.getElementIndex();
                        isPunctuation = false;
                        // 章节内容追加
                        sectionBuilder.append(element);
                        continue;
                    }
                    // 该页面及以后元素
                    // 句内容追加
                    builder.append(element);
                    // 段内容追加
                    paragraphBuilder.append(element);
                    // 以标点符号断句
                    if (element.toString().matches(".*[。？！;；，!]+.*")) {
                        // 结束元素位置索引
                        endEIndex = cursor.getElementIndex();
                        // 元素位置信息
                        String tag = pIndex + "-" + startEIndex + "-" + endEIndex;
                        // 所有的语句信息存储
                        textMap.put(tag, new Pair<>(builder.toString(), false));
                        lastTag = tag;
                        // 当该页面之前的都算了
                        if (pIndex < currentPIndex) {
                            builder.setLength(0);
                            // 游标右移
                            cursor.nextWord();
                            startEIndex = cursor.getElementIndex();
                            isPunctuation = false;
                            continue;
                        }
                        // 当前的文本（用于语音合成）
                        currentTextMap.put(tag, builder.toString());
                        builder.setLength(0);
                        isPunctuation = true;
                    }
                }
                // 游标右移
                cursor.nextWord();
                // 已经句尾，重新设置起始元素位置
                if (isPunctuation) {
                    startEIndex = cursor.getElementIndex();
                    isPunctuation = false;
                }
            }
            // 段落游标右移
            zlTextParagraphCursor = zlTextParagraphCursor.next();
            if (zlTextParagraphCursor == null) {
                break;
            }
        }

        // 下一个段落
        if (zlTextParagraphCursor != null) {
            zlTextParagraphCursor = zlTextParagraphCursor.next();
            if (zlTextParagraphCursor != null) {
                lastParagraphIndex = zlTextParagraphCursor.Index;
            }
        }

        // 计算相关数据
        totalWordCount = paragraphBuilder.length();

        if (onProcessComplete != null) {
            onProcessComplete.onComplete();
        }
    }

    @Override
    public int getTotalCount() {
        return totalWordCount;
    }

    @Override
    public long getText() {
        return 0;
    }

    @Override
    public long getPreviousText() {
        return 0;
    }

    @Override
    public long getNextText() {
        return 0;
    }

    @Override
    public LinkedHashMap<String, String> getCurrentTextMap() {
        return currentTextMap;
    }

    public int getStartIndex() {
        return sectionBuilder.length();
    }

    /**
     * 高亮显示
     */
    public void highlight(String utteranceId) {
        // 分割utteranceId获取位置信息
        String[] split = utteranceId.split("-");
        if (split.length < 3) {
            return;
        }
        Pair<String, Boolean> itemText = textMap.get(utteranceId);
        if (itemText == null) {
            return;
        }
        // 如果已经标记过就算了（避免重复标记造成UI的刷新）
        Boolean isPlayed = itemText.second;
        if (isPlayed == null || isPlayed) {
            return;
        }

        System.out.println("位置" + utteranceId);

        myFBReaderApp.getTextView().highlight(new ZLTextFixedPosition(Integer.parseInt(split[0]), Integer.parseInt(split[1]), 0),
                new ZLTextFixedPosition(Integer.parseInt(split[0]), Integer.parseInt(split[2]), 0));
        textMap.put(utteranceId, new Pair<>(itemText.first, true));

        // 判断是否是本页的最后，并做自动翻页操作
        checkPageCorrect(Integer.parseInt(split[0]), Integer.parseInt(split[2]));
    }

    /**
     * 检查页面是否正确
     *
     * @param cPIndex 当前朗读位置-段落索引
     * @param cCIndex 当前朗读位置-字符索引
     */
    private void checkPageCorrect(int cPIndex, int cCIndex) {
        // 翻页
        int endPIndex = myFBReaderApp.getTextView().getEndCursor().getParagraphIndex();
        int endEIndex = myFBReaderApp.getTextView().getEndCursor().getElementIndex();
        boolean isNeedTurnPage = (cPIndex == endPIndex && cCIndex > endEIndex) || cPIndex > endPIndex;
        if (isNeedTurnPage) {
            turnNextPage();
            Message msg = new Message();
            msg.what = 888;
            msg.arg1 = cPIndex;
            msg.arg2 = cCIndex;
            mHandler.removeMessages(888);
            mHandler.sendMessageDelayed(msg,50);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 888){
                checkPageCorrect(msg.arg1,msg.arg2);
            }
        }
    };

    /**
     * 翻到下一页
     */
    private void turnNextPage() {
        myFBReaderApp.runAction(ActionCode.TURN_PAGE_FORWARD);
    }

    public void start(String utteranceId) {
        // 进度
        Pair<String, Boolean> itemText = textMap.get(utteranceId);
        if (itemText != null) {
            startTime = System.currentTimeMillis();
        }
    }

    public void finish(String utteranceId) {
        // 章节末尾
        if (TextUtils.equals(utteranceId, lastTag)) {
            myFBReaderApp.runAction(ActionCode.TURN_PAGE_FORWARD);
            // 读下一段
            processText(lastParagraphIndex);
        }

        // 进度
        Pair<String, Boolean> itemText = textMap.get(utteranceId);
        long currentTime = System.currentTimeMillis();
        if (itemText != null) {
            sectionBuilder.append(itemText.first);
            Log.d("时间关系：", "文字内容： " + itemText.first + "  字数： " +
                    itemText.first.length() + "  时长： " +
                    (currentTime - startTime) + "  平均时长（毫秒/字）:" + (currentTime - startTime) / itemText.first.length());
            if ((currentTime - startTime) / itemText.first.length() > 500) {
                return;
            }
            totalCount++;
            totalTime += (currentTime - startTime) / itemText.first.length();
            Log.d("平均时长:", "数量： " + totalCount + "  平均值：" + totalTime / totalCount);
        }
    }

    void setOnProcessComplete(OnProcessComplete onProcessComplete) {
        this.onProcessComplete = onProcessComplete;
    }

    public interface OnProcessComplete {
        void onComplete();
    }
}
