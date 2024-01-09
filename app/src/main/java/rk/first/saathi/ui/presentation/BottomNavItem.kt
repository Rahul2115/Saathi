package rk.first.saathi.ui.presentation

sealed class BottomNavItem(
    var title: String,
) {
    data object Home :
        BottomNavItem(
            "Home",

            )

    data object OCR :
        BottomNavItem(
            "OCR",
        )

    data object OBJECT :
        BottomNavItem(
            "OBJECT",
        )

    data object LLM :
        BottomNavItem(
            "LLM",
        )
}
