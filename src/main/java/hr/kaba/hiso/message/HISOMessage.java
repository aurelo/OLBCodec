package hr.kaba.hiso.message;

import hr.kaba.hiso.constants.MessageType;
import hr.kaba.hiso.constants.ProductIndicator;
import hr.kaba.hiso.message.bitmap.BitmapField;
import hr.kaba.hiso.message.field.TransactionCode;

import java.util.Map;
import java.util.Optional;

public interface HISOMessage {

    Base24Header getHeader();
    ProductIndicator getProductType();
    MessageType getMessageType();

    String getPrimaryBitmap();

    Map<BitmapField, String> getFields();

    Optional<TransactionCode> getTransactionCode();

    Optional<TransactionCode.ISOTransactionCode> getISOTransactionalCode();

    String dataEncoded();

}
