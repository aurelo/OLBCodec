package hr.kaba.hiso.message;

import hr.kaba.hiso.constants.MessageType;
import hr.kaba.hiso.constants.ProductIndicator;
import hr.kaba.hiso.message.bitmap.BitmapField;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FormatRule {

    public enum FieldStatus {
        MANDATORY, OPTIONAL, EMPTY
    }

    private final ProductIndicator productIndicator;
    private final MessageType messageType;
    private final BitmapField field;
    private final FieldStatus fieldStatus;


    public FormatRule(ProductIndicator productIndicator, MessageType messageType, BitmapField field, FieldStatus fieldStatus) {
        this.productIndicator = productIndicator;
        this.messageType = messageType;
        this.field = field;
        this.fieldStatus = fieldStatus;
    }

    public FormatRule(ProductIndicator productIndicator, MessageType messageType, BitmapField field) {
        this(productIndicator, messageType, field, FieldStatus.MANDATORY);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        // null check
        if (obj == null) return false;
        // type check and cast
        if (getClass() != obj.getClass()) return false;

        FormatRule formatRule = (FormatRule) obj;

        // field comparison
        return Objects.equals(productIndicator, formatRule.productIndicator)
                && Objects.equals(messageType, formatRule.messageType)
                && Objects.equals(field, formatRule.field)
                ;
    }
}
