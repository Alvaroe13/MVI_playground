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

    protected val mediatorResult = MediatorLiveData<DataState<ViewStateType>>()

    init {
        mediatorResult.value = DataState.loading(true)

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


    fun processNetworkCall(response: GenericApiResponse<ResponseObject>) {
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
        mediatorResult.value = DataState.error(message)
    }
    abstract fun successResponse(response: ApiSuccessResponse<ResponseObject>)

    abstract fun callToApiService(): LiveData<GenericApiResponse<ResponseObject>>

    fun responseAsLiveData() = mediatorResult as LiveData<DataState<ViewStateType>>
}