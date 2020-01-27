package com.feicheng.authority.common.response;



import com.feicheng.authority.system.entity.Menu;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author FC
 */
@Data
public class MenuTree<T> implements Serializable {

    private static final long serialVersionUID = 7681873362531265829L;

    private String id;

    private String icon;

    private String href;

    private String title;

    private Map<String, Object> state;

    private boolean checked = false;

    private Map<String, Object> attributes;

    private List<MenuTree<T>> children = new ArrayList<>();

    private String parentId;

    private boolean hasParent = false;

    private boolean hasChild = false;

    private Menu data;


}