package com.test.ctrl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.model.FileInfo;
import com.test.model.FunctionInfo;
import com.test.model.OrgInfo;
import com.test.model.TreeInfo;
import com.test.model.UserInfo;
import com.test.service.IOrgModel;
import com.test.util.Util;

@Controller
public class MainCtrl {
	@Autowired
	private IOrgModel orgmodel;

	
	@RequestMapping("/")
	public String login()
	{
		return "login";
	}
	
	@RequestMapping("/init")
	public String home()
	{
		return "login";
	}
	
	@RequestMapping("/main")
	public String main()
	{
		return "main";
	}
	
	@ResponseBody
	@RequestMapping("/login")
	public Boolean login(HttpServletRequest req,HttpServletResponse resp,String loginId,String pwd)
	{
		try
		{
			//初始化组织机构
			orgmodel.initOrgModel();
			//记录Cookie
			Cookie c = new Cookie("loginId",loginId);  
            c.setPath(req.getContextPath());
            resp.addCookie(c);
			Cookie c2 = new Cookie("pwd",pwd);  
            c2.setPath(req.getContextPath());
            resp.addCookie(c2);
            
			String pwd2 = Util.getMD5(pwd);
			Boolean rtn = orgmodel.login(loginId, pwd2);
			System.out.println("rtn="+rtn);
			return rtn;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			String msg = e.getMessage();
			req.setAttribute("msg", msg);
		}
		return false;
	}
	
	@RequestMapping("/user")
	public String user()
	{
		return "/orgmodel/user";
	}
	
	@RequestMapping("/org")
	public String org()
	{
		return "/orgmodel/org";
	}
	
	@ResponseBody
	@RequestMapping("/orgload")
	public List orgload()
	{
		List orgs = new ArrayList();
		try
		{
			orgs = orgmodel.getOrgList();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return orgs;
	}
	
	@ResponseBody
	@RequestMapping("/userload")
	public Map userload(HttpServletRequest req,Integer page,Integer rows)
	{
		Map m = new HashMap();
		try
		{
			//判断分页参数是否为空
			if(page == null)
				page = 1;
			if(rows == null)
				rows = 4;
			//用户名称查询参数
			String qname = req.getParameter("qname");
			if(qname == null)
				qname = "";
			System.out.println("qname="+qname);
			//分页插件定义分页参数,一定在执行SQL之前执行
			PageHelper.startPage(page, rows);
			List<UserInfo> result = orgmodel.findUserListByName(qname);
			//定义分页对象存储分页数据
			PageInfo<UserInfo> pi = new PageInfo<UserInfo>(result);
			List<UserInfo> users = pi.getList();
			Long total = pi.getTotal();
			m.put("total", total);
			m.put("rows", users);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return m;
	}
	
	@ResponseBody
	@RequestMapping("/usersave")
	public Boolean usersave(HttpServletRequest req,UserInfo ui)
	{
		Integer id = ui.getId();
		System.out.println("id="+id);
		try
		{
			FileInfo fi = uploadFile(req);
			if(fi != null)
				ui.setImgId(fi.getId());
			if(id == null)
			{
				orgmodel.saveUser(ui);
			}
			else
			{
				orgmodel.updateUser(ui);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}
	
	@ResponseBody
	@RequestMapping("/userdel")
	public Boolean userdel(HttpServletRequest req,Integer[] ids)
	{
		try
		{
			if(ids != null)
			{
				for(Integer id:ids)
				{
					if(id != null && id != 1)
						orgmodel.deleteUser(id);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return true;
	}
	
	@ResponseBody
	@RequestMapping("/orgroot")
	public List<TreeInfo> orgroot()
	{
		OrgInfo root = orgmodel.getRootOrg();
		List<TreeInfo> trees = new ArrayList<TreeInfo>();
		TreeInfo rootTree = new TreeInfo();
		rootTree.setId(root.getId().toString());
		rootTree.setText(root.getName());
		rootTree.setState("closed");
		Map attributes = new HashMap();
		attributes.put("parentId",root.getParentId());
		attributes.put("priority", root.getPriority());
		attributes.put("path", "/");
		attributes.put("fullName", "/"+root.getName());
		attributes.put("isload", "false");
		rootTree.setAttributes(attributes);
		trees.add(rootTree);
		return trees;
	}
	
	@ResponseBody
	@RequestMapping("/orgchild")
	public List<TreeInfo> orgchild(Integer id)
	{
		List<OrgInfo> child = orgmodel.getChildOrg(id);
		List<TreeInfo> trees = new ArrayList<TreeInfo>();
		for(OrgInfo oi:child)
		{
			TreeInfo rootTree = new TreeInfo();
			rootTree.setId(oi.getId().toString());
			rootTree.setText(oi.getName());
			rootTree.setState("closed");
			Map attributes = new HashMap();
			attributes.put("parentId", oi.getParentId());
			attributes.put("priority", oi.getPriority());
			attributes.put("path", oi.getPath());
			attributes.put("fullName", oi.getFullName());
			attributes.put("isload", "false");
			rootTree.setAttributes(attributes);
			trees.add(rootTree);
		}
		return trees;
	}
	
	@ResponseBody
	@RequestMapping("/orgsubsave")
	public boolean orgsubsave(OrgInfo oi)
	{
		Integer parentId = oi.getId();
		oi.setId(null);
		oi.setParentId(parentId);
		orgmodel.saveOrg(oi);
		return true;
	}
	
	@RequestMapping("/function")
	public String function()
	{
		return "/orgmodel/function";
	}
	
	
	@ResponseBody
	@RequestMapping("/funcroot")
	public List<TreeInfo> funcroot()
	{
		FunctionInfo rootFi = orgmodel.getRootFunc();
		if(rootFi == null)
		{
			rootFi = orgmodel.initFunc();
		}
		List<TreeInfo> trees = new ArrayList<TreeInfo>();
		TreeInfo rootTree = new TreeInfo();
		rootTree.setId(rootFi.getId().toString());
		rootTree.setText(rootFi.getName());
		rootTree.setState("closed");
		Map attributes = new HashMap();
		attributes.put("parentId",rootFi.getParentId());
		attributes.put("path", "/");
		attributes.put("isload", "false");
		rootTree.setAttributes(attributes);
		
		trees.add(rootTree);
		return trees;
	}
	
	@ResponseBody
	@RequestMapping("/funcsubsave")
	public boolean funcsubsave(FunctionInfo fi)
	{
		Integer parentId = fi.getId();
		if(parentId == null)
		{
			return false;
		}
		fi.setId(null);
		fi.setParentId(parentId);
		orgmodel.saveFunc(fi);
		return true;
	}
	
	@ResponseBody
	@RequestMapping("/funcsave")
	public boolean funcsave(FunctionInfo fi)
	{
		Integer id = fi.getId();
		if(id == null)
			orgmodel.saveFunc(fi);
		else
			orgmodel.updateFunc(fi);
		return true;
	}
	
	@ResponseBody
	@RequestMapping("/funcdel")
	public boolean funcdel(FunctionInfo fi)
	{
		if("system".equals(fi.getType()))
		{
			return false;
		}
		orgmodel.deleteFunc(fi.getId());
		return true;
	}
	
	@ResponseBody
	@RequestMapping("/gettree")
	public List<TreeInfo> gettree()
	{
		FunctionInfo rootFi = orgmodel.getRootFunc();
		if(rootFi == null)
		{
			rootFi = orgmodel.initFunc();
		}
		List<FunctionInfo> funs = orgmodel.getChildFunc(rootFi.getId());
		List<TreeInfo> rtn = new ArrayList<TreeInfo>();
		for(FunctionInfo fi:funs)
		{
			TreeInfo ti = new TreeInfo();
			ti.setId(fi.getId().toString());
			ti.setText(fi.getName());
			ti.setIconCls(fi.getIcon()!=null?fi.getIcon():"pic_1");
			ti.setUrl(fi.getUrl());
			ti.setState("closed");
			ti.addProp("isload","false");
			if(fi.getPriority() != null)
				ti.addProp("priority",fi.getPriority().toString());
			if(fi.getParentId() != null)
				ti.addProp("parentId",fi.getParentId().toString());
			if(fi.getType() != null)
				ti.addProp("type",fi.getType().toString());
			rtn.add(ti);
		}
		return rtn;
	}
	
	@ResponseBody
	@RequestMapping("/gettreebyid")
	public List<TreeInfo> gettreebyid(Integer id)
	{
		List<FunctionInfo> funs = orgmodel.getChildFunc(id);
		List<TreeInfo> rtn = new ArrayList<TreeInfo>();
		for(FunctionInfo fi:funs)
		{
			TreeInfo ti = new TreeInfo();
			ti.setId(fi.getId().toString());
			ti.setText(fi.getName());
			ti.setIconCls(fi.getIcon()!=null?fi.getIcon():"pic_1");
			ti.setUrl(fi.getUrl());
			List<FunctionInfo> chd = orgmodel.getChildFunc(fi.getId());
			if(chd != null && chd.size()>0)
				ti.setState("closed");
			else
				ti.setState("open");
			ti.addProp("isload","false");
			if(fi.getPriority() != null)
				ti.addProp("priority",fi.getPriority().toString());
			if(fi.getParentId() != null)
				ti.addProp("parentId",fi.getParentId().toString());
			if(fi.getType() != null)
				ti.addProp("type",fi.getType().toString());
			rtn.add(ti);
		}
		return rtn;
	}
	
	public FileInfo uploadFile(HttpServletRequest req)
	{
		if(!(req instanceof MultipartHttpServletRequest))
			return null;
		MultipartHttpServletRequest mreq = (MultipartHttpServletRequest)req;
		MultipartFile multipartFile = mreq.getFile("upfile");
		try
		{
			InputStream is = multipartFile.getInputStream();
			byte[] bytes = FileCopyUtils.copyToByteArray(is);
			FileInfo fi = new FileInfo();
			fi.setName(multipartFile.getName());
			fi.setContextType(multipartFile.getContentType());
			fi.setLength(new Long(multipartFile.getBytes().length));
			fi.setContent(bytes);
			orgmodel.saveFile(fi);
			return fi;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
