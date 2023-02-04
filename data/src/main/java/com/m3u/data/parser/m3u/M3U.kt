package com.m3u.data.parser.m3u

import com.m3u.data.entity.Live

data class M3U(
    val id: String = "",
    val name: String = "",
    val cover: String = "",
    val group: String = "",
    val title: String = "",
    val url: String = ""
)

fun M3U.toLive(
    subscriptionUrl: String
): Live = Live(
    url = url,
    group = group,
    title = title,
    cover = cover,
    subscriptionUrl = subscriptionUrl
)