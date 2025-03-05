package com.example.astonfinalproject.presentation.article_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.astonfinalproject.R
import com.example.astonfinalproject.data.data.models.headlines.Article
import com.example.astonfinalproject.domain.headlines.MakeLastSentenceClickableUseCase
import com.example.astonfinalproject.room.ArticleDatabase
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val ARTICLE_THUMBNAIL = "ARTICLE_THUMBNAIL"
const val ARTICLE_TITLE = "ARTICLE_TITLE"
const val ARTICLE_DATE = "ARTICLE_DATE"
const val ARTICLE_SOURCE = "ARTICLE_SOURCE"
const val ARTICLE_TEXT = "ARTICLE_TEXT"
const val ARTICLE_URL = "ARTICLE_URL"
const val ARTICLE_GOTTEN = "ARTICLE_GOTTEN"

class ArticleFragment : Fragment() {

    private lateinit var articleThumbnailImageView: ImageView
    private var articleTitleTextView: TextView? = null
    private var articleDateTextView: TextView? = null
    private var articleSourceTextView: TextView? = null
    private lateinit var articleTextView: TextView
    private var articleThumbnail: String? = null
    private var articleTitle: String? = null
    private var articleDate: String? = null
    private var articleSource: String? = null
    private var articleText: String? = null
    private var articleUrl: String? = null
    private lateinit var favoriteButton: ImageButton
    private var isFavorite = false
    private var articleGotten: Article? = null
    private lateinit var arrowBackButton: ImageButton
    private val makeLastSentenceClickableUseCase = MakeLastSentenceClickableUseCase()
    private lateinit var roomInstance: ArticleDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            articleThumbnail = it.getString(ARTICLE_THUMBNAIL)
            articleTitle = it.getString(ARTICLE_TITLE)
            articleDate = it.getString(ARTICLE_DATE)
            articleSource = it.getString(ARTICLE_SOURCE)
            articleText = it.getString(ARTICLE_TEXT)
            articleUrl = it.getString(ARTICLE_URL)
            articleGotten = it.getParcelable(ARTICLE_GOTTEN)
        }

        roomInstance = Room.databaseBuilder(
            context = requireContext(),
            klass = ArticleDatabase::class.java,
            name = "file",
        ).build()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article, container, false)
        initViews(view)
        setListeners()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext()).load(articleThumbnail).centerCrop()
            .into(articleThumbnailImageView)
        articleTitleTextView?.text = articleTitle.toString()
        articleSourceTextView?.text = articleSource
        articleDateTextView?.text = articleDate
        articleTextView.text = articleText
        makeLastSentenceClickableUseCase.execute(
            textView = articleTextView,
            fullText = articleText.toString(),
            url = articleUrl.toString()
        )
    }

    private fun initViews(view: View) {
        articleThumbnailImageView = view.findViewById(R.id.articleThumbnail)
        val collapsedTitle: TextView = view.findViewById(R.id.collapsedTitle)
        collapsedTitle.text = articleTitleTextView?.text
        articleTitleTextView = view.findViewById(R.id.articleTitle)
        articleDateTextView = view.findViewById(R.id.articleDate)
        articleSourceTextView = view.findViewById(R.id.articleSource)
        articleTextView = view.findViewById(R.id.articleText)
        favoriteButton = view.findViewById(R.id.favoriteButton)
        arrowBackButton = view.findViewById(R.id.arrowBackButton)
        val collapsingToolbar: CollapsingToolbarLayout =
            view.findViewById(R.id.collapsingToolbarLayout)
        collapsingToolbar.minimumHeight = 200
    }

    private fun setListeners() {
        favoriteButton.setOnClickListener {
            isFavorite = !isFavorite
            if (!isFavorite) {
                favoriteButton.setImageResource(R.drawable.favorite_icon)
            } else {
                favoriteButton.setImageResource(R.drawable.favorite_icon_filled)
                if (articleGotten == null) {
                    println("it's null")
                } else {
                    CoroutineScope(Dispatchers.IO).launch {
                        roomInstance.getArticleDao().insertAnArticle(articleGotten!!)
                    }
                }
            }
        }

        arrowBackButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(article: Article) =
            ArticleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARTICLE_THUMBNAIL, article.urlToImage)
                    putString(ARTICLE_TITLE, article.title)
                    putString(ARTICLE_DATE, article.publishedAt)
                    putString(ARTICLE_SOURCE, article.headlinesSource.name)
                    putString(ARTICLE_TEXT, article.content)
                    putString(ARTICLE_URL, article.url)
                    putParcelable(ARTICLE_GOTTEN, article)
                }
            }
    }
}