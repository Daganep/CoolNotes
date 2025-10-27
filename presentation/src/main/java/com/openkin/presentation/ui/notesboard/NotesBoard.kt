package com.openkin.presentation.ui.notesboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.openkin.presentation.R
import com.openkin.presentation.navigation.IAppRouting
import org.koin.androidx.compose.koinViewModel

@Composable
fun NotesBoard(routing: IAppRouting) {
    NotesBoard(
        viewModel = koinViewModel(),
        routing = routing,
    )
}

@Composable
fun NotesBoard(viewModel: NotesBoardViewModel, routing: IAppRouting) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(color = Color.Black)
    ) {
        val (
            topBar,
            bookImage,
            bottomButtonsPanel,
            advertField,
            menuButton,
            hiddenButtons,
        ) = createRefs()
        Row(
            modifier = Modifier
                .constrainAs(topBar) {
                    top.linkTo(parent.top, margin = 4.dp)
                    start.linkTo(anchor = parent.start, margin = 16.dp)
                    end.linkTo(anchor = parent.end, margin = 8.dp)
                    height = Dimension.value(56.dp)
                    width = Dimension.fillToConstraints
                },
            verticalAlignment = Alignment.CenterVertically,
            //horizontalArrangement = Arrangement.,
        ) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                HorizontalDivider(
                    thickness = 2.dp,
                    color = Color(0xFFB4B4B4),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 2.dp),
                )
                Text(
                    text = "Журнал заметок",
                    color = Color.Yellow,
                    fontSize = 14.sp,
                    modifier = Modifier,
                    )
                HorizontalDivider(
                    thickness = 2.dp,
                    color = Color(0xFFB4B4B4),
                    modifier = Modifier.fillMaxWidth().padding(top = 2.dp),
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.book),
            contentDescription = "",
            modifier = Modifier.size(82.dp)
                .constrainAs(bookImage) {
                    top.linkTo(anchor = parent.top, margin = 4.dp)
                    start.linkTo(anchor = parent.start, margin = 4.dp)
                },
        )
        Row(
            modifier = Modifier
                .constrainAs(bottomButtonsPanel) {
                    bottom.linkTo(anchor = parent.bottom)
                    start.linkTo(anchor = parent.start, margin = 16.dp)
                    end.linkTo(anchor = parent.end, margin = 16.dp)
                    height = Dimension.value(56.dp)
                    width = Dimension.fillToConstraints
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Button(
                modifier = Modifier.height(42.dp),
                shape = ShapeDefaults.Medium,
                contentPadding = PaddingValues(vertical = 4.dp),
                colors = ButtonColors(
                    containerColor = Color.Blue,
                    disabledContainerColor = Color.Gray,
                    contentColor = Color.White,
                    disabledContentColor = Color.White,
                ),
                onClick = {  },
            ) {
                Image(
                    painter = painterResource(id = R.drawable.archives),
                    contentDescription = "Kel",
                    modifier = Modifier.size(24.dp).align(Alignment.CenterVertically),
                )
            }
            Button(
                modifier = Modifier.height(42.dp),
                shape = ShapeDefaults.Medium,
                contentPadding = PaddingValues(vertical = 4.dp),
                colors = ButtonColors(
                    containerColor = Color.Blue,
                    disabledContainerColor = Color.Gray,
                    contentColor = Color.White,
                    disabledContentColor = Color.White,
                ),
                onClick = {  },
            ) {
                Image(
                    painter = painterResource(id = R.drawable.archives),
                    contentDescription = "Kel",
                    modifier = Modifier.size(24.dp).align(Alignment.CenterVertically),
                )
            }
            Button(
                modifier = Modifier.height(42.dp),
                shape = ShapeDefaults.Medium,
                contentPadding = PaddingValues(vertical = 4.dp),
                colors = ButtonColors(
                    containerColor = Color.Blue,
                    disabledContainerColor = Color.Gray,
                    contentColor = Color.White,
                    disabledContentColor = Color.White,
                ),
                onClick = {  },
            ) {
                Image(
                    painter = painterResource(id = R.drawable.archives),
                    contentDescription = "Kel",
                    modifier = Modifier.size(24.dp).align(Alignment.CenterVertically),
                )
            }
        }
    }
}
