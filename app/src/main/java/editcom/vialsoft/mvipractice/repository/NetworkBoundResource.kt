package editcom.vialsoft.mvipractice.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import editcom.vialsoft.mvipractice.util.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "boundDebug"

abstract class NetworkBoundResource<ResponseObject, ViewStateType> {

    protected val mediatorResult = MediatorLiveData<Resource<ViewStateType>>()

    init {
        mediatorResult.value = Resource.loading(true)

        GlobalScope.launch(IO) {
            delay(1000L) //just for testing and show progressbar

            withContext(Main) {
                val apiResponse = callToApiService()

                mediatorResult.addSource(apiResponse) { response ->

                    mediatorResult.removeSource(apiResponse)
                    processNetworkCall(response)
                }
            }
        }
    }


    /**
     * this function filter the response coming from the network and send it to the ViewModel
     */
    private fun processNetworkCall(response: GenericApiResponse<ResponseObject>) {
        when (response) {

            is ApiSuccessResponse -> {
                successResponse(response)
                Log.d(TAG, "processNetworkCall: response successful")
            }
            is ApiEmptyResponse -> {
                errorMessage("204 HTTP code, response empty!")
                Log.d(TAG, "processNetworkCall: emptyu response")
            }

            is ApiErrorResponse -> {
                errorMessage(response.errorMessage)
                Log.d(TAG, "processNetworkCall: error= ${response.errorMessage}")
            }
        }
    }

    fun errorMessage(message : String){
        mediatorResult.value = Resource.error(message)
    }
    abstract fun successResponse(response: ApiSuccessResponse<ResponseObject>)

    abstract fun callToApiService(): LiveData<GenericApiResponse<ResponseObject>>

    /**
     * just cast MediatorLiveData var to LiveData
     */
    fun responseAsLiveData() = mediatorResult as LiveData<Resource<ViewStateType>>
}