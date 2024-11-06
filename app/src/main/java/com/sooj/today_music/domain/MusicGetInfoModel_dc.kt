package com.sooj.today_music.domain

import com.google.gson.annotations.SerializedName

/**  음악 정보 API  */

data class MusicGetInfoModel_dc(
    val track: Track2
)

data class Track2(
    val name: String,
    val mbid: String?,
    val url: String,
    val duration: String?,
    val streamable: Streamable,
    val listeners: String,
    val playcount: String,
    val artist: Artist,
    val album: Album, //
)

data class Streamable(
    @SerializedName("#text") val text : String,
    val fulltrack: String,
    )

data class Artist(
    val name : String,
    val mbid: String?,
    val url: String
)

data class Album( //
    val artist: String,
    val title: String,
    val mbid: String?,
    val url: String,
    val image: List<Image>
)


data class Image( //
    @SerializedName("#text") val url: String,
    val size : String
)


/**
 * {
 *     "track": {
 *         "name": "Believe",
 *         "mbid": "32ca187e-ee25-4f18-b7d0-3b6713f24635",
 *         "url": "https://www.last.fm/music/Cher/_/Believe",
 *         "duration": "238000",
 *         "streamable": {
 *             "#text": "0",
 *             "fulltrack": "0"
 *         },
 *         "listeners": "844394",
 *         "playcount": "4962647",
 *         "artist": {
 *             "name": "Cher",
 *             "mbid": "bfcc6d75-a6a5-4bc6-8282-47aec8531818",
 *             "url": "https://www.last.fm/music/Cher"
 *         },
 *         "album": {
 *             "artist": "Cher",
 *             "title": "Believe",
 *             "mbid": "63b3a8ca-26f2-4e2b-b867-647a6ec2bebd",
 *             "url": "https://www.last.fm/music/Cher/Believe",
 *             "image": [
 *                 {
 *                     "#text": "https://lastfm.freetls.fastly.net/i/u/34s/3b54885952161aaea4ce2965b2db1638.png",
 *                     "size": "small"
 *                 },
 *                 {
 *                     "#text": "https://lastfm.freetls.fastly.net/i/u/64s/3b54885952161aaea4ce2965b2db1638.png",
 *                     "size": "medium"
 *                 },
 *                 {
 *                     "#text": "https://lastfm.freetls.fastly.net/i/u/174s/3b54885952161aaea4ce2965b2db1638.png",
 *                     "size": "large"
 *                 },
 *                 {
 *                     "#text": "https://lastfm.freetls.fastly.net/i/u/300x300/3b54885952161aaea4ce2965b2db1638.png",
 *                     "size": "extralarge"
 *                 }
 *             ],
 *             "@attr": {
 *                 "position": "1"
 *             }
 *         },
 *         "toptags": {
 *             "tag": [
 *                 {
 *                     "name": "pop",
 *                     "url": "https://www.last.fm/tag/pop"
 *                 },
 *                 {
 *                     "name": "dance",
 *                     "url": "https://www.last.fm/tag/dance"
 *                 },
 *                 {
 *                     "name": "90s",
 *                     "url": "https://www.last.fm/tag/90s"
 *                 },
 *                 {
 *                     "name": "cher",
 *                     "url": "https://www.last.fm/tag/cher"
 *                 },
 *                 {
 *                     "name": "female vocalists",
 *                     "url": "https://www.last.fm/tag/female+vocalists"
 *                 }
 *             ]
 *         },
 *         "wiki": {
 *             "published": "27 Jul 2008, 15:44",
 *             "summary": "\"Believe\" is a Grammy Award winning global number one, Multi-Platinum Dance Song which served as the world-wide lead single for American singer Cher's twenty-third studio album Believe. It is noted for its use of a peculiar sound effect on the singer's vocals, which is referred to as the Cher effect today.\n\n\"Believe\" was written by a number of writers including Paul Barry, Matt Gray, Brian Higgins, Stuart McLellan, Timothy Powell, and Steven Torch. <a href=\"http://www.last.fm/music/Cher/_/Believe\">Read more on Last.fm</a>.",
 *             "content": "\"Believe\" is a Grammy Award winning global number one, Multi-Platinum Dance Song which served as the world-wide lead single for American singer Cher's twenty-third studio album Believe. It is noted for its use of a peculiar sound effect on the singer's vocals, which is referred to as the Cher effect today.\n\n\"Believe\" was written by a number of writers including Paul Barry, Matt Gray, Brian Higgins, Stuart McLellan, Timothy Powell, and Steven Torch. The song, released and recorded in 1998, peaked at number one in 23 countries worldwide .In the second week of March 1999, it reached number one in the Billboard Hot 100, making Cher the oldest female artist (at the age of 52) to perform this feat. It also was ranked as the number-one song of 1999 by Billboard, and became the biggest single in her entire career. \"Believe\" also spent seven weeks at number one in the UK singles chart and is still the best selling single by a female artist in the UK. \n\nIn March 2007, the United World Chart ranked \"Believe\" as the sixteenth most successful song in music history. The same chart lists \"Believe\" as third most successful song released by a solo female musician behind Whitney Houston's \"I Will Always Love You\" and Celine Dion's My Heart Will Go On - the biggest selling single ever for Warner Bros. Records and the biggest selling dance song ever having sold over 10 million copies worldwide. It was also the song with most weeks in the top ten, it stayed in the top ten for 28 weeks.\n\nThe success of the song not only expanded through each country's singles chart, but also most country's dance charts. In the United States \"Believe\" spent 23 weeks on the U.S. Hot Dance Club Play chart and 22 weeks on the European Hot Dance Charts. \"Believe\" also set a record in 1999 after spending 21 weeks in the top spot of the Billboard Hot Dance Singles Sales chart, it was still in the top ten one year after its entry on the chart. \n\n\"Believe\" was given the featured closing number spot for over 100 performances on Cher's 1999-2000 Do You Believe? Tour and then again the closing spot for over 300 performances on Cher's epic 2002-2005 Living Proof: The Farewell Tour. The song ranked #74 on VH1's 100 Greatest Songs of the 90s.\n\nAn interesting note about the recording of the song revolved around the highly-recognizable Auto-tune effect (\"Cher effect\") utilized in the verses and chorus. Producer Mark Taylor added the effect to Cher's vocal simply as a lark, and in interviews at the time, he claimed to be testing out his recently purchased the 'DigiTech Talker'. However, it later emerged that the effect was not created by a vocoder, but by utilizing extreme (and then unheard) settings on auto-tune.When Cher heard the results, she demanded that the effect remain in the song, and her original vocal be erased, much to the chagrin of her record company, who wanted it removed; upon their suggestion, Cher's response to the record label was \"over my dead body!\" The vocal effect is caused by a pitch correction speed that is \"set too fast for the audio that it is processing.\" \n\nChart positions\nU.S. Billboard Hot 100 Airplay - #1\nU.S. Billboard Hot 100 - #1\nU.S. Billboard Hot Dance Club Play - #1\nU.S. Billboard Hot Dance Singles Sales - #1\nU.S. Billboard Hot Adult Contemporary Tracks - #2\nU.S. Billboard Hot Adult Top 40 Tracks - #2\nU.S. Billboard Top 40 Mainstream - #2\nArgentinian Singles Chart - #2\nAustralian ARIA Singles Chart - #1\nAustrian Singles Chart - #2\nBelgian Singles Chart - #1\nBrazilian Airplay Chart - #1\nCroatian Singles Chart - #1\nCanadian Singles Chart - #1\nDanish Singles Chart - #1\nDutch Mega Top 50 Singles Chart - #1\nDutch Top 40 - #1\nEuropean Singles Chart - #1\nFinnish Singles Chart - #6\nFrench Singles Chart - #1\nGerman Singles Chart - #1\nIrish Singles Chart - #1\nIsraeli Singles Chart - #1\nItalian Singles Chart - #1\nLatvian Singles Chart - #1\nMexican Singles Chart - #1\nNew Zealand's Singles Chart - #1\nNorwegian Singles Chart - #1\nPolish Singles Chart - #1\nSpanish Singles Chart - #1\nSwedish Singles Chart - #1\nSwedish Airplay Chart - #1\nSwiss Singles Chart - #1\nUK Singles Chart - #1\nUnited World Chart - #1\n\nCher Official Website: https://www.cherfanclub.com <a href=\"http://www.last.fm/music/Cher/_/Believe\">Read more on Last.fm</a>. User-contributed text is available under the Creative Commons By-SA License; additional terms may apply."
 *         }
 *     }
 * }*/
