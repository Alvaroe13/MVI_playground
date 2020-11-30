package editcom.vialsoft.mvipractice.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import editcom.vialsoft.mvipractice.ui.main.state.MainStateEvent
import editcom.vialsoft.mvipractice.ui.main.state.MainStateEvent.*
import editcom.vialsoft.mvipractice.ui.main.state.MainViewState
import editcom.vialsoft.mvipractice.ui.util.AbsentLiveData

class MainViewModel : ViewModel() {

    //this one is to keep track of what view to show in the ui
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    //this one will be the observable containing the data to be shown in the ui
    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()

    val getViewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<MainViewState> = Transformations.switchMap(_stateEvent){ stateEvent ->

        stateEvent?.let {
            handleResponse(stateEvent)
        }

    }
    fun handleResponse(stateEvent: MainStateEvent) : LiveData<MainViewState> {
        when (stateEvent) {

            is GetBlogPostEvent -> {
                return AbsentLiveData.create()
            }
            is GetUserEvent -> {
                return AbsentLiveData.create()
            }
            is None -> {
                return AbsentLiveData.create()
            }

        }
    }
}