package com.example.mobileappfinal;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class chat_bot_MessageAdapter extends BaseAdapter {
    private Context context;
    private List<chat_bot_Message> messages;
    public chat_bot_MessageAdapter(Context context, List<chat_bot_Message> messages) {
        this.context = context;
        this.messages = messages;
    }
    @Override
    public int getCount() { return messages.size(); }
    @Override
    public Object getItem(int i) { return messages.get(i); }
    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        chat_bot_Message msg = messages.get(position);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.chat_bot_activity_list_items, parent, false);
        }

        TextView textView = view.findViewById(R.id.messageText);
        LinearLayout layout = view.findViewById(R.id.list_items);

        textView.setText(msg.content);

        if (msg.isUser) {
            textView.setBackgroundResource(R.drawable.chat_bot_user_chat_background);
            textView.setTextColor(Color.WHITE);
            layout.setGravity(Gravity.END); // Right align
        } else {
            textView.setBackgroundResource(R.drawable.chatbot_chat_background);
            layout.setGravity(Gravity.START); // left align
        }
        return view;
    }

}
