package rk.first.saathi.ui.presentation

sealed class BottomNavItem(
    var title: String,
) {
    data object Home :
        BottomNavItem(
            "Home",

            )

    data object Read :
        BottomNavItem(
            "Read",
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
}
