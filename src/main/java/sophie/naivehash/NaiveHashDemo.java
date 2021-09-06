package sophie.naivehash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sophie.Constant;
import sophie.card.Cards;
import sophie.util.PerfUtil;

import java.io.File;
import java.util.List;

public class NaiveHashDemo {

    private static final Logger logger = LoggerFactory.getLogger(NaiveHashDemo.class);
    private static final CopiedBitArray bitArray = new CopiedBitArray(Constant.ARRAY_BITS);
    private static final File FILE_SEARCH_TEST = new File(Constant.PATH_SEARCH_TEST);

    private static void insert() {
        List<String> seqNums = Cards.getCardNums();
        long startTime = System.currentTimeMillis();

        int trueNum = 0;
        int falseNum = 0;
        for (String seqNum : seqNums) {
            boolean result = bitArray.set(NaiveHashFunc.naiveFNV(seqNum));
            if (result) {
                trueNum += 1;
            } else {
                falseNum += 1;
            }
        }

        long endTime = System.currentTimeMillis();
        long usedTime = endTime - startTime;
        logger.info("INSERT TRUE NUM : {}, FALSE NUM : {}.", trueNum, falseNum);
        logger.info("INSERT DONE. TIME USED : {} ms.", usedTime);

        PerfUtil.getUsedMemory();
    }

    private static void searchTest() {
        List<String> testCardNums = Cards.read(FILE_SEARCH_TEST);
        long startTime = System.currentTimeMillis();

        int trueNum = 0;
        int falseNum = 0;
        for (String testCardNum : testCardNums) {
            boolean result = bitArray.get(NaiveHashFunc.naiveFNV(testCardNum));
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

    public static void main(String[] args) {
        haveFun();
    }

}
