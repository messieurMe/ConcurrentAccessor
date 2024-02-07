import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import concurrentAccessor.CASymbolProcessor

class CAProcessorProvider : SymbolProcessorProvider{
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        environment.logger.info("HELLO!")
        return CASymbolProcessor(
            environment

        )
    }

}