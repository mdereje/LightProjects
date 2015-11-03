package com.example.thetribunal.nothingworks;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.example.android.fragments.HeadlinesFragment;


public class MainActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.thetribunal.nothingworks.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){
            case R.id.action_search:
                //openSearch();
                return true;
            case R.id.action_settings:
                //openSetting();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    /* Called when the user clicks the Send button*/
    public void sendMessage(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        //putExtra takes key name in the first and value in the second.
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
    /*open the fragment main when read button is pressed.*/
    public void readLesson(View view){
        Intent intent = new Intent(this, HeadlinesFragment.class);
        startActivity(intent);
    }
}
