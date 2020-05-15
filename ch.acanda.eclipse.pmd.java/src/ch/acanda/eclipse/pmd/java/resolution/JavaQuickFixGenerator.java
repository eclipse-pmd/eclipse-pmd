package ch.acanda.eclipse.pmd.java.resolution;

import static java.util.Map.entry;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.ui.IMarkerResolution;
import org.osgi.framework.Version;

import ch.acanda.eclipse.pmd.exception.EclipsePMDException;
import ch.acanda.eclipse.pmd.java.resolution.bestpractices.DefaultLabelNotLastInSwitchStmtQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.bestpractices.MethodReturnsInternalArrayQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.bestpractices.UseCollectionIsEmptyQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.bestpractices.UseVarargsQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.codestyle.ExtendsObjectQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.codestyle.LocalVariableCouldBeFinalQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.codestyle.MethodArgumentCouldBeFinalQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.codestyle.UnnecessaryReturnQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.design.SingularFieldQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.design.UseUtilityClassQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.design.UselessOverridingMethodQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.emptycode.EmptyIfStmtQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.emptycode.EmptyInitializerQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.emptycode.EmptyStatementBlockQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.emptycode.EmptyStatementNotInLoopQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.emptycode.EmptyStaticInitializerQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.emptycode.EmptySwitchStatementsQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.emptycode.EmptySynchronizedBlockQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.emptycode.EmptyTryBlockQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.emptycode.EmptyWhileStmtQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.errorprone.EmptyFinallyBlockQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.errorprone.EqualsNullQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.errorprone.SuspiciousHashcodeMethodNameQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.multithreading.UseNotifyAllInsteadOfNotifyQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.performance.AddEmptyStringQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.performance.AppendCharacterWithCharQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.performance.ByteInstantiationAutoboxingQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.performance.ByteInstantiationValueOfQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.performance.IntegerInstantiationAutoboxingQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.performance.IntegerInstantiationValueOfQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.performance.LongInstantiationAutoboxingQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.performance.LongInstantiationValueOfQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.performance.RedundantFieldInitializerQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.performance.ShortInstantiationAutoboxingQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.performance.ShortInstantiationValueOfQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.performance.SimplifyStartsWithQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.performance.StringToStringQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.performance.UnnecessaryCaseChangeQuickFix;
import ch.acanda.eclipse.pmd.java.resolution.performance.UseIndexOfCharQuickFix;
import ch.acanda.eclipse.pmd.marker.PMDMarker;

@SuppressWarnings({ "PMD.CouplingBetweenObjects", "PMD.ExcessiveImports" })
public final class JavaQuickFixGenerator {

    private static final Version JAVA_5 = new Version(1, 5, 0);
    private static final Version JAVA_8 = new Version(1, 8, 0);

    @SuppressWarnings("unchecked")
    private static final Map<String, List<? extends Class<? extends IMarkerResolution>>> QUICK_FIXES =
            Map.ofEntries(
                    entry("java.best practices.DefaultLabelNotLastInSwitchStmt", List.of(DefaultLabelNotLastInSwitchStmtQuickFix.class)),
                    entry("java.best practices.MethodReturnsInternalArray", List.of(MethodReturnsInternalArrayQuickFix.class)),
                    entry("java.best practices.UseCollectionIsEmpty", List.of(UseCollectionIsEmptyQuickFix.class)),
                    entry("java.best practices.UseVarargs", List.of(UseVarargsQuickFix.class)),
                    entry("java.code style.ExtendsObject", List.of(ExtendsObjectQuickFix.class)),
                    entry("java.code style.LocalVariableCouldBeFinal", List.of(LocalVariableCouldBeFinalQuickFix.class)),
                    entry("java.code style.MethodArgumentCouldBeFinal", List.of(MethodArgumentCouldBeFinalQuickFix.class)),
                    entry("java.code style.UnnecessaryReturn", List.of(UnnecessaryReturnQuickFix.class)),
                    entry("java.design.SingularField", List.of(SingularFieldQuickFix.class)),
                    entry("java.design.UselessOverridingMethod", List.of(
                            UselessOverridingMethodQuickFix.class)),
                    entry("java.design.UseUtilityClass", List.of(UseUtilityClassQuickFix.class)),
                    entry("java.error prone.EmptyFinallyBlock", List.of(EmptyFinallyBlockQuickFix.class)),
                    entry("java.error prone.EmptyIfStmt", List.of(EmptyIfStmtQuickFix.class)),
                    entry("java.error prone.EmptyInitializer", List.of(EmptyInitializerQuickFix.class)),
                    entry("java.error prone.EmptyStatementBlock", List.of(EmptyStatementBlockQuickFix.class)),
                    entry("java.error prone.EmptyStatementNotInLoop", List.of(EmptyStatementNotInLoopQuickFix.class)),
                    entry("java.error prone.EmptyStaticInitializer", List.of(EmptyStaticInitializerQuickFix.class)),
                    entry("java.error prone.EmptySwitchStatements", List.of(EmptySwitchStatementsQuickFix.class)),
                    entry("java.error prone.EmptySynchronizedBlock", List.of(EmptySynchronizedBlockQuickFix.class)),
                    entry("java.error prone.EmptyTryBlock", List.of(EmptyTryBlockQuickFix.class)),
                    entry("java.error prone.EmptyWhileStmt", List.of(EmptyWhileStmtQuickFix.class)),
                    entry("java.error prone.EqualsNull", List.of(EqualsNullQuickFix.class)),
                    entry("java.error prone.SuspiciousHashcodeMethodName", List.of(SuspiciousHashcodeMethodNameQuickFix.class)),
                    entry("java.multithreading.UseNotifyAllInsteadOfNotify", List.of(UseNotifyAllInsteadOfNotifyQuickFix.class)),
                    entry("java.performance.AddEmptyString", List.of(AddEmptyStringQuickFix.class)),
                    entry("java.performance.AppendCharacterWithChar", List.of(AppendCharacterWithCharQuickFix.class)),
                    entry("java.performance.ByteInstantiation", List.of(
                            ByteInstantiationAutoboxingQuickFix.class,
                            ByteInstantiationValueOfQuickFix.class)),
                    entry("java.performance.IntegerInstantiation", List.of(
                            IntegerInstantiationAutoboxingQuickFix.class,
                            IntegerInstantiationValueOfQuickFix.class)),
                    entry("java.performance.LongInstantiation", List.of(
                            LongInstantiationAutoboxingQuickFix.class,
                            LongInstantiationValueOfQuickFix.class)),
                    entry("java.performance.RedundantFieldInitializer", List.of(RedundantFieldInitializerQuickFix.class)),
                    entry("java.performance.ShortInstantiation", List.of(
                            ShortInstantiationAutoboxingQuickFix.class,
                            ShortInstantiationValueOfQuickFix.class)),
                    entry("java.performance.SimplifyStartsWith", List.of(SimplifyStartsWithQuickFix.class)),
                    entry("java.performance.StringToString", List.of(StringToStringQuickFix.class)),
                    entry("java.performance.UnnecessaryCaseChange", List.of(UnnecessaryCaseChangeQuickFix.class)),
                    entry("java.performance.UseIndexOfChar", List.of(UseIndexOfCharQuickFix.class)));

    public boolean hasQuickFixes(final PMDMarker marker, final JavaQuickFixContext context) {
        if (context.getCompilerCompliance().compareTo(JAVA_5) >= 0) {
            // The SuppressWarningsQuickFix is always available when the compiler compliance is set to Java 5+.
            return true;
        }
        return QUICK_FIXES.containsKey(marker.getRuleId());
    }

    public List<? extends IMarkerResolution> getQuickFixes(final PMDMarker marker, final JavaQuickFixContext context) {
        return Stream.concat(getRuleQuickFixes(marker, context), getSuppressWarningsQuickFix(marker, context)).collect(Collectors.toList());
    }

    private static Stream<IMarkerResolution> getRuleQuickFixes(final PMDMarker marker, final JavaQuickFixContext context) {
        if (context.getCompilerCompliance().compareTo(JAVA_8) >= 0) {
            return Stream.empty();
        }
        return QUICK_FIXES.get(marker.getRuleId()).stream().map(quickFixClass -> createInstanceOf(quickFixClass, marker));
    }

    private static Stream<IMarkerResolution> getSuppressWarningsQuickFix(final PMDMarker marker, final JavaQuickFixContext context) {
        if (context.getCompilerCompliance().compareTo(JAVA_5) < 0) {
            return Stream.empty();
        }
        return Stream.of(new SuppressWarningsQuickFix(marker));
    }

    private static IMarkerResolution createInstanceOf(final Class<? extends IMarkerResolution> quickFixClass, final PMDMarker marker) {
        try {
            return quickFixClass.getConstructor(PMDMarker.class).newInstance(marker);
        } catch (SecurityException | ReflectiveOperationException e) {
            throw new EclipsePMDException("Quick fix class " + quickFixClass + " is not correctly implemented", e);
        }
    }

}
