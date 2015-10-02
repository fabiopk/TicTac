package com.example.fbio.tictactow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Main3Activity extends Activity {

    EditText vencedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        vencedor = (EditText) findViewById(R.id.nomeVencedor);



        Intent intent = null;
        try {
            intent = getIntent();
        } catch (Exception e) {

        }
        String v1 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE3);
        if (v1 == null) {
            v1 = "Deu Velha";
            TextView tv = (TextView) findViewById(R.id.vencedor);
            tv.setText("Ninguém Ganhou :(");
        } else {
            v1 = v1 +" Ganhou";
        }
        EditText textView = new EditText(this);
        vencedor.setText(v1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void BotaoVoltar(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

}
