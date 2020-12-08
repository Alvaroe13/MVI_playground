package editcom.vialsoft.mvipractice.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import editcom.vialsoft.mvipractice.R
import editcom.vialsoft.mvipractice.ui.main.state.MainStateEvent.GetBlogPostEvent
import editcom.vialsoft.mvipractice.ui.main.state.MainStateEvent.GetUserEvent
import java.lang.ClassCastException

private const val TAG = "MainFragDebug"

class MainFragment : Fragment() {

    lateinit var viewModel: MainViewModel

    lateinit var dataStateListener: DataStateListener


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_single_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        viewModel = (activity as MainActivity).viewModel
        subscribeObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuGetUsers -> {
                triggerGetUsers()
            }
            R.id.menuGetBlogs -> {
                triggerGetBlogList()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun triggerGetUsers() {
        viewModel.setStateEvent(GetUserEvent("1"))
    }

    private fun triggerGetBlogList() {
        viewModel.setStateEvent(GetBlogPostEvent())
    }

    private fun subscribeObservers() {
        Log.d(TAG, "subscribeObservers: triggered")
        dataStateObserver()
        viewStateObserver()
    }

    /**
     * This observable will fetch the data from the server and will send it back to the ViewModel
     * to be processed, once that's done, that data will be fetched by the "getViewState" observable
     * and set into the UI
     */
    private fun dataStateObserver(){
        Log.d(TAG, "dataStateObserver: called")

        viewModel.getResource.observe(viewLifecycleOwner, { dataState ->
            Log.d(TAG, "subscribeObservers: called, dataState= $dataState")

            //handle loading progress and error message to show in MainActivity
            dataStateListener.onDataChange(dataState)


            //handle data
            dataState.data?.let { event ->

                event.getContentIfNotHandled()?.let {mainViewState ->

                    mainViewState.blogList?.let { blogPostList ->
                        //post from server
                        viewModel.setBlogList(blogPostList)
                    }

                    mainViewState.user?.let { user ->
                        //user info from server
                        viewModel.setUser(user)
                    }

                }
            }
        })
    }

    /**
     * This is the obervavle that will bring the data alreadyt process from the ViewModel ready to
     * be set in th eUI
     */
    private fun viewStateObserver(){
        Log.d(TAG, "viewStateObserver: called")

        viewModel.getViewState.observe(viewLifecycleOwner, { viewState ->

            Log.d(TAG, "viewStateObserver: voewState= $viewState")

            viewState.blogList?.let {
                //info from server
                Log.d(TAG, "subscribeObservers: getViewState blogpost= ${it.size}")
            }

            viewState.user?.let {
                //info from server
                Log.d(TAG, "subscribeObservers: getViewState user= ${it.username}")
            }

        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateListener = context as DataStateListener
        }catch ( e: ClassCastException){
            Log.d(TAG, "onAttach: Error binding listener to Fragment")
        }
    }
}