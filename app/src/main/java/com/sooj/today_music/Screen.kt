package com.sooj.today_music

enum class Screen(val route : String) {
    PosterList("poster_list"),
    DetailPage("detail_page/{trackId}"),
    WritePost("write_post"),
    EditDetailPage("edit_detail_page"),
    SelectPage("select_page")
}