package com.wawproject.startup;

import android.content.Intent;

/**
 * 起動のチェックサンプル
 *
 */
public  class SampleStartUpActivity extends StartUpActivity
{
    private static final int INTENT_FIRST = 0;
    private static final int INTENT_UPDATE = 1;

    /**
     * 初回起動時に呼ばれるイベント
     */
    @Override
    protected void onFirst()
    {
        Intent intent = new Intent(SampleStartUpActivity.this, SampleDialog.class);
        intent.putExtra("DATA", "初回起動\n");
        startActivityForResult( intent, INTENT_FIRST );

    }

    /**
     * バージョン変更時に呼ばれるイベント
     */
    @Override
    protected void onUpdate(int version, int verCode)
    {
        Intent intent = new Intent(SampleStartUpActivity.this, SampleDialog.class);
        intent.putExtra("DATA", "アップデート\n");
        startActivityForResult( intent, INTENT_UPDATE );

    }

    /**
     * 通常起動時に呼ばれるイベント
     */
    @Override
    protected void onNormal()
    {
        Intent intent = new Intent(SampleStartUpActivity.this, SampleMainActivity.class);
        startActivity( intent );

    }

    /**
     * アクティビティの戻り値の確認
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (( requestCode == INTENT_FIRST || requestCode == INTENT_UPDATE ) && resultCode == RESULT_OK )
        {
            if( data.getBooleanExtra("RESULT", false) ){

                // ライセンスの状態を保存（必須）
                if( requestCode == INTENT_FIRST ) setFirst(true);
                if( requestCode == INTENT_UPDATE ) setUpdate();

                Intent intent = new Intent(SampleStartUpActivity.this, SampleMainActivity.class);
                startActivity( intent );

            }
        }

        this.finish();

    }



}
