package com.twocity.eyemuzei.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by twocity on 14-8-9.
 */
public class EyeEmPhoto {
  private long id;
  private String thumbUrl;
  private String photoUrl;
  private String webUrl;
  private String title;
  private String caption;
  @SerializedName("user")
  private EyeEmUser author;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getThumbUrl() {
    return thumbUrl;
  }

  public void setThumbUrl(String thumbUrl) {
    this.thumbUrl = thumbUrl;
  }

  public String getPhotoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }

  public String getWebUrl() {
    return webUrl;
  }

  public void setWebUrl(String webUrl) {
    this.webUrl = webUrl;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCaption() {
    return caption;
  }

  public void setCaption(String caption) {
    this.caption = caption;
  }

  public EyeEmUser getAuthor() {
    return author;
  }

  public void setAuthor(EyeEmUser author) {
    this.author = author;
  }
}
