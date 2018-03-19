package io.zipcoder;

public class ErrorLogger {

    private int errorCount = 0;

    public void incrementError() {
        errorCount++;
    }

    public String getFormattedError(){
        StringBuilder sb = new StringBuilder();
        sb.append("Errors\t\t\t\tseen: " + errorCount);
        if (ItemParseException.errorCount > 1) {
            sb.append(" times");
        }
        else {
            sb.append(" time");
        }
        sb.append("\n");
        return sb.toString();
    }
}
