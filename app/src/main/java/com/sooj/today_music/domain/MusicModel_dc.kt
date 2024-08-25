package com.sooj.today_music.domain

import com.google.gson.annotations.SerializedName

data class MusicModel_dc(
    val results: Results?
)

data class Results(
    @SerializedName("opensearch:Query") val query: OpenSearchQuery?,
    @SerializedName("opensearch:totalResults") val totalResults: String?,
    @SerializedName("opensearch:startIndex") val startIndex: String?,
    @SerializedName("opensearch:itemsPerPage") val itemsPerPage: String?,
    val trackmatches: Trackmatches?
)

data class OpenSearchQuery(
    @SerializedName("#text") val text: String?,
    val role: String?,
    val searchTerms: String?,
    val startPage: String?
)

data class Trackmatches(
    val track: List<Track>?
)

data class Track(
    val name: String?,
    val artist: String?,
    val url: String?,
    val streamable: String?,
    val listeners: String?,
    val image: List<Image2>? // 이미지가 리스트로 반환
)

data class Image2(
    @SerializedName("#text") val url: String?,
    val size: String?,
)

/** jannabi 검색 결과
 * {
 *   "results": {
 *     "query": {
 *       "text": "",
 *       "role": "request",
 *       "searchTerms": null,
 *       "startPage": "1"
 *     },
 *     "totalResults": "1634",
 *     "startIndex": "0",
 *     "itemsPerPage": "30",
 *     "trackmatches": {
 *       "track": [
 *         {
 *           "name": "for lovers who hesitate",
 *           "artist": "Jannabi",
 *           "url": "https://www.last.fm/music/Jannabi/_/for+lovers+who+hesitate",
 *           "streamable": "FIXME",
 *           "listeners": "100060",
 *           "image": [
 *             {
 *               "url": "https://lastfm.freetls.fastly.net/i/u/34s/2a96cbd8b46e442fc41c2b86b821562f.png",
 *               "size": "small"
 *             },
 *             {
 *               "url": "https://lastfm.freetls.fastly.net/i/u/64s/2a96cbd8b46e442fc41c2b86b821562f.png",
 *               "size": "medium"
 *             },
 *             {
 *               "url": "https://lastfm.freetls.fastly.net/i/u/174s/2a96cbd8b46e442fc41c2b86b821562f.png",
 *               "size": "large"
 *             },
 *             {
 *               "url": "https://lastfm.freetls.fastly.net/i/u/300x300/2a96cbd8b46e442fc41c2b86b821562f.png",
 *               "size": "extralarge"
 *             }
 *           ]
 *         },*/