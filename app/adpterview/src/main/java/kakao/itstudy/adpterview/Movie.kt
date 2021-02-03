package kakao.itstudy.adpterview

import java.io.Serializable

class Movie : Serializable {
    var movieid:Int? = null
    var title:String? = null
    var subtitle:String? = null
    var pubdate:String? = null
    var director:String? = null
    var actor:String? = null
    var genre:String? = null
    var rating:Double? = null
    var thumbnail:String? = null
    var link:String? = null

    override fun toString(): String {
        return "Movie(movieid=$movieid, title=$title, subtitle=$subtitle, pubdate=$pubdate, director=$director, actor=$actor, genre=$genre, rating=$rating, thumbnail=$thumbnail, link=$link)"
    }
}