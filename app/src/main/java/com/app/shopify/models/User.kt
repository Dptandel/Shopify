package com.app.shopify.models

data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    var profileImage: String = ""
) {
    constructor(): this("","","","")
}
