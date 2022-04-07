package com.montnets.mtcsto;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.slopeone.SlopeOneRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CachingUserSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

//推荐算法准确率召回率计算测试类
public class PreRecallEvaluation {
    public static void main(String[] args) throws IOException, TasteException
    {
        RandomUtils.useTestSeed();//确保每次结果一致

        DataModel model = new FileDataModel(new File("src/main/java/com/montnets/mtcsto/CSV/ratings.csv"));
        RecommenderIRStatsEvaluator evaluator = new GenericRecommenderIRStatsEvaluator();
        RecommenderBuilder builder = new RecommenderBuilder(){
            @Override
            public Recommender buildRecommender(DataModel model)
                        throws TasteException {
                ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);//计算内容相似度
                return new GenericItemBasedRecommender(model, similarity);//构造推荐引擎

            }

        };
        IRStatistics stats = evaluator.evaluate(builder, null, model, null, 2, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1.0);
        System.out.println(stats.getPrecision());
        System.out.println(stats.getRecall());
    }
}
