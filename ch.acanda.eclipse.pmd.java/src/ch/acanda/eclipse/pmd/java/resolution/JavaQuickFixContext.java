package ch.acanda.eclipse.pmd.java.resolution;

import org.osgi.framework.Version;

final class JavaQuickFixContext {

    private final Version compilerCompliance;

    public JavaQuickFixContext(final Version compilerCompliance) {
        this.compilerCompliance = compilerCompliance;
    }

    public Version getCompilerCompliance() {
        return compilerCompliance;
    }

}
