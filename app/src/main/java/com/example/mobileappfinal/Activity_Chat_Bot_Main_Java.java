package com.example.mobileappfinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_Chat_Bot_Main_Java extends AppCompatActivity {

    EditText editTextPrompt;
    ImageButton button,bckbtn;
    ListView chatListView;

    ArrayList<Class_Chat_Bot_Message> chatMessages;
    Class_Chat_Bot_MessageAdapter adapter;

    String url = "https://api.groq.com/openai/v1/chat/completions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot_main);

        InitCompnents();
        SetInAdapter();
        Clicker();
    }
    private void InitCompnents()
    {
        editTextPrompt = findViewById(R.id.editTextPrompt);
        button = findViewById(R.id.button);
        chatListView = findViewById(R.id.chatListView);
        bckbtn = findViewById(R.id.backbutton);
    }


    private void SetInAdapter()
    {
        chatMessages = new ArrayList<>();
        adapter = new Class_Chat_Bot_MessageAdapter(this, chatMessages);
        chatListView.setAdapter(adapter);
    }


    private void Clicker()
    {
        button.setOnClickListener(v -> {
            String promptText = editTextPrompt.getText().toString().trim();
            if (!promptText.isEmpty()) {
                chatMessages.add(new Class_Chat_Bot_Message(promptText, true));
                adapter.notifyDataSetChanged();
                editTextPrompt.setText("");
                sendRequest(promptText);
            }
        });

        bckbtn.setOnClickListener(v -> {
            startActivity(new Intent(this, Activity_Host_Java.class));
        });
    }

    private void sendRequest(String promptText) {
        RequestQueue queue = Volley.newRequestQueue(this);
        try {
            // Due to post
            JSONObject Body = new JSONObject();
            Body.put("model", "llama3-8b-8192");

            JSONArray messagesArray = new JSONArray();
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", promptText);
            messagesArray.put(message);

            Body.put("messages", messagesArray);
            Body.put("temperature", 0.7);

            JsonObjectRequest jsonRequest = new JsonObjectRequest( Request.Method.POST, url, Body,  response ->
            {
                try {
                    String reply = response
                            .getJSONArray("choices")
                            .getJSONObject(0)
                            .getJSONObject("message")
                            .getString("content");
                    //Update List
                    chatMessages.add(new Class_Chat_Bot_Message(reply.trim(), false));
                    adapter.notifyDataSetChanged();
                }
                catch (Exception e) {
                    chatMessages.add(new Class_Chat_Bot_Message("Error parsing response.", false));
                    adapter.notifyDataSetChanged();
                }
            },
                    error -> {
                        chatMessages.add(new Class_Chat_Bot_Message("Error: " + error.toString(), false));
                        adapter.notifyDataSetChanged();
                    })  {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };
            queue.add(jsonRequest);
        }
        catch (Exception e) {
            chatMessages.add(new Class_Chat_Bot_Message("Failed to send request.", false));
            adapter.notifyDataSetChanged();
        }
    }
}




