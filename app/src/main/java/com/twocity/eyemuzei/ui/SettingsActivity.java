package com.twocity.eyemuzei.ui;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by twocity on 14-8-11.
 */
public class SettingsActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    getFragmentManager().beginTransaction()
        .replace(android.R.id.content, new SettingsFragment())
        .commit();
  }
}
