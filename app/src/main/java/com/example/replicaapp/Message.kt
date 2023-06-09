package com.example.replicaapp

data class Message(
    var id: String? = null,
    var sender: String? = null,
    var message: String? = null,
    var timestamp: Long? = null,
    val userProfileName: String? = null

) {
    constructor() : this(null, null, null, null, null)
}



