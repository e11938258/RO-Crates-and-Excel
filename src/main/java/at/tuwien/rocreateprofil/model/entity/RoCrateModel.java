package at.tuwien.rocreateprofil.model.entity;

import java.time.Instant;

public class RoCrateModel {

    private String author;
    private Instant created;
    private Instant modified;
    private String workbookName;

    private Dataset dataset;
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

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public RoFile getRoFile() {
        return roFile;
    }

    public void setRoFile(RoFile roFile) {
        this.roFile = roFile;
    }
}
