package com.example.fbio.tictactow;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    public static final String EXTRA_MESSAGE = "com.example.fbio.tictactow.MESSAGE" ;
    public static final String EXTRA_MESSAGE2 = "com.example.fbio.tictactow.MESSAGE2";
    public static final String EXTRA_MESSAGE3 = "com.example.fbio.tictactow.MESSAGE3";
    //public static final String EXTRA_MESSAGE4 = "com.example.fbio.tictactow.MESSAGE4";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1 = (Button)findViewById(R.id.iniciar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void BotaoIniciar(View view){
        Intent intent = new Intent(this, Main2Activity.class);
        EditText enviar = (EditText)findViewById(R.id.nome1);
        String mensagem = enviar.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, mensagem);

        EditText enviar2 = (EditText)findViewById(R.id.nome2);
        String mensagem2 = enviar2.getText().toString();
        intent.putExtra(EXTRA_MESSAGE2, mensagem2);

        startActivity(intent);
    }

}
