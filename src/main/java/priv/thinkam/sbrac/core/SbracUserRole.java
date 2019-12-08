package priv.thinkam.sbrac.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户-角色信息
 *
 * @author thinkam
 * @date 2019/12/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SbracUserRole {
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 角色名集合
	 */
	private List<String> roleNames;
}
