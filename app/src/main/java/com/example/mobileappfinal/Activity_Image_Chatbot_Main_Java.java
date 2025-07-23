package com.example.mobileappfinal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class Activity_Image_Chatbot_Main_Java extends AppCompatActivity {

    ImageView imageView;
    Button sendBtn, backBtn;
    TextView responseText;
    Uri imageUri;

    String groqUrl = "https://api.groq.com/openai/v1/chat/completions";
 String prompt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_chatbot_main);

        imageView = findViewById(R.id.image);
        sendBtn = findViewById(R.id.send_btn);
        backBtn = findViewById(R.id.back_btn);
        responseText = findViewById(R.id.responce);

        String image = getIntent().getStringExtra("imageUri");
        if (image != null) {
            imageUri = Uri.parse(image);
            imageView.setImageURI(imageUri);
        }

        prompt = getIntent().getStringExtra("prompt");
        clicker();
    }



    private void clicker()
    {
        sendBtn.setOnClickListener(v -> {
            String base64Image = convertImageToBase64(imageUri);
            if (base64Image != null) {
                sendData(base64Image);
            } else {
                Toast.makeText(this, "Image conversion failed", Toast.LENGTH_SHORT).show();
            }
        });

        backBtn.setOnClickListener(v -> {
           startActivity(new Intent(this, Activity_Host_Java.class));
        });

    }



    private String convertImageToBase64(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 512, 512, true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            byte[] byteArray = stream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.NO_WRAP);
        } catch (Exception e) {
            return null;
        }
    }

    private void sendData(String base64Image) {
        try {
            RequestQueue queue = Volley.newRequestQueue(this);

            JSONArray array = new JSONArray();
            array.put(new JSONObject()
                    .put("type", "text")
                    .put("text", prompt));

            array.put(new JSONObject()
                    .put("type", "image_url")
                    .put("image_url", new JSONObject().put("url", "data:image/jpeg;base64," + base64Image)));

            JSONArray messages = new JSONArray();
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", array);
            messages.put(message);

            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "meta-llama/llama-4-scout-17b-16e-instruct");
            requestBody.put("messages", messages);
            requestBody.put("temperature", 1);
            requestBody.put("max_tokens", 1024);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, groqUrl, requestBody,
                    response -> {
                        try {
                            JSONArray choices = response.getJSONArray("choices");
                            if (choices.length() > 0) {
                                JSONObject replayobject = choices.getJSONObject(0);
                                JSONObject answer = replayobject.getJSONObject("message");
                                String reply = answer.getString("content");
                                responseText.setText(reply);
                            } else {
                                responseText.setText("No response from model.");
                            }
                        } catch (Exception e) {
                            responseText.setText("Parsing error: " + e.getMessage());
                        }
                    },
                    error -> {
                        responseText.setText("API Error: " + error.toString());
                    }
            ) {
                @Override
                public java.util.Map<String, String> getHeaders() {
                    java.util.Map<String, String> headers = new java.util.HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            queue.add(request);

        } catch (Exception e) {
            responseText.setText("Unexpected error");
        }
    }
}
