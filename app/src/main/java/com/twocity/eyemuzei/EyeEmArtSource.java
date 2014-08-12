package com.twocity.eyemuzei;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import com.google.android.apps.muzei.api.Artwork;
import com.google.android.apps.muzei.api.RemoteMuzeiArtSource;
import com.twocity.eyemuzei.data.DefaultConfig;
import com.twocity.eyemuzei.data.EyeEmSourceProvider;
import com.twocity.eyemuzei.data.UserManager;
import com.twocity.eyemuzei.data.api.EyeEmService;
import com.twocity.eyemuzei.data.model.EyeEmPhoto;
import javax.inject.Inject;
import timber.log.Timber;

import static android.app.AlarmManager.INTERVAL_DAY;

/**
 * Created by twocity on 14-8-8.
 */
public class EyeEmArtSource extends RemoteMuzeiArtSource {
  private static final int RETRY_INTERVAL = 60 * 1000;
  private static final String NAME = "EyeEmArtSource";

  @Inject EyeEmService eyeEmService;
  @Inject EyeEmSourceProvider sourceProvider;
  @Inject UserManager userManager;
  @Inject SharedPreferences sharedPreferences;

  private long interval = INTERVAL_DAY;

  public EyeEmArtSource() {
    super(NAME);
  }

  @Override public void onCreate() {
    super.onCreate();
    App.get(getApplicationContext()).inject(this);
    setUserCommands(BUILTIN_COMMAND_ID_NEXT_ARTWORK);
  }

  @Override protected void onTryUpdate(int reason) throws RetryException {
    if (userManager.hasUser()) {
      Resources res = getResources();
      interval = Utils.parseLong(
          sharedPreferences.getString(res.getString(R.string.frequency_preference_key),
              String.valueOf(INTERVAL_DAY)), INTERVAL_DAY
      );
      //TODO remove this
      Timber.d("###default refresh interval:%s", String.valueOf(interval));
      if (interval > 0) {
        scheduleUpdate(System.currentTimeMillis() + 60 * 60 * 1000);
      }
    }
    String currentToken = (getCurrentArtwork() != null) ? getCurrentArtwork().getToken() : null;

    sourceProvider.loadPhoto(currentToken, new EyeEmSourceProvider.OnEyeEmPhotoLoadedListener() {
      @Override public void onLoaded(EyeEmPhoto photo) {
        publishPhoto(photo);
      }

      @Override public void onFailed() {
        scheduleUpdate(System.currentTimeMillis() + RETRY_INTERVAL);
      }
    });
  }

  private void publishPhoto(EyeEmPhoto photo) {
    if (photo == null) return;
    Artwork.Builder builder = new Artwork.Builder();
    builder.imageUri(getEyeHighPixelUri(photo.getPhotoUrl()));
    if (photo.getCaption() != null) {
      builder.title(photo.getCaption());
    }
    builder.token(String.valueOf(photo.getId()));
    if (photo.getAuthor() != null) {
      builder.byline(photo.getAuthor().getNickName());
    }
    publishArtwork(builder.build());
    Timber.d("###publish art work: %s, %s", photo.getCaption(), photo.getPhotoUrl());
  }

  private static Uri getEyeHighPixelUri(String url) {
    if (url == null) {
      return Uri.parse(DefaultConfig.DEFAULT_PHOTO_URL);
    }
    String highPixelUrl = url;
    int index = url.lastIndexOf("/");
    if (index != -1) {
      String name = url.substring(index, url.length());
      highPixelUrl = DefaultConfig.EYEEM_PHOTO_URL_BASE + name;
    }
    return Uri.parse(highPixelUrl);
  }
}