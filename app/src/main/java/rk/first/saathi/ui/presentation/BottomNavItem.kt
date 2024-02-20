package rk.first.saathi.ui.presentation

sealed class BottomNavItem(
    var title: String,
) {
    data object Home :
        BottomNavItem(
            "Home",

            )

    data object Find :
        BottomNavItem(
            "Find",
        )

    data object Setting :
        BottomNavItem(
            "Setting",
        )

    data object Learn :
        BottomNavItem(
            "Learn",
        )

    data object LOOK :
        BottomNavItem(
            "Look",
        )
    data object Empty :
        BottomNavItem(
            "",
        )
    data object Read :
        BottomNavItem(
            "Read",
        )
}
