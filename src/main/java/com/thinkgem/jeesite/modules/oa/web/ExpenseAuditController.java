/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.oa.entity.ExpenseAudit;
import com.thinkgem.jeesite.modules.oa.entity.TestAudit;
import com.thinkgem.jeesite.modules.oa.service.ExpenseAuditService;
import com.thinkgem.jeesite.modules.oa.service.TestBaoXiaoAuditService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 审批Controller
 * @author hailou
 * @version 2014-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/oa/expenseAudit")
public class ExpenseAuditController extends BaseController {

	@Autowired
	private ExpenseAuditService expenseAuditService;
	
	@ModelAttribute
	public ExpenseAudit get(@RequestParam(required=false) String id){//,
//			@RequestParam(value="act.procInsId", required=false) String procInsId) {
		ExpenseAudit testAudit = null;
		if (StringUtils.isNotBlank(id)){
			testAudit = expenseAuditService.get(id);
//		}else if (StringUtils.isNotBlank(procInsId)){
//			testAudit = testAuditService.getByProcInsId(procInsId);
		}
		if (testAudit == null){
			testAudit = new ExpenseAudit();
		}
		return testAudit;
	}
	
	@RequiresPermissions("oa:testAudit:view")
	@RequestMapping(value = {"list", ""})
	public String list(ExpenseAudit expenseAudit, HttpServletRequest request, HttpServletResponse response, Model model) {
		User user = UserUtils.getUser();
		if (!user.isAdmin()){
			expenseAudit.setCreateBy(user);
		}
        Page<ExpenseAudit> page = expenseAuditService.findPage(new Page<ExpenseAudit>(request, response), expenseAudit);
        model.addAttribute("page", page);
		return "modules/oa/expenseAuditList";
	}
	
	/**
	 * 申请单填写
	 * @param testAudit
	 * @param model
	 * @return
	 */

	@RequestMapping(value = "form")
	public String form(ExpenseAudit testAudit, Model model) {
		
		String view = "expenseAuditForm";
		
		// 查看审批申请单
		if (StringUtils.isNotBlank(testAudit.getId())){//.getAct().getProcInsId())){

			// 环节编号
			String taskDefKey = testAudit.getAct().getTaskDefKey();
			
			// 查看工单
			if(testAudit.getAct().isFinishTask()){
				view = "testAuditView";
			}
			// 修改环节
			else if ("modify".equals(taskDefKey)){
				view = "testBaoXiaoAuditForm";
			}
			// 审核环节
			else if ("step_2".equals(taskDefKey)){
				view = "expenseAudit";
//				String formKey = "/oa/testAudit";
//				return "redirect:" + ActUtils.getFormUrl(formKey, testAudit.getAct());
			}
			// 审核环节2
			else if ("step_3".equals(taskDefKey)){
				view = "expenseAudit";
			}
			// 审核环节3
			else if ("step_4".equals(taskDefKey)){
				view = "expenseAudit";
			}

			// 兑现环节
			else if ("apply_end".equals(taskDefKey)){
				view = "testAuditAudit";
			}
		}

		model.addAttribute("testAudit", testAudit);
		return "modules/oa/" + view;
	}
	
	/**
	 * 申请单保存/修改
	 * @param expenseAudit
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("oa:testAudit:edit")
	@RequestMapping(value = "save")
	public String save(ExpenseAudit expenseAudit, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, expenseAudit)){
			return form(expenseAudit, model);
		}
		expenseAudit.setUserId(UserUtils.getUser().getId());
		expenseAuditService.save(expenseAudit);
		addMessage(redirectAttributes, "提交审批'" + expenseAudit.getCurrentUser().getName() + "'成功");
		return "redirect:" + adminPath + "/act/task/todo/";
	}

	/**
	 * 工单执行（完成任务）
	 * @param expenseAudit
	 * @param model
	 * @return
	 */
	@RequiresPermissions("oa:testAudit:edit")
	@RequestMapping(value = "saveAudit")
	public String saveAudit(ExpenseAudit expenseAudit, Model model) {
		if (StringUtils.isBlank(expenseAudit.getAct().getFlag())
				|| StringUtils.isBlank(expenseAudit.getAct().getComment())){
			addMessage(model, "请填写审核意见。");
			return form(expenseAudit, model);
		}
		expenseAuditService.auditSave(expenseAudit);
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	/**
	 * 删除工单
	 * @  id
	 * @param redirectAttributes
	 * @return
	 */
	/*@RequiresPermissions("oa:testAudit:edit")
	@RequestMapping(value = "delete")
	public String delete(TestAudit testAudit, RedirectAttributes redirectAttributes) {
		testAuditService.delete(testAudit);
		addMessage(redirectAttributes, "删除审批成功");
		return "redirect:" + adminPath + "/oa/testAudit/?repage";
	}*/

}
