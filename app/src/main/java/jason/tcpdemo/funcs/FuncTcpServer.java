package jason.tcpdemo.funcs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jason.tcpdemo.R;
import jason.tcpdemo.coms.TcpServer;
import jason.tcpdemo.widget.CustomDatePicker;

/**
 * Created by Jason Zhu on 2017-04-24.
 * Email: cloud_happy@163.com
 */

public class FuncTcpServer extends Activity {
    private Button btnStartServer,btnCloseServer, btnCleanServerSend, btnCleanServerRcv,btnServerRandom;private Button  btnServerSend;
    private Button  btnServerSend2;
    private Button  btnServerSend3;
    private Button  btnServerSend4;
    private TextView txtSend,txtServerIp;

    private TextView txtRcv;

    private TextView temp;
    private TextView shidu;
    private EditText editServerSend,editServerRandom, editServerPort;
    private static TcpServer tcpServer = null;
    private MyBtnClicker myBtnClicker = new MyBtnClicker();
    private final MyHandler myHandler = new MyHandler(this);
    private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    ExecutorService exec = Executors.newCachedThreadPool();
//
    private RelativeLayout selectDate, selectTime;
    private TextView currentDate, currentTime;
    private CustomDatePicker customDatePicker1, customDatePicker2;

    private class MyHandler extends android.os.Handler{
        private final WeakReference<FuncTcpServer> mActivity;
        MyHandler(FuncTcpServer activity){
            mActivity = new WeakReference<FuncTcpServer>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            FuncTcpServer activity = mActivity.get();
            if (activity!= null){
                switch (msg.what){
                    case 1:
                        String s = msg.obj.toString();
                        if(s.length()<10){
                            txtRcv.setText("");
                            txtRcv.append(msg.obj.toString());
                        }



                       if(s.length() <= 10 && s.charAt(2)==' '){
                            String s2[] = s.split(" ");
                            temp.setText(s2[0]+"℃");
//                            temp.append(" "+s2.length);
                            shidu.setText(s2[2]+"%");
                        }
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
                case "tcpServerReceiver":
                    String msg = intent.getStringExtra("tcpServerReceiver");
                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = msg;
                    myHandler.sendMessage(message);
                    break;
            }
        }
    }

    private void bindReceiver(){
        IntentFilter intentFilter = new IntentFilter("tcpServerReceiver");
        registerReceiver(myBroadcastReceiver,intentFilter);
    }

    private class MyBtnClicker implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Message message = null;
            switch (view.getId()){
                case R.id.btn_tcpServerConn:
                    Log.i("A", "onClick: 开始");
                    btnStartServer.setEnabled(false);
                    btnCloseServer.setEnabled(true);
                    btnServerSend.setEnabled(true);
                    btnServerSend2.setEnabled(true);
                    btnServerSend3.setEnabled(true);
                    btnServerSend4.setEnabled(true);

                    tcpServer = new TcpServer(getHost(editServerPort.getText().toString()));
                    exec.execute(tcpServer);
                    break;
                case R.id.btn_tcpServerClose:
                    tcpServer.closeSelf();
                    btnStartServer.setEnabled(true);
                    btnCloseServer.setEnabled(false);
                    btnServerSend.setEnabled(false);
                    btnServerSend2.setEnabled(false);
                    btnServerSend3.setEnabled(false);
                    btnServerSend4.setEnabled(false);
                    break;
                case R.id.btn_tcpCleanServerRecv:
                    txtRcv.setText("");
                    break;
                case R.id.btn_tcpCleanServerSend:
                    txtSend.setText("");
                    break;
                case R.id.btn_tcpServerRandomID:
                    break;
                case R.id.btn_tcpServerSend:
                    message = Message.obtain();
                    message.what = 2;
                    final String s = "+123456711";
//                    message.obj = editServerSend.getText().toString();
                    message.obj = s;
                    myHandler.sendMessage(message);
                    exec.execute(new Runnable() {
                        @Override
                        public void run() {
//                            tcpServer.SST.get(0).send(editServerSend.getText().toString());
                            tcpServer.SST.get(0).send(s);
                        }
                    });
                    break;
                case R.id.btn_tcpServerSend2:
                    message = Message.obtain();
                    message.what = 2;
                    final String s2 = "+123456722";
//                    message.obj = editServerSend.getText().toString();
                    message.obj = s2;
                    myHandler.sendMessage(message);
                    exec.execute(new Runnable() {
                        @Override
                        public void run() {
//                            tcpServer.SST.get(0).send(editServerSend.getText().toString());
                            tcpServer.SST.get(0).send(s2);
                        }
                    });
                    break;
                case R.id.btn_tcpServerSend3:
                    message = Message.obtain();
                    message.what = 2;
                    final String s3 = "+123456733";
//                    message.obj = editServerSend.getText().toString();
                    message.obj = s3;
                    myHandler.sendMessage(message);
                    exec.execute(new Runnable() {
                        @Override
                        public void run() {
//                            tcpServer.SST.get(0).send(editServerSend.getText().toString());
                            tcpServer.SST.get(0).send(s3);
                        }
                    });
                    break;
                case R.id.btn_tcpServerSend4:
                    message = Message.obtain();
                    message.what = 2;
                    final String s4 = "+123456744";
//                    message.obj = editServerSend.getText().toString();
                    message.obj = s4;
                    myHandler.sendMessage(message);
                    exec.execute(new Runnable() {
                        @Override
                        public void run() {
//                            tcpServer.SST.get(0).send(editServerSend.getText().toString());
                            tcpServer.SST.get(0).send(s4);
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

    private int getHost(String msg){
        if (msg.equals("")){
            msg = "1234";
        }
        return Integer.parseInt(msg);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tcp_server);
        context = this;
        bindID();
        bindListener();
        initDatePicker();
        bindReceiver();

        ini();
    }

    private void ini(){
        btnCloseServer.setEnabled(false);
        btnServerSend.setEnabled(false);
        btnServerSend2.setEnabled(false);
        btnServerSend3.setEnabled(false);
        btnServerSend4.setEnabled(false);
        txtServerIp.setText(getHostIP());


    }

    private void bindListener() {
        btnStartServer.setOnClickListener(myBtnClicker);
        btnCloseServer.setOnClickListener(myBtnClicker);
        btnCleanServerRcv.setOnClickListener(myBtnClicker);
        btnCleanServerSend.setOnClickListener(myBtnClicker);
        btnServerRandom.setOnClickListener(myBtnClicker);
        btnServerSend.setOnClickListener(myBtnClicker);
        btnServerSend2.setOnClickListener(myBtnClicker);
        btnServerSend3.setOnClickListener(myBtnClicker);
        btnServerSend4.setOnClickListener(myBtnClicker);


        selectTime.setOnClickListener(myBtnClicker);
        selectDate.setOnClickListener(myBtnClicker);
    }

    private void bindID() {
        btnStartServer = (Button) findViewById(R.id.btn_tcpServerConn);
        btnCloseServer = (Button) findViewById(R.id.btn_tcpServerClose);
        btnCleanServerRcv = (Button) findViewById(R.id.btn_tcpCleanServerRecv);
        btnCleanServerSend = (Button) findViewById(R.id.btn_tcpCleanServerSend);
        btnServerRandom = (Button) findViewById(R.id.btn_tcpServerRandomID);
        btnServerSend = (Button) findViewById(R.id.btn_tcpServerSend);
        btnServerSend2 = (Button) findViewById(R.id.btn_tcpServerSend2);
        btnServerSend3 = (Button) findViewById(R.id.btn_tcpServerSend3);
        btnServerSend4 = (Button) findViewById(R.id.btn_tcpServerSend4);
       txtRcv = (TextView) findViewById(R.id.txt_ServerRcv);


        txtSend = (TextView) findViewById(R.id.txt_ServerSend);
        txtServerIp = (TextView) findViewById(R.id.txt_Server_Ip);
        editServerRandom = (EditText) findViewById(R.id.edit_Server_ID);
        editServerSend = (EditText) findViewById(R.id.edit_tcpClientSend);
        editServerPort = (EditText)findViewById(R.id.edit_Server_Port);


        selectTime = (RelativeLayout) findViewById(R.id.selectTime);

        selectDate = (RelativeLayout) findViewById(R.id.selectDate);

        currentDate = (TextView) findViewById(R.id.currentDate);
        currentTime = (TextView) findViewById(R.id.currentTime);

        temp = (TextView) findViewById(R.id.Txv_tmp);
        shidu  = (TextView) findViewById(R.id.Txv_shidu);
    }

    /**
     * 获取ip地址
     * @return
     */
    public String getHostIP() {

        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.i("FuncTcpServer", "SocketException");
            e.printStackTrace();
        }
        return hostIp;

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
