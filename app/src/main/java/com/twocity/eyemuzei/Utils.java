package com.twocity.eyemuzei;

import android.os.Bundle;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by twocity on 14-8-9.
 */
public class Utils {
  public static Bundle decodeUrl(String s) {
    Bundle params = new Bundle();
    if (s != null) {
      String array[] = s.split("&");
      for (String parameter : array) {
        String v[] = parameter.split("=");
        try {
          params.putString(URLDecoder.decode(v[0], "UTF-8"), URLDecoder.decode(v[1], "UTF-8"));
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        }
      }
    }
    return params;
  }

  public static int parseInt(String value, int defaultValue) {
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {

    }
    return defaultValue;
  }

  public static long parseLong(String value, long defaultValue) {
    try {
      return Long.parseLong(value);
    } catch (NumberFormatException e) {

    }
    return defaultValue;
  }
}
