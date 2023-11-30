package rk.first.saathi.ui.presentation

sealed class BottomNavItem(
    var title: String,
) {
    object Home :
        BottomNavItem(
            "Home",

            )

    object OCR :
        BottomNavItem(
            "OCR",
        )

    object OBJECT :
        BottomNavItem(
            "OBJECT",
        )

    object Profile :
        BottomNavItem(
            "Profile",
        )
}
