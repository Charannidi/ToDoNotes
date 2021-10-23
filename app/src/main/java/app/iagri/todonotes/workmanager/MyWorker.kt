package app.iagri.todonotes.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import app.iagri.todonotes.NotesApp

class MyWorker(val context: Context, val workerParameters: WorkerParameters): Worker(context,workerParameters) {
    override fun doWork(): Result {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.deleteNotes(true)
        return Result.success()
    }
}