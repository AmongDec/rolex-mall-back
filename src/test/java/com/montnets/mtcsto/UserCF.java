package com.montnets.mtcsto;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.ClusterSimilarity;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.impl.similarity.CachingUserSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 基于用户的协同过滤算法
 */
public class UserCF {
    public static void main(String[] args) throws IOException, TasteException {
        basedCSV();
    }

    /**
     * 简单例子、使用csv做推荐
     */
    public static void basedCSV() throws TasteException, IOException {
        String file = "src/main/java/com/montnets/mtcsto/CSV/ratings.csv";
        DataModel model = new FileDataModel(new File(file));
        DataModel dataModel = model;

        long start = System.currentTimeMillis();

        //基于欧式距离的相似度
        UserSimilarity user = new CachingUserSimilarity( new EuclideanDistanceSimilarity(dataModel),dataModel);
        //基于阈值的邻域 ， 每个用户选择5个邻居
        NearestNUserNeighborhood neighbor = new NearestNUserNeighborhood(5, user, dataModel);
        //基于用户的推荐算法
        Recommender r = new GenericUserBasedRecommender(dataModel, neighbor, user);
        //大概有610个用户
        LongPrimitiveIterator iter = dataModel.getUserIDs();
        while (iter.hasNext()) {
            long uid = iter.nextLong();
            if(uid == 299) {
                List<RecommendedItem> list = r.recommend(uid, 5);
                System.out.printf("uid:%s", uid);
                for (RecommendedItem ritem : list) {
                    System.out.printf("(%s,%f)", ritem.getItemID(), ritem.getValue());
                }
                System.out.println();
                break;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);

    }

    public static void basedItemCSV() throws TasteException, IOException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://127.0.0.1:3306/rolex?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true&allowPublicKeyRetrieval=true");
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        dataSource.setDatabaseName("rolex");

        JDBCDataModel model = new MySQLJDBCDataModel(dataSource, "rating", "userId", "movieId", "rating", "rtimestamp");

        long start = System.currentTimeMillis();
        ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
        //基于用户的推荐算法
        Recommender r = new GenericItemBasedRecommender(model , similarity);
        //大概有610个用户
        LongPrimitiveIterator iter = model.getUserIDs();
        while (iter.hasNext()) {
            long uid = iter.nextLong();
            if(uid == 40) {
                List<RecommendedItem> list = r.recommend(uid, 5);
                System.out.printf("uid:%s", uid);
                for (RecommendedItem ritem : list) {
                    System.out.printf("(%s,%f)", ritem.getItemID(), ritem.getValue());
                }
                System.out.println();
                break;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }



    /**
     * 简单例子、使用mysql做推荐 ，
     *
     */
    public static void basedMysql() throws TasteException, IOException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://127.0.0.1:3306/rolex?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true&allowPublicKeyRetrieval=true");
        dataSource.setUser("root");
        dataSource.setPassword("123456");
        dataSource.setDatabaseName("rolex");

        JDBCDataModel dataModel = new MySQLJDBCDataModel(dataSource, "rating", "userId", "movieId", "rating", "rtimestamp");

        //基于欧式距离的相似度
        UserSimilarity user =new CachingUserSimilarity( new EuclideanDistanceSimilarity(dataModel),dataModel);
        //基于阈值的邻域 ， 每个用户选择5个邻居
        NearestNUserNeighborhood neighbor = new NearestNUserNeighborhood(5, user, dataModel);
        //基于用户的推荐算法
        Recommender r = new GenericUserBasedRecommender(dataModel, neighbor, user);
        //大概有610个用户
        LongPrimitiveIterator iter = dataModel.getUserIDs();
        long first = System.currentTimeMillis();
        while (iter.hasNext()) {
            long uid = iter.nextLong();
            System.out.println(uid);
            if(uid == 400) {
                List<RecommendedItem> list = r.recommend(uid, 4);
                System.out.printf("uid:%s", uid);
                for (RecommendedItem ritem : list) {
                    System.out.printf("(%s,%f)", ritem.getItemID(), ritem.getValue());
                }
                System.out.println();
                break;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(end - first);
    }



    public static void svd() throws TasteException, IOException {
        String file = "D:\\360极速浏览器下载\\ml-latest-small\\ratings.csv";
        DataModel dataModel = RecommendFactory.buildDataModel(file);

        RecommenderBuilder recommenderBuilder = RecommendFactory.svdRecommender(new ALSWRFactorizer(dataModel, 10, 0.05, 10));

        RecommendFactory.evaluate(RecommendFactory.EVALUATOR.AVERAGE_ABSOLUTE_DIFFERENCE, recommenderBuilder, null, dataModel, 0.7);
        RecommendFactory.statsEvaluator(recommenderBuilder, null, dataModel, 2);

        LongPrimitiveIterator iter = dataModel.getUserIDs();
        System.out.println("--------------");
        while (iter.hasNext()) {
            long uid = iter.nextLong();
            if(uid == 610) {
                List list = recommenderBuilder.buildRecommender(dataModel).recommend(uid, 5);
                //RecommendFactory.showItems(uid, list, true);
                System.out.println(list);
                break;
            }
        }
    }


    public static void treeCluster() throws TasteException, IOException {
        String file = "D:\\360极速浏览器下载\\ml-latest-small\\ratings.csv";

        DataModel dataModel = RecommendFactory.buildDataModel(file);
        UserSimilarity userSimilarity = RecommendFactory.userSimilarity(RecommendFactory.SIMILARITY.LOGLIKELIHOOD, dataModel);
        ClusterSimilarity clusterSimilarity = RecommendFactory.clusterSimilarity(RecommendFactory.SIMILARITY.FARTHEST_NEIGHBOR_CLUSTER, userSimilarity);
        RecommenderBuilder recommenderBuilder = RecommendFactory.treeClusterRecommender(clusterSimilarity, 10);

        RecommendFactory.evaluate(RecommendFactory.EVALUATOR.AVERAGE_ABSOLUTE_DIFFERENCE, recommenderBuilder, null, dataModel, 0.7);
        RecommendFactory.statsEvaluator(recommenderBuilder, null, dataModel, 2);

        LongPrimitiveIterator iter = dataModel.getUserIDs();
        while (iter.hasNext()) {
            long uid = iter.nextLong();
            List list = recommenderBuilder.buildRecommender(dataModel).recommend(uid, 5);
            RecommendFactory.showItems(uid, list, true);
        }
    }

    public static void itemCF() throws TasteException, IOException {
        String file = "D:\\360极速浏览器下载\\ml-latest-small\\ratings.csv";
        DataModel dataModel = RecommendFactory.buildDataModel(file);
        ItemSimilarity itemSimilarity = RecommendFactory.itemSimilarity(RecommendFactory.SIMILARITY.EUCLIDEAN, dataModel);
        RecommenderBuilder recommenderBuilder = RecommendFactory.itemRecommender(itemSimilarity, true);

        RecommendFactory.evaluate(RecommendFactory.EVALUATOR.AVERAGE_ABSOLUTE_DIFFERENCE, recommenderBuilder, null, dataModel, 0.7);
        RecommendFactory.statsEvaluator(recommenderBuilder, null, dataModel, 2);

        LongPrimitiveIterator iter = dataModel.getUserIDs();
        while (iter.hasNext()) {
            long uid = iter.nextLong();
            List list = recommenderBuilder.buildRecommender(dataModel).recommend(uid, 5);
            RecommendFactory.showItems(uid, list, true);
        }
    }


}
