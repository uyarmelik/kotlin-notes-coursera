package nicestring

fun String.isNice(): Boolean {
    val result = listOf(this.isBaBeBu(), this.isDouble(), this.isVowel())
    return result.filter { nice -> nice == true }.count() > 1
}

private fun String.isBaBeBu():Boolean = !this.contains("ba") && !this.contains("be") && !this.contains("bu")

private fun String.isVowel():Boolean = this.filter { char: Char -> (char in listOf('a', 'e', 'i', 'o', 'u')) }.count() > 2

private fun String.isDouble():Boolean = this.zipWithNext {a:Char,b:Char -> a==b}.any { it.equals(true) }