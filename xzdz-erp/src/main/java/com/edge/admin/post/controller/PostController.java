package com.edge.admin.post.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.edge.admin.department.entity.ERP_Department;
import com.edge.admin.department.service.inter.ERP_DepartmentService;
import com.edge.admin.post.entity.ERP_DM_Post;
import com.edge.admin.post.entity.Post_QueryVo;
import com.edge.admin.post.service.inter.PostService;
import com.google.gson.Gson;

/**
 * 岗位控制跳转层
 * 
 * @author NingCG
 *
 */
@Controller
@RequestMapping(value = "post")
public class PostController {
	@Resource
	private PostService postService;

	@Resource
	private ERP_DepartmentService departmentService;

	// 跳转至岗位列表页面
	@RequestMapping(value = "/initPostList.do")
	public String initPostList() {
		return "admin/post/postList";
	}

	// easyUI 角色列表查询
	@RequestMapping(value = "/postList.do")
	@ResponseBody
	public String roleList(@RequestParam Integer page, Integer rows, String postName, Integer department,
			String postCode) {
		// new出查询对象
		Post_QueryVo vo = new Post_QueryVo();
		Gson gson = new Gson();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		vo.setPage((page - 1) * rows + 1);
		vo.setRows(page * rows);
		if (postName != null && postName != "") {
			vo.setPostName(postName.trim());
		}
		if (department != null) {
			vo.setDepartment(department);
		}
		if (postCode != null && postCode != "") {
			vo.setPostCode(postCode);
		}
		List<ERP_DM_Post> postList = postService.postList(vo);
		for (ERP_DM_Post p : postList) {
			ERP_Department d = departmentService.queryDepById(p.getPost_Department());
			p.setDep_Name(d.getDep_Name());
		}
		map.put("total", postService.postCount(vo));
		map.put("rows", postList);
		String json = gson.toJson(map);
		return json.toString();
	}

	// 跳转至岗位新增页面
	@RequestMapping(value = "/initSavePost.do")
	public String initSavePost() {
		return "admin/post/savePost";
	}

	// 新增岗位
	@RequestMapping(value = "/savePost.do")
	public String savePost(ERP_DM_Post post, Model model) {
		post.setPost_Flag(false);
		postService.savePost(post);
		model.addAttribute("flag", true);
		return "admin/post/savePost";
	}

	// 跳转至编辑岗位页面
	@RequestMapping(value = "/initEditPost.do")
	public String initEditPost(@RequestParam Integer post_Id, Model model) {
		ERP_DM_Post post = postService.queryPostById(post_Id);
		model.addAttribute("post", post);
		return "admin/post/editPost";
	}

	// 编辑操作
	@RequestMapping(value = "/editPost.do")
	public String editPost(ERP_DM_Post post, Model model) {
		postService.editPost(post);
		model.addAttribute("flag", true);
		return "admin/post/editPost";
	}

	// 删除部门(逻辑删除)
	@RequestMapping(value = "/deletePost.do")
	@ResponseBody
	public String deletePost(Integer post_Id) {
		JSONObject jsonObject = new JSONObject();
		ERP_DM_Post post = postService.queryPostById(post_Id);
		post.setPost_Flag(true);
		postService.deletePost(post);
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 批量删除(逻辑删除)
	@RequestMapping(value = "/batchDeletePost.do")
	@ResponseBody
	public String batchDeletePost(String ids) {
		JSONObject jsonObject = new JSONObject();
		// 将ids拆分为字符组
		String[] depIds = ids.split(",");
		// 遍历该数组
		for (String id : depIds) {
			// 进行删除
			ERP_DM_Post post = postService.queryPostById(Integer.parseInt(id.trim()));
			post.setPost_Flag(true);
			postService.deletePost(post);
		}
		jsonObject.put("flag", true);
		return jsonObject.toString();
	}

	// 查询所有未删除的岗位
	@RequestMapping(value = "/queryAllPost.do")
	@ResponseBody
	public String queryAllPost() {
		JSONArray jsonArray = new JSONArray();
		List<ERP_DM_Post> post = postService.queryAllPost();
		for (ERP_DM_Post p : post) {
			jsonArray.add(p);
		}
		return jsonArray.toString();
	}
}
