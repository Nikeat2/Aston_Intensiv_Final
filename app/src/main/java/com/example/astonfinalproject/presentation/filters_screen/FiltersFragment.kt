package com.example.astonfinalproject.presentation.filters_screen

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.astonfinalproject.R
import com.example.astonfinalproject.databinding.FragmentFiltersBinding
import com.example.astonfinalproject.presentation.headlines.ui.FILTERS_GOTTEN
import com.example.astonfinalproject.presentation.headlines.ui.HEADLINES_FRAGMENT
import com.example.astonfinalproject.presentation.headlines.ui.HEADLINES_FRAGMENT_GONE_TO_FILTERS
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

const val CHOSEN_LANGUAGE = "CHOSEN_LANGUAGE"
const val CHOSEN_DATE = "CHOSEN_DATE"
const val CHOSEN_POPULARITY = "CHOSEN_POPULARITY"

class FiltersFragment : Fragment() {

    private var chosenPopularity: String? = null
    private var chosenDate: String? = null
    private var chosenLanguage: String? = null
    private var _binding :FragmentFiltersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFiltersBinding.inflate(inflater, container, false)
        return binding.root
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
                binding.chooseDateTextView.text = selectedDate
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
        binding.calendarImageButton.setOnClickListener {
            showDatePicker()
        }

        binding.categoryButtons.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.relevantButton -> chosenPopularity = "relevancy"
                    R.id.newButton -> chosenPopularity = "publishedAt"
                    R.id.popularButton -> chosenPopularity = "popularity"
                }
            }
        }

        binding.russianLanguageButton.setOnClickListener {
            chosenLanguage = "ru"
        }

        binding.englishLanguageButton.setOnClickListener {
            chosenLanguage = "en"
        }

        binding.deutschLanguageButton.setOnClickListener {
            chosenLanguage = "de"
        }

        binding.checkButton.setOnClickListener {
            setFragmentResult()
        }

        binding.arrowBackButton.setOnClickListener {
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