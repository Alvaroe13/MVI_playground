package editcom.vialsoft.mvipractice.ui.main.state

/**
 * This class is also a wrapper for the event that we will fire off and this classes are going to be
 * the only possible outcomes
 */
sealed class MainStateEvent {

    class GetBlogPostEvent : MainStateEvent()

    class GetUserEvent( val userId: String ) : MainStateEvent()

    class None : MainStateEvent()
}