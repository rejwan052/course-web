package com.gcc.course.repository;

import com.gcc.course.domain.CourseTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by huangMP on 2017/3/30.
 * decription :
 */
public interface CourseTagRepository extends JpaRepository<CourseTag, String> {
}
