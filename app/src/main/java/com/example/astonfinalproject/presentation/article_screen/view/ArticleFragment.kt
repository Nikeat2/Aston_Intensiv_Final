package com.example.astonfinalproject.presentation.article_screen.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.astonfinalproject.R
import com.example.astonfinalproject.data.data.models.headlines.Article
import com.example.astonfinalproject.databinding.FragmentArticleBinding
import com.example.astonfinalproject.domain.headlines.MakeLastSentenceClickableUseCase
import com.example.astonfinalproject.presentation.activity.MainActivity
import com.example.astonfinalproject.presentation.article_screen.viewmodel.ArticleFragmentViewModel
import com.example.astonfinalproject.room.ArticleDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleFragment : Fragment() {

    private var isFavorite = false
    private var articleGotten: Article? = null
    private val makeLastSentenceClickableUseCase = MakeLastSentenceClickableUseCase()
    private lateinit var roomInstance: ArticleDatabase
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        roomInstance = Room.databaseBuilder(
            context = requireContext(),
            klass = ArticleDatabase::class.java,
            name = "file",
        ).build()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        binding.collapsedTitle.text = binding.articleTitle.text
        binding.collapsingToolbarLayout.minimumHeight = 200
        setListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: ArticleFragmentViewModel by activityViewModels()

        lifecycleScope.launch {
            viewModel.article.collect { article ->
                Glide.with(requireContext()).load(article?.urlToImage).centerCrop()
                    .into(binding.articleThumbnail)
                binding.articleTitle.text = article?.title
                binding.articleDate.text = article?.publishedAt
                binding.articleSource.text = article?.headlinesSource?.name
                binding.articleText.text = article?.content

                makeLastSentenceClickableUseCase.execute(
                    textView = binding.articleText,
                    fullText = binding.articleText.text.toString(),
                    url = article?.url.toString()
                )
            }
        }
    }

    private fun setListeners() {
        binding.favoriteButton.setOnClickListener {
            isFavorite = !isFavorite
            if (!isFavorite) {
                binding.favoriteButton.setImageResource(R.drawable.favorite_icon)
            } else {
                binding.favoriteButton.setImageResource(R.drawable.favorite_icon_filled)
                if (articleGotten == null) {
                    println("it's null")
                } else {
                    CoroutineScope(Dispatchers.IO).launch {
                        roomInstance.getArticleDao().insertAnArticle(articleGotten!!)
                    }
                }
            }
        }

        binding.arrowBackButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ArticleFragment()
    }
}