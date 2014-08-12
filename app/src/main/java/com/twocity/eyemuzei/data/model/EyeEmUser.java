package com.twocity.eyemuzei.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by twocity on 14-8-9.
 */
public class EyeEmUser {
  private long id;
  @SerializedName("nickname")
  private String nickName;
  @SerializedName("fullname")
  private String fullName;
  private String thumbUrl;
  private String webUrl;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getThumbUrl() {
    return thumbUrl;
  }

  public void setThumbUrl(String thumbUrl) {
    this.thumbUrl = thumbUrl;
  }

  public String getWebUrl() {
    return webUrl;
  }

  public void setWebUrl(String webUrl) {
    this.webUrl = webUrl;
  }
}
