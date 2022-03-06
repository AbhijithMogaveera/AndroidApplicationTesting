package com.abhijith.androidtesting.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.abhijith.androidtesting.R
import com.abhijith.androidtesting.databinding.NoteListingFragmentLayoutBinding

class NoteListingFragment : Fragment() {

    var inflate: NoteListingFragmentLayoutBinding? = null
    val binding get() =  inflate!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inflate = NoteListingFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnGotoDetailsScreen.setOnClickListener {
            findNavController().navigate(R.id.toNoteDetailsScreen)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        inflate = null
    }
}