package com.example.jingbin.cloudreader.bean

class FilmDetailNewBean {

    val image: String? = null
    val titleCn: String? = null
    val titleEn: String? = null
    val rating: String? = null
    val scoreCount: String? = null
    val year: String? = null
    val content: String? = null
    val type: List<String>? = null
    val runTime: String? = null
    val url: String? = null
    val commonSpecial: String? = null
    val wapUrl: String? = null
    val director: DirectorBean? = null
    val actorList: List<ActorBean>? = null
    val directors: List<String>? = null
    val actors: List<String>? = null
    val release: ReleaseBean? = null
    val images: List<String>? = null
    val video: String? = null
    val videos: List<VideoBean>? = null

    class VideoBean {
        val url: String? = null
        val image: String? = null
        val title: String? = null
        val length: Int = 0
    }

    class ReleaseBean {
        val location: String? = null
        val date: String? = null
    }

    class DirectorBean {
        val directorName: String? = null
        val directorNameEn: String? = null
        val directorImg: String? = null

    }

    class ActorBean {
        var actor: String? = null
        var actorEn: String? = null
        var actorImg: String? = null
        var roleName: String? = null
    }
}

