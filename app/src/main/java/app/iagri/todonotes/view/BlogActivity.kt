package app.iagri.todonotes.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.iagri.todonotes.R
import app.iagri.todonotes.adapter.BlogAdapter
import app.iagri.todonotes.model.jsonResponse
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener

class BlogActivity : AppCompatActivity() {
    lateinit var recyclerViewBlog: RecyclerView
    val TAG = "BlogActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)
        bindViews()
        getBlogs()
    }

    private fun getBlogs() {
        AndroidNetworking.get("https://www.mocky.io/v2/5926ce9d11000096006ccb30")
                .setPriority(Priority.HIGH)
                .build()
                .getAsObject(jsonResponse::class.java,object : ParsedRequestListener<jsonResponse>{
                    override fun onResponse(response: jsonResponse?) {
                        setUpRecyclerView(response)
                    }

                    override fun onError(anError: ANError?) {
                        Log.d(TAG, anError?.localizedMessage!!)
                    }

                })

    }

    private fun bindViews() {
        recyclerViewBlog = findViewById(R.id.recyclerViewBlog)
    }

    private fun setUpRecyclerView(response: jsonResponse?) {
        val blogAdapter = BlogAdapter(response!!.data)
        val linearLayoutManager = LinearLayoutManager(this@BlogActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewBlog.layoutManager = linearLayoutManager
        recyclerViewBlog.adapter = blogAdapter
    }
}