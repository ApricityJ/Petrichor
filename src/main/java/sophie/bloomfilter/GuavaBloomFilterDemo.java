package sophie.bloomfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sophie.Constant;
import sophie.card.Cards;
import sophie.util.PerfUtil;

import java.io.File;
import java.util.List;

public class GuavaBloomFilterDemo {

    private static final Logger logger = LoggerFactory.getLogger(GuavaBloomFilterDemo.class);
    private static final GuavaBloomFilter bloomFilter = new GuavaBloomFilter(Constant.BLOOM_FILTER_CAPACITY, Constant.FPP);
    private static final File FILE_SEARCH_TEST = new File(Constant.PATH_SEARCH_TEST);

    private static void insert() {
        List<String> cardNums = Cards.getCardNums();
        long startTime = System.currentTimeMillis();

        for (String cardNum : cardNums) {
            bloomFilter.insert(cardNum);
        }

        long endTime = System.currentTimeMillis();
        long usedTime = endTime - startTime;
        logger.info("INSERT DONE. TIME USED : {} ms.", usedTime);

        PerfUtil.getUsedMemory();
        PerfUtil.bloomCompute();
    }

    private static void searchTest() {
        List<String> testCardNums = Cards.read(FILE_SEARCH_TEST);
        long startTime = System.currentTimeMillis();

        int trueNum = 0;
        int falseNum = 0;
        for (String testCardNum : testCardNums) {
            boolean result = bloomFilter.search(testCardNum);
            if (result) {
                trueNum += 1;
            } else {
                falseNum += 1;
            }
        }

        long endTime = System.currentTimeMillis();
        long usedTime = endTime - startTime;
        logger.info("SEARCH TRUE NUM : {}, FALSE NUM : {}.", trueNum, falseNum);
        logger.info("SEARCH DONE. TIME USED : {} ms.", usedTime);
    }

    public static void haveFun() {
        insert();
        searchTest();
    }
}
