package com.wawproject.startup;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;

/**
 * @author kerukerupappa
 * 初回起動のチェックアクティビティ
 * 初回起動時にはonFirst()が発生
 * バージョンに差異がある場合はonUpdate()が発生
 * 通常起動の場合はonNormal()が発生
 *　
 *　例 ）
 *　public class SampleActivity extends StartUpActivity
 *
 */
public abstract class StartUpActivity extends Activity {

    protected abstract void onFirst();
    protected abstract void onUpdate( int version , int verCode );
    protected abstract void onNormal();
    protected abstract void onActivityResult(int requestCode, int resultCode, Intent data);

    private final static String PREFERNCE_NAME = "preferences_startup";
    private final static String FIRST = "FIRST";
    private final static String VERSION = "VERSION";

    private SharedPreferences mPreference = null;
    private Editor mEditor = null;
    private PackageInfo mPackageInfo = null;

    /**
     * クリエイト処理
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.mPreference = getSharedPreferences( PREFERNCE_NAME, MODE_PRIVATE );
        this.mEditor = this.mPreference.edit();
        this.mPackageInfo = getPackageInfo();

        this.verCheck();

    }

    /**
     * バージョンのチェック処理を行う
     */
    private void verCheck()
    {
        // 初回起動のチェック
        if ( !this.mPreference.getBoolean(FIRST, false) )
        {
            Log.i( FIRST , "preferenceCode : "+ this.mPreference.getInt( VERSION, 0 ) +" / "+"versionCode : " + this.mPackageInfo.versionCode );
            // 初回起動時のイベント
            this.onFirst();

        }

        // バージョンのチェック
        else if ( this.mPreference.getInt( VERSION, 0) != this.mPackageInfo.versionCode )
        {
            Log.i( "UPDATE" , "preferenceCode : " + this.mPreference.getInt( VERSION, 0 ) + " / " + "versionCode : " + this.mPackageInfo.versionCode );

            // Update時のイベント
            this.onUpdate( this.mPreference.getInt(VERSION, 0), this.mPackageInfo.versionCode );

        }

        // 通常起動
        else
        {
            this.onNormal();
            this.finish();

        }

    }
    /**
     * 初回起動の状態を保存
     * @param bool
     */
    protected void setFirst(boolean bool )
    {
        this.mEditor.putBoolean( FIRST , bool );
        this.mEditor.commit();

    }
    /**
     * バージョンアップの状態を保存
     */
    protected void setUpdate( )
    {
        this.mEditor.putInt( VERSION, this.mPackageInfo.versionCode );
        this.mEditor.commit();

    }



    /**
     * 自パッケージを返却
     * @return PackageInfo
     */
    private PackageInfo getPackageInfo()
    {

        PackageInfo packageInfo = null;

        // パッケージの取得
        try
        {
            packageInfo = getPackageManager().getPackageInfo( getPackageName(), PackageManager.GET_META_DATA );

        }
        // エクセプション
        catch (NameNotFoundException e)
        {
            e.printStackTrace();

        }

        return packageInfo;
    }

}