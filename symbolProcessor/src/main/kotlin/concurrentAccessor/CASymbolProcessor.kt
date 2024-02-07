package concurrentAccessor

import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import concurrentAccessor.generators.generatedType.GClass.Companion.gClass
import concurrentAccessor.generators.generatedType.GFile.Companion.gFile
import concurrentAccessor.generators.generatedType.GMethod.Companion.gMethod

class CASymbolProcessor(
    private val environment: SymbolProcessorEnvironment
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        resolver.getSymbolsWithAnnotation(ConcurrentAccessor::class.java.canonicalName).forEach { annotated ->
            environment.logger.warn("Started processing")
            val annotatedClass = annotated as KSClassDeclaration

            environment.logger.warn("Processing ${annotatedClass.simpleName.getShortName()}")


            val packageName = annotatedClass.packageName.asString()
            val generatedFile = gFile(packageName){
                gClass(annotatedClass){
                    annotatedClass.getDeclaredFunctions().forEach { function ->
                        gMethod(function)
                    }

                }
            }

            environment.codeGenerator.createNewFile(
                dependencies = Dependencies(aggregating = false,), // because we depend only on annotated files
                packageName = packageName,
                fileName = "${annotatedClass.simpleName.getShortName()}ConcurrentAccessed"
            ).bufferedWriter(Charsets.UTF_8)
                .apply {
                    write(generatedFile.generateCode().code)
                    close()
                }
        }
        return emptyList() // we can process everything in one round
    }
}