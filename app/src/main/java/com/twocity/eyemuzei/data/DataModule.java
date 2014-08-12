package com.twocity.eyemuzei.data;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.twocity.eyemuzei.EyeEmArtSource;
import com.twocity.eyemuzei.data.api.ApiModule;
import com.twocity.eyemuzei.ui.AuthorizeActivity;
import com.twocity.eyemuzei.ui.SettingsFragment;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by twocity on 14-8-9.
 */
@Module(
    injects = { AuthorizeActivity.class, SettingsFragment.class, EyeEmArtSource.class },
    includes = { ApiModule.class },
    library = true,
    complete = false)
public class DataModule {

  @Singleton @Provides SharedPreferences provideSharedPreferences(Application app) {
    return PreferenceManager.getDefaultSharedPreferences(app);
  }

  @Singleton @Provides UserManager provideUserManager(SharedPreferences sharedPreferences) {
    return new UserManager(sharedPreferences);
  }
}
