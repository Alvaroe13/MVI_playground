package editcom.vialsoft.mvipractice.ui.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BlogPost(
    @Expose
    @SerializedName("pk")
    var pk: Int? = 0,
    @Expose
    @SerializedName("title")
    var title: String? = "",
    @Expose
    @SerializedName("body")
    var body: String? = "",

) {
    override fun toString(): String {
        return "BlogPost(pk=$pk, title=$title, body=$body)"
    }
}