package com.github.soonboylena.myflow.Auth.jpa;

import com.github.soonboylena.myflow.Auth.bean.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
*   @author lungern xiii.at.cn@gmail.com
*   @date 2018/2/2
*
*/

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findAllByCurrentKey(String key);

    List<Menu> findAllByCurrentKeyIn(List<String> keyList);

}
