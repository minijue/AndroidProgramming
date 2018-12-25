package com.wangjue.blackandwhite;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.drawable.Icon;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.widget.Toast;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class BaWService extends TileService {
    private static final int MSG_REFRESH = 0x0417;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {//此方法在ui线程运行
            switch (msg.what) {
                case MSG_REFRESH:
                    refreshTile();
                    break;
            }
        }
    };

    private Icon icon;
    private BroadcastReceiver mBatInfoReceiver;

    private Handler handler = new Handler();
    private boolean toggleState = false;
    private String[] tasks = {/*"com.gudianbiquge.ebook.app", "com.biqugebendi.ebook.app", */"com.amazon.kindlefc", "com.ilike.cartoon", "com.duokan.reader",
            "com.chaozh.iReaderFree", "com.netease.snailread"};
    private Runnable runnable = () -> {
        if (!toggleState && getTask()) {
            toggleState = true;
            toggleScreen();

            mHandler.obtainMessage(MSG_REFRESH).sendToTarget();
        }
        handler.postDelayed(this.runnable, 1000);
    };

    private boolean getTask() {
        String topPackageName;
        UsageStatsManager mUsageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        long time = System.currentTimeMillis();
        // We get usage stats for the last 10 seconds
        List<UsageStats> stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000, time);
        // Sort the stats by the last time used
        if (stats != null) {
            SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
            for (UsageStats usageStats : stats) {
                mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
            }
            if (mySortedMap != null && !mySortedMap.isEmpty()) {
                topPackageName = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                if (topPackageName.contains("biquge")) {    // 适应各种“笔趣阁”
                    Toast.makeText(this, "“笔趣阁”阅读模式自动开启", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    for (String n : tasks) {
                        if (n.equals(topPackageName)) {
                            Toast.makeText(this, "阅读模式自动开启", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // 监听亮度变化，并保持黑白模式
    private final ContentObserver mBrightnessObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            if (toggleState) {
                toggleScreen();
            }
        }
    };
    private final IntentFilter filter = new IntentFilter();

    private void refreshTile() {
        getQsTile().setState(toggleState ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);

        getQsTile().setIcon(icon);
        getQsTile().updateTile();
    }

    private void toggleScreen() {
        Settings.Secure.putInt(getContentResolver(), "night_display_activated", toggleState ? 1 : 0);    // 打开/关闭夜间模式（护眼模式）
        Settings.Secure.putInt(getContentResolver(), "accessibility_display_daltonizer_enabled", toggleState ? 1 : 0);   // 启动/关闭色彩校正
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mBatInfoReceiver);
        handler.removeCallbacks(runnable);

        super.onDestroy();
    }

    @Override
    public void onTileAdded() {
        refreshTile();
    }

    //当用户从快速设定栏中移除的时候调用
    @Override
    public void onTileRemoved() {
        if (toggleState) {
            toggleState = false;
            toggleScreen();
            refreshTile();
        }
    }

    @Override
    public void onClick() {
        toggleState = !toggleState;

        if (!toggleState && getTask()) {
            handler.removeCallbacks(runnable);
        } else {
            handler.postDelayed(runnable, 1000);
        }

        toggleScreen();

        refreshTile();
    }

    @Override
    public void onStartListening() {
        refreshTile();
    }

    // 关闭下拉菜单的时候调用,当快速设置按钮并没有在编辑栏拖到设置栏中不会调用
    // 在onTileRemoved移除之前也会调用移除
    @Override
    public void onStopListening() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        icon = Icon.createWithResource(getApplicationContext(), R.drawable.page);

        // 注册亮度事件
        getApplicationContext().getContentResolver().registerContentObserver(
                Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS), true,
                mBrightnessObserver);

        // 锁屏退出黑白模式
        filter.addAction(Intent.ACTION_USER_PRESENT);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mBatInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                if (toggleState) {
                    toggleState = false;
                    toggleScreen();
                    refreshTile();
                }
            }
        };
        registerReceiver(mBatInfoReceiver, filter);

        Settings.Secure.putString(getContentResolver(), "accessibility_display_daltonizer", "0");  // 黑白要求模式为“全色盲”

        try {
            toggleState = (Settings.Secure.getInt(getContentResolver(), "accessibility_display_daltonizer_enabled") == 1);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        handler.postDelayed(runnable, 1000);
    }
}
