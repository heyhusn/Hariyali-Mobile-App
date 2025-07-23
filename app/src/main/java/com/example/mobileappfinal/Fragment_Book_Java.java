package com.example.mobileappfinal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_Book_Java extends Fragment {

    private EditText searchInput;
    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);

        searchInput = view.findViewById(R.id.article_search_input);
        webView = view.findViewById(R.id.google_webview);

        setupWebView();
        loadSearch("Plant care basics");

        searchInput.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER &&
                            event.getAction() == KeyEvent.ACTION_DOWN)) {

                String query = searchInput.getText().toString().trim();
                if (!query.isEmpty()) {
                    loadSearch(query);
                } else {
                    Toast.makeText(getContext(), "Please enter a plant name", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });

        return view;
    }

    private void setupWebView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
    }

    private void loadSearch(String plantName) {
        String query = plantName + " plant information, growth, diseases prevention and solution";
        String url = "https://www.google.com/search?q=" + query.replace(" ", "+");
        webView.loadUrl(url);
    }
}