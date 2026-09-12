package hpl.local.build.utils

const val SLASH = '\\'
const val N = '\n'
const val Q = '"'
const val R = '\r'
const val T = '\t'
const val B = '\b'
const val S = '$'

const val SLASH_STRING = "\\\\"
const val N_STRING = "\\n"
const val Q_STRING = "\\\""
const val R_STRING = "\\r"
const val T_STRING = "\\t"
const val B_STRING = "\\b"
const val S_STRING = "\\$"
fun String.toLiteral(): String = buildString {
    append(Q)
    for (c in this@toLiteral) {
        when (c) {
            SLASH -> append(SLASH_STRING)
            N -> append(N_STRING)
            Q  -> append(Q_STRING)
            R -> append(R_STRING)
            T -> append(T_STRING)
            B -> append(B_STRING)
            S  -> append(S_STRING)
            else -> append(c)
        }
    }
    append(Q)
}

internal object Utils{
    const val PROJECT_LICENCE_FILE_NAME = "LICENSE"
}