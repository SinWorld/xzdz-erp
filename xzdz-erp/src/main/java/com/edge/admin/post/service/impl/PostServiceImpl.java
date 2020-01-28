package com.edge.admin.post.service.impl;

/**
 * 岗位业务逻辑层
 * @author NingCG
 *
 */

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.edge.admin.post.dao.PostDao;
import com.edge.admin.post.entity.ERP_DM_Post;
import com.edge.admin.post.entity.Post_QueryVo;
import com.edge.admin.post.service.inter.PostService;

@Service
public class PostServiceImpl implements PostService {
	@Resource
	private PostDao postDao;

	// 分页显示岗位列表
	public List<ERP_DM_Post> postList(Post_QueryVo vo) {
		return postDao.postList(vo);
	}

	// 显示岗位列表数量
	public Integer postCount(Post_QueryVo vo) {
		return postDao.postCount(vo);
	}

	// 新增岗位
	public void savePost(ERP_DM_Post post) {
		postDao.savePost(post);
	}

	// 根据id获得岗位对象
	public ERP_DM_Post queryPostById(Integer post_Id) {
		return postDao.queryPostById(post_Id);
	}

	// 编辑岗位
	public void editPost(ERP_DM_Post post) {
		postDao.editPost(post);
	}

	// 删除岗位(逻辑删除)
	public void deletePost(ERP_DM_Post post) {
		postDao.deletePost(post);
	}

	// 查询所有的未删除的岗位
	public List<ERP_DM_Post> queryAllPost() {
		return postDao.queryAllPost();
	}

}
