package editcom.vialsoft.mvipractice.util

/**
 * This class is a copy of the Rersource class created by Google (also used in the newsApp from
 * Philipp lackner tutorial, with the purpouse the keep track of the current state of the data
 * coming from the server.
 */
data class DataState<T>(
    var data: Event<T>? = null,
    var isLoading: Boolean = false,
    var errorMessage: Event<String>? = null
) {

    companion object {

        fun <T> data(data: T? = null, errorMessage: String?): DataState<T> {
            return DataState(data = Event.dataEvent(data), isLoading = false, errorMessage = Event.messageEvent(errorMessage))
        }

        fun <T> loading(isLoading: Boolean): DataState<T> {
            return DataState(data = null, isLoading = isLoading, errorMessage = null)
        }

        fun <T> error(message: String): DataState<T> {
            return DataState(data = null, isLoading = false, errorMessage = Event(message))
        }

    }
}