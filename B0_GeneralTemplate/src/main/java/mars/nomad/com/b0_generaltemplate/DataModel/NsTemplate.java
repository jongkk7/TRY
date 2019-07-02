package mars.nomad.com.b0_generaltemplate.DataModel;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;



public class NsTemplate implements Serializable {

    @NonNull
    private String templateName;

    private String description;

    private List<NsFile> templateFiles;

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public List<NsFile> getTemplateFiles() {
        return templateFiles;
    }

    public void setTemplateFiles(List<NsFile> templateFiles) {
        this.templateFiles = templateFiles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "NsTemplate{" +
                "templateName='" + templateName + '\'' +
                ", description='" + description + '\'' +
                ", templateFiles=" + templateFiles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NsTemplate template = (NsTemplate) o;
        return templateName.equals(template.templateName) &&
                Objects.equals(description, template.description) &&
                Objects.equals(templateFiles, template.templateFiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(templateName, description, templateFiles);
    }
}
