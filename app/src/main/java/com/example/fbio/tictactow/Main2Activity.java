package com.example.fbio.tictactow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main2Activity extends Activity {


    public static final String EXTRA_MESSAGE3 = "com.example.fbio.tictactow.MESSAGE3";
    private static final String EXTRA_MESSAGE4 = "com.example.fbio.tictactow.MESSAGE4";
    //private static final String EXTRA_MESSAGE4 = "com.example.fbio.tictactow.MESSAGE4";

    static TicTac game;
    public ArrayList<Button> buttons = new ArrayList<>();
    static String n1;
    static String n2;
    static EditText vamosmudar;
    static Button b11, b12, b13, b21, b22, b23, b31, b32, b33;
    private static boolean turn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        game = new TicTac();


        b11 = (Button) findViewById(R.id.b11);
        b12 = (Button) findViewById(R.id.b12);
        b13 = (Button) findViewById(R.id.b13);

        b21 = (Button) findViewById(R.id.b21);
        b22 = (Button) findViewById(R.id.b22);
        b23 = (Button) findViewById(R.id.b23);

        b31 = (Button) findViewById(R.id.b31);
        b32 = (Button) findViewById(R.id.b32);
        b33 = (Button) findViewById(R.id.b33);

        buttons.add(b11);
        buttons.add(b12);
        buttons.add(b13);
        buttons.add(b21);
        buttons.add(b22);
        buttons.add(b23);
        buttons.add(b31);
        buttons.add(b32);
        buttons.add(b33);


        game.initialize();

        Intent intent = getIntent();
        vamosmudar = (EditText) findViewById(R.id.nome12);
        n1 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        n2 = intent.getStringExtra(MainActivity.EXTRA_MESSAGE2);

        if(n1.compareTo(n2) > 0){
            Toast.makeText(this, "MAIORR!", Toast.LENGTH_SHORT).show();
            turn = true;
        }
        else {
            Toast.makeText(this, "menor", Toast.LENGTH_SHORT).show();
        turn = false;
        }

        refreshVez();

    }

    public void vamos(){


        Intent intent2 = new Intent(this, Main3Activity.class);
        EditText ganhador = (EditText) findViewById(R.id.nome12);    //passsa quem esta em nome12
        String mensagem = ganhador.getText().toString();

        intent2.putExtra(EXTRA_MESSAGE3, mensagem);
        startActivity(intent2);

    }

    public void velha(){

        Intent intent2 = new Intent(this, Main3Activity.class);
        startActivity(intent2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
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

    public static void markB(int x, int y){
        game.mark(x, y, stateFromTurn());
        if(x ==1 && y ==1) refreshButton(x, y, b11);
        if(x ==1 && y ==2) refreshButton(x, y, b12);
        if(x ==1 && y ==3) refreshButton(x, y, b13);
        if(x ==2 && y ==1) refreshButton(x, y, b21);
        if(x ==2 && y ==2) refreshButton(x, y, b22);
        if(x ==2 && y ==3) refreshButton(x, y, b23);
        if(x ==3 && y ==1) refreshButton(x, y, b31);
        if(x ==3 && y ==2) refreshButton(x, y, b32);
        if(x ==3 && y ==3) refreshButton(x, y, b33);

        refreshVez();
        if( (game.checkForWinner()) != TicTac.State.C) {
            //vamos();
        }

        turn = !turn;

    }

    public void Click11(View view) {
        int x = 1;
        int y = 1;
        if (turn && game.mark(x, y, stateFromTurn())) {
            turn = !turn;
            MainActivity.sendMessage(String.valueOf(x) + " " + String.valueOf(y));
            Toast.makeText(getApplicationContext(),String.valueOf(x) + " " + String.valueOf(y),Toast.LENGTH_SHORT);
            if( (game.checkForWinner()) != TicTac.State.C) {

                vamos();
            }
            if(game.checkForWinner() == TicTac.State.C){
                if(game.checkForVelha()) {             velha();         }
            }
            refreshVez();
        }
        refreshButton(x, y, b11);
    }

    public void Click12(View view) {
        int x = 1;
        int y = 2;
        if (turn && game.mark(x, y, stateFromTurn())) {
            turn = !turn;
            MainActivity.sendMessage(String.valueOf(x) + " " + String.valueOf(y));
            Toast.makeText(getApplicationContext(),String.valueOf(x) + " " + String.valueOf(y),Toast.LENGTH_SHORT);
            if( (game.checkForWinner()) != TicTac.State.C) {
                vamos();
            }
            if(game.checkForWinner() == TicTac.State.C){
                if(game.checkForVelha()) {             velha();         }
            }


            refreshVez();
        }
        refreshButton(x, y, b12);
    }

    public void Click13(View view) {
        int x = 1;
        int y = 3;
        if (turn && game.mark(x, y, stateFromTurn())) {
            turn = !turn;
            MainActivity.sendMessage(String.valueOf(x) + " " + String.valueOf(y));
            Toast.makeText(getApplicationContext(),String.valueOf(x) + " " + String.valueOf(y),Toast.LENGTH_SHORT);
            if( (game.checkForWinner()) != TicTac.State.C) {
                vamos();
            }
            if(game.checkForWinner() == TicTac.State.C){
                if (game.checkForVelha())           velha();
            }
            refreshVez();
        }
        refreshButton(x, y, b13);
    }

    public void Click21(View view) {
        int x = 2;
        int y = 1;
        if (turn && game.mark(x, y, stateFromTurn())) {
            turn = !turn;
            MainActivity.sendMessage(String.valueOf(x) + " " + String.valueOf(y));
            Toast.makeText(getApplicationContext(),String.valueOf(x) + " " + String.valueOf(y),Toast.LENGTH_SHORT);
            if( (game.checkForWinner()) != TicTac.State.C) {
                vamos();
            }
            if(game.checkForWinner() == TicTac.State.C){
                if(game.checkForVelha()) {             velha();         }
            }
            refreshVez();
        }
        refreshButton(x, y, b21);
    }

    public void Click22(View view) {
        int x = 2;
        int y = 2;
        if (turn && game.mark(x, y, stateFromTurn())) {
            turn = !turn;
            MainActivity.sendMessage(String.valueOf(x) + " " + String.valueOf(y));
            Toast.makeText(getApplicationContext(),String.valueOf(x) + " " + String.valueOf(y),Toast.LENGTH_SHORT);
            if( (game.checkForWinner()) != TicTac.State.C) {
                vamos();
            }
            if(game.checkForWinner() == TicTac.State.C){
                if(game.checkForVelha()) {             velha();         }
            }
            refreshVez();
        }
        refreshButton(x, y, b22);
    }

    public void Click23(View view) {
        int x = 2;
        int y = 3;
        if (turn && game.mark(x, y, stateFromTurn())) {
            turn = !turn;
            MainActivity.sendMessage(String.valueOf(x) + " " + String.valueOf(y));
            Toast.makeText(getApplicationContext(),String.valueOf(x) + " " + String.valueOf(y),Toast.LENGTH_SHORT);

            if( (game.checkForWinner()) != TicTac.State.C) {
                vamos();
            }
            if(game.checkForWinner() == TicTac.State.C){
                if(game.checkForVelha()) {             velha();         }
            }
            refreshVez();
        }
        refreshButton(x, y, b23);
    }

    public void Click31(View view) {
        int x = 3;
        int y = 1;
        if (turn && game.mark(x, y, stateFromTurn())) {


            turn = !turn;
            MainActivity.sendMessage(String.valueOf(x) + " " + String.valueOf(y));
            Toast.makeText(getApplicationContext(),String.valueOf(x) + " " + String.valueOf(y),Toast.LENGTH_SHORT);
            if( (game.checkForWinner()) != TicTac.State.C) {
                vamos();
            }
            if(game.checkForWinner() == TicTac.State.C){
                if(game.checkForVelha()) {             velha();         }
            }
            refreshVez();
        }
        refreshButton(x, y, b31);
    }

    public void Click32(View view) {
        int x = 3;
        int y = 2;
        if (turn && game.mark(x, y, stateFromTurn())) {
            turn = !turn;
            MainActivity.sendMessage(String.valueOf(x) + " " + String.valueOf(y));
            Toast.makeText(getApplicationContext(),String.valueOf(x) + " " + String.valueOf(y),Toast.LENGTH_SHORT);
            if( (game.checkForWinner()) != TicTac.State.C) {
                vamos();
            }
            if(game.checkForWinner() == TicTac.State.C){
                if(game.checkForVelha()) {             velha();         }
            }
            refreshVez();
        }
        refreshButton(x, y, b32);
    }

    public void Click33(View view) {
        int x = 3;
        int y = 3;
        if (turn && game.mark(x, y, stateFromTurn())) {
            turn = !turn;
            MainActivity.sendMessage(String.valueOf(x) + " " + String.valueOf(y));
            Toast.makeText(getApplicationContext(),String.valueOf(x) + " " + String.valueOf(y),Toast.LENGTH_SHORT);
            if( (game.checkForWinner()) != TicTac.State.C) {
                vamos();
            }
            if(game.checkForWinner() == TicTac.State.C){
                if(game.checkForVelha()) {             velha();         }
            }
            refreshVez();
        }
        refreshButton(x, y, b33);
    }

    public  static void refreshButton(int x, int y, Button b) {
        TicTac.State s = game.field.get(new Key(x, y));
        if (s.equals(TicTac.State.C)) {
            b.setText(" ");
        } else if (s.equals(TicTac.State.O)) {
            b.setText("O");
        } else {
            b.setText("X");
        }
    }

    public static void refreshVez(){
        String s;
        if (!turn) {
            s = n1;
        }else{
            s = n2;
        }

        vamosmudar.setText(s);

    }

    public static TicTac.State stateFromTurn() {

        if (turn == true) return TicTac.State.X;
        return TicTac.State.O;


    }


}