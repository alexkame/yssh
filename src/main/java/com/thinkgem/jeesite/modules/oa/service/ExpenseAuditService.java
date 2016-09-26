/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.act.service.ActTaskService;
import com.thinkgem.jeesite.modules.act.utils.ActUtils;
import com.thinkgem.jeesite.modules.oa.dao.ExpenseAuditDao;
import com.thinkgem.jeesite.modules.oa.entity.ExpenseAudit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 审批Service
 * @author thinkgem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class ExpenseAuditService extends CrudService<ExpenseAuditDao, ExpenseAudit> {

	@Autowired
	private ActTaskService actTaskService;
	
	/*public TestAudit getByProcInsId(String procInsId) {
		return dao.getByProcInsId(procInsId);
	}*/
	
	public Page<ExpenseAudit> findPage(Page<ExpenseAudit> page, ExpenseAudit expenseAudit) {
		expenseAudit.setPage(page);
		page.setList(dao.findList(expenseAudit));
		return page;
	}
	
	/**
	 * 审核新增或编辑
	 * @param testAudit
	 */
	@Transactional(readOnly = false)
	public void save(ExpenseAudit testAudit) {
		
		// 申请发起
		if (StringUtils.isBlank(testAudit.getId())){
			testAudit.preInsert();
			dao.insert(testAudit);
			
			// 启动流程
			actTaskService.startProcess(ActUtils.PD_Expense_AUDIT[0], ActUtils.PD_Expense_AUDIT[1], testAudit.getId(), testAudit.getTitle());
			
		}
		
		// 重新编辑申请		
		else{
			testAudit.preUpdate();
			dao.updateExpenseAudit(testAudit);

			testAudit.getAct().setComment(("yes".equals(testAudit.getAct().getFlag())?"[重申] ":"[销毁] ")+testAudit.getAct().getComment());
			
			// 完成流程任务
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("pass", "yes".equals(testAudit.getAct().getFlag())? "1" : "0");
			actTaskService.complete(testAudit.getAct().getTaskId(), testAudit.getAct().getProcInsId(), testAudit.getAct().getComment(), testAudit.getTitle(), vars);
		}
	}

	/**
	 * 审核审批保存
	 * @param testAudit
	 */
	@Transactional(readOnly = false)
	public void auditSave(ExpenseAudit testAudit) {
		
		// 设置意见
		testAudit.getAct().setComment(("yes".equals(testAudit.getAct().getFlag())?"[同意] ":"[驳回] ")+testAudit.getAct().getComment());
		
		testAudit.preUpdate();
		
		// 对不同环节的业务逻辑进行操作
		String taskDefKey = testAudit.getAct().getTaskDefKey();

		// 审核环节
		if ("audit".equals(taskDefKey)){
			
		}
		else if ("step_2".equals(taskDefKey)){
			testAudit.setStepOne(testAudit.getAct().getComment());
			dao.updateExpenseAudit(testAudit);
		}
		else if ("step_3".equals(taskDefKey)){
			testAudit.setStepTwo(testAudit.getAct().getComment());
			dao.updateExpenseAudit(testAudit);
		}
		else if ("step_4".equals(taskDefKey)){
			testAudit.setStepThree(testAudit.getAct().getComment());
			dao.updateExpenseAudit(testAudit);
		}

		// 未知环节，直接返回
		else{
			return;
		}
		
		// 提交流程任务
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", "yes".equals(testAudit.getAct().getFlag())? "1" : "0");
		actTaskService.complete(testAudit.getAct().getTaskId(), testAudit.getAct().getProcInsId(), testAudit.getAct().getComment(), vars);

//		vars.put("var_test", "yes_no_test2");
//		actTaskService.getProcessEngine().getTaskService().addComment(testAudit.getAct().getTaskId(), testAudit.getAct().getProcInsId(), testAudit.getAct().getComment());
//		actTaskService.jumpTask(testAudit.getAct().getProcInsId(), testAudit.getAct().getTaskId(), "audit2", vars);
	}
	
}
