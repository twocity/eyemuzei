package com.twocity.eyemuzei.data;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.text.TextUtils;
import com.twocity.eyemuzei.R;
import com.twocity.eyemuzei.Utils;
import com.twocity.eyemuzei.data.api.EyeEmService;
import com.twocity.eyemuzei.data.model.EyeEmPhoto;
import com.twocity.eyemuzei.data.model.EyeEmPhotos;
import com.twocity.eyemuzei.data.model.EyeEmUser;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import javax.inject.Singleton;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

import static com.twocity.eyemuzei.data.DefaultConfig.DEFAULT_PHOTO_TITLE;
import static com.twocity.eyemuzei.data.DefaultConfig.DEFAULT_PHOTO_URL;
import static com.twocity.eyemuzei.data.DefaultConfig.DEFAULT_PHOTO_USER;
import static com.twocity.eyemuzei.ui.Settings.CATEGORY_LIKED;
import static com.twocity.eyemuzei.ui.Settings.CATEGORY_ONBOARD;
import static com.twocity.eyemuzei.ui.Settings.CATEGORY_POPULAR;

/**
 * Created by twocity on 14-8-11.
 */
@Singleton
public class EyeEmSourceProvider {
  private final UserManager userManager;
  private final SharedPreferences preferences;
  private final EyeEmService eyeEmService;
  private final EyeEmPhoto defaultPhoto;
  private final Resources res;

  @Inject
  public EyeEmSourceProvider(UserManager userManager, EyeEmService service,
      SharedPreferences sharedPreferences, Application app) {
    this.userManager = userManager;
    this.eyeEmService = service;
    this.preferences = sharedPreferences;
    res = app.getResources();

    defaultPhoto = new EyeEmPhoto();
    defaultPhoto.setId(40780178);
    defaultPhoto.setPhotoUrl(DEFAULT_PHOTO_URL);
    EyeEmUser user = new EyeEmUser();
    defaultPhoto.setCaption(DEFAULT_PHOTO_TITLE);
    user.setNickName(DEFAULT_PHOTO_USER);
    defaultPhoto.setAuthor(user);
  }

  public void loadPhoto(String currentToken, OnEyeEmPhotoLoadedListener listener) {
    if (userManager.hasUser()) {
      loadPhotos(currentToken, listener);
    } else {
      listener.onLoaded(defaultPhoto);
    }
  }

  private void loadPhotos(String token, OnEyeEmPhotoLoadedListener listener) {
    int categoryType = Utils.parseInt(
        preferences.getString(res.getString(R.string.category_preference_key),
            String.valueOf(CATEGORY_LIKED)), CATEGORY_LIKED
    );
    switch (categoryType) {
      case CATEGORY_LIKED:
        eyeEmService.likedPhotos(new PhotoLoaded(token, defaultPhoto, listener));
        break;
      case CATEGORY_POPULAR:
        eyeEmService.popularPhotos(new PhotoLoaded(token, defaultPhoto, listener));
        break;
      case CATEGORY_ONBOARD:
        eyeEmService.likedPhotos(new PhotoLoaded(token, defaultPhoto, listener));
        break;
      default:
        eyeEmService.likedPhotos(new PhotoLoaded(token, defaultPhoto, listener));
    }
  }

  private static class PhotoLoaded implements Callback<EyeEmPhotos> {
    private final OnEyeEmPhotoLoadedListener listener;
    private final String currentToken;
    private final EyeEmPhoto defaultPhoto;

    public PhotoLoaded(String token, EyeEmPhoto defaultPhoto, OnEyeEmPhotoLoadedListener listener) {
      this.listener = listener;
      this.currentToken = token;
      this.defaultPhoto = defaultPhoto;
    }

    @Override public void success(EyeEmPhotos eyeEmPhotos, Response response) {
      List<EyeEmPhoto> photos = eyeEmPhotos.getPhotos();
      if (photos == null || photos.isEmpty()) {
        listener.onLoaded(defaultPhoto);
      }
      Timber.d("###load EyePhotos: %d", photos.size());
      Random random = new Random();
      EyeEmPhoto photo;
      String token;
      while (true) {
        photo = photos.get(random.nextInt(photos.size()));
        token = String.valueOf(photo.getId());
        if (!TextUtils.equals(token, currentToken)) {
          break;
        }
      }
      listener.onLoaded(photo == null ? defaultPhoto : photo);
    }

    @Override public void failure(RetrofitError error) {
      listener.onFailed();
    }
  }

  public interface OnEyeEmPhotoLoadedListener {
    public void onLoaded(EyeEmPhoto photo);

    public void onFailed();
  }
}
