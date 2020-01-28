package com.edge.admin.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.admin.department.entity.ERP_Department;
import com.edge.admin.post.entity.ERP_DM_Post;
import com.edge.admin.user.dao.ERP_UserDao;
import com.edge.admin.user.entity.ERP_User;
import com.edge.admin.user.entity.User_QueryVo;
import com.edge.admin.user.service.inter.ERP_UserService;

/**
 * 用户业务逻辑层
 * 
 * @author NingCG
 *
 */
@Service
public class ERP_UserServiceImpl implements ERP_UserService {

	@Resource
	private ERP_UserDao erp_UserDao;

	// 分页展现用户列表
	public List<ERP_User> userList(User_QueryVo vo) {
		return erp_UserDao.userList(vo);
	}

	// 用户列表总数量
	public Integer userCount(User_QueryVo vo) {
		return erp_UserDao.userCount(vo);
	}

	// 新增用户
	public void saveUser(ERP_User user) {
		erp_UserDao.saveUser(user);
	}

	// 根据id获得用户对象
	public ERP_User queryUserById(Integer userId) {
		return erp_UserDao.queryUserById(userId);
	}

	// 编辑操作
	public void editUser(ERP_User user) {
		erp_UserDao.editUser(user);
	}

	// 删除操作
	public void deleteUserById(ERP_User user) {
		erp_UserDao.deleteUserById(user);
	}

	// 加载所有未删除的岗位
	public List<ERP_DM_Post> postList() {
		return erp_UserDao.postList();
	}

	// 加载所有未删除的部门
	public List<ERP_Department> depList() {
		return erp_UserDao.depList();
	}

	// 登录名检测
	public ERP_User checkLoginName(String loginName) {
		return erp_UserDao.checkLoginName(loginName);
	}

	// 根据岗位获得用户对象集合
	public List<ERP_User> queryUserByPost(Integer post_Id) {
		return erp_UserDao.queryUserByPost(post_Id);
	}

}
