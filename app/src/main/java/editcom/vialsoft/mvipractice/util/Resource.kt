package editcom.vialsoft.mvipractice.util

/**
 * This class is a copy of the Rersource class created by Google (also used in the newsApp from
 * Philipp lackner tutorial, with the porpoise the keep track of the current state of the data
 * coming from the server.
 */
data class Resource<T>(
    var data: Event<T>? = null,
    var isLoading: Boolean = false,
    var errorMessage: Event<String>? = null
) {

    companion object {

        fun <T> data(data: T? = null, errorMessage: String?): Resource<T> {
            return Resource(data = Event.dataEvent(data), isLoading = false, errorMessage = Event.messageEvent(errorMessage))
        }

        fun <T> loading(isLoading: Boolean): Resource<T> {
            return Resource(data = null, isLoading = isLoading, errorMessage = null)
        }

        fun <T> error(message: String): Resource<T> {
            return Resource(data = null, isLoading = false, errorMessage = Event(message))
        }

    }
}