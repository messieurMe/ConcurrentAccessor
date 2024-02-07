package concurrentAccessor.generators.generatedType

import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.Modifier
import concurrentAccessor.generators.BaseGenerator
import concurrentAccessor.generators.model.GeneratedSource

class GMethod private constructor(
    private val methodDeclaration: KSFunctionDeclaration
) : BaseGenerator() {

    companion object {
        fun GClass.gMethod(
            methodDeclaration: KSFunctionDeclaration,
            body: GMethod.() -> Unit = {}
        ) : GMethod {
            return GMethod(methodDeclaration)
                .also { gMethods.add(it); body(it) }
        }
    }

    override fun generateCode(): GeneratedSource {
        val methodName = methodDeclaration.simpleName.getShortName()
        val parametersWithType = methodDeclaration.parameters.joinToString(", ") { parameter ->
            "${parameter.name?.getShortName()}: ${parameter.type.resolve().declaration.getSimpleShortName()}"
        }

        val parameters = methodDeclaration.parameters.joinToString(
            separator = ", ",
            transform = { parameter -> parameter.name?.getShortName() ?: "" })

        val returnType = methodDeclaration.returnType?.resolve()?.declaration?.getSimpleShortName() ?: "Unit"

        val isSuspend = Modifier.SUSPEND in methodDeclaration.modifiers
        val suspend = if (isSuspend) " suspend" else ""

        val mutualExclusionDeclaration = generateMutualExclusionDeclaration()

        return GeneratedSource(
            imports = mutualExclusionDeclaration.imports,
            code = """
               |    ${mutualExclusionDeclaration.code}
               |    
               |    override$suspend fun $methodName($parametersWithType): $returnType =
               |        ${methodDeclaration}Mutex.withLock {
               |            baseClass.$methodDeclaration($parameters)
               |        }
               |        
            """.trimMargin()
        )
    }

    private fun generateMutualExclusionDeclaration() : GeneratedSource{
        return if (Modifier.SUSPEND in methodDeclaration.modifiers) {
            GeneratedSource(
                imports = setOf(
                    "kotlinx.coroutines.sync.Mutex",
                    "kotlinx.coroutines.sync.withLock"
                ),
                code = "val ${methodDeclaration}Mutex = Mutex()",
            )
        } else {
            GeneratedSource(
                imports = setOf(
                    "java.util.concurrent.locks.ReentrantLock",
                    "kotlin.concurrent.withLock"
                ),
                code = "val ${methodDeclaration}Mutex = ReentrantLock()"
            )
        }
    }
}