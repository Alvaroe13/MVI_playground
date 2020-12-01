package editcom.vialsoft.mvipractice.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import editcom.vialsoft.mvipractice.R
import editcom.vialsoft.mvipractice.ui.main.state.MainStateEvent
import editcom.vialsoft.mvipractice.ui.main.state.MainStateEvent.*

private const val TAG = "MainFragDebug"

class MainFragment : Fragment() {

    lateinit var viewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_single_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated: called")
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
            Log.d(TAG, "subscribeObservers: called")

            dataState.blogList?.let { blogPostList ->
                //post from server
                viewModel.setBlogList(blogPostList)
            }

            dataState.user?.let { user ->
                //user info from server
                viewModel.setUser(user)
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
}