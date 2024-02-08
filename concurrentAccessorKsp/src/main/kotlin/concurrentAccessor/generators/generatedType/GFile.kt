package concurrentAccessor.generators.generatedType

import concurrentAccessor.generators.BaseGenerator
import concurrentAccessor.generators.model.GeneratedSource

internal class GFile private constructor(private val filePackage: String) : BaseGenerator() {

    companion object {

        fun gFile(
            filePackage: String,
            body: GFile.() -> Unit
        ): GFile {
            return GFile(filePackage).also(body)
        }
    }

    var gClass: GClass? = null

    override fun generateCode(): GeneratedSource {

        val generatedCode = gClass?.generateCode()

        val body = StringBuilder().apply {
            append("package $filePackage")
            repeat(2){ appendLine() }

            generatedCode?.imports?.forEach { import ->
                append("import ")
                append(import)
                appendLine()
            }
            appendLine()

            append(generatedCode?.code)
        }

        return generateSource(body.toString())
    }
}