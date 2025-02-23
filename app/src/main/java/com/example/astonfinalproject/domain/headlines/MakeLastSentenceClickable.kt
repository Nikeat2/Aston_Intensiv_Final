package com.example.astonfinalproject.domain.headlines

import android.widget.TextView

interface MakeLastSentenceClickable {

    fun execute(textView: TextView, fullText: String, url: String)

}