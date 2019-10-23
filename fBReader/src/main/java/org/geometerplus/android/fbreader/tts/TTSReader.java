package org.geometerplus.android.fbreader.tts;

import java.util.HashMap;

/**
 * ================================================
 * 作    者：Herve、Li
 * 创建日期：2019/10/16
 * 描    述：阅读器提供给语音的接口实现
 * 修订历史：
 * ================================================
 */
public interface TTSReader {

    /**
     * 处理文字内容
     */
    void processText();

    /**
     * 总的文字数量（带标点）
     *
     * @return 总的文字数量（带标点）
     */
    int getTotalCount();

    /**
     * 获取当前的文字内容
     *
     * @return 当前的文字内容
     */
    long getText();

    /**
     * 获取前一个章节的文字内容
     *
     * @return 前一个章节的文字内容
     */
    long getPreviousText();

    /**
     * 获取后一个章节的文字内容
     *
     * @return 后一个章节的文字内容
     */
    long getNextText();

    /**
     * 获取当前语音合成的集合
     *
     * @return 前语音合成的集合
     */
    HashMap<String, String> getCurrentTextMap();
}