package sophie.searchtree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sophie.Constant;
import sophie.card.Cards;
import sophie.util.PerfUtil;

import java.io.File;
import java.util.List;

public class SearchTreeDemo {

    private static final Logger logger = LoggerFactory.getLogger(SearchTree.class);
    private static final TreeNode<Integer> root = new TreeNode<>(19);
    private static final SearchTree<Integer> searchTree = new SearchTree<>(root);
    private static final File FILE_SEARCH_TEST = new File(Constant.PATH_SEARCH_TEST);

    private static void insert() {
        List<List<Integer>> splitCardNums = Cards.getComSplitCardNums();
        long startTime = System.currentTimeMillis();

        for (List<Integer> splitCardNum : splitCardNums) {
            searchTree.insert(splitCardNum);
        }

        long endTime = System.currentTimeMillis();
        long usedTime = endTime - startTime;
        logger.info("INSERT DONE. TIME USED : {} ms.", usedTime);

        PerfUtil.getUsedMemory();
    }

    private static void searchTest() {
        List<String> testCardNums = Cards.read(FILE_SEARCH_TEST);
        long startTime = System.currentTimeMillis();

        int trueNum = 0;
        int falseNum = 0;
        for (String testCardNum : testCardNums) {
            List<Integer> splitTestCardNum = Cards.toComSplitCardNum(testCardNum);
            boolean result = searchTree.search(splitTestCardNum);
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
