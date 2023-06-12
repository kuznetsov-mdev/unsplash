package com.skillbox.unsplash.data.auth

import net.openid.appauth.ResponseTypeValues

object AuthConfig {
    const val AUTH_URI = "https://unsplash.com/oauth/authorize"
    const val TOKEN_URI = "https://unsplash.com/oauth/token"
    const val END_SESSION_URI = ""
    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE = "public read_user read_photos read_collections"

    const val CLIENT_ID = "NRZ6_0bADaBNd-O9g6luCXiwxu7kCD43uI-5LC_iuQ8"
    const val CLIENT_SECRET = "O_JtOEmApbbkc66r8P7LlovK25IC2masLAP3uNBTBj8"
    const val CALLBACK_URL = "unsplash://com.skillbox.unsplash/callback"
    const val LOGOUT_CALLBACK_URL = "unsplash://com.skillbox.unsplash/logout_callback"
}