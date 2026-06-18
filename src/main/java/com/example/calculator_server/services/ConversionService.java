package com.example.calculator_server.services;

import org.springframework.stereotype.Service;

@Service
public class ConversionService {

    public String[] convert(String value, String convertTo) {
        try {
            // Clean the input: remove 0x/0X prefix if present
            String cleanValue = value.trim();
            if (cleanValue.toLowerCase().startsWith("0x")) {
                cleanValue = cleanValue.substring(2);
            }
            
            final String input = cleanValue;  // Use final for switch
            
            return switch (convertTo.toLowerCase()) {
                case "binary" -> new String[] {
                    binaryToDecimal(input),
                    binaryToHexadecimal(input)
                };
                case "decimal" -> new String[] {
                    decimalToBinary(input),
                    decimalToHexadecimal(input)
                };
                case "hexadecimal" -> new String[] {
                    hexadecimalToDecimal(input),
                    hexadecimalToBinary(input)
                };
                default -> throw new IllegalArgumentException("Invalid conversion type: " + convertTo);
            };
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input: " + value);
        }
    }

    // ==================== BINARY CONVERSIONS ====================

    /**
     * Converts a binary string to decimal using manual method
     * Uses long to support larger numbers (up to 63 bits)
     */
    private String binaryToDecimal(String value) {
        if (!value.matches("[01]+")) {
            throw new IllegalArgumentException("Invalid binary number (only 0 and 1 allowed): " + value);
        }
        
        // Use long for larger numbers (supports up to 63 bits)
        long decimal = 0;
        long power = 1;
        
        for (int i = value.length() - 1; i >= 0; i--) {
            char digitChar = value.charAt(i);
            int digit = digitChar - '0';
            decimal += digit * power;
            power *= 2;
        }
        
        return String.valueOf(decimal);
    }

    /**
     * Converts a binary string to hexadecimal
     * Algorithm: binary → decimal → hex
     */
    private String binaryToHexadecimal(String value) {
        String decimal = binaryToDecimal(value);
        return decimalToHexadecimal(decimal);
    }

    // ==================== DECIMAL CONVERSIONS ====================

    /**
     * Converts a decimal string to binary using manual method
     * Supports large numbers using long
     */
    private String decimalToBinary(String value) {
        if (!value.matches("[0-9]+")) {
            throw new IllegalArgumentException("Invalid decimal number (only digits 0-9 allowed): " + value);
        }
        
        long decimal = Long.parseLong(value);  // Use long for larger numbers
        
        if (decimal == 0) {
            return "0";
        }
        
        String binary = "";
        
        while (decimal > 0) {
            long remainder = decimal % 2;
            binary = remainder + binary;
            decimal /= 2;
        }
        
        return binary;
    }

    /**
     * Converts a decimal string to hexadecimal using manual method
     * Supports large numbers using long
     */
    private String decimalToHexadecimal(String value) {
        if (!value.matches("[0-9]+")) {
            throw new IllegalArgumentException("Invalid decimal number (only digits 0-9 allowed): " + value);
        }
        
        long decimal = Long.parseLong(value);  // Use long for larger numbers
        
        if (decimal == 0) {
            return "0";
        }
        
        String hex = "";
        
        while (decimal > 0) {
            long remainder = decimal % 16;
            
            char hexDigit;
            if (remainder < 10) {
                hexDigit = (char) ('0' + remainder);
            } else {
                hexDigit = (char) ('A' + (remainder - 10));
            }
            
            hex = hexDigit + hex;
            decimal /= 16;
        }
        
        return hex;
    }

    // ==================== HEXADECIMAL CONVERSIONS ====================

    /**
     * Converts a hexadecimal string to decimal using manual method
     * Supports large hex numbers using long
     */
    private String hexadecimalToDecimal(String value) {
        if (!value.matches("[0-9A-Fa-f]+")) {
            throw new IllegalArgumentException("Invalid hexadecimal number (only 0-9 and A-F allowed): " + value);
        }
        
        String upperValue = value.toUpperCase();
        long decimal = 0;
        long power = 1;
        
        for (int i = upperValue.length() - 1; i >= 0; i--) {
            char c = upperValue.charAt(i);
            
            long digitValue;
            if (c >= '0' && c <= '9') {
                digitValue = c - '0';
            } else {
                digitValue = (c - 'A') + 10;
            }
            
            decimal += digitValue * power;
            power *= 16;
        }
        
        return String.valueOf(decimal);
    }

    /**
     * Converts a hexadecimal string to binary
     * Algorithm: hex → decimal → binary
     */
    private String hexadecimalToBinary(String value) {
        String decimal = hexadecimalToDecimal(value);
        return decimalToBinary(decimal);
    }
}