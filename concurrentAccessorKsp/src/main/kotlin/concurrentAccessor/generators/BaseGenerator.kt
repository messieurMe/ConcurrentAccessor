package concurrentAccessor.generators

import com.google.devtools.ksp.symbol.KSDeclaration
import concurrentAccessor.generators.model.GeneratedSource
import java.util.*

internal abstract class BaseGenerator {

    val imports: MutableSet<String> = TreeSet()

    abstract fun generateCode(): GeneratedSource

    protected fun generateSource(code: String) : GeneratedSource{
        return GeneratedSource(
            code = code,
            imports = imports
        )
    }

    protected fun KSDeclaration.getSimpleShortName(): String {
        return simpleName.getShortName()
    }
}