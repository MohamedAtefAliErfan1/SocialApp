package com.mohamedabdelaziz.socialapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mohamedabdelaziz.socialapp.model.MessagesModel.Meta


class MessagesModel {
    @SerializedName("data")
    @Expose
    var data: ArrayList<Data?>? = null

    @SerializedName("links")
    @Expose
    var links: Links? = null

    @SerializedName("meta")
    @Expose
    var meta: Meta? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("errors")
    @Expose
    val errors: LoginModel.Errors? = null


    @SerializedName("check")
    @Expose
    var isCheck = false


    class Data {
        @SerializedName("id")
        @Expose
        var id = 0

        @SerializedName("contant")
        @Expose
        var contant: String? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null

        @SerializedName("url")
        @Expose
        var url: String? = null

        @SerializedName("User")
        @Expose
        var user: User? = null
    }

    class Example {
        @SerializedName("data")
        @Expose
        var data: List<Data>? = null

        @SerializedName("links")
        @Expose
        var links: Links? = null

        @SerializedName("meta")
        @Expose
        var meta: Meta? = null
    }

    class Link {
        @SerializedName("url")
        @Expose
        var url: Any? = null

        @SerializedName("label")
        @Expose
        var label: String? = null

        @SerializedName("active")
        @Expose
        var isActive = false
    }

    class Links {
        @SerializedName("first")
        @Expose
        var first: String? = null

        @SerializedName("last")
        @Expose
        var last: String? = null

        @SerializedName("prev")
        @Expose
        var prev: Any? = null

        @SerializedName("next")
        @Expose
        var next: Any? = null
    }

    class Meta {
        @SerializedName("current_page")
        @Expose
        var currentPage = 0

        @SerializedName("from")
        @Expose
        var from = 0

        @SerializedName("last_page")
        @Expose
        var lastPage = 0

        @SerializedName("links")
        @Expose
        var links: List<Link>? = null

        @SerializedName("path")
        @Expose
        var path: String? = null

        @SerializedName("per_page")
        @Expose
        var perPage = 0

        @SerializedName("to")
        @Expose
        var to = 0

        @SerializedName("total")
        @Expose
        var total = 0
    }

    class User {
        @SerializedName("id")
        @Expose
        var id = 0

        @SerializedName("name")
        @Expose
        var name: Any? = null

        @SerializedName("email")
        @Expose
        var email: String? = null

        @SerializedName("phone")
        @Expose
        var phone: Any? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null

        @SerializedName("avatar")
        @Expose
        var avatar: Any? = null
    }
}