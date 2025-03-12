package com.example.astonfinalproject.presentation.filters_screen

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.astonfinalproject.R
import com.example.astonfinalproject.presentation.headlines.ui.FILTERS_GOTTEN
import com.example.astonfinalproject.presentation.headlines.ui.HEADLINES_FRAGMENT
import com.example.astonfinalproject.presentation.headlines.ui.HEADLINES_FRAGMENT_GONE_TO_FILTERS
import com.google.android.material.button.MaterialButtonToggleGroup
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

const val CHOSEN_LANGUAGE = "CHOSEN_LANGUAGE"
const val CHOSEN_DATE = "CHOSEN_DATE"
const val CHOSEN_POPULARITY = "CHOSEN_POPULARITY"

class FiltersFragment : Fragment() {

    private lateinit var arrowBackButton: ImageButton
    private lateinit var checkButton: ImageButton
    private lateinit var chooseDateTextView: TextView
    private lateinit var calendarImageButton: ImageButton
    private lateinit var choosePopularityButton: MaterialButtonToggleGroup
    private var chosenPopularity: String? = null
    private var chosenDate: String? = null
    private var chosenLanguage: String? = null
    private lateinit var russianLanguageButton: Button
    private lateinit var englishLanguageButton: Button
    private lateinit var deutschLanguageButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_filters, container, false)
        arrowBackButton = view.findViewById(R.id.arrowBackButton)
        checkButton = view.findViewById(R.id.checkButton)
        chooseDateTextView = view.findViewById(R.id.chooseDateTextView)
        calendarImageButton = view.findViewById(R.id.calendarImageButton)
        choosePopularityButton = view.findViewById(R.id.categoryButtons)
        russianLanguageButton = view.findViewById(R.id.russianLanguageButton)
        englishLanguageButton = view.findViewById(R.id.englishLanguageButton)
        deutschLanguageButton = view.findViewById(R.id.deutschLanguageButton)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnButtonClickListeners()
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->

                val selectedDate = formatDateToISO(selectedYear, selectedMonth + 1, selectedDay)
                chooseDateTextView.text = selectedDate
                chosenDate = selectedDate
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun formatDateToISO(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance().apply {
            set(year, month - 1, day)
        }

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun setOnButtonClickListeners() {
        calendarImageButton.setOnClickListener {
            showDatePicker()
        }

        choosePopularityButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.relevantButton -> chosenPopularity = "relevancy"
                    R.id.newButton -> chosenPopularity = "publishedAt"
                    R.id.popularButton -> chosenPopularity = "popularity"
                }
            }
        }

        russianLanguageButton.setOnClickListener {
            chosenLanguage = "ru"
        }

        englishLanguageButton.setOnClickListener {
            chosenLanguage = "en"
        }

        deutschLanguageButton.setOnClickListener {
            chosenLanguage = "de"
        }

        checkButton.setOnClickListener {
            setFragmentResult()
        }

        arrowBackButton.setOnClickListener {
            parentFragmentManager.popBackStack(HEADLINES_FRAGMENT_GONE_TO_FILTERS, 0)
        }
    }

    private fun setFragmentResult() {
        val filtersChosenInfo = bundleOf(
            CHOSEN_LANGUAGE to chosenLanguage,
            CHOSEN_DATE to chosenDate,
            CHOSEN_POPULARITY to chosenPopularity
        )

        val filtersChosen = Bundle().apply {
            putBundle(FILTERS_GOTTEN, filtersChosenInfo)
        }

        parentFragmentManager.setFragmentResult(FILTERS_GOTTEN, filtersChosen)
        parentFragmentManager.popBackStack(HEADLINES_FRAGMENT, 0)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FiltersFragment()
    }
}