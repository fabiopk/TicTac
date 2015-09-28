package com.example.fbio.tictactow;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
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

public class MainActivity extends Activity {

    public static final String EXTRA_MESSAGE = "com.example.fbio.tictactow.MESSAGE" ;
    public static final String EXTRA_MESSAGE2 = "com.example.fbio.tictactow.MESSAGE2";
    public static final String EXTRA_MESSAGE3 = "com.example.fbio.tictactow.MESSAGE3";

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1 = (Button)findViewById(R.id.iniciar);

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
        Intent intent = new Intent(this, Main2Activity.class);
        EditText enviar = (EditText)findViewById(R.id.nome1);
        String mensagem = enviar.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, mensagem);

        startActivity(intent);
    }

    private void setupConnection() {

        // Inicialização dos serviços relacionados à comunicação bluetooth
        mBtService = new BluetoothService(this, mHandler);
    }
    private void sendMessage(String message) {
        // Verifica se há conexão
        if (mBtService.getState() != BluetoothService.STATE_CONNECTED) {
            Toast.makeText(this, "Não há conexão estabelecida!", Toast.LENGTH_SHORT).show();
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
                   // msgTextView.setText(readMessage);
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
