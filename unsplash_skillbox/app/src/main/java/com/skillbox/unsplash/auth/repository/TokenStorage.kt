package com.skillbox.unsplash.auth.repository

object TokenStorage {
    var accessToken: String? = null
    var refreshToken: String? = null
    var idToken: String? = null
}