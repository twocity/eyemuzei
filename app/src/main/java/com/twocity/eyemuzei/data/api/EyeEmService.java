package com.twocity.eyemuzei.data.api;

import com.twocity.eyemuzei.data.model.AccessToken;
import com.twocity.eyemuzei.data.model.EyeEmPhotos;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by twocity on 14-8-9.
 */
public interface EyeEmService {
  @POST("/oauth/token")
  public void authorize(@Query("grant_type") String grantType, @Query("client_id") String clientId,
      @Query("client_secret") String clientSecret, @Query("redirect_uri") String redirectUri,
      @Query("code") String code, Callback<AccessToken> callback);

  @GET("/users/me/likedPhotos?limit=100")
  public void likedPhotos(Callback<EyeEmPhotos> callback);

  @GET("/photos/popular?limit=100")
  public void popularPhotos(Callback<EyeEmPhotos> callback);
}