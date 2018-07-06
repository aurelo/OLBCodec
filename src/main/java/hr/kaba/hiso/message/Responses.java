package hr.kaba.hiso.message;

import hr.kaba.hiso.constants.ProductIndicator;
import hr.kaba.hiso.message.bitmap.BitmapField;
import hr.kaba.hiso.message.bitmap.PrimaryBitmapField;
import hr.kaba.hiso.message.bitmap.SecondaryBitmapField;

import java.util.*;


public abstract class Responses {

    private final static List<BitmapField> nmmFields;
    private final static List<BitmapField> atmFields;
    private final static List<BitmapField> posFields;

    static
    {
        nmmFields = Arrays.asList(new BitmapField[]{PrimaryBitmapField.P39});
        atmFields = Arrays.asList(new BitmapField[]{PrimaryBitmapField.P39, PrimaryBitmapField.P44A});
        posFields = Arrays.asList(new BitmapField[]{PrimaryBitmapField.P39, SecondaryBitmapField.S127P});
    }

    public static List<BitmapField> responseFields(ProductIndicator product) {
        switch (product){
            case NMM:
                return nmmFields;
            case ATM:
                return atmFields;
            case POS:
                return posFields;
            default:
                return Collections.EMPTY_LIST;
        }

    }

    public static boolean validResponse(ProductIndicator product, Map<BitmapField, String> response) {
        return response.containsKey(responseFields(product));
    }

}
