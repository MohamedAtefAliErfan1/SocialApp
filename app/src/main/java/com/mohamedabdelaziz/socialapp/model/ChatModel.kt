package com.mohamedabdelaziz.socialapp.model
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mohamedabdelaziz.socialapp.model.LoginModel.User
import com.mohamedabdelaziz.socialapp.model.ChatModel.LastMessage
class ChatModel {
    @SerializedName("data")
    @Expose
     var data: List<Data?>? = null

    @SerializedName("Conversation")
    @Expose
     var conversation: Conversation? = null

    class Data {
        @SerializedName("id")
        @Expose
        var id = 0

        @SerializedName("name")
        @Expose
        var name: String? = null

        @SerializedName("email")
        @Expose
        var email: String? = null

        @SerializedName("phone")
        @Expose
        var phone: String? = null

        @SerializedName("created_at")
        @Expose
        var createdAt: String? = null

        @SerializedName("updated_at")
        @Expose
        var updatedAt: String? = null

        @SerializedName("Conversation")
        @Expose
        var conversation: Conversation? = null
        @SerializedName("avatar")
        @Expose
        var avatar: String? = null
    }

    class LastMessage {
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
        var url: Any? = null

        @SerializedName("User")
        @Expose
        var user: User? = null
    }

    class Other {
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
        var avatar: String? = null
    }

    class Conversation {
        @SerializedName("id")
        @Expose
        var id = 0

        @SerializedName("last_message")
        @Expose
        var lastMessage: LastMessage? = null

        @SerializedName("others")
        @Expose
        var others: List<Other>? = null
    }
}