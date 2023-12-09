import org.apache.lucene.analysis.fr.FrenchAnalyzer
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute
import java.io.StringReader

sealed interface SomeThing

data class Token(val token: String, val original: String) : SomeThing

@JvmInline
value class Text(val value: String) : SomeThing

fun tokenizeFrenchText(text: String): MutableList<SomeThing> {
    val analyzer = FrenchAnalyzer()
    val tokenStream = analyzer.tokenStream(null, StringReader(text))
    val termAttr = tokenStream.addAttribute(CharTermAttribute::class.java)
    val offsetAttr = tokenStream.addAttribute(OffsetAttribute::class.java)

    val tokens = mutableListOf<SomeThing>()
    tokenStream.reset()

    var previousPosition = 0
    while (tokenStream.incrementToken()) {
        if (offsetAttr.startOffset() != previousPosition) {
            tokens += Text(text.substring(previousPosition, offsetAttr.startOffset()))
        }
        tokens += Token(termAttr.toString(), text.substring(offsetAttr.startOffset(), offsetAttr.endOffset()))
        previousPosition = offsetAttr.endOffset()
    }
    tokens += Text(text.substring(previousPosition, text.length))

    tokenStream.end()
    tokenStream.close()

    return tokens
}