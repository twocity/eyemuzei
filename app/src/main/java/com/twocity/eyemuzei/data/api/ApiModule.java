package com.twocity.eyemuzei.data.api;

import com.google.gson.GsonBuilder;
import com.twocity.eyemuzei.data.EyeEmPhotosDeserializer;
import com.twocity.eyemuzei.data.UserManager;
import com.twocity.eyemuzei.data.model.EyeEmPhotos;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;

import static com.twocity.eyemuzei.data.DefaultConfig.BASE_URL;

/**
 * Created by twocity on 14-8-9.
 */
@Module(
    complete = false,
    library = true)
public class ApiModule {
  private static final String AUTHORIZATION_PREFIX = "Authorization";

  @Singleton @Provides Converter provideConverter() {
    GsonBuilder builder = new GsonBuilder();
    builder.
        registerTypeAdapter(EyeEmPhotos.class, new EyeEmPhotosDeserializer());

    return new GsonConverter(builder.create());
  }

  @Singleton @Provides RestAdapter provideRestAdapter(final UserManager userManager,
      final Converter converter) {
    return new RestAdapter.Builder().setEndpoint(BASE_URL)
        .setRequestInterceptor(new RequestInterceptor() {
          @Override public void intercept(RequestFacade request) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(userManager.getAccessTokenType())
                .append(" ")
                .append(userManager.getAccessToken());
            request.addHeader(AUTHORIZATION_PREFIX, stringBuilder.toString());
          }
        })
        .setConverter(converter)
        .setLogLevel(RestAdapter.LogLevel.HEADERS)
        .build();
  }

  @Singleton @Provides EyeEmService provideEyeEmService(RestAdapter restAdapter) {
    return restAdapter.create(EyeEmService.class);
  }
}
