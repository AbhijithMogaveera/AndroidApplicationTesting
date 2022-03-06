package com.abhijith.androidtesting.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.abhijith.androidtesting.databinding.NoteDetailsFragmentLayoutBinding

class NoteDetailFragment : Fragment() {
    var inflate: NoteDetailsFragmentLayoutBinding? = null
    val binding = inflate!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         inflate = NoteDetailsFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        inflate = null
    }

}