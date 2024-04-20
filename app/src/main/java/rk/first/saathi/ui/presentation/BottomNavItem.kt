package rk.first.saathi.ui.presentation

import rk.first.saathi.R

sealed class BottomNavItem(
    var title: String,
    var icon: Int
) {
    data object Home :
        BottomNavItem(
            "Home",
            R.drawable.home
        )

    data object Find :
        BottomNavItem(
            "Find",
            R.drawable.find
        )

    data object Setting :
        BottomNavItem(
            "Setting",
            R.drawable.questionmark
        )

    data object Learn :
        BottomNavItem(
            "Learn",
            R.drawable.learn
        )

    data object LOOK :
        BottomNavItem(
            "Look",
            R.drawable.look
        )
    data object Empty :
        BottomNavItem(
            "",
            R.drawable.questionmark
        )
    data object Read :
        BottomNavItem(
            "Read",
            R.drawable.read
        )
}
