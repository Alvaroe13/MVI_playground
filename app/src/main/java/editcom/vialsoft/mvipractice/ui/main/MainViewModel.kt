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

    //this one will be the observable containing the data to be shown in the ui
    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()


    /**
     * This one is the communication with the server   (this direction) ------->
     */
    fun handleResponse(stateEvent: MainStateEvent) : LiveData<Resource<MainViewState>> {

        when (stateEvent) {

            //this gets fire off when user request to get the blog post from the server
            is GetBlogPostEvent -> {
                return MainRepository.getBlogPosts()
            }
            //this gets fire off when user request the user info from the server
            is GetUserEvent -> {
                return MainRepository.getUserInfo(stateEvent.userId)
            }
            is None -> {
                return AbsentLiveData.create()
            }

        }
    }

    //get current view
    fun getCurrentViewStateOrNew() : MainViewState{
        val value = getViewState.value?.let {
            it
        }?: MainViewState()
        return value
    }

    //setters
    fun setBlogList(blogPostList : List<BlogPost>){
        val update = getCurrentViewStateOrNew()
        update.blogList = blogPostList
        _viewState.value = update
    }

    fun setUser(user: User){
        val update = getCurrentViewStateOrNew()
        update.user = user
        _viewState.value = update
    }

    /**
     * this is the triggering element of the whole process coming from the UI
     */
    fun setStateEvent( event : MainStateEvent){
        _stateEvent.value = event
    }

    /////////////////////////////// GETTERS ///////////////////////////////////////

    //observable1
    val getViewState: LiveData<MainViewState>
        get() = _viewState

    //observable2
    val getResource: LiveData<Resource<MainViewState>> = Transformations
        .switchMap(_stateEvent){ stateEvent ->

            stateEvent?.let {
                handleResponse(stateEvent)
            }

        }
}