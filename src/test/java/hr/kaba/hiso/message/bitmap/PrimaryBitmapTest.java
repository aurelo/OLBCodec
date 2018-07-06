package hr.kaba.hiso.message.bitmap;

import static hr.kaba.hiso.message.bitmap.PrimaryBitmapField.*;

import hr.kaba.hiso.constants.ProductIndicator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrimaryBitmapTest {

    @Test
    public void testBitmapRepresentation() {
        String hex = "C220000000018010";
        String binary = "1100001000100000000000000000000000000000000000011000000000010000";

        Bitmap bitmap = new Bitmap(hex, PrimaryBitmapField.class, ProductIndicator.ATM);

        assertEquals(binary, bitmap.getBinaryRepresentation());
        assertEquals(hex, bitmap.getHexRepresentation());
    }

    @Test
    public void testExpectedFields() {
        String hex = "C220000000018010";
        PrimaryBitmapField[] bitmapContainsFields = {P1, P2, P7, P11, P48A, P49, P60A};


        Bitmap bitmap = new Bitmap(hex, PrimaryBitmapField.class, ProductIndicator.ATM);

        assertArrayEquals(bitmapContainsFields, bitmap.getPresentFields().toArray());
    }

    @Test
    public void testContainsField() {
        String hex = "C220000000018010";
        Bitmap bitmap = new Bitmap(hex, PrimaryBitmapField.class, ProductIndicator.ATM);

        assertTrue(bitmap.contains(P1));
        assertTrue(bitmap.contains(P2));
        assertTrue(bitmap.contains(P7));
        assertTrue(bitmap.contains(P11));
        assertTrue(bitmap.contains(P48A));
        assertTrue(bitmap.contains(P49));
        assertTrue(bitmap.contains(P60A));

        assertFalse(bitmap.contains(P3));
        assertFalse(bitmap.contains(P8));
        assertFalse(bitmap.contains(P61A));

    }

    @Test
    public void testATMDepositBitmapRepresentation() {
        String ATMDepositHex = "23880012EA080180";
        String ATMDepositBin = "0010001110001000000000000001001011101010000010000000000110000000";

        Bitmap bitmap = new Bitmap(ATMDepositHex, PrimaryBitmapField.class, ProductIndicator.ATM);

        assertEquals(ATMDepositBin, bitmap.getBinaryRepresentation());
        assertEquals(ATMDepositHex, bitmap.getHexRepresentation());
    }

    @Test
    public void testATMDepositExpectedFields() {
        Bitmap bitmap = new Bitmap("B23880012EA08018", PrimaryBitmapField.class, ProductIndicator.ATM);

        PrimaryBitmapField[] expected = new PrimaryBitmapField[]{P1, P3, P4, P7, P11, P12, P13, P17, P32, P35, P37, P38, P39, P41, P43, P49, P60A, P61A};

        assertArrayEquals(expected, bitmap.getPresentFields().toArray());

    }

}