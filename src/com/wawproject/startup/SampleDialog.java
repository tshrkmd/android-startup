package com.wawproject.startup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public  class SampleDialog extends Activity implements OnClickListener
{
    private static final String RESULT_TEXT = "RESULT";

    Button mYes = null;
    Button mNo = null;
    TextView mTextView  = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        setTitle(getString(R.string.activity_dialog_title));


        Intent intent = getIntent();
        intent.getStringExtra("DATA");

        this.mTextView = ( TextView ) findViewById( R.id.activity_dialog_mess );
        this.mTextView.setText(intent.getStringExtra("DATA") + this.mTextView.getText());

        this.mYes = ( Button ) findViewById( R.id.activity_dialog_yes );
        this.mNo = ( Button ) findViewById(R.id.activity_dialog_no );

        this.mYes.setOnClickListener( this );
        this.mNo.setOnClickListener( this );

    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent();

        switch ( v.getId() )
        {
            case R.id.activity_dialog_yes:
                intent.putExtra( RESULT_TEXT , true );
                setResult( RESULT_OK, intent );
                break;

            case R.id.activity_dialog_no:
                intent.putExtra( RESULT_TEXT , false );
                setResult( RESULT_OK, intent );
                break;

            default:
                break;
        }
        finish();

    }

}
