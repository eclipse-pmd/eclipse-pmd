package ch.acanda.eclipse.pmd.marker;

import org.eclipse.core.resources.IMarker;

public interface PMDMarker {

    String getRuleId();

    String getRuleName();

    String getViolationClassName();

    String getVariableName();

    String getMarkerText();

    boolean isOtherWithSameRuleId(IMarker other);

}
