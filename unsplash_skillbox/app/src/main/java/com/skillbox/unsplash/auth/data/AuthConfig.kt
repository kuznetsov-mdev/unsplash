package com.skillbox.unsplash.auth.data

import net.openid.appauth.ResponseTypeValues

object AuthConfig {
    const val AUTH_URI = "https://unsplash.com/oauth/authorize"
    const val TOKEN_URI = "https://unsplash.com/oauth/token"
    const val END_SESSION_URI = ""
    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE = "public, read_user"

    const val CLIENT_ID = "9ewwu1hGsu9JQtZlpPONImKo7QNfQZc4RCk_rmgghQw"
    const val CLIENT_SECRET = "M_0uyC3KmjL5f1Gc5_VRSAnxSFBY9RcAx2RzgG5A_Ic"
    const val CALLBACK_URL = "com.skillbox.unsplash.oauth://unsplash.com/callback"
    const val LOGOUT_CALLBACK_URL = "com.skillbox.unsplash.oauth://logout_callback"
}