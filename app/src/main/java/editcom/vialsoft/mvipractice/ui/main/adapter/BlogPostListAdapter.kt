package editcom.vialsoft.mvipractice.ui.main.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import editcom.vialsoft.mvipractice.R
import editcom.vialsoft.mvipractice.model.BlogPost
import kotlinx.android.synthetic.main.fragment_main_layout.view.*
import kotlinx.android.synthetic.main.list_single_layout.view.*

class BlogPostListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BlogPost>() {

        override fun areItemsTheSame(oldItem: BlogPost, newItem: BlogPost): Boolean {
            return oldItem.pk == newItem.pk
        }

        @SuppressLint("DiffUtilEquals") //this annotation is for the useless warning on "=="
        override fun areContentsTheSame(oldItem: BlogPost, newItem: BlogPost): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return BlogPostViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_blog_post_single_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BlogPostViewHolder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<BlogPost>) {
        differ.submitList(list)
    }

    class BlogPostViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: BlogPost) = with(itemView) {

            //bind click listener interface
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            //set ui
            itemView.blog_title.text = item.title
            Glide.with(itemView.context)
                .load(itemView.image)
                .into(itemView.blog_image)


        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: BlogPost)
    }
}
