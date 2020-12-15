package kz.kolesateam.confapp.events.data

import android.content.SharedPreferences
import kz.kolesateam.confapp.events.domain.UserNameDataSource

const val USER_NAME_KEY = "user_name"
const val EMPTY_KEY = ""

class UserNameSharedPrefsDataSource(
    private val sharedPreferences: SharedPreferences
): UserNameDataSource {

    override fun saveUserName(userName: String){
        sharedPreferences.edit().putString(USER_NAME_KEY, userName).apply()
    }

    override fun getUserName(): String {
        return sharedPreferences.getString(USER_NAME_KEY, EMPTY_KEY) ?: EMPTY_KEY
    }
}