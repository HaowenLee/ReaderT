package org.geometerplus.android.fbreader.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.geometerplus.android.fbreader.tts.TTSPlayer;
import org.geometerplus.android.fbreader.tts.TTSProvider;
import org.geometerplus.android.fbreader.tts.util.TimeUtils;
import org.geometerplus.android.fbreader.ui.dialog.BottomListDialog;
import org.geometerplus.android.fbreader.util.AndroidImageSynchronizer;
import org.geometerplus.fbreader.Paths;
import org.geometerplus.fbreader.book.CoverUtil;
import org.geometerplus.fbreader.formats.PluginCollection;
import org.geometerplus.zlibrary.core.image.ZLImage;
import org.geometerplus.zlibrary.core.image.ZLImageProxy;
import org.geometerplus.zlibrary.ui.android.R;
import org.geometerplus.zlibrary.ui.android.image.ZLAndroidImageData;
import org.geometerplus.zlibrary.ui.android.image.ZLAndroidImageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 语音合成的播放页面
 */
public class TTSPlayerActivity extends AppCompatActivity {

    private final AndroidImageSynchronizer myImageSynchronizer = new AndroidImageSynchronizer(this);
    private ImageView ivCover;
    private ImageView ivBack;
    private ImageView ivPlay;
    private ImageView ivClose;
    private TextView tvPosition;
    private TextView tvDuration;
    private SeekBar audioProgress;
    private TextView tvName;
    private ImageView ivSpeed;
    private TextView tvSpeed;
    private BottomListDialog speedDialog;
    /**
     * 语速数据
     */
    private List<Pair<String, String>> speedList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts_player);

        tvName = findViewById(R.id.tvName);
        ivCover = findViewById(R.id.ivCover);
        ivBack = findViewById(R.id.ivBack);
        ivPlay = findViewById(R.id.ivPlay);
        ivClose = findViewById(R.id.ivClose);
        tvPosition = findViewById(R.id.tvPosition);
        tvDuration = findViewById(R.id.tvDuration);
        audioProgress = findViewById(R.id.audioProgress);
        ivSpeed = findViewById(R.id.ivSpeed);
        tvSpeed = findViewById(R.id.tvSpeed);

        initViews();

        initConfig();

        initInfo();

        initListener();
    }

    private void initViews() {
        if (TTSPlayer.getInstance().isPlaying()) {
            ivPlay.setImageResource(R.drawable.reader_player_pause_icon);
        } else {
            ivPlay.setImageResource(R.drawable.reader_player_start_icon);
        }
    }

    private void initConfig() {
        speedList.add(new Pair<>("0.7倍语速", "3"));
        speedList.add(new Pair<>("正常语速", "5"));
        speedList.add(new Pair<>("1.2倍语速", "8"));
        speedList.add(new Pair<>("1.5倍语速", "10"));
        speedList.add(new Pair<>("2.0倍语速", "15"));
    }

    private void initInfo() {
        // 章节名
        tvName.setText(TTSPlayer.getInstance().getName());

        tvSpeed.setText(getSpeedText(TTSPlayer.getInstance().getSpeed()));

        final PluginCollection pluginCollection =
                PluginCollection.Instance(Paths.systemInfo(this));
        final ZLImage image = CoverUtil.getCover(TTSPlayer.getInstance().getBook(), pluginCollection);

        if (image == null) {
            return;
        }

        if (image instanceof ZLImageProxy) {
            ((ZLImageProxy) image).startSynchronization(myImageSynchronizer, () ->
                    runOnUiThread(() ->
                            setCover(ivCover, image)));
        } else {
            setCover(ivCover, image);
        }
    }

    private void initListener() {
        ivBack.setOnClickListener(v -> finish());

        ivPlay.setOnClickListener(v -> {
            if (TTSPlayer.getInstance().isPlaying()) {
                ivPlay.setImageResource(R.drawable.reader_player_start_icon);
                TTSPlayer.getInstance().pause();
            } else {
                ivPlay.setImageResource(R.drawable.reader_player_pause_icon);
                TTSPlayer.getInstance().start();
            }
            TTSPlayer.getInstance().setPlaying(!TTSPlayer.getInstance().isPlaying());
        });

        ivClose.setOnClickListener(v -> {
            TTSPlayer.getInstance().stop();
            finish();
        });

        // 语速选择
        ivSpeed.setOnClickListener(v -> {
            if (speedDialog == null) {
                speedDialog = new BottomListDialog(TTSPlayerActivity.this, speedList);
                speedDialog.setOnCheckListener(value -> {
                    TTSPlayer.getInstance().setSpeed(value);
                    tvSpeed.setText(getSpeedText(TTSPlayer.getInstance().getSpeed()));
                    speedDialog.dismiss();
                });
            }
            speedDialog.show(TTSPlayer.getInstance().getSpeed());
        });

        TTSPlayer.getInstance().addPlayCallback((currentPosition, duration) ->
                runOnUiThread(() -> {
                    tvPosition.setText(TimeUtils.millis2Time(currentPosition));
                    tvDuration.setText(TimeUtils.millis2Time(duration));
                    audioProgress.setMax(duration);
                    audioProgress.setProgress(currentPosition);
                }));
    }

    /**
     * 设置封面
     */
    private void setCover(ImageView coverView, ZLImage image) {
        final ZLAndroidImageData data =
                ((ZLAndroidImageManager) ZLAndroidImageManager.Instance()).getImageData(image);
        if (data == null) {
            return;
        }

        final DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        final Bitmap coverBitmap = data.getBitmap((int) getResources().getDisplayMetrics().density * 56,
                (int) getResources().getDisplayMetrics().density * 74);
        if (coverBitmap == null) {
            return;
        }

        coverView.setImageBitmap(coverBitmap);
    }

    private String getSpeedText(String speed) {
        if ("3".equals(speed)) {
            return "0.7x";
        } else if ("5".equals(speed)) {
            return "倍速播放";
        } else if ("8".equals(speed)) {
            return "1.25x";
        } else if ("10".equals(speed)) {
            return "1.5x";
        } else if ("15".equals(speed)) {
            return "2.0x";
        } else {
            return "";
        }
    }
}
