package com.cctv.heygongc.data.local

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.cctv.heygongc.util.Constants.ACCESS_TOKEN
import com.cctv.heygongc.util.Constants.LOCAL_PREFERENCES
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = LOCAL_PREFERENCES)

class LocalDataStoreManager @Inject constructor(@ApplicationContext context: Context) :
    DataStoreManager(context.dataStore) {

    suspend fun getAccessToken() = getStringValue(ACCESS_TOKEN)

    suspend fun setAccessToken(accessToken: String) {
        saveValue(ACCESS_TOKEN, accessToken)
    }
}