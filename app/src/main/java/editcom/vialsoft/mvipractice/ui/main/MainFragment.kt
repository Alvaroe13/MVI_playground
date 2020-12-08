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
                getUsers()
            }
            R.id.menuGetBlogs -> {
                getBlogList()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getUsers() {
        viewModel.setStateEvent(GetUserEvent("1"))
    }

    private fun getBlogList() {
        viewModel.setStateEvent(GetBlogPostEvent())
    }

    private fun subscribeObservers() {
        Log.d(TAG, "subscribeObservers: called")

        viewModel.getDataState.observe(viewLifecycleOwner, { dataState ->
            Log.d(TAG, "subscribeObservers: called, dataState= ${dataState}")

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

        viewModel.getViewState.observe(viewLifecycleOwner, { viewState ->
            Log.d(TAG, "subscribeObservers: called")

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