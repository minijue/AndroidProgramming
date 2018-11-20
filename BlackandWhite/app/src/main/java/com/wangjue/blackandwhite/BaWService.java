package com.wangjue.blackandwhite;

import android.database.ContentObserver;
import android.graphics.drawable.Icon;
import android.os.Handler;
import android.provider.Settings;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

public class BaWService extends TileService {
    private Icon icon;
    private boolean toggleState = false;

    // 监听亮度变化，并保持黑白模式
    private ContentObserver mBrightnessObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            if (toggleState) {
                Settings.Secure.putInt(getContentResolver(), "accessibility_display_daltonizer_enabled", 1);   // 启动/关闭色彩校正
            }
        }
    };

    private void taggleScreeen(boolean b) {
        toggleState = b;
        int active = b ? Tile.STATE_ACTIVE : Tile.STATE_INACTIVE;
        getQsTile().setState(active);// 更改成非活跃状态

        try {
            int value1 = b ? Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL :
                    Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, value1);   // 自动亮度会关闭色彩校正，因此打开色彩校正时需要关闭自动亮度
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        String olddisplay = b ? "0" : Settings.Secure.getString(getContentResolver(), "accessibility_display_daltonizer");   // 原来的校正模式
        Settings.Secure.putString(getContentResolver(), "accessibility_display_daltonizer", olddisplay);  // 恢复原来的校正模式

        int value2 = b ? 1 : 0;
        Settings.Secure.putInt(getContentResolver(), "accessibility_display_daltonizer_enabled", value2);   // 启动/关闭色彩校正
    }

    @Override
    public void onTileAdded() {
    }

    //当用户从快速设定栏中移除的时候调用
    @Override
    public void onTileRemoved() {
    }


    @Override
    public void onClick() {
        toggleState = !toggleState;
        taggleScreeen(toggleState);

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
