package com.twocity.eyemuzei.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by twocity on 14-8-9.
 */
public class EyeEmPhotos {
  private int limit;
  private int offset;
  private int total;

  @SerializedName("items")
  private List<EyeEmPhoto> photos;

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }

  public int getOffset() {
    return offset;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public List<EyeEmPhoto> getPhotos() {
    return photos;
  }

  public void setPhotos(List<EyeEmPhoto> photos) {
    this.photos = photos;
  }
}
