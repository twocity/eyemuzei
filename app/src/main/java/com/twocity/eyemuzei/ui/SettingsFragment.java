package com.twocity.eyemuzei.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import com.google.android.apps.muzei.api.MuzeiArtSource;
import com.google.android.apps.muzei.api.internal.ProtocolConstants;
import com.twocity.eyemuzei.App;
import com.twocity.eyemuzei.EyeEmArtSource;
import com.twocity.eyemuzei.R;
import com.twocity.eyemuzei.data.UserManager;
import javax.inject.Inject;

/**
 * Created by twocity on 14-8-11.
 */
public class SettingsFragment extends PreferenceFragment
    implements SharedPreferences.OnSharedPreferenceChangeListener {

  private boolean changed = false;
  @Inject UserManager userManager;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.settings);
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    App.get(getActivity()).inject(this);

    SharedPreferences preferences = getPreferenceManager().getSharedPreferences();
    changed = preferences.getBoolean("is_first", true);
    preferences.edit().putBoolean("is_first", false).apply();

    Preference logoutPreference = findPreference(getString(R.string.logout_preference_key));
    logoutPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
      @Override public boolean onPreferenceClick(Preference preference) {
        logout();
        return true;
      }
    });
  }

  private void logout() {
    App application = App.get(getActivity());
    userManager.setAccessToken("", "");
    application.buildObjectGraph();
    startActivity(new Intent(getActivity(), AuthorizeActivity.class));
    getActivity().finish();
  }

  private void startArtService() {
    Intent updateIntent = new Intent(getActivity(), EyeEmArtSource.class);
    updateIntent.setAction(ProtocolConstants.ACTION_HANDLE_COMMAND);
    updateIntent.putExtra(ProtocolConstants.EXTRA_COMMAND_ID,
        MuzeiArtSource.BUILTIN_COMMAND_ID_NEXT_ARTWORK);
    getActivity().startService(updateIntent);
  }

  @Override
  public void onResume() {
    super.onResume();
    getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
  }

  @Override
  public void onPause() {
    super.onPause();
    getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
  }

  @Override public void onDetach() {
    super.onDetach();
    if (changed) startArtService();
  }

  @Override public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    changed = true;
  }
}
