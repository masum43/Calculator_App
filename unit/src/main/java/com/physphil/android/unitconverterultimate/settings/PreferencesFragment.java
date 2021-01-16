/*
 * Copyright 2018 Phil Shadlyn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.physphil.android.unitconverterultimate.settings;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.util.DisplayMetrics;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.physphil.android.unitconverterultimate.AcknowledgementsActivity;
import com.physphil.android.unitconverterultimate.BuildConfig;
import com.physphil.android.unitconverterultimate.MainActivity;
import com.physphil.android.unitconverterultimate.R;
import com.physphil.android.unitconverterultimate.UnitConverterApplication;
import com.physphil.android.unitconverterultimate.lang.AppLangSessionManager;
import com.physphil.android.unitconverterultimate.models.Language;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

public class PreferencesFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static PreferencesFragment newInstance() {
        return new PreferencesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);


        Preference privacy = findPreference("privacy_policy");
        privacy.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                openPrivacyPolicy();
                return true;
            }
        });


//        final Preference language = findPreference("language");
//        language.setOnPreferenceChangeListener(new Preference.OnPreferenceClickListener() {
//
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//                showLanguageChangeDialog();
//                return true;
//            }
//        });
        //sortLanguageOptions(language);
    }

    @Override
    public void onResume() {
        super.onResume();
        Preferences.getInstance(getActivity()).getPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Preferences.getInstance(getActivity()).getPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }



    private void openPrivacyPolicy() {
        String url = "https://willypeng.com/privacy-policy/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void sortLanguageOptions(final ListPreference preference) {
        // Sort language options so they're always alphabetical, no matter what language the user has chosen
        final Language[] languages = Language.values();
        Arrays.sort(languages, new Comparator<Language>() {
            @Override
            public int compare(Language lang1, Language lang2) {
                // Always put DEFAULT at top of list, then sort the rest alphabetically
                if (lang1 == Language.DEFAULT) {
                    return Integer.MIN_VALUE;
                }
                else if (lang2 == Language.DEFAULT) {
                    return Integer.MAX_VALUE;
                }
                else {
                    return getString(lang1.getDisplayStringId()).compareTo(getString(lang2.getDisplayStringId()));
                }
            }
        });

        // Create CharSequence[] arrays from the sorted list of Languages to supply to ListPreference
        final int size = languages.length;
        final CharSequence[] entries = new CharSequence[size];
        final CharSequence[] entryValues = new CharSequence[size];
        for (int i = 0; i < size; i++) {
            entries[i] = getString(languages[i].getDisplayStringId());
            entryValues[i] = languages[i].getId();
        }

        preference.setEntries(entries);
        preference.setEntryValues(entryValues);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(Preferences.PREFS_THEME)) {
            // Theme change, restart all open activities and reload with new theme
            getActivity().finish();
        }
    }

    private void showLanguageChangeDialog()
    {
        //add in dialog
        final String[] languageList = {
                "English", //that is position 0
                "Danish",
                "German",
                "French",
                "Dutch",
                "Russian",
                "Swedish"
        };

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        mBuilder.setTitle(R.string.choose_language);

        mBuilder.setSingleChoiceItems(languageList, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        setLocale("en");
                        AppLangSessionManager.setLanguage(getContext(),"en");
                        restart();
                        break;
                    case 1:
                        setLocale("da");
                        AppLangSessionManager.setLanguage(getContext(),"da");
                        restart();
                        break;
                    case 2:
                        setLocale("de");
                        AppLangSessionManager.setLanguage(getContext(),"de");
                        restart();
                        break;
                    case 3:
                        setLocale("fr");
                        AppLangSessionManager.setLanguage(getContext(),"fr");
                        restart();
                        break;
                    case 4:
                        setLocale("nl");
                        AppLangSessionManager.setLanguage(getContext(),"nl");
                        restart();
                        break;
                    case 5:
                        setLocale("ru");
                        AppLangSessionManager.setLanguage(getContext(),"ru");
                        restart();
                        break;
                    case 6:
                        setLocale("sv");
                        AppLangSessionManager.setLanguage(getContext(),"sv");
                        restart();
                        break;
                }
                dialogInterface.dismiss();

            }
        });

        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    //TODO :  Using for Set Locale
    public void setLocale(String lang) {

        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    private void restart() {
        Intent i = new Intent(getContext(), MainActivity.class);
        startActivity(i);
        getActivity().finish();
    }
}
