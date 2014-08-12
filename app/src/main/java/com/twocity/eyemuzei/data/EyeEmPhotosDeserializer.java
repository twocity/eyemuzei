package com.twocity.eyemuzei.data;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.twocity.eyemuzei.data.model.EyeEmPhotos;
import java.lang.reflect.Type;

/**
 * Created by twocity on 14-8-10.
 */
public class EyeEmPhotosDeserializer implements JsonDeserializer<EyeEmPhotos> {
  @Override public EyeEmPhotos deserialize(JsonElement json, Type typeOfT,
      JsonDeserializationContext context) throws JsonParseException {
    Gson gson = new Gson();
    JsonObject jsonObject = json.getAsJsonObject();
    if (jsonObject.has("likedPhotos")) {
      return gson.fromJson(jsonObject.getAsJsonObject("likedPhotos"), EyeEmPhotos.class);
    } else if (jsonObject.has("photos")) {
      return gson.fromJson(jsonObject.getAsJsonObject("photos"), EyeEmPhotos.class);
    } else {
      return new EyeEmPhotos();
    }
  }
}