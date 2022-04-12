package com.abhijith.feature_notes.presentaion.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.abhijith.core.theme.AndroidTestingTheme
import com.abhijith.core.util.getContrastColor
import com.abhijith.feature_notes.presentaion.viewmodel.NoteCreationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteCreationScreen(

) : Fragment() {

    val vm: NoteCreationViewModel by viewModels()

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
                        color = Color(
                            getContrastColor(
                                MaterialTheme.colors.background.toArgb()
                            )
                        )
                    ) {
                        PageContent()
                    }
                }
            }
        }
    }

    @Composable
    @Preview
    private fun PageContent() {
        Column {
            NoteTitleInput(
                modifier = Modifier.fillMaxWidth(),
                title = vm.titleState,
                onTitleChange = vm::updateTitle
            )
            Row(
                modifier = Modifier.weight(1f),
            ) {
                NoteBodyInput(
                    title = vm.bodyState,
                    modifier = Modifier.weight(1f),
                    onTitleChange = vm::updateBody
                )
            }
        }
    }
}


@Composable
@Preview
fun NoteTitleInput(
    modifier: Modifier = Modifier,
    title: String = "",
    onTitleChange: (title: String) -> Unit = {}
) {
    Row(
        modifier = modifier
    ) {
        TextField(
            value = title,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = onTitleChange,
            label = {
                Text(
                    text = "Enter your title here"
                )
            }
        )
    }
}

@Composable
@Preview
fun NoteBodyInput(
    modifier: Modifier = Modifier,
    title: String = "",
    onTitleChange: (title: String) -> Unit = {}
) {
    Row(
        modifier = modifier
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = onTitleChange,
            label = {
                Text(
                    text = "Enter your title here"
                )
            }
        )
    }
}