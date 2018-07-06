package hr.kaba.hiso.message;

import hr.kaba.hiso.message.bitmap.Bitmap;
import hr.kaba.hiso.message.bitmap.BitmapField;
import hr.kaba.hiso.message.bitmap.PrimaryBitmapField;
import hr.kaba.hiso.constants.MessageType;

import java.util.Map;

public class HISOMessageHeader {
    private String message;
    private Base24Header base24Header;
    private MessageType messageType;
    private Bitmap<PrimaryBitmapField> primaryBitmap;

    private static final String ISO = "ISO";

    private HISOMessageHeader(String message) {

        this.message = message;

        int indexOfFirstLetterAfterISO = message.indexOf(ISO) + 3;

        base24Header = Base24Header.parse(message.substring(indexOfFirstLetterAfterISO, indexOfFirstLetterAfterISO + 9));
        messageType = MessageType.from(message.substring(indexOfFirstLetterAfterISO + 9, indexOfFirstLetterAfterISO + 9 + 4));
        primaryBitmap = new Bitmap(message.substring(indexOfFirstLetterAfterISO + 9 + 4, indexOfFirstLetterAfterISO + 9 + 4 + 16), PrimaryBitmapField.class, base24Header.getProductIndicator());
    }

    private static boolean isValidHeader(String header) {
        return ((header != null) && (header.length() >= 32));
    }

    public HISOMessageHeader(Base24Header base24Header, MessageType messageType, Bitmap<PrimaryBitmapField> primaryBitmap) {
        this.base24Header = base24Header;
        this.messageType = messageType;
        this.primaryBitmap = primaryBitmap;

        this.message = String.format("%s%s%s%s", ISO, base24Header.getOriginalMessage(), messageType.getCode(), primaryBitmap.getHexRepresentation());
    }


    public HISOMessageHeader response() {
        MessageType response = MessageType.responseFor(getMessageType());

        return new HISOMessageHeader(getBase24Header(), response, getPrimaryBitmap());
    }

    public static HISOMessageHeader parseFrom(String header) throws IllegalArgumentException {

        if (!isValidHeader(header)) {
            throw new IllegalArgumentException(String.format("HISO header: %s is invalid", header));
        }

        return new HISOMessageHeader(header);
    }

    public Base24Header getBase24Header() {
        return base24Header;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public Bitmap getPrimaryBitmap() {
        return primaryBitmap;
    }

    public boolean isSecondaryBitmapPresent() {
        return this.primaryBitmap.contains(PrimaryBitmapField.P1);
    }


    public void recodePrimaryBitmap(Map<BitmapField, String> fields) {
        this.primaryBitmap = new Bitmap(Bitmap.encode(fields), PrimaryBitmapField.class, this.getBase24Header().getProductIndicator());
    }



    @Override
    public String toString() {
        return this.message;
    }
}
