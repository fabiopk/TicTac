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

import java.util.ArrayList;

public class Main2Activity extends Activity {


    public static final String EXTRA_MESSAGE3 = "com.example.fbio.tictactow.MESSAGE3";
    //private static final String EXTRA_MESSAGE4 = "com.example.fbio.tictactow.MESSAGE4";

    TicTac game;
    public ArrayList<Button> buttons = new ArrayList<>();
    String n1, n2;
    EditText vamosmudar;
    Button b11;
    Button b12;
    Button b13;
    Button b21;
    Button b22;
    Button b23;
    Button b31;
    Button b32;
    Button b33;
    private boolean turn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        game = new TicTac();

        turn = false;
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

        vamosmudar.setText(n1);

    }

    public void vamos(){


        Intent intent2 = new Intent(this, Main3Activity.class);
        EditText ganhador = (EditText) findViewById(R.id.nome12);    //passsa quem esta em nome12
        String mensagem = ganhador.getText().toString();

        intent2.putExtra(EXTRA_MESSAGE3, mensagem);
        startActivity(intent2);

    }

   /* public void velha(){

        Intent intent2 = new Intent(this, Main3Activity.class);
        EditText ganhador = (EditText) findViewById(R.id.nome12);    //passsa quem esta em nome12
        String mensagem = ganhador.getText().toString();

        intent2.putExtra(EXTRA_MESSAGE4, mensagem);
        startActivity(intent2);
    }
*/



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


    public void Click11(View view) {
        int x = 1;
        int y = 1;
        if (game.mark(x, y, stateFromTurn())) {
            turn = !turn;
            if( (game.checkForWinner()) != TicTac.State.C) {
                vamos();
            }
            if(game.checkForWinner() == TicTac.State.C){
                //velha();
            }
            refreshVez();
        }
        refreshButton(x, y, b11);
    }

    public void Click12(View view) {
        int x = 1;
        int y = 2;
        if (game.mark(x, y, stateFromTurn())) {
            turn = !turn;
            if( (game.checkForWinner()) != TicTac.State.C) {
                vamos();
            }
            if(game.checkForWinner() == TicTac.State.C){
                //velha();
            }


            refreshVez();
        }
        refreshButton(x, y, b12);
    }

    public void Click13(View view) {
        int x = 1;
        int y = 3;
        if (game.mark(x, y, stateFromTurn())) {
            turn = !turn;
            if( (game.checkForWinner()) != TicTac.State.C) {
                vamos();
            }
            if(game.checkForWinner() == TicTac.State.C){
//                velha();
            }
            refreshVez();
        }
        refreshButton(x, y, b13);
    }

    public void Click21(View view) {
        int x = 2;
        int y = 1;
        if (game.mark(x, y, stateFromTurn())) {
            turn = !turn;
            if( (game.checkForWinner()) != TicTac.State.C) {
                vamos();
            }
            if(game.checkForWinner() == TicTac.State.C){
                //velha();
            }
            refreshVez();
        }
        refreshButton(x, y, b21);
    }

    public void Click22(View view) {
        int x = 2;
        int y = 2;
        if (game.mark(x, y, stateFromTurn())) {
            turn = !turn;
            if( (game.checkForWinner()) != TicTac.State.C) {
                vamos();
            }
            if(game.checkForWinner() == TicTac.State.C){
                //velha();
            }
            refreshVez();
        }
        refreshButton(x, y, b22);
    }

    public void Click23(View view) {
        int x = 2;
        int y = 3;
        if (game.mark(x, y, stateFromTurn())) {
            turn = !turn;
            if( (game.checkForWinner()) != TicTac.State.C) {
                vamos();
            }
            if(game.checkForWinner() == TicTac.State.C){
                //velha();
            }
            refreshVez();
        }
        refreshButton(x, y, b23);
    }

    public void Click31(View view) {
        int x = 3;
        int y = 1;
        if (game.mark(x, y, stateFromTurn())) {
            turn = !turn;
            if( (game.checkForWinner()) != TicTac.State.C) {
                vamos();
            }
            if(game.checkForWinner() == TicTac.State.C){
                //velha();
            }
            refreshVez();
        }
        refreshButton(x, y, b31);
    }

    public void Click32(View view) {
        int x = 3;
        int y = 2;
        if (game.mark(x, y, stateFromTurn())) {
            turn = !turn;
            if( (game.checkForWinner()) != TicTac.State.C) {
                vamos();
            }
            if(game.checkForWinner() == TicTac.State.C){
                //velha();
            }
            refreshVez();
        }
        refreshButton(x, y, b32);
    }

    public void Click33(View view) {
        int x = 3;
        int y = 3;
        if (game.mark(x, y, stateFromTurn())) {
            turn = !turn;
            if( (game.checkForWinner()) != TicTac.State.C) {
                vamos();
            }
            if(game.checkForWinner() == TicTac.State.C){
                //velha();
            }
            refreshVez();
        }
        refreshButton(x, y, b33);
    }

    public void refreshButton(int x, int y, Button b) {
        TicTac.State s = game.field.get(new Key(x, y));
        if (s.equals(TicTac.State.C)) {
            b.setText(" ");
        } else if (s.equals(TicTac.State.O)) {
            b.setText("O");
        } else {
            b.setText("X");
        }
    }

    public void refreshVez(){
        String s;
        if (!turn) {
            s = n1;
        }else{
            s = n2;
        }

        vamosmudar.setText(s);

    }

    public TicTac.State stateFromTurn() {
        if (turn == true) return TicTac.State.X;
        return TicTac.State.O;
    }
}