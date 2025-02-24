package com.example.astonfinalproject.presentation.headlines.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.astonfinalproject.R
import com.example.astonfinalproject.data.data.api.GetArticlesByUserTextInputImpl
import com.example.astonfinalproject.data.data.api.GetNewsFromApiRetrofitImpl
import com.example.astonfinalproject.data.data.models.headlines.Article
import com.example.astonfinalproject.data.data.models.headlines.HeadlinesToolBarTabs
import com.example.astonfinalproject.data.data.CompositeDisposableInstance
import com.example.astonfinalproject.domain.headlines.OnArticleClick
import com.example.astonfinalproject.presentation.article_screen.ArticleFragment
import com.example.astonfinalproject.presentation.filters_screen.CHOSEN_DATE
import com.example.astonfinalproject.presentation.filters_screen.CHOSEN_LANGUAGE
import com.example.astonfinalproject.presentation.filters_screen.CHOSEN_POPULARITY
import com.example.astonfinalproject.presentation.filters_screen.FiltersFragment
import com.example.astonfinalproject.presentation.filters_screen.GetArticlesByFiltersImpl

const val HEADLINES_FRAGMENT = "HEADLINES_FRAGMENT"
const val FILTERS_GOTTEN = "FILTERS_GOTTEN"

class HeadlinesFragment : Fragment(), OnArticleClick {
    var chosenLanguage: String? = null
    var chosenDate: String? = null
    var chosenPopularity: String? = null
    private lateinit var adapter: HeadlinesAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var recyclerViewHeadlines: RecyclerView
    private lateinit var headlinesToolBar: RecyclerView
    private lateinit var headlinesToolBarAdapter: HeadlinesToolBarAdapter
    private lateinit var listOfArticles: MutableList<Article>
    private lateinit var editTextForSearching: EditText
    private var topAppBar: Toolbar? = null
    private var searchButton: ImageButton? = null
    private var filterButton: ImageButton? = null
    private var page = 1
    private var topic = "general"
    private val getNewsApiRetrofitImpl = GetNewsFromApiRetrofitImpl()
    var isLoading = false
    private lateinit var arrowBackButton: ImageButton
    private val filterFragment = FiltersFragment.newInstance()
    private val getArticlesByFilters = GetArticlesByFiltersImpl()
    private var isFilterApplied = false
    private val getArticlesByUserTextInput = GetArticlesByUserTextInputImpl()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var fragmentManagerHeadlines: FragmentManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_headlines, container, false)
        initViews(view)
        setOnClickListeners()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener()
        headlinesToolBar.adapter = headlinesToolBarAdapter
        addOnScrollListenerToRecyclerView(recyclerViewHeadlines)
    }

    override fun onResume() {
        super.onResume()
        if (!isFilterApplied) {
            isLoading = true
            getNewsApiRetrofitImpl.execute(
                listOfArticles = listOfArticles,
                topic = "general",
                adapter = adapter,
                page = 1,
                fragmentManager = fragmentManagerHeadlines
            )
            isLoading = false
            adapter.submitList(adapter.currentList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        CompositeDisposableInstance.compositeDisposable.clear()
    }

    override fun onClick(position: Int, article: Article) {
        val articleUserWantsToSee = adapter.currentList[position]
        val articleFragment = ArticleFragment.newInstance(articleUserWantsToSee)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, articleFragment).addToBackStack(HEADLINES_FRAGMENT)
            .commit()
    }

    private fun initViews(view: View) {
        recyclerViewHeadlines = view.findViewById(R.id.recyclerViewHeadlines)
        headlinesToolBar = view.findViewById(R.id.headlinesToolBar)
        topAppBar = view.findViewById(R.id.topToolBar)
        editTextForSearching = view.findViewById(R.id.editTextForSearching)
        (requireActivity() as AppCompatActivity).setSupportActionBar(topAppBar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        searchButton = view.findViewById(R.id.searchButton)
        filterButton = view.findViewById(R.id.filterButton)
        headlinesToolBar.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        layoutManager = LinearLayoutManager(requireContext())
        listOfArticles = mutableListOf()
        adapter = HeadlinesAdapter(this)
        recyclerViewHeadlines.adapter = adapter
        recyclerViewHeadlines.layoutManager = layoutManager
        editTextForSearching = view.findViewById(R.id.editTextForSearching)
        arrowBackButton = view.findViewById(R.id.arrowBackButton)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        fragmentManagerHeadlines = parentFragmentManager
    }

    private fun addOnScrollListenerToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                when (isFilterApplied) {
                    false ->
                        if (!isLoading && !recyclerView.canScrollVertically(1)) {
                            isLoading = true
                            page++
                            getNewsApiRetrofitImpl.execute(
                                adapter.currentList,
                                topic,
                                adapter,
                                page,
                                fragmentManager = fragmentManagerHeadlines
                            )
                            isLoading = false
                        }
                    else -> {
                        if (!isLoading && !recyclerView.canScrollVertically(1)) {
                            isLoading = true
                            page++
                            getArticlesByFilters.execute(
                                adapter = adapter,
                                date = chosenDate,
                                language = chosenLanguage,
                                listOfArticles = adapter.currentList,
                                page = page,
                                popularity = chosenPopularity,
                                fragmentManager = fragmentManagerHeadlines
                            )
                            isLoading = false
                        }
                    }
                }
            }
        })
    }

    private fun setFragmentResultListener() {
        parentFragmentManager.setFragmentResultListener(
            FILTERS_GOTTEN, viewLifecycleOwner
        ) { _, bundle ->
            isFilterApplied = true
            val gottenFilters = bundle.getBundle(FILTERS_GOTTEN)
            chosenLanguage = gottenFilters?.getString(CHOSEN_LANGUAGE)
            chosenDate = gottenFilters?.getString(CHOSEN_DATE)
            chosenPopularity = gottenFilters?.getString(CHOSEN_POPULARITY)
            listOfArticles.clear()
            page = 1
            headlinesToolBar.visibility = View.GONE
            isLoading = true
            getArticlesByFilters.execute(
                adapter = adapter,
                date = chosenDate.toString(),
                language = chosenLanguage.toString(),
                page = page,
                popularity = chosenPopularity.toString(),
                listOfArticles = listOfArticles,
                fragmentManager = fragmentManagerHeadlines
            )
            isLoading = false
        }
    }

    private fun setOnClickListeners() {
        searchButton?.setOnClickListener {
            topAppBar?.visibility = View.GONE
            editTextForSearching.visibility = View.VISIBLE
        }

        arrowBackButton.setOnClickListener {
            topAppBar?.visibility = View.VISIBLE
            editTextForSearching.visibility = View.GONE
            editTextForSearching.text.clear()
        }

        filterButton?.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, filterFragment).addToBackStack(
                    HEADLINES_FRAGMENT
                ).commit()
        }

        headlinesToolBarAdapter =
            HeadlinesToolBarAdapter(listOfTabs = HeadlinesToolBarTabs.tabs) { tab ->
                isFilterApplied = false
                when (tab.tabText) {
                    "General" -> topic = "general"
                    "Business" -> topic = "business"
                    "Travelling" -> topic = "travelling"
                    "Health" -> topic = "health"
                    "Entertainment" -> topic = "entertainment"
                    "Technology" -> topic = "technology"
                }
                getNewsApiRetrofitImpl.execute(
                    listOfArticles,
                    topic,
                    adapter,
                    page,
                    fragmentManagerHeadlines
                )
            }

        setOnTextChangedListener(editTextForSearching)

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            getNewsApiRetrofitImpl.execute(
                listOfArticles,
                topic,
                adapter,
                page = 1,
                fragmentManager = fragmentManagerHeadlines
            )
            swipeRefreshLayout.isRefreshing = false
        }
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
                        getArticlesByUserTextInput.execute(
                            adapter = adapter,
                            listOfArticles = listOfArticles,
                            page = 1,
                            topic = query
                        )
                        isLoading = false
                    }
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = HeadlinesFragment()
    }
}