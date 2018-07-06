package hr.kaba.hiso;

import hr.kaba.hiso.message.HISOMessage;
import hr.kaba.hiso.message.OLBMessage;
import hr.kaba.hiso.message.bitmap.BitmapField;

import java.util.Map;

public class OLBCodec {
    public static HISOMessage decode(String binaryMessage) {
        return OLBMessage.parseFrom(binaryMessage);
    }

    public static String encode(HISOMessage message) {
        return null;
    }

    public static HISOMessage respondTo(HISOMessage originalMessage, Map<BitmapField, String> responseFields) {
        return originalMessage.respond(responseFields);
    }

}
