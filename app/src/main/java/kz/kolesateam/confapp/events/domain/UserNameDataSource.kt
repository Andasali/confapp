package kz.kolesateam.confapp.events.domain

interface UserNameDataSource {

    fun saveUserName(userName: String)
    fun getUserName(): String
}