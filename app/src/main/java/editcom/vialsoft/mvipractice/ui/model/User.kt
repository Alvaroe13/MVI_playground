package editcom.vialsoft.mvipractice.ui.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class User(
    @Expose
    @SerializedName("email")
    var email: String? = "",
    @Expose
    @SerializedName("username")
    var username: String? = "",
    @Expose
    @SerializedName("image")
    var image: String? = "",
)