package com.example.fbio.tictactow;

import java.util.HashMap;



public class TicTac {

    public enum State{
        O,X,C               //C é clear, ou seja, ninguém marcou nada naquele lugar ainda.
    }

    public HashMap<Key,State> field;

    TicTac() {
        //constructor sem nada
        field = new HashMap<>();
    }

    public void initialize() {      //nicializa todas as posições em CLEAR (C)
        Key k = new Key(0, 0);

        for (int x = 1; x <= 3; x++) {
            for (int y = 1; y <= 3; y++) {
                k = new Key(x, y);
                field.put(k, State.C);
            }

        }
    }

    public boolean mark(int x, int y, State s) {    //Tenta marcar com X ou O uma posição. Se não estiver livre (não é C) retorna falso.
        Key k = new Key(x,y);
        if(field.get(k).equals(State.C)) {
            field.put(k, s);
            return true;
        }
        return false;
    }

    public State checkForWinner(){
        for (int x = 1; x <= 3; x++) {      //checando linhas
            Key k1 = new Key(x,1);
            Key k2 = new Key(x,2);
            Key k3 = new Key(x,3);

            if (field.get(k1).equals(field.get(k2)) && field.get(k3).equals(field.get(k2))) {
                return field.get(k1);
            }
        }

        for (int x = 1; x <= 3; x++) {      //checando colunas
            Key k1 = new Key(1,x);
            Key k2 = new Key(2,x);
            Key k3 = new Key(3,x);

            if (field.get(k1).equals(field.get(k2)) && field.get(k3).equals(field.get(k2))) {
                return field.get(k1);
            }
        }

        //Checando diagonais
        if (field.get(new Key(1,1)).equals(field.get(new Key(2,2))) && field.get(new Key(1, 1)).equals(field.get(new Key(3, 3)))) {
            return field.get(new Key(1,1));
        }
        if (field.get(new Key(3,1)).equals(field.get(new Key(2,2))) && field.get(new Key(3, 1)).equals(field.get(new Key(1, 3)))) {
            return field.get(new Key(1,3));
        }

        return State.C; //se retornar C é por que não tem ganhador.
    }

    public boolean checkForVelha() {
        State s;
        for (int x = 1; x <= 3; x++) {
            for (int y = 1; y <= 3; y++) {

                s = field.get(new Key(x,y));
                if (s.equals(State.C)) return false;
            }

        }
        return true;
    }
}
