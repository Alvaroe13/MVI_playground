package editcom.vialsoft.mvipractice.ui.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import editcom.vialsoft.mvipractice.R
import editcom.vialsoft.mvipractice.databinding.FragmentMainLayoutBinding
import editcom.vialsoft.mvipractice.model.BlogPost
import editcom.vialsoft.mvipractice.ui.main.adapter.BlogPostListAdapter
import editcom.vialsoft.mvipractice.ui.main.state.MainStateEvent.GetBlogPostEvent
import editcom.vialsoft.mvipractice.ui.main.state.MainStateEvent.GetUserEvent
import editcom.vialsoft.mvipractice.util.RecyclerViewDecoration
import java.lang.ClassCastException

private const val TAG = "MainFragDebug"

class MainFragment : Fragment(R.layout.fragment_main_layout), BlogPostListAdapter.Interaction {

    private lateinit var viewModel: MainViewModel
    private lateinit var dataStateListener: DataStateListener
    private lateinit var blogAdapter: BlogPostListAdapter
    private lateinit var binding: FragmentMainLayoutBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainLayoutBinding.bind(view)
        setHasOptionsMenu(true)
        viewModel = (activity as MainActivity).viewModel
        subscribeObservers()
        initRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuGetUsers -> triggerGetUsers()
            R.id.menuGetBlogs -> triggerGetBlogList()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            val paddingTopDecoration = RecyclerViewDecoration(30)
            addItemDecoration(paddingTopDecoration)
            blogAdapter = BlogPostListAdapter(this@MainFragment)
            adapter = blogAdapter
        }
    }

    private fun triggerGetUsers() {
        viewModel.setStateEvent(GetUserEvent("1"))
    }

    private fun triggerGetBlogList() {
        viewModel.setStateEvent(GetBlogPostEvent())
    }

    private fun subscribeObservers() {
        dataStateObserver()
        viewStateObserver()
    }

    /**
     * This observable will fetch the data from the dataSource and will send it back to the ViewModel
     * to be processed, once that's done, that data will be fetched by the "getViewState" observable
     * and set into the UI
     */
    private fun dataStateObserver() {

        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            Log.d(TAG, "subscribeObservers: called, dataState= $dataState")

            //handle loading progress and error message to show in MainActivity
            dataStateListener.onDataChange(dataState)

            dataState.data?.let { event ->

                event.getContentIfNotHandled()?.let { mainViewState ->

                    mainViewState.blogList?.let { blogPostList ->
                        viewModel.setBlogList(blogPostList)
                    }

                    mainViewState.user?.let { user ->
                        viewModel.setUser(user)
                    }

                }
            }
        })
    }

    /**
     * This is the observable that will bring the data already process from the ViewModel ready to
     * be set in th eUI
     */
    private fun viewStateObserver() {
        viewModel.getViewState.observe(viewLifecycleOwner, { viewState ->

            Log.d(TAG, "viewStateObserver: viewState= $viewState")

            viewState.blogList?.let { blogPostList ->
                //TODO feed recyclerVie with it
                Log.d(TAG, "viewStateObserver: list size= ${blogPostList.size}")
                // blogAdapter.submitList(blogPostList)
            }

            viewState.user?.let {
                //TODO set it in the ui
                Log.d(TAG, "viewStateObserver: getViewState user= ${it.username}")
            }

        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateListener = context as DataStateListener
        } catch (e: ClassCastException) {
            Log.d(TAG, "onAttach: Error binding listener to Fragment")
        }
    }

    override fun onItemSelected(position: Int, item: BlogPost) {
        Log.d(TAG, "onItemSelected: clicked $position")
        Log.d(TAG, "onItemSelected: post clicked title = ${item.title}")
    }
}