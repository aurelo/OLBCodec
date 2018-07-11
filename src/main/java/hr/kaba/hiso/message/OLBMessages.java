package hr.kaba.hiso.message;

import hr.kaba.hiso.constants.MessageType;
import hr.kaba.hiso.message.bitmap.Bitmap;
import hr.kaba.hiso.message.bitmap.BitmapField;
import hr.kaba.hiso.message.bitmap.PrimaryBitmapField;
import hr.kaba.hiso.message.bitmap.SecondaryBitmapField;
import hr.kaba.hiso.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class OLBMessages {

    private final static String ISO = "ISO";
    private final static int BASE24_HEADER_LENGTH = 9;
    private final static int MESSAGE_TYPE_LENGTH = 4;
    private final static int PRIMARY_BITMAP_LENGTH = 16;

    /**
     *
     * @param encodedMessage
     * @return OLB message (POJO) parsed from encoded message
     */
    public static OLBMessage parse(String encodedMessage) {

        if ((encodedMessage == null) || encodedMessage.length() <= ISO.length() + BASE24_HEADER_LENGTH + MESSAGE_TYPE_LENGTH + PRIMARY_BITMAP_LENGTH) {
            throw new IllegalArgumentException(String.format("OLB message can not be parsed from %s", encodedMessage));
        }

        int indexOfFirstLetterAfterISO = encodedMessage.indexOf(ISO) + 3;


        Base24Header base24Header = Base24Header.parse(encodedMessage.substring(indexOfFirstLetterAfterISO, indexOfFirstLetterAfterISO + 9));
        MessageType messageType = MessageType.from(encodedMessage.substring(indexOfFirstLetterAfterISO + 9, indexOfFirstLetterAfterISO + 9 + 4));
        Bitmap<PrimaryBitmapField> primaryBitmap = new Bitmap(encodedMessage.substring(indexOfFirstLetterAfterISO + 9 + 4, indexOfFirstLetterAfterISO + 9 + 4 + 16)
                                                             ,PrimaryBitmapField.class
                                                             ,base24Header.getProductIndicator());


       String messageBody = encodedMessage.substring(encodedMessage.indexOf(ISO) + 3 + BASE24_HEADER_LENGTH + MESSAGE_TYPE_LENGTH + PRIMARY_BITMAP_LENGTH);

        Pair<Map<BitmapField, String>, String> parsePrimaryBitmapResult = primaryBitmap.mapFieldValues(messageBody);

        Map<BitmapField, String> filledValues = new HashMap<>();
        filledValues.putAll(parsePrimaryBitmapResult.getFirst());

        // is there a secondary bitmap signifying fields from 65 onwards
        if (primaryBitmap.contains(PrimaryBitmapField.P1)) {
            Bitmap<SecondaryBitmapField> secondaryBitmap = new Bitmap(filledValues.get(PrimaryBitmapField.P1), SecondaryBitmapField.class, base24Header.getProductIndicator());

            Pair<Map<BitmapField, String>, String> parseSecondaryBitmapResult = secondaryBitmap.mapFieldValues(parsePrimaryBitmapResult.getSecond());
            filledValues.putAll(parseSecondaryBitmapResult.getFirst());
        }

        return new OLBMessage(base24Header, messageType, filledValues);
    }

    /**
     * Create a response message based on original and response fields
     * @param originalMessage - message being responded to
     * @param responseFields - map of response fields with their values
     * @return response message
     */
    public static OLBMessage respond(HISOMessage originalMessage, Map<BitmapField, String> responseFields) {

        MessageType responseMessageType = MessageType.responseFor(originalMessage.getMessageType());

        Map<BitmapField, String> responseFieldsValues = new HashMap<>(originalMessage.getFields());
        responseFieldsValues.putAll(responseFields);

        // leave only fields required for appropriate message type / product
        responseFieldsValues = FormatRules.filterFields(originalMessage.getProductType(), responseMessageType, responseFieldsValues);

        return new OLBMessage(originalMessage.getHeader(), responseMessageType, responseFieldsValues);
    }

    /**
     *
     * @param message
     * @return OLB message encoded to string
     */

    public static String encode(HISOMessage message) {
        return String.format("%s%s%s%s%s", ISO, message.getHeader(), message.getMessageType(), message.getPrimaryBitmap(), message.dataEncoded());
    }

}
