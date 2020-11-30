package editcom.vialsoft.mvipractice.ui.main.state

import editcom.vialsoft.mvipractice.ui.model.BlogPost
import editcom.vialsoft.mvipractice.ui.model.User

/**
 * This is the class that will work as a wrapper, meaning that every time we fire off an event to
 * the network, we go through this class as it encapsulates the models we have in this view
 * (user , BlogPost)
 */
data class MainViewState(
    var blogList: List<BlogPost>? = null,
    var user: User? = null
)