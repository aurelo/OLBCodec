package hr.kaba.hiso.message;

import hr.kaba.hiso.constants.MessageType;
import hr.kaba.hiso.constants.ProductIndicator;
import hr.kaba.hiso.message.bitmap.Bitmap;
import hr.kaba.hiso.message.bitmap.BitmapField;
import hr.kaba.hiso.message.bitmap.PrimaryBitmapField;
import hr.kaba.hiso.message.bitmap.SecondaryBitmapField;
import hr.kaba.hiso.message.field.ATMTransactionCode;
import hr.kaba.hiso.message.field.POSTransactionCode;
import hr.kaba.hiso.message.field.TransactionCode;
import hr.kaba.hiso.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class OLBMessage implements HISOMessage {

    private final String binaryMessage;
    private final HISOMessageHeader header;
    private final String messageBody;

    private Map<BitmapField, String> filedValues = new HashMap<>();

    private final static String ISO = "ISO";
    private final static int BASE24_HEADER_LENGTH = 9;
    private final static int MESSAGE_TYPE_LENGTH = 4;
    private final static int PRIMARY_BITMAP_LENGTH = 16;

    public OLBMessage(HISOMessageHeader header, Map<BitmapField, String> filedValues) {
        this.header = header;
        this.filedValues = filedValues;

        boolean existsSecondaryFields = filedValues.keySet().stream().anyMatch(e -> e instanceof SecondaryBitmapField);

        String secondaryBitmap = Bitmap.binaryBitmapFromFields(filedValues.entrySet().stream().filter(entry -> entry.getKey() instanceof SecondaryBitmapField).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        if (existsSecondaryFields) {
            this.filedValues.put(PrimaryBitmapField.P1, secondaryBitmap);
        }
        else {
            this.filedValues.remove(PrimaryBitmapField.P1);
        }

        // recode header with new primary field
        this.header.recodePrimaryBitmap(filedValues);
        this.messageBody = Bitmap.encode(filedValues);
        this.binaryMessage = toString();
    }

    public OLBMessage(String binaryMessage) {
        this.binaryMessage = binaryMessage;

        this.header = HISOMessageHeader.parseFrom(binaryMessage);

        this.messageBody = binaryMessage.substring(binaryMessage.indexOf(ISO) + 3 + BASE24_HEADER_LENGTH + MESSAGE_TYPE_LENGTH + PRIMARY_BITMAP_LENGTH);

        Pair<Map<BitmapField, String>, String> parsePrimaryBitmapResult = this.header.getPrimaryBitmap().mapFieldValues(messageBody);

        filedValues.putAll(parsePrimaryBitmapResult.getFirst());

        if (header.isSecondaryBitmapPresent()) {
            Bitmap<SecondaryBitmapField> secondaryBitmap = new Bitmap(filedValues.get(PrimaryBitmapField.P1), SecondaryBitmapField.class, header.getBase24Header().getProductIndicator());

            Pair<Map<BitmapField, String>, String> parseSecondaryBitmapResult = secondaryBitmap.mapFieldValues(parsePrimaryBitmapResult.getSecond());
            filedValues.putAll(parseSecondaryBitmapResult.getFirst());
        }

    }

    @Override
    public ProductIndicator getProductType() {
        return this.header.getBase24Header().getProductIndicator();
    }

    @Override
    public MessageType getMessegeType() {
        return this.header.getMessageType();
    }


    @Override
    public Map<BitmapField, String> getFields() {
        return filedValues;
    }


    public static OLBMessage parseFrom(String binaryMessage) {
        return new OLBMessage(binaryMessage);
    }

    @Override
    public Optional<TransactionCode> getTransactionCode() {

        Optional<TransactionCode> maybeTransactionCode = Optional.empty();

        if (getProductType() == ProductIndicator.ATM) {
            maybeTransactionCode = Optional.of(ATMTransactionCode.from(getFields().get(PrimaryBitmapField.P3)));
        }
        else if (getProductType() == ProductIndicator.POS) {
            maybeTransactionCode = Optional.of(POSTransactionCode.from(getFields().get(PrimaryBitmapField.P3)));
        }


        return maybeTransactionCode;
    }

    @Override
    public Optional<TransactionCode.ISOTransactionCode> getISOTransactionalCode() {
        if (!getTransactionCode().isPresent()) {
            return Optional.empty();
        }

        return Optional.of(getTransactionCode().get().getISOTransactionCode());
    }

    @Override
    public HISOMessage respond(Map<BitmapField, String> responseFields) {
        Map<BitmapField, String> newFields = new HashMap<>(this.filedValues);
        newFields.putAll(responseFields);

        HISOMessageHeader newHeader = this.header.response();

        OLBMessage newMessage = new OLBMessage(newHeader, newFields);

        return newMessage;
    }

    @Override
    public String toString() {
        return String.format("%s%s", this.header.toString(), this.messageBody);
    }
}
