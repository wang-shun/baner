package org.inn.baner.persist.mapper;

import org.inn.baner.bean.Post;

import java.util.List;

/**
 * Created by zhangxiaoyun on 2017/7/7.
 */
public interface PostMapperDIY {
    public List<Post> qryByTopicId(String topicid);
}
