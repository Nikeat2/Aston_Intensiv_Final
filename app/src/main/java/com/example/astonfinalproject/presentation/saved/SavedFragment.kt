package com.example.astonfinalproject.presentation.saved

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.astonfinalproject.R
import com.example.astonfinalproject.data.data.SavedHeadlines
import com.example.astonfinalproject.data.data.models.headlines.Article
import com.example.astonfinalproject.domain.headlines.OnArticleClick
import com.example.astonfinalproject.presentation.article_screen.ArticleFragment
import com.example.astonfinalproject.presentation.filters_screen.FiltersFragment
import com.example.astonfinalproject.presentation.headlines.view.HEADLINES_FRAGMENT
import com.example.astonfinalproject.presentation.headlines.view.HeadlinesAdapter

class SavedFragment : Fragment(), OnArticleClick {

    private lateinit var savedSearchButton: ImageButton
    private lateinit var savedFilterButton: ImageButton
    private lateinit var savedHeadlinesRecyclerView: RecyclerView
    private lateinit var adapter: HeadlinesAdapter
    private val listOfSavedHeadlines = mutableListOf<Article>()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var editTextForSearching: EditText
    private lateinit var savedTitleTextView: TextView
    private val filterFragment = FiltersFragment.newInstance()
    private lateinit var arrowBackButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_saved, container, false)
        initViews(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            adapter.submitList(listOfSavedHeadlines)
            swipeRefreshLayout.isRefreshing = false
        }
        setOnTextChangedListener(editTextForSearching)
        setOnClickListeners()
    }

    override fun onClick(position: Int, article: Article) {
        val articleUserWantsToSee = SavedHeadlines.listOfSavedHeadlines[position]
        val articleFragment = ArticleFragment.newInstance(articleUserWantsToSee)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, articleFragment).addToBackStack("Saved Fragment")
            .commit()
    }

    private fun initViews(view: View) {
        savedSearchButton = view.findViewById(R.id.savedSearchButton)
        savedFilterButton = view.findViewById(R.id.savedFilterButton)
        savedHeadlinesRecyclerView = view.findViewById(R.id.savedHeadlinesRecyclerView)
        adapter = HeadlinesAdapter(this)
        savedHeadlinesRecyclerView.adapter = adapter
        savedHeadlinesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        editTextForSearching = view.findViewById(R.id.editTextForSearching)
        savedTitleTextView = view.findViewById(R.id.savedTitleTextView)
        arrowBackButton = view.findViewById(R.id.arrowBackButton)
        listOfSavedHeadlines.clear()
        listOfSavedHeadlines.addAll(SavedHeadlines.listOfSavedHeadlines)
        adapter.submitList(listOfSavedHeadlines)
    }

    private fun setOnTextChangedListener(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                adapter.filter(query)
            }
        })
    }

    private fun setOnClickListeners() {
        savedSearchButton.setOnClickListener {
            savedTitleTextView.visibility = View.GONE
            savedFilterButton.visibility = View.GONE
            savedSearchButton.visibility = View.GONE
            arrowBackButton.visibility = View.VISIBLE
            editTextForSearching.visibility = View.VISIBLE
        }

        savedFilterButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, filterFragment).addToBackStack(
                    HEADLINES_FRAGMENT
                ).commit()
        }

        arrowBackButton.setOnClickListener {
            savedTitleTextView.visibility = View.VISIBLE
            savedFilterButton.visibility = View.VISIBLE
            savedSearchButton.visibility = View.VISIBLE
            arrowBackButton.visibility = View.GONE
            editTextForSearching.visibility = View.GONE
            editTextForSearching.text.clear()
            adapter.submitList(SavedHeadlines.listOfSavedHeadlines)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SavedFragment()
    }
}