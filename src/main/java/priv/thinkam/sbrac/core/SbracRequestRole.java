package priv.thinkam.sbrac.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 请求url，请求http方法，角色信息
 *
 * @author thinkam
 * @date 2019/12/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SbracRequestRole {
    /**
     * http请求url
     */
    private String requestUrl;
    /**
     * http请求方法
     */
    private String requestMethod;
    /**
     * 角色名集合
     */
    private List<String> roleNames;
}