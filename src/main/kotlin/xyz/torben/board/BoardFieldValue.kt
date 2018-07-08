package xyz.torben.board

enum class BoardFieldValue {
    NONE,
    X,
    O;

    fun getPrintableString(): String {
        if (this == NONE) return " "
        return name;
    }
}
