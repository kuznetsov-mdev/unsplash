package com.skillbox.unsplash.data.auth

import net.openid.appauth.ResponseTypeValues

object AuthConfig {
    const val AUTH_URI = "https://unsplash.com/oauth/authorize"
    const val TOKEN_URI = "https://unsplash.com/oauth/token"
    const val END_SESSION_URI = "https://unsplash.com/logout"
    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE = "public read_user read_photos read_collections write_likes"

    const val CLIENT_ID = "9ewwu1hGsu9JQtZlpPONImKo7QNfQZc4RCk_rmgghQw"
    const val CLIENT_SECRET = "M_0uyC3KmjL5f1Gc5_VRSAnxSFBY9RcAx2RzgG5A_Ic"
    const val CALLBACK_URL = "unsplash://com.skillbox.unsplash/callback"
    const val LOGOUT_CALLBACK_URL = "unsplash://com.skillbox.unsplash/logout_callback"

    const val CLIENT_KEY = "client_id"
}