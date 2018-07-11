package hr.kaba.hiso;

import hr.kaba.hiso.message.HISOMessage;
import hr.kaba.hiso.message.OLBMessages;
import hr.kaba.hiso.message.bitmap.BitmapField;

import java.util.Map;

public class OLBCodec {
    public static HISOMessage decode(String binaryMessage) {

        return OLBMessages.parse(binaryMessage);
    }

    public static String encode(HISOMessage message) {
        return OLBMessages.encode(message);
    }

    public static HISOMessage respondTo(HISOMessage originalMessage, Map<BitmapField, String> responseFields) {
        return OLBMessages.respond(originalMessage, responseFields);
    }

}
