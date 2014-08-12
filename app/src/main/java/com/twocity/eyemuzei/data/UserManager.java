package com.twocity.eyemuzei.data;

import android.content.SharedPreferences;
import android.text.TextUtils;

import static com.twocity.eyemuzei.data.DefaultConfig.ACCESS_TOKEN_KEY;
import static com.twocity.eyemuzei.data.DefaultConfig.ACCESS_TOKEN_TYPE_KEY;

/**
 * Created by twocity on 14-8-9.
 */
public class UserManager {
  private final SharedPreferences preferences;

  public UserManager(SharedPreferences sharedPreferences) {
    this.preferences = sharedPreferences;
  }

  public boolean hasUser() {
    if (!TextUtils.isEmpty(getAccessToken()) && !TextUtils.isEmpty(getAccessTokenType())) {
      return true;
    }
    return false;
  }

  public void setAccessToken(String token, String tokenType) {
    SharedPreferences.Editor editor = preferences.edit();
    editor.putString(ACCESS_TOKEN_KEY, token);
    editor.putString(ACCESS_TOKEN_TYPE_KEY, tokenType);
    editor.apply();
  }

  public String getAccessToken() {
    return preferences.getString(ACCESS_TOKEN_KEY, "");
  }

  public String getAccessTokenType() {
    return preferences.getString(ACCESS_TOKEN_TYPE_KEY, "");
  }
}
