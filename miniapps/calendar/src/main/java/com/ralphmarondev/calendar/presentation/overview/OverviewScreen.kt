package com.ralphmarondev.calendar.presentation.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ralphmarondev.core.presentation.component.LumiGestureHandler
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun OverviewScreenRoot(
    navigateBack: () -> Unit
) {
    LumiGestureHandler(
        onBackSwipe = navigateBack
    ) {
        OverviewScreen(
            navigateBack = navigateBack
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OverviewScreen(
    navigateBack: () -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = 1000,
        pageCount = { Int.MAX_VALUE }
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Calendar")
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(innerPadding)
        ) { page ->
            val monthOffset = page - 1000
            val monthDate = YearMonth.now().plusMonths(monthOffset.toLong())
            MonthView(month = monthDate, onDayClick = {})
        }
    }
}

@Composable
private fun MonthView(
    month: YearMonth,
    onDayClick: (LocalDate) -> Unit
) {
    val today = LocalDate.now()
    var selectedDate: LocalDate? = remember { null }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = month.month.getDisplayName(
                TextStyle.FULL,
                Locale.getDefault()
            ) + " " + month.year,
            style = MaterialTheme.typography.headlineSmall.copy(
                color = MaterialTheme.colorScheme.primary
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        val firstDay = month.atDay(1)
        val totalDays = month.lengthOfMonth()
        val firstWeekDay = if (firstDay.dayOfWeek.value == 7) 0 else firstDay.dayOfWeek.value

        val dayCells = mutableListOf<LocalDate?>()
        repeat(firstWeekDay) { dayCells.add(null) }
        for (d in 1..totalDays) dayCells.add(month.atDay(d))

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(dayCells) { day ->
                if (day == null) {
                    Spacer(modifier = Modifier.aspectRatio(1f))
                } else {
                    val isToday = day == today
                    val isSelected = day == selectedDate

                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .padding(4.dp)
                            .background(
                                color = when {
                                    isSelected || isToday -> MaterialTheme.colorScheme.primary
                                    else -> MaterialTheme.colorScheme.onPrimary
                                },
                                shape = CircleShape
                            )
                            .clickable {
                                selectedDate = day
                                onDayClick(day)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${day.dayOfMonth}",
                            color = when {
                                isSelected || isToday -> MaterialTheme.colorScheme.onPrimary
                                else -> MaterialTheme.colorScheme.secondary
                            }
                        )
                    }
                }
            }
        }
    }
}
