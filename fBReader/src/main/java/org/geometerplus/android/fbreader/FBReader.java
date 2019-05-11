/*
 * Copyright (C) 2009-2015 FBReader.ORG Limited <contact@fbreader.org>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */

package org.geometerplus.android.fbreader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.geometerplus.android.fbreader.api.ApiListener;
import org.geometerplus.android.fbreader.api.ApiServerImplementation;
import org.geometerplus.android.fbreader.api.FBReaderIntents;
import org.geometerplus.android.fbreader.api.MenuNode;
import org.geometerplus.android.fbreader.api.PluginApi;
import org.geometerplus.android.fbreader.dict.DictionaryUtil;
import org.geometerplus.android.fbreader.formatPlugin.PluginUtil;
import org.geometerplus.android.fbreader.httpd.DataService;
import org.geometerplus.android.fbreader.libraryService.BookCollectionShadow;
import org.geometerplus.android.fbreader.sync.SyncOperations;
import org.geometerplus.android.fbreader.tips.TipsActivity;
import org.geometerplus.android.fbreader.ui.BookMarkFragment;
import org.geometerplus.android.fbreader.ui.BookNoteFragment;
import org.geometerplus.android.fbreader.ui.TOCFragment;
import org.geometerplus.android.fbreader.util.AndroidImageSynchronizer;
import org.geometerplus.android.fbreader.util.AnimationHelper;
import org.geometerplus.android.fbreader.util.ScreenUtils;
import org.geometerplus.android.fbreader.util.SizeUtils;
import org.geometerplus.android.util.DeviceType;
import org.geometerplus.android.util.SearchDialogUtil;
import org.geometerplus.android.util.UIMessageUtil;
import org.geometerplus.android.util.UIUtil;
import org.geometerplus.fbreader.Paths;
import org.geometerplus.fbreader.book.Book;
import org.geometerplus.fbreader.book.BookUtil;
import org.geometerplus.fbreader.book.Bookmark;
import org.geometerplus.fbreader.book.CoverUtil;
import org.geometerplus.fbreader.bookmodel.BookModel;
import org.geometerplus.fbreader.bookmodel.TOCTree;
import org.geometerplus.fbreader.fbreader.ActionCode;
import org.geometerplus.fbreader.fbreader.DictionaryHighlighting;
import org.geometerplus.fbreader.fbreader.FBReaderApp;
import org.geometerplus.fbreader.fbreader.FBView;
import org.geometerplus.fbreader.fbreader.options.CancelMenuHelper;
import org.geometerplus.fbreader.fbreader.options.ColorProfile;
import org.geometerplus.fbreader.formats.ExternalFormatPlugin;
import org.geometerplus.fbreader.formats.PluginCollection;
import org.geometerplus.fbreader.tips.TipsManager;
import org.geometerplus.zlibrary.core.application.ZLApplicationWindow;
import org.geometerplus.zlibrary.core.filesystem.ZLFile;
import org.geometerplus.zlibrary.core.image.ZLImage;
import org.geometerplus.zlibrary.core.image.ZLImageProxy;
import org.geometerplus.zlibrary.core.library.ZLibrary;
import org.geometerplus.zlibrary.core.options.Config;
import org.geometerplus.zlibrary.core.resources.ZLResource;
import org.geometerplus.zlibrary.core.view.ZLViewWidget;
import org.geometerplus.zlibrary.text.view.ZLTextElement;
import org.geometerplus.zlibrary.text.view.ZLTextParagraphCursor;
import org.geometerplus.zlibrary.text.view.ZLTextRegion;
import org.geometerplus.zlibrary.text.view.ZLTextView;
import org.geometerplus.zlibrary.text.view.ZLTextWord;
import org.geometerplus.zlibrary.text.view.ZLTextWordCursor;
import org.geometerplus.zlibrary.ui.android.R;
import org.geometerplus.zlibrary.ui.android.error.ErrorKeys;
import org.geometerplus.zlibrary.ui.android.image.ZLAndroidImageData;
import org.geometerplus.zlibrary.ui.android.image.ZLAndroidImageManager;
import org.geometerplus.zlibrary.ui.android.library.ZLAndroidApplication;
import org.geometerplus.zlibrary.ui.android.library.ZLAndroidLibrary;
import org.geometerplus.zlibrary.ui.android.view.AndroidFontUtil;
import org.geometerplus.zlibrary.ui.android.view.ZLAndroidWidget;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class FBReader extends FBReaderMainActivity implements ZLApplicationWindow {
    public static final int RESULT_DO_NOTHING = RESULT_FIRST_USER;
    public static final int RESULT_REPAINT = RESULT_FIRST_USER + 1;
    private static final String PLUGIN_ACTION_PREFIX = "___";
    /**
     * 动画时长
     */
    private static final int DURATION = 200;
    final DataService.Connection DataConnection = new DataService.Connection();
    private final FBReaderApp.Notifier myNotifier = new AppNotifier(this);
    private final List<PluginApi.ActionInfo> myPluginActions =
            new LinkedList<PluginApi.ActionInfo>();
    private final HashMap<MenuItem, String> myMenuItemMap = new HashMap<>();
    volatile boolean IsPaused = false;
    volatile Runnable OnResumeAction = null;
    List<Fragment> fragments = new ArrayList<>();
    private FBReaderApp myFBReaderApp;
    private final BroadcastReceiver myPluginInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final ArrayList<PluginApi.ActionInfo> actions = getResultExtras(true).getParcelableArrayList(PluginApi.PluginInfo.KEY);
            if (actions != null) {
                synchronized (myPluginActions) {
                    int index = 0;
                    while (index < myPluginActions.size()) {
                        myFBReaderApp.removeAction(PLUGIN_ACTION_PREFIX + index++);
                    }
                    myPluginActions.addAll(actions);
                    index = 0;
                    for (PluginApi.ActionInfo info : myPluginActions) {
                        myFBReaderApp.addAction(
                                PLUGIN_ACTION_PREFIX + index++,
                                new RunPluginAction(FBReader.this, myFBReaderApp, info.getId())
                        );
                    }
                }
            }
        }
    };
    private final MenuItem.OnMenuItemClickListener myMenuListener =
            new MenuItem.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    myFBReaderApp.runAction(myMenuItemMap.get(item));
                    return true;
                }
            };
    private volatile Book myBook;
    private RelativeLayout myRootView;
    private ZLAndroidWidget myMainView;
    private volatile boolean myShowStatusBarFlag;
    private String myMenuLanguage;
    private volatile long myResumeTimestamp;
    private Intent myCancelIntent = null;
    private Intent myOpenBookIntent = null;
    private PowerManager.WakeLock myWakeLock;
    private boolean myWakeLockToCreate;
    private boolean myStartTimer;
    private int myBatteryLevel;
    private BroadcastReceiver myBatteryInfoReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            final int level = intent.getIntExtra("level", 100);
            final ZLAndroidApplication application = (ZLAndroidApplication) getApplication();
            setBatteryLevel(level);
            switchWakeLock(
                    hasWindowFocus() &&
                            getZLibrary().BatteryLevelToTurnScreenOffOption.getValue() < level
            );
        }
    };
    private BroadcastReceiver mySyncUpdateReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            myFBReaderApp.useSyncInfo(myResumeTimestamp + 10 * 1000 > System.currentTimeMillis(), myNotifier);
        }
    };
    /**
     * 菜单动画
     */
    private Animation menuOpenAnim;
    private Animation menuCloseAnim;
    /**
     * 菜单
     */
    private View firstMenu;
    private boolean isLoad = false;

    public static void openBookActivity(Context context, Book book, Bookmark bookmark) {
        final Intent intent = defaultIntent(context);
        FBReaderIntents.putBookExtra(intent, book);
        FBReaderIntents.putBookmarkExtra(intent, bookmark);
        context.startActivity(intent);
    }

    public static Intent defaultIntent(Context context) {
        return new Intent(context, FBReader.class)
                .setAction(FBReaderIntents.Action.VIEW)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 321);

        bindService(
                new Intent(this, DataService.class),
                DataConnection,
                DataService.BIND_AUTO_CREATE
        );

        final Config config = Config.Instance();
        config.runOnConnect(new Runnable() {
            public void run() {
                config.requestAllValuesForGroup("Options");
                config.requestAllValuesForGroup("Style");
                config.requestAllValuesForGroup("LookNFeel");
                config.requestAllValuesForGroup("Fonts");
                config.requestAllValuesForGroup("Colors");
                config.requestAllValuesForGroup("Files");
            }
        });

        final ZLAndroidLibrary zlibrary = getZLibrary();
        myShowStatusBarFlag = zlibrary.ShowStatusBarOption.getValue();

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 程序的界面，这个界面是ZLAndroidWidget类
        setContentView(R.layout.main);
        myRootView = findViewById(R.id.root_view);
        myMainView = findViewById(R.id.main_view);
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);

        firstMenu = findViewById(R.id.firstMenu);

        initListener();

        myFBReaderApp = (FBReaderApp) FBReaderApp.Instance();
        if (myFBReaderApp == null) {
            myFBReaderApp = new FBReaderApp(Paths.systemInfo(this), new BookCollectionShadow());
        }
        getCollection().bindToService(this, null);
        myBook = null;

        myFBReaderApp.setWindow(this);
        myFBReaderApp.initWindow();

        myFBReaderApp.setExternalFileOpener(new ExternalFileOpener(this));

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                myShowStatusBarFlag ? 0 : WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        if (myFBReaderApp.getPopupById(TextSearchPopup.ID) == null) {
            new TextSearchPopup(myFBReaderApp);
        }
        if (myFBReaderApp.getPopupById(NavigationPopup.ID) == null) {
            new NavigationPopup(myFBReaderApp);
        }
        if (myFBReaderApp.getPopupById(SelectionPopup.ID) == null) {
            new SelectionPopup(myFBReaderApp);
        }

        myFBReaderApp.addAction(ActionCode.SHOW_LIBRARY, new ShowLibraryAction(this, myFBReaderApp));
        myFBReaderApp.addAction(ActionCode.SHOW_PREFERENCES, new ShowPreferencesAction(this, myFBReaderApp));
        myFBReaderApp.addAction(ActionCode.SHOW_BOOK_INFO, new ShowBookInfoAction(this, myFBReaderApp));
        myFBReaderApp.addAction(ActionCode.SHOW_TOC, new ShowTOCAction(this, myFBReaderApp));
        myFBReaderApp.addAction(ActionCode.SHOW_BOOKMARKS, new ShowBookmarksAction(this, myFBReaderApp));
        myFBReaderApp.addAction(ActionCode.SHOW_NETWORK_LIBRARY, new ShowNetworkLibraryAction(this, myFBReaderApp));

        myFBReaderApp.addAction(ActionCode.SHOW_MENU, new ShowMenuAction(this, myFBReaderApp));
        myFBReaderApp.addAction(ActionCode.SHOW_NAVIGATION, new ShowNavigationAction(this, myFBReaderApp));
        myFBReaderApp.addAction(ActionCode.SEARCH, new SearchAction(this, myFBReaderApp));
        myFBReaderApp.addAction(ActionCode.SHARE_BOOK, new ShareBookAction(this, myFBReaderApp));

        myFBReaderApp.addAction(ActionCode.SELECTION_SHOW_PANEL, new SelectionShowPanelAction(this, myFBReaderApp));
        myFBReaderApp.addAction(ActionCode.SELECTION_HIDE_PANEL, new SelectionHidePanelAction(this, myFBReaderApp));
        myFBReaderApp.addAction(ActionCode.SELECTION_COPY_TO_CLIPBOARD, new SelectionCopyAction(this, myFBReaderApp));
        myFBReaderApp.addAction(ActionCode.SELECTION_SHARE, new SelectionShareAction(this, myFBReaderApp));
        myFBReaderApp.addAction(ActionCode.SELECTION_TRANSLATE, new SelectionTranslateAction(this, myFBReaderApp));
        myFBReaderApp.addAction(ActionCode.SELECTION_BOOKMARK, new SelectionBookmarkAction(this, myFBReaderApp));

        myFBReaderApp.addAction(ActionCode.DISPLAY_BOOK_POPUP, new DisplayBookPopupAction(this, myFBReaderApp));
        myFBReaderApp.addAction(ActionCode.PROCESS_HYPERLINK, new ProcessHyperlinkAction(this, myFBReaderApp));
        myFBReaderApp.addAction(ActionCode.OPEN_VIDEO, new OpenVideoAction(this, myFBReaderApp));
        myFBReaderApp.addAction(ActionCode.HIDE_TOAST, new HideToastAction(this, myFBReaderApp));

        myFBReaderApp.addAction(ActionCode.SHOW_CANCEL_MENU, new ShowCancelMenuAction(this, myFBReaderApp));
        myFBReaderApp.addAction(ActionCode.OPEN_START_SCREEN, new StartScreenAction(this, myFBReaderApp));

        myFBReaderApp.addAction(ActionCode.SET_SCREEN_ORIENTATION_SYSTEM, new SetScreenOrientationAction(this, myFBReaderApp, ZLibrary.SCREEN_ORIENTATION_SYSTEM));
        myFBReaderApp.addAction(ActionCode.SET_SCREEN_ORIENTATION_SENSOR, new SetScreenOrientationAction(this, myFBReaderApp, ZLibrary.SCREEN_ORIENTATION_SENSOR));
        myFBReaderApp.addAction(ActionCode.SET_SCREEN_ORIENTATION_PORTRAIT, new SetScreenOrientationAction(this, myFBReaderApp, ZLibrary.SCREEN_ORIENTATION_PORTRAIT));
        myFBReaderApp.addAction(ActionCode.SET_SCREEN_ORIENTATION_LANDSCAPE, new SetScreenOrientationAction(this, myFBReaderApp, ZLibrary.SCREEN_ORIENTATION_LANDSCAPE));
        if (getZLibrary().supportsAllOrientations()) {
            myFBReaderApp.addAction(ActionCode.SET_SCREEN_ORIENTATION_REVERSE_PORTRAIT, new SetScreenOrientationAction(this, myFBReaderApp, ZLibrary.SCREEN_ORIENTATION_REVERSE_PORTRAIT));
            myFBReaderApp.addAction(ActionCode.SET_SCREEN_ORIENTATION_REVERSE_LANDSCAPE, new SetScreenOrientationAction(this, myFBReaderApp, ZLibrary.SCREEN_ORIENTATION_REVERSE_LANDSCAPE));
        }
        myFBReaderApp.addAction(ActionCode.OPEN_WEB_HELP, new OpenWebHelpAction(this, myFBReaderApp));
        myFBReaderApp.addAction(ActionCode.INSTALL_PLUGINS, new InstallPluginsAction(this, myFBReaderApp));

        myFBReaderApp.addAction(ActionCode.SWITCH_THEME_WHITE_PROFILE, new SwitchProfileAction(this, myFBReaderApp, ColorProfile.THEME_WHITE));
        myFBReaderApp.addAction(ActionCode.SWITCH_THEME_YELLOW_PROFILE, new SwitchProfileAction(this, myFBReaderApp, ColorProfile.THEME_YELLOW));
        myFBReaderApp.addAction(ActionCode.SWITCH_THEME_GREEN_PROFILE, new SwitchProfileAction(this, myFBReaderApp, ColorProfile.THEME_GREEN));
        myFBReaderApp.addAction(ActionCode.SWITCH_THEME_BLACK_PROFILE, new SwitchProfileAction(this, myFBReaderApp, ColorProfile.THEME_BLACK));

        final Intent intent = getIntent();
        final String action = intent.getAction();

        myOpenBookIntent = intent;
        if ((intent.getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) == 0) {
            if (FBReaderIntents.Action.CLOSE.equals(action)) {
                myCancelIntent = intent;
                myOpenBookIntent = null;
            } else if (FBReaderIntents.Action.PLUGIN_CRASH.equals(action)) {
                myFBReaderApp.ExternalBook = null;
                myOpenBookIntent = null;
                getCollection().bindToService(this, new Runnable() {
                    public void run() {
                        myFBReaderApp.openBook(null, null, null, myNotifier);
                    }
                });
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
            case REQUEST_PREFERENCES:
                if (resultCode != RESULT_DO_NOTHING && data != null) {
                    final Book book = FBReaderIntents.getBookExtra(data, myFBReaderApp.Collection);
                    if (book != null) {
                        getCollection().bindToService(this, new Runnable() {
                            public void run() {
                                onPreferencesUpdate(book);
                            }
                        });
                    }
                }
                break;
            case REQUEST_CANCEL_MENU:
                runCancelAction(data);
                break;
        }
    }

    public void hideDictionarySelection() {
        myFBReaderApp.getTextView().hideOutline();
        myFBReaderApp.getTextView().removeHighlightings(DictionaryHighlighting.class);
        myFBReaderApp.getViewWidget().reset();
        myFBReaderApp.getViewWidget().repaint();
    }

    private BookCollectionShadow getCollection() {
        return (BookCollectionShadow) myFBReaderApp.Collection;
    }

    private void onPreferencesUpdate(Book book) {
        AndroidFontUtil.clearFontCache();
        myFBReaderApp.onBookUpdated(book);
    }

    private void runCancelAction(Intent intent) {
        final CancelMenuHelper.ActionType type;
        try {
            type = CancelMenuHelper.ActionType.valueOf(
                    intent.getStringExtra(FBReaderIntents.Key.TYPE)
            );
        } catch (Exception e) {
            // invalid (or null) type value
            return;
        }
        Bookmark bookmark = null;
        if (type == CancelMenuHelper.ActionType.returnTo) {
            bookmark = FBReaderIntents.getBookmarkExtra(intent);
            if (bookmark == null) {
                return;
            }
        }
        myFBReaderApp.runCancelAction(type, bookmark);
    }

    @Override
    protected void onStart() {
        super.onStart();

        getCollection().bindToService(this, new Runnable() {
            public void run() {
                new Thread() {
                    public void run() {
                        getPostponedInitAction().run();
                    }
                }.start();

                myFBReaderApp.getViewWidget().repaint();
            }
        });

        initPluginActions();

        final ZLAndroidLibrary zlibrary = getZLibrary();

        Config.Instance().runOnConnect(new Runnable() {
            public void run() {
                final boolean showStatusBar = zlibrary.ShowStatusBarOption.getValue();
                if (showStatusBar != myShowStatusBarFlag) {
                    finish();
                    startActivity(new Intent(FBReader.this, FBReader.class));
                }
                zlibrary.ShowStatusBarOption.saveSpecialValue();
                myFBReaderApp.ViewOptions.ColorProfileName.saveSpecialValue();
                SetScreenOrientationAction.setOrientation(FBReader.this, zlibrary.getOrientationOption().getValue());
            }
        });

        ((PopupPanel) myFBReaderApp.getPopupById(TextSearchPopup.ID)).setPanelInfo(this, myRootView);
        ((NavigationPopup) myFBReaderApp.getPopupById(NavigationPopup.ID)).setPanelInfo(this, myRootView);
        ((PopupPanel) myFBReaderApp.getPopupById(SelectionPopup.ID)).setPanelInfo(this, myRootView);
    }

    private Runnable getPostponedInitAction() {
        return new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        new TipRunner().start();
                        DictionaryUtil.init(FBReader.this, null);
                        final Intent intent = getIntent();
                        if (intent != null && FBReaderIntents.Action.PLUGIN.equals(intent.getAction())) {
                            new RunPluginAction(FBReader.this, myFBReaderApp, intent.getData()).run();
                        }
                    }
                });
            }
        };
    }

    private void initPluginActions() {
        synchronized (myPluginActions) {
            int index = 0;
            while (index < myPluginActions.size()) {
                myFBReaderApp.removeAction(PLUGIN_ACTION_PREFIX + index++);
            }
            myPluginActions.clear();
        }

        sendOrderedBroadcast(
                new Intent(PluginApi.ACTION_REGISTER),
                null,
                myPluginInfoReceiver,
                null,
                RESULT_OK,
                null,
                null
        );
    }

    @Override
    protected void onStop() {
        ApiServerImplementation.sendEvent(this, ApiListener.EVENT_READ_MODE_CLOSED);
        // TODO: 2019/5/9 移除后状态恢复有问题
        // PopupPanel.removeAllWindows(myFBReaderApp, this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        getCollection().unbind();
        unbindService(DataConnection);
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return (myMainView != null && myMainView.onKeyDown(keyCode, event)) || super.onKeyDown(keyCode, event);
    }

    @Override
    public void onLowMemory() {
        myFBReaderApp.onWindowClosing();
        super.onLowMemory();
    }

    @Override
    protected void onPause() {
        SyncOperations.quickSync(this, myFBReaderApp.SyncOptions);

        IsPaused = true;
        try {
            unregisterReceiver(mySyncUpdateReceiver);
        } catch (IllegalArgumentException e) {
        }

        try {
            unregisterReceiver(myBatteryInfoReceiver);
        } catch (IllegalArgumentException e) {
            // do nothing, this exception means that myBatteryInfoReceiver was not registered
        }

        myFBReaderApp.stopTimer();
        if (getZLibrary().DisableButtonLightsOption.getValue()) {
            setButtonLight(true);
        }
        myFBReaderApp.onWindowClosing();

        super.onPause();
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        final String action = intent.getAction();
        final Uri data = intent.getData();

        if ((intent.getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) != 0) {
            super.onNewIntent(intent);
        } else if (Intent.ACTION_VIEW.equals(action)
                && data != null && "fbreader-action".equals(data.getScheme())) {
            myFBReaderApp.runAction(data.getEncodedSchemeSpecificPart(), data.getFragment());
        } else if (Intent.ACTION_VIEW.equals(action) || FBReaderIntents.Action.VIEW.equals(action)) {
            myOpenBookIntent = intent;
            if (myFBReaderApp.Model == null && myFBReaderApp.ExternalBook != null) {
                final BookCollectionShadow collection = getCollection();
                final Book b = FBReaderIntents.getBookExtra(intent, collection);
                if (!collection.sameBook(b, myFBReaderApp.ExternalBook)) {
                    try {
                        final ExternalFormatPlugin plugin =
                                (ExternalFormatPlugin) BookUtil.getPlugin(
                                        PluginCollection.Instance(Paths.systemInfo(this)),
                                        myFBReaderApp.ExternalBook
                                );
                        startActivity(PluginUtil.createIntent(plugin, FBReaderIntents.Action.PLUGIN_KILL));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (FBReaderIntents.Action.PLUGIN.equals(action)) {
            new RunPluginAction(this, myFBReaderApp, data).run();
        } else if (Intent.ACTION_SEARCH.equals(action)) {
            final String pattern = intent.getStringExtra(SearchManager.QUERY);
            final Runnable runnable = new Runnable() {
                public void run() {
                    final TextSearchPopup popup = (TextSearchPopup) myFBReaderApp.getPopupById(TextSearchPopup.ID);
                    popup.initPosition();
                    myFBReaderApp.MiscOptions.TextSearchPattern.setValue(pattern);
                    if (myFBReaderApp.getTextView().search(pattern, true, false, false, false) != 0) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                myFBReaderApp.showPopup(popup.getId());
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                UIMessageUtil.showErrorMessage(FBReader.this, "textNotFound");
                                popup.StartPosition = null;
                            }
                        });
                    }
                }
            };
            UIUtil.wait("search", runnable, this);
        } else if (FBReaderIntents.Action.CLOSE.equals(intent.getAction())) {
            myCancelIntent = intent;
            myOpenBookIntent = null;
        } else if (FBReaderIntents.Action.PLUGIN_CRASH.equals(intent.getAction())) {
            final Book book = FBReaderIntents.getBookExtra(intent, myFBReaderApp.Collection);
            myFBReaderApp.ExternalBook = null;
            myOpenBookIntent = null;
            getCollection().bindToService(this, new Runnable() {
                public void run() {
                    final BookCollectionShadow collection = getCollection();
                    Book b = collection.getRecentBook(0);
                    if (collection.sameBook(b, book)) {
                        b = collection.getRecentBook(1);
                    }
                    myFBReaderApp.openBook(b, null, null, myNotifier);
                }
            });
        } else {
            super.onNewIntent(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        myStartTimer = true;
        Config.Instance().runOnConnect(new Runnable() {
            public void run() {
                SyncOperations.enableSync(FBReader.this, myFBReaderApp.SyncOptions);

                final int brightnessLevel =
                        getZLibrary().ScreenBrightnessLevelOption.getValue();
                if (brightnessLevel != 0) {
                    getViewWidget().setScreenBrightness(brightnessLevel);
                } else {
                    setScreenBrightnessAuto();
                }
                if (getZLibrary().DisableButtonLightsOption.getValue()) {
                    setButtonLight(false);
                }

                getCollection().bindToService(FBReader.this, new Runnable() {
                    public void run() {
                        final BookModel model = myFBReaderApp.Model;
                        if (model == null || model.Book == null) {
                            return;
                        }
                        onPreferencesUpdate(myFBReaderApp.Collection.getBookById(model.Book.getId()));
                    }
                });
            }
        });

        registerReceiver(myBatteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        IsPaused = false;
        myResumeTimestamp = System.currentTimeMillis();
        if (OnResumeAction != null) {
            final Runnable action = OnResumeAction;
            OnResumeAction = null;
            action.run();
        }

        registerReceiver(mySyncUpdateReceiver, new IntentFilter(FBReaderIntents.Event.SYNC_UPDATED));

        SetScreenOrientationAction.setOrientation(this, getZLibrary().getOrientationOption().getValue());
        if (myCancelIntent != null) {
            final Intent intent = myCancelIntent;
            myCancelIntent = null;
            getCollection().bindToService(this, new Runnable() {
                public void run() {
                    runCancelAction(intent);
                }
            });
            return;
        } else if (myOpenBookIntent != null) {
            final Intent intent = myOpenBookIntent;
            myOpenBookIntent = null;
            getCollection().bindToService(this, new Runnable() {
                public void run() {
                    openBook(intent, null, true);
                }
            });
        } else if (myFBReaderApp.getCurrentServerBook(null) != null) {
            getCollection().bindToService(this, new Runnable() {
                public void run() {
                    myFBReaderApp.useSyncInfo(true, myNotifier);
                }
            });
        } else if (myFBReaderApp.Model == null && myFBReaderApp.ExternalBook != null) {
            getCollection().bindToService(this, new Runnable() {
                public void run() {
                    myFBReaderApp.openBook(myFBReaderApp.ExternalBook, null, null, myNotifier);
                }
            });
        } else {
            getCollection().bindToService(this, new Runnable() {
                public void run() {
                    myFBReaderApp.useSyncInfo(true, myNotifier);
                }
            });
        }

        PopupPanel.restoreVisibilities(myFBReaderApp);
        ApiServerImplementation.sendEvent(this, ApiListener.EVENT_READ_MODE_OPENED);
    }

    private synchronized void openBook(Intent intent, final Runnable action, boolean force) {
        if (!force && myBook != null) {
            return;
        }

        myBook = FBReaderIntents.getBookExtra(intent, myFBReaderApp.Collection);
        final Bookmark bookmark = FBReaderIntents.getBookmarkExtra(intent);
        if (myBook == null) {
            final Uri data = intent.getData();
            if (data != null) {
                myBook = createBookForFile(ZLFile.createFileByPath(data.getPath()));
            }
        }
        if (myBook != null) {
            ZLFile file = BookUtil.fileByBook(myBook);
            if (!file.exists()) {
                if (file.getPhysicalFile() != null) {
                    file = file.getPhysicalFile();
                }
                UIMessageUtil.showErrorMessage(this, "fileNotFound", file.getPath());
                myBook = null;
            } else {
                NotificationUtil.drop(this, myBook);
            }
        }
        Config.Instance().runOnConnect(new Runnable() {
            public void run() {
                myFBReaderApp.openBook(myBook, bookmark, action, myNotifier);
                AndroidFontUtil.clearFontCache();
            }
        });
    }

    private Book createBookForFile(ZLFile file) {
        if (file == null) {
            return null;
        }
        Book book = myFBReaderApp.Collection.getBookByFile(file.getPath());
        if (book != null) {
            return book;
        }
        if (file.isArchive()) {
            for (ZLFile child : file.children()) {
                book = myFBReaderApp.Collection.getBookByFile(child.getPath());
                if (book != null) {
                    return book;
                }
            }
        }
        return null;
    }

    private void setButtonLight(boolean enabled) {
        setButtonLightInternal(enabled);
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    private void setButtonLightInternal(boolean enabled) {
        final WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.buttonBrightness = enabled ? -1.0f : 0.0f;
        getWindow().setAttributes(attrs);
    }

    public void showSelectionPanel() {
        final ZLTextView view = myFBReaderApp.getTextView();
        // 显示弹框
        myFBReaderApp.showPopup(SelectionPopup.ID);
        // 位置移动
        ((SelectionPopup) myFBReaderApp.getPopupById(SelectionPopup.ID)).move(view.getSelectionStartY(), view.getSelectionEndY());
    }

    public void hideSelectionPanel() {
        final FBReaderApp.PopupPanel popup = myFBReaderApp.getActivePopup();
        if (popup != null && popup.getId() == SelectionPopup.ID) {
            myFBReaderApp.hideActivePopup();
        }
    }

    public void navigate() {
        ((NavigationPopup) myFBReaderApp.getPopupById(NavigationPopup.ID)).runNavigation();
    }

    private Menu addSubmenu(Menu menu, String id) {
        return menu.addSubMenu(ZLResource.resource("menu").getResource(id).getValue());
    }

    private void addMenuItem(Menu menu, String actionId, Integer iconId, String name) {
        if (name == null) {
            name = ZLResource.resource("menu").getResource(actionId).getValue();
        }
        final MenuItem menuItem = menu.add(name);
        if (iconId != null) {
            menuItem.setIcon(iconId);
        }
        menuItem.setOnMenuItemClickListener(myMenuListener);
        myMenuItemMap.put(menuItem, actionId);
    }

    private void addMenuItem(Menu menu, String actionId, String name) {
        addMenuItem(menu, actionId, null, name);
    }

    private void addMenuItem(Menu menu, String actionId, int iconId) {
        addMenuItem(menu, actionId, iconId, null);
    }

    private void addMenuItem(Menu menu, String actionId) {
        addMenuItem(menu, actionId, null, null);
    }

    private void fillMenu(Menu menu, List<MenuNode> nodes) {
        for (MenuNode n : nodes) {
            if (n instanceof MenuNode.Item) {
                final Integer iconId = ((MenuNode.Item) n).IconId;
                if (iconId != null) {
                    addMenuItem(menu, n.Code, iconId);
                } else {
                    addMenuItem(menu, n.Code);
                }
            } else /* if (n instanceof MenuNode.Submenu) */ {
                final Menu submenu = addSubmenu(menu, n.Code);
                fillMenu(submenu, ((MenuNode.Submenu) n).Children);
            }
        }
    }

    private void setupMenu(Menu menu) {
        final String menuLanguage = ZLResource.getLanguageOption().getValue();
        if (menuLanguage.equals(myMenuLanguage)) {
            return;
        }
        myMenuLanguage = menuLanguage;

        menu.clear();
        fillMenu(menu, MenuData.topLevelNodes());
        synchronized (myPluginActions) {
            int index = 0;
            for (PluginApi.ActionInfo info : myPluginActions) {
                if (info instanceof PluginApi.MenuActionInfo) {
                    addMenuItem(
                            menu,
                            PLUGIN_ACTION_PREFIX + index++,
                            ((PluginApi.MenuActionInfo) info).MenuItemName
                    );
                }
            }
        }

        refresh();
    }

    protected void onPluginNotFound(final Book book) {
        final BookCollectionShadow collection = getCollection();
        collection.bindToService(this, new Runnable() {
            public void run() {
                final Book recent = collection.getRecentBook(0);
                if (recent != null && !collection.sameBook(recent, book)) {
                    myFBReaderApp.openBook(recent, null, null, null);
                } else {
                    myFBReaderApp.openHelpBook();
                }
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return (myMainView != null && myMainView.onKeyUp(keyCode, event)) || super.onKeyUp(keyCode, event);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        switchWakeLock(hasFocus &&
                getZLibrary().BatteryLevelToTurnScreenOffOption.getValue() <
                        myFBReaderApp.getBatteryLevel()
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        setupMenu(menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        setStatusBarVisibility(true);
        setupMenu(menu);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setStatusBarVisibility(false);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
        setStatusBarVisibility(false);
    }

    @Override
    public boolean onSearchRequested() {
        final FBReaderApp.PopupPanel popup = myFBReaderApp.getActivePopup();
        myFBReaderApp.hideActivePopup();
        if (DeviceType.Instance().hasStandardSearchDialog()) {
            final SearchManager manager = (SearchManager) getSystemService(SEARCH_SERVICE);
            manager.setOnCancelListener(new SearchManager.OnCancelListener() {
                public void onCancel() {
                    if (popup != null) {
                        myFBReaderApp.showPopup(popup.getId());
                    }
                    manager.setOnCancelListener(null);
                }
            });
            startSearch(myFBReaderApp.MiscOptions.TextSearchPattern.getValue(), true, null, false);
        } else {
            SearchDialogUtil.showDialog(
                    this, FBReader.class, myFBReaderApp.MiscOptions.TextSearchPattern.getValue(), new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface di) {
                            if (popup != null) {
                                myFBReaderApp.showPopup(popup.getId());
                            }
                        }
                    }
            );
        }
        return true;
    }

    private void setStatusBarVisibility(boolean visible) {
        final ZLAndroidLibrary zlibrary = getZLibrary();
        if (DeviceType.Instance() != DeviceType.KINDLE_FIRE_1ST_GENERATION && !myShowStatusBarFlag) {
            if (visible) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            } else {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            }
        }
    }

    private void switchWakeLock(boolean on) {
        if (on) {
            if (myWakeLock == null) {
                myWakeLockToCreate = true;
            }
        } else {
            if (myWakeLock != null) {
                synchronized (this) {
                    if (myWakeLock != null) {
                        myWakeLock.release();
                        myWakeLock = null;
                    }
                }
            }
        }
    }

    @SuppressLint("InvalidWakeLockTag")
    public final void createWakeLock() {
        if (myWakeLockToCreate) {
            synchronized (this) {
                if (myWakeLockToCreate) {
                    myWakeLockToCreate = false;
                    myWakeLock = ((PowerManager) getSystemService(POWER_SERVICE)).newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "FBReader");
                    myWakeLock.acquire();
                }
            }
        }
        if (myStartTimer) {
            myFBReaderApp.startTimer();
            myStartTimer = false;
        }
    }

    @Override
    public void setWindowTitle(final String title) {
        runOnUiThread(new Runnable() {
            public void run() {
                setTitle(title);
            }
        });
    }

    // methods from ZLApplicationWindow interface
    @Override
    public void showErrorMessage(String key) {
        UIMessageUtil.showErrorMessage(this, key);
    }

    @Override
    public void showErrorMessage(String key, String parameter) {
        UIMessageUtil.showErrorMessage(this, key, parameter);
    }

    @Override
    public FBReaderApp.SynchronousExecutor createExecutor(String key) {
        return UIUtil.createExecutor(this, key);
    }

    @Override
    public void processException(Exception exception) {
        exception.printStackTrace();

        final Intent intent = new Intent(
                FBReaderIntents.Action.ERROR,
                new Uri.Builder().scheme(exception.getClass().getSimpleName()).build()
        );
        intent.setPackage(FBReaderIntents.DEFAULT_PACKAGE);
        intent.putExtra(ErrorKeys.MESSAGE, exception.getMessage());
        final StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));
        intent.putExtra(ErrorKeys.STACKTRACE, stackTrace.toString());
		/*
		if (exception instanceof BookReadingException) {
			final ZLFile file = ((BookReadingException)exception).File;
			if (file != null) {
				intent.putExtra("file", file.getPath());
			}
		}
		*/
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // ignore
            e.printStackTrace();
        }
    }

    @Override
    public void refresh() {
        runOnUiThread(new Runnable() {
            public void run() {
                for (Map.Entry<MenuItem, String> entry : myMenuItemMap.entrySet()) {
                    final String actionId = entry.getValue();
                    final MenuItem menuItem = entry.getKey();
                    menuItem.setVisible(myFBReaderApp.isActionVisible(actionId) && myFBReaderApp.isActionEnabled(actionId));
                    switch (myFBReaderApp.isActionChecked(actionId)) {
                        case TRUE:
                            menuItem.setCheckable(true);
                            menuItem.setChecked(true);
                            break;
                        case FALSE:
                            menuItem.setCheckable(true);
                            menuItem.setChecked(false);
                            break;
                        case UNDEFINED:
                            menuItem.setCheckable(false);
                            break;
                    }
                }
            }
        });
    }

    @Override
    public ZLViewWidget getViewWidget() {
        return myMainView;
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public int getBatteryLevel() {
        return myBatteryLevel;
    }

    private void setBatteryLevel(int percent) {
        myBatteryLevel = percent;
    }

    public void outlineRegion(ZLTextRegion.Soul soul) {
        myFBReaderApp.getTextView().outlineRegion(soul);
        myFBReaderApp.getViewWidget().repaint();
    }

    public void hideOutline() {
        myFBReaderApp.getTextView().hideOutline();
        myFBReaderApp.getViewWidget().repaint();
    }

    /**
     * 显示一级菜单
     */
    public void openOrCloseSettingMenu() {
        final View menuView = findViewById(R.id.menuSetting);
        if (menuView.getVisibility() == View.VISIBLE) {
            closeMenu(false, menuView);
        } else {
            // 主题
            RadioGroup radioGroup = findViewById(R.id.book_menu_color_group);
            if (!myFBReaderApp.isActionVisible(ActionCode.SWITCH_THEME_WHITE_PROFILE)) {
                radioGroup.check(R.id.color_white);
            }
            if (!myFBReaderApp.isActionVisible(ActionCode.SWITCH_THEME_YELLOW_PROFILE)) {
                radioGroup.check(R.id.color_yellow);
            }
            if (!myFBReaderApp.isActionVisible(ActionCode.SWITCH_THEME_GREEN_PROFILE)) {
                radioGroup.check(R.id.color_green);
            }
            if (!myFBReaderApp.isActionVisible(ActionCode.SWITCH_THEME_BLACK_PROFILE)) {
                radioGroup.check(R.id.color_black);
            }
            openMenu(false, menuView);
            closeMenu(false, firstMenu);
        }
    }

    /**
     * 显示一级菜单
     */
    public void openMenu() {
        if (findViewById(R.id.firstMenu).getVisibility() == View.VISIBLE) {
            AnimationHelper.closeTopMenu(findViewById(R.id.menuTop));
            AnimationHelper.closeBottomMenu(findViewById(R.id.firstMenu));
            scaleReader(false);
        } else if (findViewById(R.id.menuSetting).getVisibility() == View.VISIBLE) {
            AnimationHelper.closeTopMenu(findViewById(R.id.menuTop));
            AnimationHelper.closeBottomMenu(findViewById(R.id.menuSetting));
        } else if (findViewById(R.id.menuMore).getVisibility() == View.VISIBLE) {
            AnimationHelper.closeTopMenu(findViewById(R.id.menuTop));
            AnimationHelper.closeBottomMenu(findViewById(R.id.menuMore));
        } else {
            initBookInfoView();
            AnimationHelper.openTopMenu(findViewById(R.id.menuTop));
            AnimationHelper.openBottomMenu(findViewById(R.id.firstMenu));
            scaleReader(true);

            // Setup book progress
            SeekBar seekBar = findViewById(R.id.bookProgress);
            final FBView textView = myFBReaderApp.getTextView();
            ZLTextView.PagePosition pagePosition = textView.pagePosition();
            if (seekBar.getMax() != pagePosition.Total - 1 || seekBar.getProgress() != pagePosition.Current - 1) {
                seekBar.setMax(pagePosition.Total - 1);
                seekBar.setProgress(pagePosition.Current - 1);
            }
        }
    }

    private void scaleReader(boolean isScale) {
        if (isScale) {
            // 缩放
            ScaleAnimation animation = new ScaleAnimation(1 / 0.75f, 1, 1 / 0.75f,
                    1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.4f);
            animation.setDuration(DURATION);
            myMainView.startAnimation(animation);
            myMainView.setPreview(true);
        } else {
            // 缩放
            ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1 / 0.75f, 1,
                    1 / 0.75f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.4f);
            scaleAnimation.setDuration(DURATION + 100);
            myMainView.startAnimation(scaleAnimation);
            scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    myMainView.setPreview(false);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }

    /**
     * 关闭菜单
     */
    private void closeMenu(boolean isNeedScale, final View menuView) {
        if (isNeedScale) {
            scaleReader(false);
        }
        Animation animation = getMenuAnim(false);
        menuView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                menuView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void openMenu(boolean scaleReader, View menuView) {
        scaleReader(scaleReader);

        menuView.startAnimation(getMenuAnim(true));
        menuView.setVisibility(View.VISIBLE);
    }

    /**
     * 获取菜单动画
     *
     * @param isOpen 打开与否
     * @return 菜单动画
     */
    private Animation getMenuAnim(boolean isOpen) {
        if (isOpen) {
            if (menuOpenAnim == null) {
                menuOpenAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                        0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
                        1, Animation.RELATIVE_TO_SELF, 0);
                menuOpenAnim.setDuration(DURATION);
            }
            return menuOpenAnim;
        } else {
            if (menuCloseAnim == null) {
                menuCloseAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                        0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
                        0, Animation.RELATIVE_TO_SELF, 1);
                menuCloseAnim.setDuration(DURATION);
            }
            return menuCloseAnim;
        }
    }

    private void initListener() {
        firstMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Empty body.
            }
        });
        firstMenu.findViewById(R.id.quick_theme_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myFBReaderApp.isActionVisible(ActionCode.SWITCH_THEME_BLACK_PROFILE)) {
                    myFBReaderApp.runAction(ActionCode.SWITCH_THEME_BLACK_PROFILE);
                } else {
                    myFBReaderApp.runAction(ActionCode.SWITCH_THEME_WHITE_PROFILE);
                }
            }
        });

        findViewById(R.id.menuTop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Empty body.
            }
        });

        // 更多菜单
        findViewById(R.id.ivMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initMoreBookInfoView();
                AnimationHelper.openBottomMenu(findViewById(R.id.menuMore));
                AnimationHelper.closeBottomMenu(findViewById(R.id.firstMenu));
                AnimationHelper.closeBottomMenu(findViewById(R.id.menuSetting));
                scaleReader(false);
            }
        });

        findViewById(R.id.book_mark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 添加书签
                getCollection().saveBookmark(myFBReaderApp.createBookmark(20, Bookmark.Type.BookMark));
                Toast.makeText(FBReader.this, "书签已添加", Toast.LENGTH_SHORT).show();
            }
        });

        firstMenu.findViewById(R.id.shangyizhang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFBReaderApp.runAction(ActionCode.TURN_PAGE_BACK);
            }
        });

        firstMenu.findViewById(R.id.xiayizhang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFBReaderApp.runAction(ActionCode.TURN_PAGE_FORWARD);
            }
        });

        SeekBar seekBar = firstMenu.findViewById(R.id.bookProgress);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    final int page = progress + 1;
                    gotoPage(page);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            private void gotoPage(int page) {
                FBView textView = myFBReaderApp.getTextView();
                if (page == 1) {
                    textView.gotoHome();
                } else {
                    textView.gotoPage(page);
                }
                myFBReaderApp.getViewWidget().reset();
                myFBReaderApp.getViewWidget().repaint();
            }
        });

        // 亮度设置
        SeekBar lightProgress = findViewById(R.id.lightProgress);
        lightProgress.setProgress(myMainView.getScreenBrightness());
        lightProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                myMainView.setScreenBrightness(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        firstMenu.findViewById(R.id.open_slid_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFragment();
                openSlideMenu();
            }
        });

        findViewById(R.id.slideMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Empty body.
            }
        });

        // 返回
        findViewById(R.id.viewBackground).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    closeSlideMenu();
                }
                return true;
            }
        });

        // 设置
        firstMenu.findViewById(R.id.showSetMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOrCloseSettingMenu();
            }
        });

        // 字体大小
        findViewById(R.id.font_small).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFBReaderApp.runAction(ActionCode.DECREASE_FONT);
            }
        });
        findViewById(R.id.font_big).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myFBReaderApp.runAction(ActionCode.INCREASE_FONT);
            }
        });

        RadioGroup radioGroup = findViewById(R.id.book_menu_color_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.color_white:
                        myFBReaderApp.runAction(ActionCode.SWITCH_THEME_WHITE_PROFILE);
                        break;
                    case R.id.color_yellow:
                        myFBReaderApp.runAction(ActionCode.SWITCH_THEME_YELLOW_PROFILE);
                        break;
                    case R.id.color_green:
                        myFBReaderApp.runAction(ActionCode.SWITCH_THEME_GREEN_PROFILE);
                        break;
                    case R.id.color_black:
                        myFBReaderApp.runAction(ActionCode.SWITCH_THEME_BLACK_PROFILE);
                        break;
                    default:
                        break;
                }
            }
        });

        findViewById(R.id.goto_tts_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019/5/4 获取文本测试
                final TOCTree tocElement = myFBReaderApp.getCurrentTOCElement();
                int paragraphIndex = tocElement.getReference().ParagraphIndex;
                String paragraphText = getParagraphText(paragraphIndex);
                System.out.println(paragraphText);
                Toast.makeText(FBReader.this, "敬请期待", Toast.LENGTH_SHORT).show();
            }
        });

        // 本地书库
        findViewById(R.id.book_library).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationHelper.closeBottomMenu(findViewById(R.id.menuMore));
                AnimationHelper.closeBottomMenu(findViewById(R.id.menuTop));
                myFBReaderApp.runAction(ActionCode.SHOW_LIBRARY);
            }
        });
    }

    private final AndroidImageSynchronizer myImageSynchronizer = new AndroidImageSynchronizer(this);

    /**
     * 初始化点击more后的图书信息
     */
    private void initMoreBookInfoView() {
        final Book book = myFBReaderApp.getCurrentBook();
        if (book == null) {
            return;
        }
        final TextView tvBookName = findViewById(R.id.book_name);
        tvBookName.setText(book.getTitle());
        final TextView tvAuthor = findViewById(R.id.author);
        tvAuthor.setText(book.authorsString(""));
        final ImageView coverView = findViewById(R.id.book_img);
        final PluginCollection pluginCollection =
                PluginCollection.Instance(Paths.systemInfo(this));
        final ZLImage image = CoverUtil.getCover(book, pluginCollection);

        if (image == null) {
            return;
        }

        if (image instanceof ZLImageProxy) {
            ((ZLImageProxy) image).startSynchronization(myImageSynchronizer, new Runnable() {
                public void run() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            setCover(coverView, image);
                        }
                    });
                }
            });
        } else {
            setCover(coverView, image);
        }
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

    /**
     * 初始化书籍信息
     */
    private void initBookInfoView() {
        Book book = myFBReaderApp.getCurrentBook();
        if (book == null) {
            return;
        }
        String title = book.getTitle();

        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(title);
    }

    public String getParagraphText(int paragraphIndex) {
        System.out.println("段落索引" + paragraphIndex);
        final StringBuilder builder = new StringBuilder();
        ZLTextParagraphCursor zlTextParagraphCursor = new ZLTextParagraphCursor(myFBReaderApp.Model.getTextModel(), paragraphIndex);
        while (!zlTextParagraphCursor.isEndOfSection()) {
            getParagraphText(builder, zlTextParagraphCursor.Index);
            zlTextParagraphCursor = zlTextParagraphCursor.next();
        }
        return builder.toString();
    }

    private void getParagraphText(StringBuilder builder, int index) {
        final ZLTextWordCursor cursor = new ZLTextWordCursor(myFBReaderApp.getTextView().getStartCursor());
        cursor.moveToParagraph(index);
        cursor.moveToParagraphStart();
        while (!cursor.isEndOfParagraph()) {
            ZLTextElement element = cursor.getElement();
            if (element instanceof ZLTextWord) {
                builder.append(element.toString());
            }
            cursor.nextWord();
        }
    }

    public FBReaderApp getMyFBReaderApp() {
        return myFBReaderApp;
    }

    private void initFragment() {
        if (isLoad) {
            ((TOCFragment) fragments.get(0)).initTree();
            return;
        }
        isLoad = true;
        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        fragments.add(new TOCFragment());
        fragments.add(new BookMarkFragment());
        fragments.add(new BookNoteFragment());
        final String[] titles = new String[]{"目录", "书签", "笔记"};
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }

    private void openSlideMenu() {
        View slideView = findViewById(R.id.slideMenu);
        slideView.setVisibility(View.VISIBLE);
        TranslateAnimation inAnimation = new TranslateAnimation(-(ScreenUtils.getWidth(this) - SizeUtils.dp2px(this, 40)), 0, 0, 0);
        inAnimation.setDuration(DURATION);
        slideView.startAnimation(inAnimation);

        View viewBackground = findViewById(R.id.viewBackground);
        viewBackground.setVisibility(View.VISIBLE);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 0.3f);
        alphaAnimation.setDuration(DURATION);
        alphaAnimation.setFillAfter(true);
        viewBackground.startAnimation(alphaAnimation);

        View readerView = findViewById(R.id.readerView);
        TranslateAnimation outAnimation = new TranslateAnimation(0,
                ScreenUtils.getWidth(this) - SizeUtils.dp2px(this, 40), 0, 0);
        outAnimation.setDuration(DURATION);
        outAnimation.setFillAfter(true);
        readerView.startAnimation(outAnimation);

        closeMenu(true, firstMenu);
        // 关闭-->顶部菜单
        AnimationHelper.closeTopMenu(findViewById(R.id.menuTop));
    }

    public void closeSlideMenu() {
        final View slideView = findViewById(R.id.slideMenu);
        final TranslateAnimation inAnimation = new TranslateAnimation(0, -(ScreenUtils.getWidth(this) - SizeUtils.dp2px(this, 40)), 0, 0);
        inAnimation.setDuration(DURATION);
        inAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                slideView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        slideView.startAnimation(inAnimation);

        final View viewBackground = findViewById(R.id.viewBackground);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 0);
        alphaAnimation.setDuration(DURATION);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewBackground.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        viewBackground.startAnimation(alphaAnimation);

        View readerView = findViewById(R.id.readerView);
        TranslateAnimation outAnimation = new TranslateAnimation(ScreenUtils.getWidth(this)
                - SizeUtils.dp2px(this, 40), 0, 0, 0);
        outAnimation.setDuration(DURATION);
        readerView.startAnimation(outAnimation);
    }

    private class TipRunner extends Thread {
        TipRunner() {
            setPriority(MIN_PRIORITY);
        }

        public void run() {
            final TipsManager manager = new TipsManager(Paths.systemInfo(FBReader.this));
            switch (manager.requiredAction()) {
                case Initialize:
                    startActivity(new Intent(
                            TipsActivity.INITIALIZE_ACTION, null, FBReader.this, TipsActivity.class
                    ));
                    break;
                case Show:
                    startActivity(new Intent(
                            TipsActivity.SHOW_TIP_ACTION, null, FBReader.this, TipsActivity.class
                    ));
                    break;
                case Download:
                    manager.startDownloading();
                    break;
                case None:
                    break;
            }
        }
    }
}
