package app.iagri.todonotes.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FargmentAdapter(fragmentManager: FragmentActivity) : FragmentStateAdapter(fragmentManager) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnBoardingOneFragment()
            1 -> OnBoardingTwoFragment()
            else -> OnBoardingOneFragment()
        }
    }
}