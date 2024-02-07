package concurrentAccessor.generators.generatedType

import com.google.devtools.ksp.isOpen
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import concurrentAccessor.generators.BaseGenerator
import concurrentAccessor.generators.model.GeneratedSource
import java.util.LinkedList

class GClass private constructor(
    private val classDeclaration: KSClassDeclaration
): BaseGenerator() {

    companion object {

        fun GFile.gClass(
            classDeclaration: KSClassDeclaration,
            body: GClass.() -> Unit
        ): GClass {
            return GClass(
                classDeclaration
            )
                .also{ gClass = it }
                .also(body)
        }
    }


    val gMethods: MutableList<BaseGenerator> = LinkedList()
    val fields: MutableList<BaseGenerator> = LinkedList()

    override fun generateCode(): GeneratedSource {
        with(classDeclaration) {
            imports.add(packageName.asString() + "." + simpleName.getShortName())
        }


        val className = classDeclaration.simpleName.getShortName()
        val generatedClassName = className + "ConcurrentAccessed"

        val generatedMethods = gMethods
            .map(BaseGenerator::generateCode)

        val generatedMethodsCode =  generatedMethods.joinToString(separator = "\n\n",) { it.code }
        generatedMethods.forEach { imports.addAll(it.imports) }



        return GeneratedSource(
            imports = imports,
            code = """
                |class $generatedClassName(private val baseClass: $className) : $className by baseClass {
                |
                |$generatedMethodsCode
                |
                |}
            """.trimMargin()
        )
    }
}