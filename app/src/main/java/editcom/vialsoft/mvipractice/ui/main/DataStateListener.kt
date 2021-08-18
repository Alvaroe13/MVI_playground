package editcom.vialsoft.mvipractice.ui.main

import editcom.vialsoft.mvipractice.util.DataState

interface DataStateListener {

    fun onDataChange(dataState : DataState<*>?)
}