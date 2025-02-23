package com.example.astonfinalproject.presentation.sources.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.astonfinalproject.ErrorFragment
import com.example.astonfinalproject.R
import com.example.astonfinalproject.data.data.models.sources.Source
import com.example.astonfinalproject.presentation.sources.data.GetSourcesImpl
import com.example.astonfinalproject.presentation.sources.data.OnSourceClick
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val SOURCES_FRAGMENT = "SOURCES_FRAGMENT"

class SourcesFragment : Fragment(), OnSourceClick {

    private lateinit var sourcesRecyclerView: RecyclerView
    private lateinit var adapter: SourcesAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private val listOfSources = mutableListOf<Source>()
    private val getSourcesImpl = GetSourcesImpl()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var searchButton: ImageButton
    private lateinit var filterButton: ImageButton
    private lateinit var fragmentManagerSources: FragmentManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sources, container, false)
        sourcesRecyclerView = view.findViewById(R.id.sourcesRecyclerView)
        adapter = SourcesAdapter(listOfSources, this)
        layoutManager = LinearLayoutManager(requireContext())
        sourcesRecyclerView.adapter = adapter
        sourcesRecyclerView.layoutManager = layoutManager
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        searchButton = view.findViewById(R.id.searchButton)
        filterButton = view.findViewById(R.id.filterButton)
        fragmentManagerSources = parentFragmentManager
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            getSourcesImpl.execute(
                listOfSources = listOfSources,
                adapter = adapter,
                fragmentManagerSources
            )
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            CoroutineScope(Dispatchers.IO).launch {
                getSourcesImpl.execute(
                    listOfSources = listOfSources,
                    adapter = adapter,
                    fragmentManagerSources
                )
            }
            swipeRefreshLayout.isRefreshing = false
        }
    }



    override fun onSourceClick(source: Source) {
        val newsBySourceFragment = NewsBySourceFragment.newInstance(source)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, newsBySourceFragment).addToBackStack(
                SOURCES_FRAGMENT
            ).commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SourcesFragment()
    }

}