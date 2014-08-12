package com.twocity.eyemuzei;

import android.app.Application;
import com.twocity.eyemuzei.data.DataModule;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by twocity on 14-8-9.
 */
@Module(
    includes = { DataModule.class },
    complete = false)
public class AppModule {
  private final Application app;

  public AppModule(Application app) {
    this.app = app;
  }

  @Singleton @Provides Application provideApplication() {
    return app;
  }
}
