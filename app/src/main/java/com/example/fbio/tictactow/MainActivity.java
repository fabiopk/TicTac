package com.example.fbio.tictactow;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends Activity {


    public static final String EXTRA_MESSAGE = "com.example.fbio.tictactow.MESSAGE" ;
    public static final String EXTRA_MESSAGE2 = "com.example.fbio.tictactow.MESSAGE2";
    public static final String EXTRA_MESSAGE3 = "com.example.fbio.tictactow.MESSAGE3";
    private static Context context;

    public String nome2;

    // Códigos para troca de intent
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_CONNECT_DEVICE = 2;
    private static final int RESTART = 3;
    private BluetoothAdapter myBluetoothAdapter;
    public static String mConnectedDeviceName = null;

    // Retornos do handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    // Adaptador Bluetooth
    public static BluetoothService mBtService = null;

    // Tipos de mensagem enviadas pelo Handler do BluetoothService
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public boolean nomeRecebido;

    EditText enviar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1 = (Button)findViewById(R.id.iniciar);
        enviar = (EditText) findViewById(R.id.nome1);

        MainActivity.context = getApplicationContext();
        nomeRecebido = false;

        // instancia o adaptador bluetooth do dispositivo
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(myBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }

    public void onStart() {
        super.onStart();

        // Verifica ao iniciar se o BT já está ativado
        if (!myBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT); // Requisita habilitação do bluetooth

            // Chama método para preparar futuras conexões
        } else {
            if (mBtService == null) setupConnection();
        }
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
    public void conectar(View view){

        Intent serverIntent = new Intent(this, DeviceList.class);
        startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
    }
    public void ficarVisivel(View view){
        if (myBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }

    }

    public void BotaoIniciar(View view){

        if(nomeRecebido) {
            Intent intent = new Intent(this, Main2Activity.class);
            EditText enviar = (EditText) findViewById(R.id.nome1);
            String mensagem = enviar.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, mensagem);

            intent.putExtra(EXTRA_MESSAGE2, nome2);
            startActivity(intent);
        }
    }

    public void enviarNome(View view){
        if(mBtService != null){
            sendMessage(enviar.getText().toString());
        }

    }

    private void setupConnection() {


        // Inicialização dos serviços relacionados à comunicação bluetooth
        mBtService = new BluetoothService(this, mHandler);
    }
    public static void sendMessage(String message) {
        // Verifica se há conexão
        if (mBtService.getState() != BluetoothService.STATE_CONNECTED) {
            Toast.makeText(MainActivity.context, "Não há conexão estabelecida!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifica se há informação para ser enviada
        if (message.length() > 0) {
            // Pega a string passada como parâmetro e transforma em um vetor de bytes
            byte[] send = message.getBytes();
            // Passa o vetor de bytes para o método de envio da classe bluetooth
            mBtService.write(send);
            // Limpa a mensagem (para evitar envios errados)
            message="";
        }
    }
    // Método para obter as respostas das chamadas startActivityForResult()
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case REQUEST_CONNECT_DEVICE:     // Chamado qndo o usuário clica em connect no menu
                // Quando a classe ListDevice retorna um dispositivo para se conectar
                if (resultCode == Activity.RESULT_OK) {
                    // Recebimento do MAC
                    String address = data.getExtras().getString(DeviceList.EXTRA_DEVICE_ADDRESS);
                    // Recebe o objeto do dispositivo a se conectar
                    BluetoothDevice device = myBluetoothAdapter.getRemoteDevice(address);
                    // Tenta estabelecer uma conexão
                    mBtService.connect(device);
                }
                break;

            case REQUEST_ENABLE_BT:      // Retorno da requisição de habilitar bt

                if (resultCode == Activity.RESULT_OK) {
                    // BT ligado, prepara para conexões
                    setupConnection();
                } else {
                    // Caso usuário não habilite o bt, encerra o aplicativo
                    Toast.makeText(this, "Bluetooth não ativado...", Toast.LENGTH_SHORT).show();
                    finish();
                }

        }
    }


    @Override
    public synchronized void onResume() {
        super.onResume();
		/*
		 * Caso o BT não tenha sido ligado no inicio,
		 * deve ser habilitado nesse momento.
		 * onResume() é executada após o retorno da
		 * requisição de ativação do adaptador
		 */
        if (mBtService != null) {
            // Estado NONE idica que as threads de conexão ainda não foram iniciadas
            if (mBtService.getState() == BluetoothService.STATE_NONE) {
                // Inicializa as threads necessárias para comunicação -> Ver BluetoothService.java
                mBtService.start();
            }
        }
    }

    private final Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:      // Se houve troca no estado da conexão
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED: // Se o estado passou para conectado
                            Toast.makeText(getApplicationContext(),"Connected to "+ mConnectedDeviceName,
                                    Toast.LENGTH_LONG).show();

                            break;
                        case BluetoothService.STATE_CONNECTING: // Se o estado passou para conectando
                            Toast.makeText(getApplicationContext(),"Connecting...",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
                            break;
                    }
                    break;

                case MESSAGE_WRITE:     // Se está enviando uma mensagem
                    // Coloca que a mensagem foi enviada no textView

                    byte[] writeBuf = (byte[]) msg.obj;
                    String writeMessage = new String(writeBuf);
                    break;

                case MESSAGE_READ:      // Se está recebendo uma mensagem
                    // Obtém a mensagem recebida
                    byte[] readBuf = (byte[]) msg.obj;
                    String readMessage = new String(readBuf, 0, msg.arg1);

                    // Coloca a mensagem recebida no text view
                    if(!nomeRecebido) {
                        Toast.makeText(getBaseContext(), "Seu oponente: " + readMessage, Toast.LENGTH_SHORT).show();
                        nome2 = readMessage;
                        nomeRecebido = true;
                    }
                    else if (readMessage.length() <= 4 ){
                        String[] parts = readMessage.split(" ");
                        int x = Integer.parseInt(parts[0]);
                        int y = Integer.parseInt(parts[1]);
                        Main2Activity.markB(x,y);                    }
                    else if(readMessage.equals("##########")){
                        Main2Activity.vamos2(context);
                    }

                    break;


                case MESSAGE_DEVICE_NAME:       // Se está recebendo o nome do dispositivo
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), "Connected to "
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;

                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
