package hr.kaba.hiso.message;

import hr.kaba.hiso.constants.MessageType;
import hr.kaba.hiso.constants.ProductIndicator;
import hr.kaba.hiso.message.bitmap.Bitmap;
import hr.kaba.hiso.message.bitmap.PrimaryBitmapField;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OLBMessagesTest {
    @Test
    public void testNullHeaderCreation() {
        assertThrows(IllegalArgumentException.class, () -> OLBMessages.parse(null));
    }

    @Test
    public void testIllegalStringHeaderCreation() {
        assertThrows(IllegalArgumentException.class, () -> OLBMessages.parse("127856"));
    }

    @Test
    public void parsingLogonRequest() {
        String logonRequestString = " DISO0060000100800822000000000000004000000000000000619111227000001001";

        OLBMessage logonReqHeader = OLBMessages.parse(logonRequestString);

        assertNotNull(logonReqHeader);
//        assertEquals("006000010", logonReqHeader.getBase24Header().getOriginalMessage());
        assertEquals(MessageType.NMM_REQ, logonReqHeader.getMessageType());
        assertEquals("8220000000000000", logonReqHeader.getPrimaryBitmap());
    }

    @Test
    void testParsingLogonResponse() {
        String logonResponseString = " FISO006000051081082200000020000000400000000000000061911125100000100001";

        OLBMessage logonResponseHeader = OLBMessages.parse(logonResponseString);

        assertNotNull(logonResponseHeader);
//        assertEquals("006000051", logonResponseHeader.getBase24Header().getOriginalMessage());
        assertEquals(MessageType.NMM_RESP, logonResponseHeader.getMessageType());
        assertEquals("8220000002000000", logonResponseHeader.getPrimaryBitmap());
    }

    @Test
    void testParsingEchoRequest() {
        String echoRequestString = " DISO0060000100800822000000000000004000000000000000619111236000001301";

        OLBMessage echoReqHeader = OLBMessages.parse(echoRequestString);

        assertNotNull(echoReqHeader);
//        assertEquals("006000010", echoReqHeader.getBase24Header().getOriginalMessage());
        assertEquals(MessageType.NMM_REQ, echoReqHeader.getMessageType());
        assertEquals("8220000000000000", echoReqHeader.getPrimaryBitmap());
    }

    @Test
    void testParsingEchoResponse(){
        String echoResponseString = " FISO006000015081082200000020000000400000000000000061911125300000100301";

        OLBMessage echoResponseHeader = OLBMessages.parse(echoResponseString);

        assertNotNull(echoResponseHeader);
//        assertEquals("006000015", echoResponseHeader.getBase24Header().getOriginalMessage());
        assertEquals(MessageType.NMM_RESP, echoResponseHeader.getMessageType());
        assertEquals("8220000002000000", echoResponseHeader.getPrimaryBitmap());
    }

    @Test
    void testParsingLogoffRequest(){
        String logoffReqString = "  DISO0060000400800822000000000000004000000000000000619111247000001002";

        OLBMessage logoffReqHeader = OLBMessages.parse(logoffReqString);

        assertNotNull(logoffReqHeader);
//        assertEquals("006000040", logoffReqHeader.getBase24Header().getOriginalMessage());
        assertEquals(MessageType.NMM_REQ, logoffReqHeader.getMessageType());
        assertEquals("8220000000000000", logoffReqHeader.getPrimaryBitmap());
    }

    @Test
    void testParsingLogoffResponse(){
        String logoffResponseString = " FISO006000015081082200000020000000400000000000000061911125600000100002";

        OLBMessage logoffResponseHeader = OLBMessages.parse(logoffResponseString);

        assertNotNull(logoffResponseHeader);
//        assertEquals("006000015", logoffResponseHeader.getBase24Header().getOriginalMessage());
        assertEquals(MessageType.NMM_RESP, logoffResponseHeader.getMessageType());
        assertEquals("8220000002000000", logoffResponseHeader.getPrimaryBitmap());
    }

    @Test
    void testParsingATMReqUplata() {
        String atmReqString = "\u0001\u000BISO0160000100200B238820128A0801800000000100000000120000000000400000607091818011583111818060706070021119101247005376020202001939466=190422100000216080003347        A1247005        KABA D.RESA-STARA POSLDUGA RESA    HR HR1910122400PRO1+0000132400PRO10100P1124000080000";

        OLBMessage atmReqHeader = OLBMessages.parse(atmReqString);

        assertNotNull(atmReqHeader);
//        assertEquals("016000010", atmReqHeader.getBase24Header().getOriginalMessage());
        assertEquals(MessageType.TRX_REQ, atmReqHeader.getMessageType());
        assertEquals("B238820128A08018", atmReqHeader.getPrimaryBitmap());
    }

    @Test
    void testParsingATMReqIsplata() {
        String atmReqString = ">ISO0160000130221B23880012EA0801800000040100000102100300000000100000628080948000007100624062806281119100055555375590722400899963=191220100000000000005964        75542200S1AWNIPV        IZ SIM                ZAGREB IZ       HR1910122402TES1+0000132400TES10031P02005964        06281006242506280000000000112400000000000144";

        OLBMessage atmReqHeader = OLBMessages.parse(atmReqString);

        assertNotNull(atmReqHeader);
//        assertEquals("016000013", atmReqHeader.getBase24Header().getOriginalMessage());
        assertEquals(MessageType.TRX_ADVICE_REPEAT, atmReqHeader.getMessageType());
        assertEquals("B23880012EA08018", atmReqHeader.getPrimaryBitmap());
    }

}