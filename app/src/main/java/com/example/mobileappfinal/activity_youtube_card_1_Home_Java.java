package com.example.mobileappfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class activity_youtube_card_1_Home_Java extends AppCompatActivity {

    private WebView youtubeWebView;
    private EditText searchInput;
    private FloatingActionButton fabBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_card_1_home);

        searchInput = findViewById(R.id.search_input);
        youtubeWebView = findViewById(R.id.youtube_webview);
        fabBack = findViewById(R.id.fab_back);

        setupWebView();
        loadYouTubeSearch("Plant care basics"); // default search on launch

        searchInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER &&
                            event.getAction() == KeyEvent.ACTION_DOWN)) {

                String query = searchInput.getText().toString().trim();
                if (!query.isEmpty()) {
                    loadYouTubeSearch(query);
                } else {
                    Toast.makeText(this, "Please enter a plant name", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });

        fabBack.setOnClickListener(v -> {
            Intent intent = new Intent(activity_youtube_card_1_Home_Java.this, Activity_Host_Java.class);
            startActivity(intent);
        });
    }

    private void setupWebView() {
        WebSettings settings = youtubeWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);

        youtubeWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false; // load URLs within WebView
            }
        });
    }

    private void loadYouTubeSearch(String plantName) {
        String fullQuery = plantName + " plant information, growth, diseases prevention and solution";
        String searchUrl = "https://www.youtube.com/results?search_query=" + fullQuery.replace(" ", "+");
        youtubeWebView.loadUrl(searchUrl);
    }
}
