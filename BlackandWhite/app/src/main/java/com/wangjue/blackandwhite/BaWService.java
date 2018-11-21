package com.wangjue.blackandwhite;

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
    private Map<String, Object> mapState = new HashMap<>();

    // 监听亮度变化，并保持黑白模式
    private final ContentObserver mBrightnessObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            if (toggleState) {
                Settings.Secure.putInt(getContentResolver(), "accessibility_display_daltonizer_enabled", 1);   // 启动/关闭色彩校正
            }
        }
    };

    private void toggleScreeen() {
        toggleState = !toggleState;

        try {
            if (toggleState) {
                mapState.put(SCREEN_BRIGHTNESS_MODE, Settings.System.getInt(getContentResolver(), SCREEN_BRIGHTNESS_MODE));
                mapState.put("accessibility_display_daltonizer", Settings.Secure.getString(getContentResolver(), "accessibility_display_daltonizer"));
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        int vNightMode = toggleState ? 1 : 0;
        Settings.Secure.putInt(getContentResolver(), "night_display_activated", vNightMode);

        int vBrightness = toggleState ? 0 : ((Integer) mapState.get(SCREEN_BRIGHTNESS_MODE)).intValue();
        Settings.System.putInt(getContentResolver(), SCREEN_BRIGHTNESS_MODE, vBrightness);   // 自动亮度会关闭色彩校正，因此打开色彩校正时需要关闭自动亮度

        String olddisplay = toggleState ? "0" : (String) mapState.get("accessibility_display_daltonizer");   // 原来的校正模式
        Settings.Secure.putString(getContentResolver(), "accessibility_display_daltonizer", olddisplay);  // 恢复原来的校正模式

        int value2 = toggleState ? 1 : 0;
        Settings.Secure.putInt(getContentResolver(), "accessibility_display_daltonizer_enabled", value2);   // 启动/关闭色彩校正

        int active = toggleState ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE;
        getQsTile().setState(active);// 更改成非活跃状态
    }

    @Override
    public void onTileAdded() {
        int active = toggleState ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE;
        getQsTile().setState(active);

        getQsTile().setIcon(icon);
        getQsTile().updateTile();
    }


    //当用户从快速设定栏中移除的时候调用
    @Override
    public void onTileRemoved() {
    }


    @Override
    public void onClick() {
        toggleScreeen();

        getQsTile().setIcon(icon);//设置图标
        getQsTile().updateTile();//更新Tile
    }


    @Override
    public void onStartListening() {
    }

    // 关闭下拉菜单的时候调用,当快速设置按钮并没有在编辑栏拖到设置栏中不会调用
    // 在onTileRemoved移除之前也会调用移除
    @Override
    public void onStopListening() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        icon = Icon.createWithResource(getApplicationContext(), R.drawable.tv);
        // 注册事件
        getApplicationContext().getContentResolver().registerContentObserver(
                Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS), true,
                mBrightnessObserver);

    }
}
