@file:Suppress("RedundantOverride")

package app.iagri.todonotes

import android.app.Application
import app.iagri.todonotes.db.NotesDatabase
import com.androidnetworking.AndroidNetworking

class NotesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(applicationContext)
    }

    fun getNotesDb(): NotesDatabase {
        return NotesDatabase.getInstance(this)
    }
}