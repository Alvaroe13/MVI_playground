package editcom.vialsoft.mvipractice.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import editcom.vialsoft.mvipractice.ui.main.state.MainStateEvent
import editcom.vialsoft.mvipractice.ui.main.state.MainStateEvent.*
import editcom.vialsoft.mvipractice.ui.main.state.MainViewState
import editcom.vialsoft.mvipractice.model.BlogPost
import editcom.vialsoft.mvipractice.model.User
import editcom.vialsoft.mvipractice.repository.MainRepository
import editcom.vialsoft.mvipractice.util.AbsentLiveData
import editcom.vialsoft.mvipractice.util.Resource

class MainViewModel : ViewModel() {

    //this one is to keep track of what view to show in the ui
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    /**
     * this will be the one called whenever we want to execute an event from the ui
     */
    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    val getViewState: LiveData<MainViewState>
        get() = _viewState

    /**
     * "switchMap" gets triggered when whenever "getResource" is triggered as well
     */
    val dataState: LiveData<Resource<MainViewState>> = Transformations
        .switchMap(_stateEvent) { stateEvent ->

            stateEvent?.let {
                handleResponse(stateEvent)
            }

        }



    /**
     * triggered with any event executed from the ui, this one makes the request to dataSource
     * and return such value
     */
     private fun handleResponse(stateEvent: MainStateEvent): LiveData<Resource<MainViewState>> {
        return when (stateEvent) {
            //this gets fire off when user request to get the blog post from the server
            is GetBlogPostEvent -> {
                MainRepository.getBlogPosts()
            }
            //this gets fire off when user request the user info from the server
            is GetUserEvent -> {
                MainRepository.getUserInfo(stateEvent.userId)
            }
            is None -> {
                AbsentLiveData.create()
            }
        }
    }

    //get current view
    private fun getCurrentViewStateOrNew(): MainViewState {
        val value = getViewState.value?.let {
            it
        } ?: MainViewState()
        return value
    }

    //setters
    fun setBlogList(blogPostList: List<BlogPost>) {
        val update = getCurrentViewStateOrNew()
        update.blogList = blogPostList
        _viewState.value = update
    }

    fun setUser(user: User) {
        val update = getCurrentViewStateOrNew()
        update.user = user
        _viewState.value = update
    }

    /**
     * this is the triggering element of the whole process coming from the UI
     */
    fun setStateEvent(event: MainStateEvent) {
        _stateEvent.value = event
    }

}