package editcom.vialsoft.mvipractice.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * This class is for adding padding to the recyclerView item only
 */
class RecyclerViewDecoration(private val padding: Int) : RecyclerView.ItemDecoration (){
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        //just setting the padding top in the recyclerView item programmatically
        outRect.top = padding
    }
}