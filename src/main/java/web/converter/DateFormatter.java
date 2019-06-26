package web.converter;

import org.springframework.format.Formatter;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Locale;

public class DateFormatter implements Formatter<LocalDate>{
    @Override
    public LocalDate parse(@Nullable String str, Locale locale) {
        return StringUtils.isEmpty(str) ? null : LocalDate.parse(str);
    }

    @Override
    public String print(LocalDate lt, Locale locale) {
        return lt.format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
