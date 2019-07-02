package mars.nomad.com.b0_generaltemplate.DataModel;

import java.io.Serializable;

/**
 * Created by SJH, NomadSoft.Inc, 2019-07-02
 */
public class InputDataModel implements Serializable {

    private String fieldName;

    private String value;

    public InputDataModel() {
    }

    public InputDataModel(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "InputDataModel{" +
                "fieldName='" + fieldName + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
