package app.iagri.todonotes.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import app.iagri.todonotes.R

class OnBoardingTwoFragment : Fragment() {

    lateinit var buttonDone: Button
    lateinit var buttonPrevious: Button
    lateinit var onOptionClick: OnOptionClick

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onOptionClick = context as OnOptionClick
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
    }

    private fun bindViews(view: View) {
        buttonDone = view.findViewById(R.id.buttonDone)
        buttonPrevious =view.findViewById(R.id.buttonPrevious)
        clickListeners()
    }

    private fun clickListeners() {
        buttonDone.setOnClickListener {
            onOptionClick.onOptionDone()
        }
        buttonPrevious.setOnClickListener {
            onOptionClick.onOptionBack()
        }
    }

    interface OnOptionClick{
        fun onOptionBack()
        fun onOptionDone()
    }
}