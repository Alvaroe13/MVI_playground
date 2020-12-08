package editcom.vialsoft.mvipractice.ui.main

import editcom.vialsoft.mvipractice.util.Resource

interface DataStateListener {

    fun onDataChange(resource : Resource<*>?)
}