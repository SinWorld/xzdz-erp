package com.edge.admin.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.admin.department.entity.ERP_Department;
import com.edge.admin.post.entity.ERP_DM_Post;
import com.edge.admin.user.entity.ERP_User;
import com.edge.admin.user.entity.User_QueryVo;

public interface ERP_UserDao {
	// 分页展现用户列表
	public List<ERP_User> userList(User_QueryVo vo);

	// 用户列表数量
	public Integer userCount(User_QueryVo vo);

	// 新增用户
	public void saveUser(ERP_User user);

	// 根据id获得用户对象
	public ERP_User queryUserById(@Param("userId") Integer userId);

	// 编辑操作
	public void editUser(ERP_User user);

	// 删除操作
	public void deleteUserById(ERP_User user);

	// 加载所有没有删除的岗位
	public List<ERP_DM_Post> postList();

	// 加载所有没有删除的部门
	public List<ERP_Department> depList();
	
	//登录名检测
	public ERP_User checkLoginName(@Param("loginName") String loginName);
	
	//根据岗位获得用户对象集合
	public List<ERP_User> queryUserByPost(@Param("post_Id")Integer post_Id);

}
