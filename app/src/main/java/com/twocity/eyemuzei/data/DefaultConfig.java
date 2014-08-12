package com.twocity.eyemuzei.data;

/**
 * Created by twocity on 14-8-8.
 */
public class DefaultConfig {
  public static final String AUTHORIZE_URL =
      "http://www.eyeem.com/oauth/authorize?response_type=code&client_id=%s&redirect_uri=%s";
  public static final String CALLBACK_URL = "http://www.github.com/twocity/eyemuzei";
  public static final String CLIENT_ID = "grqWT7F4zHp8DtR2yjzwpPBVPTmvKrx3";
  public static final String CLIENT_SECRET = "hJbjj1eKHGkpoQQhOcBsnixexzLFLWJX";
  public static final String BASE_URL = "https://api.eyeem.com/v2";
  //public static final String

  public static final String EYEEM_PHOTO_URL_BASE = "http://cdn.eyeem.com/thumb/h/1000/";
  public static final String ACCESS_TOKEN_KEY = "eyeem_access_token_key";
  public static final String ACCESS_TOKEN_TYPE_KEY = "eyeem_access_token_type_key";
  public static final String DEFAULT_PHOTO_USER = "Stephen Day";
  public static final String DEFAULT_PHOTO_TITLE = "blue sky";
  public static final String DEFAULT_PHOTO_URL =
      "http://cdn.eyeem.com/thumb/h/2000/da899d738f067ce4ad31ed666d8ea69579b1956e-1405095116";
}