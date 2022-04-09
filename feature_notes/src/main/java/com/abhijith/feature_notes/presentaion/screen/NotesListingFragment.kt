package com.abhijith.feature_notes.presentaion.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.abhijith.core.theme.AndroidTestingTheme
import com.abhijith.feature_notes.presentaion.components.NoteViewHolderPreview
import com.abhijith.core.util.getContrastColor

class NotesListingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AndroidTestingTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color(getContrastColor(MaterialTheme.colors.background.toArgb()))
                    ) {
                        NoteViewHolderPreview()
                    }
                }
            }
        }
    }
}