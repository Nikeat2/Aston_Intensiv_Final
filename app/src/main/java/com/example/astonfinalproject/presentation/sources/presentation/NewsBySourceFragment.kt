package com.example.astonfinalproject.presentation.sources.presentation

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
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.astonfinalproject.R
import com.example.astonfinalproject.data.data.models.headlines.Article
import com.example.astonfinalproject.data.data.models.sources.Source
import com.example.astonfinalproject.domain.headlines.OnArticleClick
import com.example.astonfinalproject.presentation.article_screen.ArticleFragment
import com.example.astonfinalproject.presentation.filters_screen.FiltersFragment
import com.example.astonfinalproject.presentation.headlines.view.HEADLINES_FRAGMENT
import com.example.astonfinalproject.presentation.sources.data.GetArticlesBySourceImpl
import io.reactivex.rxjava3.disposables.CompositeDisposable

class NewsBySourceFragment : Fragment(), OnArticleClick {
    private lateinit var sourceName: String
    private lateinit var newsBySourceRecyclerView: RecyclerView
    private lateinit var adapter: NewsBySourceAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private val listOfArticlesBySource = mutableListOf<Article>()
    private val getArticlesBySource = GetArticlesBySourceImpl()
    private var page = 1
    private val compositeDisposable = CompositeDisposable()
    private var isLoading = false
    private lateinit var titleTextView: TextView
    private lateinit var filterButton: ImageButton
    private lateinit var searchButton: ImageButton
    private val filterFragment = FiltersFragment.newInstance()
    private lateinit var editTextForSearching: EditText
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var arrowBackButton: ImageButton
    private lateinit var fragmentManagerNewsBySource: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sourceName = it.getString("key").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_by_source_fragment, container, false)
        fragmentManagerNewsBySource = parentFragmentManager
        initViews(view)
        setOnClickListeners()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isLoading = true
        getArticlesBySource.execute(
            listOfArticles = adapter.currentList,
            adapter = adapter,
            page = page,
            source = sourceName,
            fragmentManager = fragmentManagerNewsBySource
        )
        isLoading = false
    }

    override fun onResume() {
        super.onResume()
        addOnScrollListenerToRecyclerView(newsBySourceRecyclerView)
        setOnTextChangedListener(editTextForSearching)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

    override fun onClick(position: Int, article: Article) {
        val articleUserWantsToSee = adapter.currentList[position]
        val articleFragment = ArticleFragment.newInstance(articleUserWantsToSee)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, articleFragment).addToBackStack(HEADLINES_FRAGMENT)
            .commit()
    }

    private fun initViews(view: View) {
        newsBySourceRecyclerView = view.findViewById(R.id.newsBySourceRecyclerView)
        adapter = NewsBySourceAdapter(this)
        titleTextView = view.findViewById(R.id.titleTextView)
        titleTextView.text = sourceName.uppercase()
        filterButton = view.findViewById(R.id.filterButton)
        searchButton = view.findViewById(R.id.searchButton)
        editTextForSearching = view.findViewById(R.id.editTextForSearching)
        layoutManager = LinearLayoutManager(requireContext())
        newsBySourceRecyclerView.adapter = adapter
        arrowBackButton = view.findViewById(R.id.arrowBackButton)
        newsBySourceRecyclerView.layoutManager = layoutManager
        adapter.submitList(listOfArticlesBySource)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
    }

    private fun setOnClickListeners() {
        filterButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, filterFragment).commit()
        }

        searchButton.setOnClickListener {
            editTextForSearching.visibility = View.VISIBLE
            titleTextView.visibility = View.GONE
            filterButton.visibility = View.GONE
            arrowBackButton.visibility = View.VISIBLE
            searchButton.visibility = View.GONE
        }

        arrowBackButton.setOnClickListener {
            editTextForSearching.visibility = View.GONE
            titleTextView.visibility = View.VISIBLE
            filterButton.visibility = View.VISIBLE
            arrowBackButton.visibility = View.GONE
            searchButton.visibility = View.VISIBLE
            editTextForSearching.text.clear()
        }

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            listOfArticlesBySource.clear()
            isLoading = true
            getArticlesBySource.execute(
                listOfArticles = listOfArticlesBySource,
                adapter = adapter,
                page = 1,
                source = sourceName.lowercase(),
                fragmentManager = fragmentManagerNewsBySource
            )
            adapter.notifyDataSetChanged()
            isLoading = false
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun addOnScrollListenerToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!isLoading && !recyclerView.canScrollVertically(1)) {
                    isLoading = true
                    page++
                    getArticlesBySource.execute(
                        listOfArticles = adapter.currentList,
                        adapter = adapter,
                        source = sourceName,
                        page = page,
                        fragmentManager = fragmentManagerNewsBySource
                    )
                    isLoading = false
                }
            }
        })
    }

    private fun setOnTextChangedListener(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                if (!isLoading) {
                    if (query.length >= 3) {
                        isLoading = true
                        getArticlesBySource.execute(
                            listOfArticles = listOfArticlesBySource,
                            adapter = adapter,
                            page = 1,
                            source = query,
                            fragmentManager = fragmentManagerNewsBySource
                        )
                        isLoading = false
                    }
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(source: Source) =
            NewsBySourceFragment().apply {
                arguments = Bundle().apply {
                    putString("key", source.name)
                }
            }
    }
}