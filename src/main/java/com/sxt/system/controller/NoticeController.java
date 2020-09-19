package com.sxt.system.controller;


import com.sxt.system.common.ActiveUser;
import com.sxt.system.common.ResultObj;
import com.sxt.system.domain.Notice;
import com.sxt.system.domain.User;
import com.sxt.system.service.NoticeService;
import com.sxt.system.vo.NoticeVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * create by zkc 2020.08.26
 */
@RestController
@RequestMapping("notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 查询
     * @param noticeVo
     * @return
     */
    @RequestMapping("loadAllNotice")
    public Object loadAllNotice(NoticeVo noticeVo){
       return this.noticeService.queryAllNotice(noticeVo);
    }

    /**
     * 添加公告
     * 只需传两个参数就行，而传过来的title跟contend就包含在notice里面，只需传notice就可
     *
     * @param notice
     * @return
     */
    @RequestMapping("addNotice")
    public ResultObj addNotice(Notice notice){
        try{
            Subject subject = SecurityUtils.getSubject();
            ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
            User user = activeUser.getUser();
            notice.setOpername(user.getLoginname());
            notice.setCreatetime(new Date());
            this.noticeService.save(notice);
            return ResultObj.ADD_SUCCESS;
        }catch(Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改公告
     * @param notice
     * @return
     */
    @RequestMapping("updateNotice")
    public ResultObj updateNotice(Notice notice){
        try{
            this.noticeService.updateById(notice);
            return  ResultObj.UPDATE_SUCCESS;
        }catch(Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("deleteNotice")
    public ResultObj deleteNotice(Integer id){
        try{
            this.noticeService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        }catch(Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }


    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping("batchDeleteNotice")
    public ResultObj batchDeleteNotice(Integer[] ids){
        try{
            if (null!=ids&&ids.length>0){
                List<Integer> idsList = new ArrayList<>();
                for(Integer id:ids){
                    idsList.add(id);
                }
                this.noticeService.removeByIds(idsList);
                return  ResultObj.DELETE_SUCCESS;
            }else{
                return new ResultObj(-1,"传入ID不能为空!");
            }

        }catch (Exception e){
            e.printStackTrace();
            return  ResultObj.DELETE_ERROR;
        }
    }
}
