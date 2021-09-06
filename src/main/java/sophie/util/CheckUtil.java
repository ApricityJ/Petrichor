package sophie.util;

import com.google.common.base.Strings;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckUtil {

    private static final Logger logger = LoggerFactory.getLogger(CheckUtil.class);

    public static void checkArgument(boolean expression, @Nullable Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    public static void checkArgument(boolean b, @Nullable String errorMessageTemplate, long p1) {
        if (!b) {
            throw new IllegalArgumentException(Strings.lenientFormat(errorMessageTemplate, new Object[]{p1}));
        }
    }

    public static int checkedCast(long value) {
        int result = (int)value;
        checkArgument((long)result == value, "Out of range: %s", value);
        return result;
    }
}
