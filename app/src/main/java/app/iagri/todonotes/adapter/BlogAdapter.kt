package app.iagri.todonotes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.iagri.todonotes.R
import app.iagri.todonotes.model.Data
import com.bumptech.glide.Glide

class BlogAdapter(val list: List<Data>) : RecyclerView.Adapter<BlogAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_blog, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val blog = list[position]
        val title = blog.title
        val description = blog.description

        holder.textViewTitle.text = title
        holder.textViewDescription.text = description

        Glide.with(holder.itemView).load(blog.img_url).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}