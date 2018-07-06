import hr.kaba.hiso.constants.ProductIndicator;

import java.math.BigInteger;

public class App {
    private static String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");

        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }

        return output.toString();
    }


    public static void main(String[] args) {
        System.out.println("App");

        String str = "010849534f303136303030303130303230304232333838303031323841303830313830303030303030303130303030303030323130303330303030303030303035303030303632383037333932363031363837353039333932363036323830363238313131393130303035353535353337353539303732323430303839393934383d3139313232303130303030303031313137303030353936312020202020202020533141574e4950562020202020202020495a2053494d202020202020202020202020202020205a414752454220495a20202020202020485231393130313232343032544553312b303030303133323430305445533130303331503131323430303030303030303003";
        System.out.println(str);
        System.out.println(hexToAscii(str));

        String format = "%04d";
        System.out.println(String.format(format, 5));
    }
}
