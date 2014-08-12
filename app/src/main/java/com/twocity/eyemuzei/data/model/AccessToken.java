package com.twocity.eyemuzei.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by twocity on 14-8-9.
 */
public class AccessToken {
  @SerializedName("access_token")
  private String accessToken;
  @SerializedName("expires_in")
  private long expiresIn;
  @SerializedName("token_type")
  private String tokenType;

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public long getExpiresIn() {
    return expiresIn;
  }

  public void setExpiresIn(long expiresIn) {
    this.expiresIn = expiresIn;
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }
}
