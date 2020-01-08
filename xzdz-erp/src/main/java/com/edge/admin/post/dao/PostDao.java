package com.edge.admin.post.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.edge.admin.post.entity.ERP_DM_Post;
import com.edge.admin.post.entity.Post_QueryVo;

public interface PostDao {
	// 分页显示岗位列表
	public List<ERP_DM_Post> postList(Post_QueryVo vo);

	// 显示岗位列表数量
	public Integer postCount(Post_QueryVo vo);

	// 新增岗位
	public void savePost(ERP_DM_Post post);

	// 根据id获得岗位对象
	public ERP_DM_Post queryPostById(@Param("post_Id") Integer post_Id);

	// 编辑岗位
	public void editPost(ERP_DM_Post post);
	
	//删除岗位(逻辑删除)
	public void deletePost(ERP_DM_Post post);
	
}
