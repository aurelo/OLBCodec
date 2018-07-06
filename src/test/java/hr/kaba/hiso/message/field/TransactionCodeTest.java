package hr.kaba.hiso.message.field;

import hr.kaba.hiso.constants.ProductIndicator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionCodeTest {

    @Test
    public void testAtmDepositParsing() {
        String atmString = "210030";

        ATMTransactionCode atmTransactionCode = ATMTransactionCode.from(atmString);

        assertEquals(ProductIndicator.ATM, atmTransactionCode.forProduct());
        assertEquals(TransactionCode.ISOTransactionCode.DEPOSIT, atmTransactionCode.getIsoTransactionCode());
        assertEquals(TransactionCode.AccountType.NO_ACCOUNT_SPECIFIED, atmTransactionCode.getFromAccountType());
        assertEquals(TransactionCode.TransactionSettlementIndicator.PORAVNANJE_RAZMJENOM_PODATAKA, atmTransactionCode.getTransactionSettlementIndicator());
    }

    @Test
    public void testPodProdajaParsing() {
        String posProdajaString = "002000";

        POSTransactionCode posTransactionCode = POSTransactionCode.from(posProdajaString);

        assertEquals(ProductIndicator.POS, posTransactionCode.forProduct());
        assertEquals(TransactionCode.ISOTransactionCode.GOODS_AND_SERVICES, posTransactionCode.getIsoTransactionCode());
        assertEquals(TransactionCode.AccountType.CHECKING, posTransactionCode.getFromAccountType());
        assertEquals(TransactionCode.AccountType.NO_ACCOUNT_SPECIFIED, posTransactionCode.getToAccountType());
    }

}