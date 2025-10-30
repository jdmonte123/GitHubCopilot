import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class DateTimeConverter {
    /**
     * Converts java.util.Date to LocalDateTime using system default timezone.
     * @param date the java.util.Date to convert
     * @return LocalDateTime representation
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) return null;
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * Converts java.util.Date to LocalDateTime in EST timezone (America/New_York).
     * @param date the java.util.Date to convert
     * @return LocalDateTime in EST timezone
     */
    public static LocalDateTime toLocalDateTimeEST(Date date) {
        if (date == null) return null;
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.of("America/New_York"));
    }

    public static void main(String[] args) {
        java.util.Date now = new java.util.Date();
        System.out.println("java.util.Date: " + now);
        System.out.println("LocalDateTime (system default): " + DateTimeConverter.toLocalDateTime(now));
        System.out.println("LocalDateTime (America/New_York): " + DateTimeConverter.toLocalDateTimeEST(now));
        // demonstrate null handling
        System.out.println("Null input -> " + DateTimeConverter.toLocalDateTime(null));
    }
}
