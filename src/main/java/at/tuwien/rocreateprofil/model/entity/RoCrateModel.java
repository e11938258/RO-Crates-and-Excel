package at.tuwien.rocreateprofil.model.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class RoCrateModel {

    private String author;
    private Instant created;
    private Instant modified;
    private String workbookName;
    private String applicationName;
    private String applicationVersion;

    private RoFile roFile;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getModified() {
        return modified;
    }

    public void setModified(Instant modified) {
        this.modified = modified;
    }

    public String getWorkbookName() {
        return workbookName;
    }

    public void setWorkbookName(String workbookName) {
        this.workbookName = workbookName;
    }

    public RoFile getRoFile() {
        return roFile;
    }

    public void setRoFile(RoFile roFile) {
        this.roFile = roFile;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }
}
