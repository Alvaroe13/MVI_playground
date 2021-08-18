package editcom.vialsoft.mvipractice.ui.main.state

/**
 * State event is the one firing off events to viewmodel (MVI is an event oriented arch)
 */
sealed class MainStateEvent {

    class GetBlogPostEvent : MainStateEvent()

    class GetUserEvent( val userId: String ) : MainStateEvent()

    class None : MainStateEvent()
}