package com.chengxinping.intelligentrobot.Activity;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.chengxinping.intelligentrobot.R;
import com.chengxinping.intelligentrobot.bean.ChatMessage;

import com.chengxinping.intelligentrobot.utils.HttpUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mMsgs;
    private ChatMessageAdapter mAdappter;
    private List<ChatMessage> mDatas;

    private EditText mInputMsg;
    private Button mSendMsg;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //等待接收,子线程完成数据返回
            ChatMessage fromMessage = (ChatMessage) msg.obj;
            mDatas.add(fromMessage);
            mAdappter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initView();
        initDatas();
        //初始化事件
        initListener();
        mMsgs.setSelection(mMsgs.getCount() - 1);
        initListListener();//长按监听
    }

    private void initListListener() {
        mMsgs.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setPositiveButton("复制", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ClipboardManager cpm = (ClipboardManager) getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
                        ChatMessage message = mDatas.get(position);
                        cpm.setText(message.getMsg());
                        Toast.makeText(getApplication(), "文本已负责到粘贴版", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("删除聊天记录", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatas.removeAll(mDatas);
                        mAdappter.notifyDataSetChanged();
                    }
                });
                builder.show();
                return false;
            }
        });
    }


    private void initListener() {
        mSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String toMsg = mInputMsg.getText().toString();
                if (TextUtils.isEmpty(toMsg)) {
                    Toast.makeText(MainActivity.this, "发送消息不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                ChatMessage toMessage = new ChatMessage();
                toMessage.setDate(new Date());
                toMessage.setMsg(toMsg);
                toMessage.setType(ChatMessage.Type.OUTCOMING);
                mDatas.add(toMessage);
                mAdappter.notifyDataSetChanged();
                mInputMsg.setText("");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ChatMessage fromMessage = HttpUtils.sendMessage(toMsg);
                        Message m = Message.obtain();
                        m.obj = fromMessage;
                        mHandler.sendMessage(m);
                    }
                }).start();

            }
        });
    }

    @SuppressLint("SimpleDateFormat")
    private void initDatas() {
        mDatas = new ArrayList<ChatMessage>();
        ChatMessage initMessage = new ChatMessage(new Date(), ChatMessage.Type.INCOMING, "你好，我是大格");
        mDatas.add(initMessage);
        mAdappter = new ChatMessageAdapter(this, mDatas);
        mMsgs.setAdapter(mAdappter);
    }

    private void initView() {
        mMsgs = (ListView) findViewById(R.id.id_listview_msgs);
        mInputMsg = (EditText) findViewById(R.id.id_input_msg);
        mSendMsg = (Button) findViewById(R.id.id_send_msg);
    }
}
