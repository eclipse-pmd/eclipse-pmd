package ch.acanda.eclipse.pmd.builder;

import java.net.URI;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;

import net.sourceforge.pmd.lang.document.FileId;

final class IFieldId implements FileId {

    private final IFile file;

    IFieldId(final IFile file) {
        this.file = file;
    }

    @Override
    public String getUriString() {
        final URI uri = file.getLocationURI();
        return uri == null ? null : uri.toString();
    }

    @Override
    public FileId getParentFsPath() {
        return null;
    }

    @Override
    public String getOriginalPath() {
        return file.getProjectRelativePath().toOSString();
    }

    @Override
    public String getFileName() {
        return file.getName();
    }

    @Override
    public String getAbsolutePath() {
        final IPath path = file.getLocation();
        return path == null ? null : path.makeAbsolute().toOSString();
    }

}
