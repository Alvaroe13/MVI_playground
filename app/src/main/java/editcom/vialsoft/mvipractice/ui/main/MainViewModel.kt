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
import editcom.vialsoft.mvipractice.util.DataState

class MainViewModel : ViewModel() {

    //this one is to keep track of what view to show in the ui
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    //this one will be the observable containing the data to be shown in the ui
    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()

    val getViewState: LiveData<MainViewState>
        get() = _viewState

    val getDataState: LiveData<DataState<MainViewState>> = Transformations
                .switchMap(_stateEvent){ stateEvent ->

        stateEvent?.let {
            handleResponse(stateEvent)
        }

    }
    fun handleResponse(stateEvent: MainStateEvent) : LiveData<DataState<MainViewState>> {
        when (stateEvent) {

            is GetBlogPostEvent -> {
                return MainRepository.getBlogPosts()
            }
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

    fun setStateEvent( event : MainStateEvent){
        _stateEvent.value = event
    }
}