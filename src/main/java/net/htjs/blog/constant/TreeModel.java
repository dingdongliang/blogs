package net.htjs.blog.constant;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * blog/net.htjs.blog.model
 *
 * @Description: 递归菜单模型
 * @Author: dingdongliang
 * @Date: 2018/9/11 11:41
 */
@Getter
@Setter
public class TreeModel {
    private String text;
    private String id;
    private String pid;
    private List<TreeModel> nodes;
}
