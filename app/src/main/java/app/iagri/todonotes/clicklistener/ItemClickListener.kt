package app.iagri.todonotes.clicklistener

import app.iagri.todonotes.db.Notes


interface ItemClickListener {
    fun onClick(notes: Notes)
    fun onUpdate(notes: Notes)
}