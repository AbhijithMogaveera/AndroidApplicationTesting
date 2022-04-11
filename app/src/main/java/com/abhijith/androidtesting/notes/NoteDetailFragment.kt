package com.abhijith.androidtesting.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.abhijith.androidtesting.databinding.NoteDetailsFragmentLayoutBinding

class NoteDetailFragment : Fragment() {

    private var _binding: NoteDetailsFragmentLayoutBinding? = null
    private val binding: NoteDetailsFragmentLayoutBinding = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         _binding = NoteDetailsFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        _binding = null
    }

}