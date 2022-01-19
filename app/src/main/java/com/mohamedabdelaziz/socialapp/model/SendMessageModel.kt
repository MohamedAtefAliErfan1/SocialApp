package com.mohamedabdelaziz.socialapp.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class SendMessageModel {
    @SerializedName("data")
    @Expose
     var data: MessagesModel.Data? = null

    @SerializedName("message")
    @Expose
     var message: String? = null

    @SerializedName("check")
    @Expose
     var check = false


}