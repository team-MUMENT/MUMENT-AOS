package com.mument_android.app.datastore


import android.content.Context
import androidx.datastore.core.DataStore
import java.util.prefs.Preferences

val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "settings")