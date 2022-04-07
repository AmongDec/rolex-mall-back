package com.rolex.mall.util;

import com.rolex.mall.mapper.good.GoodMapper;
import com.rolex.mall.pojo.good.GoodKun;
import com.rolex.mall.pojo.good.GoodRating;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.slopeone.SlopeOneRecommender;
import org.apache.mahout.cf.taste.impl.similarity.CachingUserSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Component
public class UserCF {
    @Autowired
    private DataSource dataSource;

    /**
     * 简单例子、使用mysql做推荐 ，
     *
     */
    public  List<RecommendedItem> basedMysql(long userid) throws TasteException, IOException {

        JDBCDataModel dataModel = new MySQLJDBCDataModel(dataSource, "rating", "user_id", "movie_id", "rating", "rtimestamp");

        //基于欧式距离的相似度
        UserSimilarity user =new CachingUserSimilarity( new EuclideanDistanceSimilarity(dataModel),dataModel);
        //基于阈值的邻域 ， 每个用户选择5个邻居
        NearestNUserNeighborhood neighbor = new NearestNUserNeighborhood(10, user, dataModel);
        //基于用户的推荐算法
        Recommender r = new GenericUserBasedRecommender(dataModel, neighbor, user);
        //大概有610个用户
        LongPrimitiveIterator iter = dataModel.getUserIDs();
        long first = System.currentTimeMillis();
        List<RecommendedItem> list = null;
        while (iter.hasNext()) {
            long uid = iter.nextLong();
            System.out.println(uid);
            if(uid == userid) {
                list = r.recommend(uid, 8);
                //System.out.printf("uid:%s", uid);
                for (RecommendedItem ritem : list) {
                    //System.out.printf("(%s,%f)", ritem.getItemID(), ritem.getValue());
                }
                //System.out.println();
                break;
            }
        }
        long end = System.currentTimeMillis();
        //System.out.println(end - first);
        return list;
    }

    @Autowired
    private GoodMapper goodMapper;

    /**
     * 处理推荐出来数据
     */
    public List<GoodRating> parseResult(List<RecommendedItem> list){

        List<GoodRating> result = new ArrayList<>();
        if(list == null){
            return null;
        }
        Map<Long,RecommendedItem> set = new HashMap<>();
        for(RecommendedItem i : list){
            set.put(i.getItemID()%50+1 , i);
        }
        for(Long lo : set.keySet()){
            GoodRating rating = new GoodRating();
            rating.setGoodTable(goodMapper.selectById(lo+6000000));
            rating.setRating(set.get(lo).getValue());
            result.add(rating);
        }

        return result;
    }

    /**
     * 构建捆绑包
     */
    public List<GoodKun> parseKun(List<GoodRating> list){
        if(list == null){
            return null;
        }
        List<GoodKun> kun = new ArrayList<>();
        System.out.println(list);
        for(int i = 0 ; i+1 < list.size() ; i=i+2){
            GoodKun goodKun = new GoodKun();
            goodKun.setG1(list.get(i));
            goodKun.setG2(list.get(i+1));
            double src = list.get(i).getGoodTable().getGPrice()+list.get(i+1).getGoodTable().getGPrice();
            goodKun.setSrcprice(src);
            goodKun.setDesprice(src*0.3*(list.get(i).getRating()+list.get(i+1).getRating())/20);
            kun.add(goodKun);
        }
        return kun;
    }

    /**
     * 未登录，采用数据集，csv进行随机推荐
     */
    public List<RecommendedItem> basedCSV(long userid) throws TasteException, IOException {
        String file = "src/main/java/com/montnets/mtcsto/CSV/ratings.csv";
        DataModel model = new FileDataModel(new File(file));
        DataModel dataModel = model;

        long start = System.currentTimeMillis();

        //基于欧式距离的相似度
        UserSimilarity user = new CachingUserSimilarity( new EuclideanDistanceSimilarity(dataModel),dataModel);
        //基于阈值的邻域 ， 每个用户选择5个邻居
        NearestNUserNeighborhood neighbor = new NearestNUserNeighborhood(10, user, dataModel);
        //基于用户的推荐算法
        Recommender r = new GenericUserBasedRecommender(dataModel, neighbor, user);
        //大概有610个用户
        LongPrimitiveIterator iter = dataModel.getUserIDs();
        List<RecommendedItem> list = null;
        while (iter.hasNext()) {
            long uid = iter.nextLong();
            if(uid == userid) {
                list = r.recommend(uid, 10);
                //System.out.printf("uid:%s", uid);
                for (RecommendedItem ritem : list) {
                    //System.out.printf("(%s,%f)", ritem.getItemID(), ritem.getValue());
                }
                //System.out.println();
                break;
            }
        }
        return list;

    }

    /**
     * 捆绑包推荐
     * @throws TasteException
     * @throws IOException
     */
    public List<RecommendedItem>  basedItemCSV(long userid) throws TasteException, IOException {

        JDBCDataModel model = new MySQLJDBCDataModel(dataSource, "rating", "user_id", "movie_id", "rating", "rtimestamp");

        Recommender r = new SlopeOneRecommender(model);
        //大概有610个用户
        LongPrimitiveIterator iter = model.getUserIDs();
        List<RecommendedItem> list = null;
        while (iter.hasNext()) {
            long uid = iter.nextLong();
            if(uid == userid) {
                list = r.recommend(uid, 10);
                System.out.printf("uid:%s", uid);
                for (RecommendedItem ritem : list) {
                    System.out.printf("(%s,%f)", ritem.getItemID(), ritem.getValue());
                }
                System.out.println();
                break;
            }
        }
        return list;
    }

}
