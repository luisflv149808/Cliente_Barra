package clientearchivobarra;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Metodos {

    public static final String IPv4_REGEX = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
            + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
            + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\."
            + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    public static final Pattern IPv4_PATTERN = Pattern.compile(IPv4_REGEX);

    public static boolean Numerico(String cadena) {

        boolean resultado;
        try {

            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }

    public static boolean ValidarIp(String ip) {
        if (ip == null) {
            return false;
        }
        Matcher matcher = IPv4_PATTERN.matcher(ip);
        return matcher.matches();
    }
}
