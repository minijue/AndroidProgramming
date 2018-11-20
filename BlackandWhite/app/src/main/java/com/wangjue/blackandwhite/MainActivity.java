package com.wangjue.blackandwhite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
//    public static boolean RootCommand(String command) {
//        Process process = null;
//        DataOutputStream os = null;
//        try {
//            process = Runtime.getRuntime().exec("su");
//            os = new DataOutputStream(process.getOutputStream());
//            os.writeBytes(command + "\n");
//            os.writeBytes("exit\n");
//            os.flush();
//            process.waitFor();
//        } catch (Exception e) {
//            Log.d("*** DEBUG ***", "ROOT REE" + e.getMessage());
//            return false;
//        } finally {
//            try {
//                if (os != null) {
//                    os.close();
//                }
//                process.destroy();
//            } catch (Exception e) {
//            }
//        }
//
//        Log.d("*** DEBUG ***", "Root SUC ");
//        return true;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        String apkRoot = "chmod 777 " + getPackageCodePath();
//        RootCommand(apkRoot);
        Intent start = new Intent(this, BaWService.class);
        startService(start);
    }
}
