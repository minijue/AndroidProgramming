package com.wangjue.blackandwhite;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.drawable.Icon;
import android.os.Handler;
import android.provider.Settings;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

public class BaWService extends TileService {
    private Icon icon;

    private static boolean toggleState = false;

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
        BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
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
    }
}
