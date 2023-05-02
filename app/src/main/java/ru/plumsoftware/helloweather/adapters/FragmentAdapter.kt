package ru.plumsoftware.helloweather.adapters

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.plumsoftware.helloweather.fragments.BlankFragment

class FragmentAdapter(
    var context: Context,
    var activity: FragmentActivity
) : FragmentStateAdapter (activity){
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = BlankFragment()

        fragment.arguments = Bundle().apply {
            putInt("part", position)
        }

        return fragment
    }
}