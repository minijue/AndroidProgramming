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

import java.util.HashMap;
import java.util.Map;

import static android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE;

public class BaWService extends TileService {
    private Icon icon;

    private boolean toggleState = false;
    // 监听亮度变化，并保持黑白模式
    private final ContentObserver mBrightnessObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            if (toggleState) {
                Settings.Secure.putInt(getContentResolver(), "night_display_activated", 1);
                Settings.Secure.putInt(getContentResolver(), "accessibility_display_daltonizer_enabled", 1);   // 启动/关闭色彩校正
            }
        }
    };
    private final IntentFilter filter = new IntentFilter();
    private Map<String, Object> mapState = new HashMap<>(); // 用于保存其他显示状态，在关闭时恢复

    private void refreshTile() {
        getQsTile().setState(toggleState ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE);

        getQsTile().setIcon(icon);
        getQsTile().updateTile();
    }

    private void toggleScreen() {
        int vNightMode = toggleState ? 1 : ((Integer) mapState.get("night_display_activated")).intValue();
        Settings.Secure.putInt(getContentResolver(), "night_display_activated", vNightMode);    // 打开/关闭夜间模式（护眼模式）

        int vBrightness = toggleState ? 0 : ((Integer) mapState.get(SCREEN_BRIGHTNESS_MODE)).intValue();
        Settings.System.putInt(getContentResolver(), SCREEN_BRIGHTNESS_MODE, vBrightness);   // 自动亮度会关闭色彩校正，因此打开色彩校正时需要关闭自动亮度

        String olddisplay = toggleState ? "0" : (String) mapState.get("accessibility_display_daltonizer");   // 原来的校正模式
        Settings.Secure.putString(getContentResolver(), "accessibility_display_daltonizer", olddisplay);  // 设置/恢复原来的校正模式，黑白要求模式为“全色盲”

        int value2 = toggleState ? 1 : 0;
        Settings.Secure.putInt(getContentResolver(), "accessibility_display_daltonizer_enabled", value2);   // 启动/关闭色彩校正
    }

    @Override
    public void onTileAdded() {
        refreshTile();
    }


    //当用户从快速设定栏中移除的时候调用
    @Override
    public void onTileRemoved() {
    }


    @Override
    public void onClick() {
        toggleState = !toggleState;
        toggleScreen();

        refreshTile();
    }


    @Override
    public void onStartListening() {
        try {
            if (!toggleState) {
                // 打开阅读模式前保存其他显示状态
                mapState.put(SCREEN_BRIGHTNESS_MODE, Settings.System.getInt(getContentResolver(), SCREEN_BRIGHTNESS_MODE));
                mapState.put("accessibility_display_daltonizer", Settings.Secure.getString(getContentResolver(), "accessibility_display_daltonizer"));
                mapState.put("night_display_activated", Settings.Secure.getInt(getContentResolver(), "night_display_activated"));
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
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
//        filter.addAction(Intent.ACTION_USER_PRESENT);
//        filter.addAction(Intent.ACTION_SCREEN_ON);
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
    }
}
