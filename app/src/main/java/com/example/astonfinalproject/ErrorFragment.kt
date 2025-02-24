package com.example.astonfinalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.astonfinalproject.presentation.activity.INITIAL_FRAGMENT

class ErrorFragment : Fragment() {

    private lateinit var refreshIcon: ImageButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_error, container, false)
        refreshIcon = view.findViewById(R.id.refreshIcon)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshIcon.setOnClickListener {
            parentFragmentManager.beginTransaction().detach(this).commit()
            parentFragmentManager.popBackStack(INITIAL_FRAGMENT, 0)
        }
    }
}