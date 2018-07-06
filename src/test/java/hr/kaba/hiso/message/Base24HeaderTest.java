package hr.kaba.hiso.message;

import hr.kaba.hiso.constants.InitiatorType;
import hr.kaba.hiso.constants.ProductIndicator;
import hr.kaba.hiso.message.Base24Header;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Base24HeaderTest {

    @Test
    void testParsingNullString() {
        assertThrows(IllegalArgumentException.class, () -> Base24Header.parse(null));

    }

    @Test
    void testParsingNonSensicalString() {
        assertThrows(IllegalArgumentException.class, () -> Base24Header.parse("12356"));
        assertThrows(IllegalArgumentException.class, () -> Base24Header.parse("12356ASDASFAFWEDEAWSDW"));
    }


    @Test
    void testParsingLogonRequest() {

        String logonRequestString = "006000010";
        Base24Header logonRequest = Base24Header.parse(logonRequestString);
        assertNotNull(logonRequest);

        assertEquals(logonRequest.getOriginalMessage(), logonRequestString);
        assertEquals(logonRequest.getProductIndicator(), ProductIndicator.NMM);
        assertEquals(logonRequest.getReleaseNumber(), "60");
        assertEquals(logonRequest.getStatus(), "000");
        assertEquals(logonRequest.getOriginatorCode(), InitiatorType.BASE24_TERMINAL);
        assertEquals(logonRequest.getResponderCode(), InitiatorType.UNKNOWN);
    }

    @Test
    void testParsingLogonResponse() {
        String logonResponseString = "006000051";

        Base24Header logonResponse = Base24Header.parse(logonResponseString);
        assertNotNull(logonResponse);

        assertEquals(logonResponse.getOriginalMessage(), logonResponseString);
        assertEquals(logonResponse.getProductIndicator(), ProductIndicator.NMM);
        assertEquals(logonResponse.getReleaseNumber(), "60");
        assertEquals(logonResponse.getStatus(), "000");
        assertEquals(logonResponse.getOriginatorCode(), InitiatorType.HOST);
        assertEquals(logonResponse.getResponderCode(), InitiatorType.BASE24_TERMINAL);
    }

    @Test
    void testParsingEchoRequest() {
        String echoRequestString = "006000010";

        Base24Header echoRequest = Base24Header.parse(echoRequestString);
        assertNotNull(echoRequest);

        assertEquals(echoRequest.getOriginalMessage(), echoRequestString);
        assertEquals(echoRequest.getProductIndicator(), ProductIndicator.NMM);
        assertEquals(echoRequest.getReleaseNumber(), "60");
        assertEquals(echoRequest.getStatus(), "000");
        assertEquals(echoRequest.getOriginatorCode(), InitiatorType.BASE24_TERMINAL);
        assertEquals(echoRequest.getResponderCode(), InitiatorType.UNKNOWN);
    }

    @Test
    void testParsingEchoResponse() {
        String echoResponseString = "006000015";

        Base24Header echoResponse = Base24Header.parse(echoResponseString);
        assertNotNull(echoResponse);

        assertEquals(echoResponse.getOriginalMessage(), echoResponseString);
        assertEquals(echoResponse.getProductIndicator(), ProductIndicator.NMM);
        assertEquals(echoResponse.getReleaseNumber(), "60");
        assertEquals(echoResponse.getStatus(), "000");
        assertEquals(echoResponse.getOriginatorCode(), InitiatorType.BASE24_TERMINAL);
        assertEquals(echoResponse.getResponderCode(), InitiatorType.HOST);
    }

    @Test
    void testParsingLogoffRequest() {
        String logoffRequestString = "006000040";

        Base24Header logoffRequest = Base24Header.parse(logoffRequestString);
        assertNotNull(logoffRequest);

        assertEquals(logoffRequest.getOriginalMessage(), logoffRequestString);
        assertEquals(logoffRequest.getProductIndicator(), ProductIndicator.NMM);
        assertEquals(logoffRequest.getReleaseNumber(), "60");
        assertEquals(logoffRequest.getStatus(), "000");
        assertEquals(logoffRequest.getOriginatorCode(), InitiatorType.HISO_PROCESS);
        assertEquals(logoffRequest.getResponderCode(), InitiatorType.UNKNOWN);
    }

    @Test
    void testParsingLogoffResponse() {
        String logoffResponseString = "006000051";

        Base24Header logoffResponse = Base24Header.parse(logoffResponseString);
        assertNotNull(logoffResponse);

        assertEquals(logoffResponse.getOriginalMessage(), logoffResponseString);
        assertEquals(logoffResponse.getProductIndicator(), ProductIndicator.NMM);
        assertEquals(logoffResponse.getReleaseNumber(), "60");
        assertEquals(logoffResponse.getStatus(), "000");
        assertEquals(logoffResponse.getOriginatorCode(), InitiatorType.HOST);
        assertEquals(logoffResponse.getResponderCode(), InitiatorType.BASE24_TERMINAL);

        assertFalse(logoffResponse.isATM());
        assertFalse(logoffResponse.isPOS());
    }

    @Test
    void testParsingATMRequest() {
        String atmRequestString = "016000010";

        Base24Header atmRequest = Base24Header.parse(atmRequestString);
        assertNotNull(atmRequest);

        assertEquals(atmRequest.getOriginalMessage(), atmRequestString);
        assertEquals(atmRequest.getProductIndicator(), ProductIndicator.ATM);
        assertEquals(atmRequest.getReleaseNumber(), "60");
        assertEquals(atmRequest.getStatus(), "000");
        assertEquals(atmRequest.getOriginatorCode(), InitiatorType.BASE24_TERMINAL);
        assertEquals(atmRequest.getResponderCode(), InitiatorType.UNKNOWN);

        assertTrue(atmRequest.isATM());
        assertFalse(atmRequest.isPOS());

    }

}