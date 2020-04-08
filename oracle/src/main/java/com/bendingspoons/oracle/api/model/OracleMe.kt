package com.bendingspoons.oracle.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class OracleMe(
    @field:Json(name = "device_id") val deviceId: String = "",
    @field:Json(name = "is_subscribed") val isSubscribed: Boolean = false
)
