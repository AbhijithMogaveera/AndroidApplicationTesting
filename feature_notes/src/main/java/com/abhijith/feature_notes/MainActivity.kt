package com.abhijith.feature_notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.abhijith.core.theme.AndroidTestingTheme

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
                        color = MaterialTheme.colors.background
                    ) {
                        Greeting("Android")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidTestingTheme {
        Greeting("Android")
    }
}

@Preview
@Composable
fun NoteViewHolderPreview() {
    NoteViewHolder(

    )
}

@Composable
fun NoteViewHolder(
    modifier: Modifier = Modifier
) {
    Card(
        backgroundColor = Color.Blue,
        modifier = modifier
    ) {
        Column {
            Text(
                text = "Hii",
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = "Hii hello how are you just checking whats happening",
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}