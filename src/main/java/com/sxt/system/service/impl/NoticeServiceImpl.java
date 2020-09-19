package com.sxt.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sxt.system.common.DataGridView;
import com.sxt.system.vo.NoticeVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sxt.system.mapper.NoticeMapper;
import com.sxt.system.domain.Notice;
import com.sxt.system.service.NoticeService;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService{

private  Log log = LogFactory.getLog(NoticeService.class);
    @Autowired
    private NoticeMapper noticeMapper;
    /**
     * 查询所有公告
     * @param noticeVo
     * @return
     */
    @Override
    public DataGridView queryAllNotice(NoticeVo noticeVo) {
        /**
         * 分页操作
         * Page类：
         * total:总条数，current：当前页数 size：当前每页显示数，即每页几条
         * records："records":[{"id":"","opername":"","startTime":"","endTime":""},{"id":"2","name":"zzz","age":"12"}]
         * Vo类：
         * page:当前页数  limit：每页几条
         */
        IPage<Notice> page = new Page<>(noticeVo.getPage(),noticeVo.getLimit());
        QueryWrapper<Notice> qw = new QueryWrapper<>();
        qw.like(StringUtils.isNotBlank(noticeVo.getOpername()),"operName",noticeVo.getOpername());
        qw.like(StringUtils.isNotBlank(noticeVo.getTitle()),"title",noticeVo.getTitle());
        qw.ge(noticeVo.getStartTime()!=null,"createtime",noticeVo.getStartTime());
        qw.le(noticeVo.getEndTime()!=null,"createtime",noticeVo.getEndTime());
        qw.orderByDesc("createtime");
        this.noticeMapper.selectPage(page,qw);
        return new DataGridView(page.getTotal(),page.getRecords());
    }
}
