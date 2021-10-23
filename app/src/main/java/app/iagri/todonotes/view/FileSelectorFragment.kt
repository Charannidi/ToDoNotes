package app.iagri.todonotes.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import app.iagri.todonotes.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FileSelectorFragment : BottomSheetDialogFragment() {
    companion object {
        const val TAG = "FileSelectorFragment"
        fun newInstance() = FileSelectorFragment()
    }

    private lateinit var textViewCamera: TextView
    private lateinit var textViewGallery: TextView
    private lateinit var onOptionClickListener: onOptionClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onOptionClickListener = context as onOptionClickListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_selector, container, false)
        bindView(view)
        clickListeners()
        return view
    }

    private fun clickListeners() {
        textViewGallery.setOnClickListener {
            onOptionClickListener.onGalleryClick()
            dismiss()
        }
        textViewCamera.setOnClickListener {
           onOptionClickListener.onCameraClick()
            dismiss()
        }
    }

    private fun bindView(view: View) {
        textViewCamera = view.findViewById(R.id.textViewCamera)
        textViewGallery = view.findViewById(R.id.textViewGallery)
    }
}