package com.example.astonfinalproject.domain.headlines

import android.content.Intent
import android.net.Uri
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView

class MakeLastSentenceClickableUseCase : MakeLastSentenceClickable {
    override fun execute(textView: TextView, fullText: String, url: String) {
        val spannableString = SpannableString(fullText)

        val lastSentenceStart = fullText.lastIndexOf(". ") + 1
        if (lastSentenceStart < 0) return

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                widget.context.startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
            }
        }
        spannableString.setSpan(
            clickableSpan,
            lastSentenceStart,
            fullText.length,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView.text = spannableString
        textView.movementMethod = LinkMovementMethod.getInstance()
    }
}