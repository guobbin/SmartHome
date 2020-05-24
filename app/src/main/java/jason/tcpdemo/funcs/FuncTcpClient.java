package jason.tcpdemo.funcs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;

import jason.tcpdemo.R;
import jason.tcpdemo.coms.TcpClient;
import jason.tcpdemo.widget.CustomDatePicker;


/**
 * Created by Jason Zhu on 2017-04-24.
 * Email: cloud_happy@163.com
 */

public class FuncTcpClient extends Activity {
    private String TAG = "FuncTcpClient";
    @SuppressLint("StaticFieldLeak")
    public static Context context ;
    private Button btnStartClient,btnCloseClient, btnCleanClientSend, btnCleanClientRcv,btnClientRandom;
    private Button btnClientSend;
    private Button btnClientSend2,btnClientSend3,btnClientSend4;
    private TextView txtRcv,txtSend;
    private EditText editClientSend,editClientID, editClientPort,editClientIp;
    private static TcpClient tcpClient = null;
    private MyBtnClicker myBtnClicker = new MyBtnClicker();
    private final MyHandler myHandler = new MyHandler(this);
    private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
    ExecutorService exec = Executors.newCachedThreadPool();
//
    private RelativeLayout selectDate, selectTime;
    private TextView currentDate, currentTime;
    private CustomDatePicker customDatePicker1, customDatePicker2;



    private class MyBtnClicker implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_tcpClientConn:
                    Toast.makeText(FuncTcpClient.this, "连接成功", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onClick: 开始");
                    btnStartClient.setEnabled(false);
                    btnCloseClient.setEnabled(true);
                    btnClientSend.setEnabled(true);
                    btnClientSend2.setEnabled(true);
                    btnClientSend3.setEnabled(true);
                    btnClientSend4.setEnabled(true);

                    btnClientSend2.setEnabled(true);
                    btnClientSend3.setEnabled(true);
                    btnClientSend4.setEnabled(true);
                    tcpClient = new TcpClient(editClientIp.getText().toString(),getPort(editClientPort.getText().toString()));
                    exec.execute(tcpClient);
                    break;
                case R.id.btn_tcpClientClose:
                    tcpClient.closeSelf();
                    btnStartClient.setEnabled(true);
                    btnCloseClient.setEnabled(false);
                    btnClientSend.setEnabled(false);
                    btnClientSend2.setEnabled(false);
                    btnClientSend3.setEnabled(false);
                    btnClientSend4.setEnabled(false);
                    Toast.makeText(FuncTcpClient.this, "连接断开", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_tcpCleanClientRecv:
                    txtRcv.setText("");
                    break;
                case R.id.btn_tcpCleanClientSend:
                    txtSend.setText("");
                    break;
                case R.id.btn_tcpClientRandomID:
                    break;
                case R.id.btn_tcpClientSend:
                    Message message = Message.obtain();
                    message.what = 2;
                   final String s = "1+";
                    message.obj = s;
                   // message.obj = editClientSend.getText().toString();
                    myHandler.sendMessage(message);
                    exec.execute(new Runnable() {
                        @Override
                        public void run() {
                            //tcpClient.send(editClientSend.getText().toString());
                            tcpClient.send(s);
                        }
                    });
                    break;

                 case R.id.btn_tcpClientSend2:
                    Message message2 = Message.obtain();
                    message2.what = 2;
                     final String s2 = "2";
                     message2.obj = s2;
                     //message.obj = editClientSend.getText().toString();
                     myHandler.sendMessage(message2);
                     exec.execute(new Runnable() {
                         @Override
                         public void run() {
                             //tcpClient.send(editClientSend.getText().toString());
                             tcpClient.send(s2);
                         }
                     });
                    break;
                case R.id.btn_tcpClientSend3:
                    Message message3 = Message.obtain();
                    message3.what = 2;
                    final String s3 = "3";
                    message3.obj = s3;
                    //message.obj = editClientSend.getText().toString();
                    myHandler.sendMessage(message3);
                    exec.execute(new Runnable() {
                        @Override
                        public void run() {
                            //tcpClient.send(editClientSend.getText().toString());
                            tcpClient.send(s3);
                        }
                    });
                    break;
                case R.id.btn_tcpClientSend4:
                    Message message4 = Message.obtain();
                    message4.what = 2;
                    final String s4 = "4";
                    message4.obj = s4;
                    //message.obj = editClientSend.getText().toString();
                    myHandler.sendMessage(message4);
                    exec.execute(new Runnable() {
                        @Override
                        public void run() {
                            //tcpClient.send(editClientSend.getText().toString());
                            tcpClient.send(s4);
                        }
                    });
                    break;

                case R.id.selectDate:
                    // 日期格式为yyyy-MM-dd
                    customDatePicker1.show(currentDate.getText().toString());
                    break;

                case R.id.selectTime:
                    // 日期格式为yyyy-MM-dd HH:mm
                    customDatePicker2.show(currentTime.getText().toString());
                    break;
            }
        }
    }

    private class MyHandler extends android.os.Handler{
        private WeakReference<FuncTcpClient> mActivity;

        MyHandler(FuncTcpClient activity){
            mActivity = new WeakReference<FuncTcpClient>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity != null){
                switch (msg.what){
                    case 1:
                        txtRcv.setText("");
                        txtRcv.append(msg.obj.toString());
                        break;
                    case 2:
                        txtSend.append(msg.obj.toString());
                        break;
                }
            }
        }
    }

    private class MyBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String mAction = intent.getAction();
            switch (mAction){
                case "tcpClientReceiver":
                    String msg = intent.getStringExtra("tcpClientReceiver");
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = msg;
                    myHandler.sendMessage(message);
                    break;
            }
        }
    }


    private int getPort(String msg){
        if (msg.equals("")){
            msg = "1234";
        }
        return Integer.parseInt(msg);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tcp_client);
        context = this;
        bindID();
        bindListener();
        initDatePicker();
        bindReceiver();
        Ini();
    }



    private void bindID(){
        btnStartClient = (Button) findViewById(R.id.btn_tcpClientConn);
        btnCloseClient = (Button) findViewById(R.id.btn_tcpClientClose);
        btnCleanClientRcv = (Button) findViewById(R.id.btn_tcpCleanClientRecv);
        btnCleanClientSend = (Button) findViewById(R.id.btn_tcpCleanClientSend);
        btnClientRandom = (Button) findViewById(R.id.btn_tcpClientRandomID);
        btnClientSend = (Button) findViewById(R.id.btn_tcpClientSend);

        btnClientSend2 = (Button) findViewById(R.id.btn_tcpClientSend2);

        btnClientSend3 = (Button) findViewById(R.id.btn_tcpClientSend3);

        btnClientSend4 = (Button) findViewById(R.id.btn_tcpClientSend4);


        editClientPort = (EditText) findViewById(R.id.edit_tcpClientPort);
        editClientIp = (EditText) findViewById(R.id.edit_tcpClientIp);
        editClientSend = (EditText) findViewById(R.id.edit_tcpClientSend);
        txtRcv = (TextView) findViewById(R.id.txt_ClientRcv);
        txtSend = (TextView) findViewById(R.id.txt_ClientSend);

        selectTime = (RelativeLayout) findViewById(R.id.selectTime);

        selectDate = (RelativeLayout) findViewById(R.id.selectDate);

        currentDate = (TextView) findViewById(R.id.currentDate);
        currentTime = (TextView) findViewById(R.id.currentTime);


    }
    private void bindListener(){
        btnStartClient.setOnClickListener(myBtnClicker);
        btnCloseClient.setOnClickListener(myBtnClicker);
        btnCleanClientRcv.setOnClickListener(myBtnClicker);
        btnCleanClientSend.setOnClickListener(myBtnClicker);
        btnClientRandom.setOnClickListener(myBtnClicker);
        btnClientSend.setOnClickListener(myBtnClicker);
        btnClientSend2.setOnClickListener(myBtnClicker);
        btnClientSend3.setOnClickListener(myBtnClicker);
        btnClientSend4.setOnClickListener(myBtnClicker);

        selectTime.setOnClickListener(myBtnClicker);
        selectDate.setOnClickListener(myBtnClicker);


    }
    private void bindReceiver(){
        IntentFilter intentFilter = new IntentFilter("tcpClientReceiver");
        registerReceiver(myBroadcastReceiver,intentFilter);
    }
    private void Ini(){
        btnCloseClient.setEnabled(false);
        btnClientSend.setEnabled(false);
        btnClientSend2.setEnabled(false);
        btnClientSend3.setEnabled(false);
        btnClientSend4.setEnabled(false);

    }
    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        currentDate.setText(now.split(" ")[0]);
        currentTime.setText(now);

        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentDate.setText(time.split(" ")[0]);
            }
        }, now,"2021-12-31 23:59"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker1.showSpecificTime(false); // 不显示时和分
        customDatePicker1.setIsLoop(false); // 不允许循环滚动

        customDatePicker2 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                currentTime.setText(time);
            }
        }, now,"2021-12-31 23:59"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker2.showSpecificTime(true); // 显示时和分
        customDatePicker2.setIsLoop(true); // 允许循环滚动
    }

}
