package com.epam.izh.rd.online.service;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRegExpService implements RegExpService {

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    private final static String FILE_NAME = "src/main/resources/sensitive_data.txt";

    @Override
    public String maskSensitiveData() {
        String content = readFileByName();

        Pattern pattern = Pattern.compile("(\\d{4} \\d{4} \\d{4} \\d{4})");
        Matcher matcher = pattern.matcher(content);

        String maskedString = content;

        while (matcher.find()) {
            String code = matcher.group();
            String masked = getMaskedCardNumber(code);
            maskedString = maskedString.replace(code, masked);
        }

        return maskedString;
    }

    String readFileByName() {
        File file = new File(SimpleRegExpService.FILE_NAME);

        StringBuilder sb = new StringBuilder();
        try (FileInputStream fileInputStream = new FileInputStream(file);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)
        ) {
            String buffer;
            while ((buffer = bufferedReader.readLine()) != null) {
                sb.append(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    /*
     *    Alternative way
     *    String masked = new String(cardNumber);
     *    masked = masked.replaceAll("(?<=\\d{4} )\\d{4}(?= \\d{4} \\d{4})", "****");
     *    masked = masked.replaceAll("(?<=\\d{4} .{4} )\\d{4}(?= \\d{4})", "****");
     *    return masked;
     * */
    String getMaskedCardNumber(String cardNumber) {
        Pattern pattern = Pattern.compile("\\d{4} (\\d{4}) (\\d{4}) \\d{4}");
        Matcher matcher = pattern.matcher(cardNumber);

        int groupCount = matcher.groupCount();
        StringBuilder output = new StringBuilder(cardNumber);
        while (matcher.find()) {
            for (int i = 1; i <= groupCount; i++) {
                int start = matcher.start(i);
                int end = matcher.end(i);
                output.replace(start, end, "****");
            }
        }

        return output.toString();
    }

    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        return null;
    }
}
