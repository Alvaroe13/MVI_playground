package editcom.vialsoft.mvipractice.ui.main.state

import editcom.vialsoft.mvipractice.model.BlogPost
import editcom.vialsoft.mvipractice.model.User

/**
 * This class is meant to hold the info to be display in the view. In this project we print a list
 * of blogpost and the user info on top of the list.
 * NOTE: If we were to show more info we just need to create another data class with such values
 * and add it to this class.
 */
data class MainViewState(
    var blogList: List<BlogPost>? = null,
    var user: User? = null
)