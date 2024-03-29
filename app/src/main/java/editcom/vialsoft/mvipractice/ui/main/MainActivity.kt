 package editcom.vialsoft.mvipractice.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import editcom.vialsoft.mvipractice.R
import editcom.vialsoft.mvipractice.databinding.ActivityMainBinding
import editcom.vialsoft.mvipractice.util.DataState

 private const val TAG = "MaiActDebug"

class MainActivity : AppCompatActivity(), DataStateListener {

    lateinit var viewModel : MainViewModel

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        inflateMainFragment()
    }

    fun initViewModel(){
        Log.d(TAG, "called: ")
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java);
    }

    fun inflateMainFragment(){
        Log.d(TAG, "inflateMainFragment: called")
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment() , "MainFragment")
            .commit()
    }

    override fun onDataChange(dataState: DataState<*>?) {
         handleDataState(dataState)
    }

    private fun handleDataState(dataState: DataState<*>?) {

        dataState?.let { dataStateVal ->


            dataStateVal.errorMessage?.let {event->

                event.getContentIfNotHandled()?.let{message ->
                    showToast(message)
                }

            }

            dataStateVal.isLoading.let { loadingState->
                progressBarState(loadingState)
            }

        }
    }

    private fun progressBarState(isLoading: Boolean) {
        if (isLoading){
            binding.progressSomething.visibility = View.VISIBLE
        }else{
            binding.progressSomething.visibility = View.INVISIBLE
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(this , message, Toast.LENGTH_SHORT).show()
    }

}