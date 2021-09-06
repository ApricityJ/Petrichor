package sophie.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sophie.Constant;

public class PerfUtil {

    private static final Logger logger = LoggerFactory.getLogger(PerfUtil.class);

    public static void getUsedMemory() {
        long totalMemory = Runtime.getRuntime().totalMemory() / (1024*1024);
        long freeMemory = Runtime.getRuntime().freeMemory() / (1024*1024);
        long usedMemory = totalMemory - freeMemory;
        logger.info("TOTAL MEMORY : {} MB.", totalMemory);
        logger.info("FREE MEMORY : {} MB.", freeMemory);
        logger.info("USED MEMORY : {} MB.", usedMemory);
    }

    public static void bloomCompute() {
        int bloomFilterCapacity = Constant.BLOOM_FILTER_CAPACITY;
        double fpp = Constant.FPP;
        long needBits = (long)((double)(-bloomFilterCapacity) * Math.log(fpp) / (Math.log(2.0D) * Math.log(2.0D)));
        int hashFuncNum = Math.max(1, (int)Math.round((double)needBits / (double)bloomFilterCapacity * Math.log(2.0D)));
        logger.info("NEED BITS : {}.", needBits);
        logger.info("NEED HASH FUNCTION : {}.", hashFuncNum);
    }
}
