package com.twocity.eyemuzei;

import android.app.Application;
import android.content.Context;
import dagger.ObjectGraph;
import timber.log.Timber;

/**
 * Created by twocity on 14-8-9.
 */
public class App extends Application {
  private ObjectGraph objectGraph;

  @Override public void onCreate() {
    super.onCreate();
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    } else {
    }
    Timber.d("####at Application onCreate");
    buildObjectGraph();
  }

  public void buildObjectGraph() {
    objectGraph = ObjectGraph.create(new AppModule(this));
  }

  public void inject(Object o) {
    objectGraph.inject(o);
  }

  public static App get(Context context) {
    return (App) context.getApplicationContext();
  }
}
