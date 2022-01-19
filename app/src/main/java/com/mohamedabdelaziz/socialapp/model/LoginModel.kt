package com.mohamedabdelaziz.socialapp.model

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class LoginModel {
    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("errors")
    @Expose
    val errors: Errors? = null


    @SerializedName("check")
    @Expose
    var isCheck = false

    @SerializedName("data")
    @Expose
    var data: Data? = null

    inner class Data {
        @SerializedName("user")
        @Expose
        var user: User? = null

        @SerializedName("Token")
        @Expose
        var token: Token? = null

        @SerializedName("avatar")
        @Expose
        var avatar: String? = null
    }

    inner class Token {
        @SerializedName("token_type")
        @Expose
        var tokenType: String? = null

        @SerializedName("expires_in")
        @Expose
        var expiresIn: Any? = null

        @SerializedName("refresh_token")
        @Expose
        var refreshToken: Any? = null

        @SerializedName("access_token")
        @Expose
        var accessToken: String? = null
    }

    inner class User {
        @SerializedName("id")
        @Expose
        var id = 0

        @SerializedName("remember_token")
        @Expose
        var rememberToken: Any? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: Any? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: Any? = null

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("email")
        @Expose
        var email: String? = null

        @SerializedName("phone")
        @Expose
        var phone: String? = null
        @SerializedName("avatar")
        @Expose
        var avatar: Any? = null
    }
    class Errors {
        @SerializedName("email")
        @Expose
        var email: List<String>? = null

        @SerializedName("password")
        @Expose
        var password: List<String>? = null
    }
}