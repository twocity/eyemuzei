package com.twocity.eyemuzei.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import com.twocity.eyemuzei.App;
import com.twocity.eyemuzei.R;
import com.twocity.eyemuzei.Utils;
import com.twocity.eyemuzei.data.UserManager;
import com.twocity.eyemuzei.data.api.EyeEmService;
import com.twocity.eyemuzei.data.model.AccessToken;
import java.net.MalformedURLException;
import java.net.URL;
import javax.inject.Inject;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.twocity.eyemuzei.data.DefaultConfig.AUTHORIZE_URL;
import static com.twocity.eyemuzei.data.DefaultConfig.CALLBACK_URL;
import static com.twocity.eyemuzei.data.DefaultConfig.CLIENT_ID;
import static com.twocity.eyemuzei.data.DefaultConfig.CLIENT_SECRET;

/**
 * Created by twocity on 14-8-8.
 */
public class AuthorizeActivity extends Activity {
  private Button authorizeButton;
  private WebView authorizeWebView;
  private View webviewContainer;
  private View welcomeContainer;
  private ProgressBar progressBar;
  private ProgressDialog progressDialog;

  @Inject UserManager userManager;
  @Inject EyeEmService eyeEmService;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_setting);
    App.get(this).inject(this);

    if (userManager.hasUser()) {
      startSettingsActivity();
    } else {
      initViews();
      showAuthorizeButton();
    }
  }

  private void initViews() {
    welcomeContainer = findViewById(R.id.welcome_container);
    webviewContainer = findViewById(R.id.webview_container);
    progressBar = (ProgressBar) findViewById(R.id.progressbar);
    authorizeButton = (Button) findViewById(R.id.authorize_button);
    authorizeWebView = (WebView) findViewById(R.id.authorize_webview);

    authorizeButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        showWebView();
        startAuthorizing();
      }
    });
  }

  private void setupWebView() {
    WebViewClient webViewClient = new WebViewClient() {
      @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (!TextUtils.isEmpty(url) && url.startsWith(CALLBACK_URL)) {
          showAuthorizeButton();
          try {
            URL u = new URL(url);
            Bundle args = Utils.decodeUrl(u.getQuery());
            if (args != null && args.containsKey("code")) {
              authorize(args.getString("code"));
            }
          } catch (MalformedURLException e) {
            e.printStackTrace();
          }
        }
      }
    };
    authorizeWebView.setWebChromeClient(new WebChromeClient() {
      public void onProgressChanged(WebView view, int progress) {
        progressBar.setProgress(progress);
      }
    });

    authorizeWebView.setWebViewClient(webViewClient);
  }

  private void authorize(String code) {
    showAuthorizingDialog();
    eyeEmService.authorize("authorization_code", CLIENT_ID, CLIENT_SECRET, CALLBACK_URL, code,
        new Callback<AccessToken>() {
          @Override public void success(AccessToken accessToken, Response response) {
            dismissAuthorizingDialog();
            userManager.setAccessToken(accessToken.getAccessToken(), accessToken.getTokenType());
            App.get(AuthorizeActivity.this).buildObjectGraph();
            App.get(AuthorizeActivity.this).inject(AuthorizeActivity.this);
            startSettingsActivity();
          }

          @Override public void failure(RetrofitError error) {
            dismissAuthorizingDialog();
          }
        }
    );
  }

  private void showAuthorizingDialog() {
    if (progressDialog == null) {
      progressDialog = new ProgressDialog(this);
      progressDialog.setIndeterminate(true);
      progressDialog.setMessage("Authorizing...");
      progressDialog.setCanceledOnTouchOutside(false);
    }
    progressDialog.show();
  }

  private void dismissAuthorizingDialog() {
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  private void startAuthorizing() {
    setupWebView();
    String url = String.format(AUTHORIZE_URL, CLIENT_ID, CALLBACK_URL);
    authorizeWebView.loadUrl(url);
  }

  private void showAuthorizeButton() {
    welcomeContainer.setVisibility(View.VISIBLE);
    webviewContainer.setVisibility(View.GONE);
  }

  private void showWebView() {
    welcomeContainer.setVisibility(View.GONE);
    webviewContainer.setVisibility(View.VISIBLE);
  }

  private void startSettingsActivity() {
    Intent intent = new Intent(this, SettingsActivity.class);
    startActivity(intent);
    finish();
  }
}