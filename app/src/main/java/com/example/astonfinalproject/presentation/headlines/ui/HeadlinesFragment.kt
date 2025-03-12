package com.example.astonfinalproject.presentation.headlines.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.astonfinalproject.R
import com.example.astonfinalproject.data.data.CompositeDisposableInstance
import com.example.astonfinalproject.data.data.models.headlines.Article
import com.example.astonfinalproject.data.data.models.headlines.HeadlinesToolBarTabs
import com.example.astonfinalproject.databinding.FragmentHeadlinesBinding
import com.example.astonfinalproject.di.App
import com.example.astonfinalproject.domain.headlines.OnArticleClick
import com.example.astonfinalproject.presentation.activity.MainActivity
import com.example.astonfinalproject.presentation.article_screen.view.ArticleFragment
import com.example.astonfinalproject.presentation.article_screen.viewmodel.ArticleFragmentViewModel
import com.example.astonfinalproject.presentation.filters_screen.CHOSEN_DATE
import com.example.astonfinalproject.presentation.filters_screen.CHOSEN_LANGUAGE
import com.example.astonfinalproject.presentation.filters_screen.CHOSEN_POPULARITY
import com.example.astonfinalproject.presentation.filters_screen.FiltersFragment
import com.example.astonfinalproject.presentation.headlines.model.ArticleRepository
import com.example.astonfinalproject.presentation.headlines.presenter.ArticlePresenter
import com.example.astonfinalproject.presentation.headlines.view.ArticleView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

const val HEADLINES_FRAGMENT = "HEADLINES_FRAGMENT"
const val FILTERS_GOTTEN = "FILTERS_GOTTEN"
const val HEADLINES_FRAGMENT_GONE_TO_FILTERS = "HEADLINES_FRAGMENT_GONE_TO_FILTERS"
const val ARTICLE_FRAGMENT = "ARTICLE_FRAGMENT"

class HeadlinesFragment : MvpAppCompatFragment(), OnArticleClick, ArticleView {
    var chosenLanguage: String? = null
    var chosenDate: String? = null
    var chosenPopularity: String? = null
    private lateinit var adapter: HeadlinesAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var headlinesToolBarAdapter: HeadlinesToolBarAdapter
    private val filterFragment = FiltersFragment.newInstance()
    private var isFilterApplied = false
    private var _binding: FragmentHeadlinesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var articleRepository: ArticleRepository

    @InjectPresenter
    lateinit var presenter: ArticlePresenter

    @ProvidePresenter
    fun provideArticlePresenter(): ArticlePresenter {
        return ArticlePresenter(articleRepository)
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHeadlinesBinding.inflate(inflater, container, false)
        initViews()
        setOnClickListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener()
        binding.headlinesToolBar.adapter = headlinesToolBarAdapter

        addOnScrollListenerToRecyclerView(binding.recyclerViewHeadlines)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        CompositeDisposableInstance.compositeDisposable.clear()
    }


    override fun onClick(position: Int, article: Article) {
        val articleUserWantsToSee = adapter.currentList[position]
        val viewModel: ArticleFragmentViewModel by activityViewModels()
        viewModel.updateMutableFlow(articleUserWantsToSee)
        val articleFragment = ArticleFragment.newInstance()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, articleFragment).addToBackStack(ARTICLE_FRAGMENT)
            .commit()
    }

    private fun initViews() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.topToolBar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.headlinesToolBar.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        layoutManager = LinearLayoutManager(requireContext())
        adapter = HeadlinesAdapter(this)
        binding.recyclerViewHeadlines.adapter = adapter
        binding.recyclerViewHeadlines.layoutManager = layoutManager
    }

    private fun addOnScrollListenerToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                when (isFilterApplied) {
                    false ->
                        if (!recyclerView.canScrollVertically(1)) {
                            presenter.loadMoreData()
                        }

                    true -> {
                        if (!recyclerView.canScrollVertically(1)) {
                            presenter.loadMoreArticlesByFilters(
                                date = chosenDate.toString(),
                                language = chosenLanguage.toString(),
                                popularity = chosenPopularity.toString()
                            )
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
            binding.headlinesToolBar.visibility = View.GONE
            presenter.loadArticlesByFilters(
                date = chosenDate.toString(),
                language = chosenLanguage.toString(),
                popularity = chosenPopularity.toString()
            )
        }
    }

    private fun setOnClickListeners() {
        binding.searchButton.setOnClickListener {
            binding.topToolBar.visibility = View.GONE
            binding.editTextForSearching.visibility = View.VISIBLE
        }

        binding.arrowBackButton.setOnClickListener {
            binding.topToolBar.visibility = View.VISIBLE
            binding.editTextForSearching.visibility = View.GONE
            binding.editTextForSearching.text.clear()
        }

        binding.filterButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, filterFragment).addToBackStack(
                    HEADLINES_FRAGMENT_GONE_TO_FILTERS
                ).commit()
        }

        headlinesToolBarAdapter =
            HeadlinesToolBarAdapter(listOfTabs = HeadlinesToolBarTabs.tabs) { tab ->
                isFilterApplied = false
                when (tab.tabText) {
                    "General" -> presenter.topic = "general"
                    "Business" -> presenter.topic = "business"
                    "Travelling" -> presenter.topic = "travelling"
                    "Health" -> presenter.topic = "health"
                    "Entertainment" -> presenter.topic = "entertainment"
                    "Technology" -> presenter.topic = "technology"
                }
                presenter.refreshArticles()
            }

        setOnTextChangedListener(binding.editTextForSearching)

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            isFilterApplied = false
            binding.topToolBar.visibility = View.VISIBLE
            presenter.refreshArticles()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setOnTextChangedListener(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length >= 3) {
                    presenter.loadArticlesByTextInput(userInput = s.toString())
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = HeadlinesFragment()
    }

    override fun showLoading(isLoading: Boolean) {}

    override fun showArticles(articles: List<Article>) {
        adapter.submitList(articles)
    }

    override fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}