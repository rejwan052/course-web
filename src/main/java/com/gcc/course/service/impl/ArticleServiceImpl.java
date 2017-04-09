package com.gcc.course.service.impl;

import com.gcc.course.domain.Section;
import com.gcc.course.domain.Tag;
import com.gcc.course.repository.ArticleRepository;
import com.gcc.course.domain.Article;
import com.gcc.course.repository.TagRepository;
import com.gcc.course.service.ArticleService;
import com.gcc.course.service.SectionService;
import com.gcc.course.service.TagService;
import com.gcc.course.utils.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by WangZK on 2017/3/12.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TagService tagService;

    /**
     * 保存文章，如果文章已经存在，对文章进行更新
     *
     * @param article
     * @return
     */
    @Override
    public WebResult save(Article article) {
        WebResult webResult = null;
        String id = article.getId();
        article.setTags(tagService.saveBySet(article.getTags()));
        articleRepository.save(article);
        if (articleRepository.findOne(id) == null) {
            webResult = new WebResult(1,"文章保存成功",article);
        } else {
            webResult = new WebResult(1,"文章更新成功",article);
        }

        return webResult;
    }

    /**
     * 发布文章
     *
     * @param article
     * @return
     */
    @Override
    public WebResult release(Article article) {
        WebResult webResult = null;

        //将文章状态设置为发布
        article.setState(1);
        article.setTags(tagService.saveBySet(article.getTags()));
        articleRepository.save(article);
        webResult = new WebResult(1,"文章发布成功",article);

        return webResult;
    }


    /**
     * 发布文章
     *
     * @param id
     * @return
     */
    @Override
    public WebResult release(String id) {
        WebResult webResult = new WebResult();
        Article article = articleRepository.findOne(id);
        if (article != null) {
            //将文章状态设置为发布
            article.setState(1);
            articleRepository.save(article);
            webResult.setStatus(1);
            webResult.setMsg("文章发布成功");
            webResult.setData(article);
        }
        return webResult;
    }

    /**
     * 根据id查找文章
     *
     * @param id
     * @return
     */
    @Override
    public Article get(String id) {
        try {
            return articleRepository.findOne(id);
        } catch (IllegalArgumentException e) {
            return null;
        } catch (InvalidDataAccessApiUsageException e) {
            return null;

        }
    }


    /**
     * 修改文章
     *
     * @param article
     * @return
     */
    @Override
    public WebResult update(Article article) {
        return null;
    }

    /**
     * 删除文章
     *
     * @param id
     * @return
     */
    @Override
    public boolean remove(String id) {
        try {
            Integer result = articleRepository.uploadStatusAndDeletedTimeById(1, LocalDateTime.now(), id);
            if (result == 1) {
                return true;
            } else {
                return false;
            }
        } catch (IllegalArgumentException e) {
            return false;
        } catch (InvalidDataAccessApiUsageException e) {
            return false;
        }
    }

    /**
     * 恢复文章
     *
     * @param id
     * @return
     */
    @Override
    public boolean recovery(String id) {
        try {
            Integer result = articleRepository.uploadStatusAndDeletedTimeById(0, null, id);
            if (result == 1) {
                return true;
            } else {
                return false;
            }
        } catch (IllegalArgumentException e) {
            return false;
        } catch (InvalidDataAccessApiUsageException e) {
            return false;
        }
    }
}
