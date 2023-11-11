package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.Order;
import com.ruoyi.system.service.IOrderService;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/order")
public class OrderController extends BaseController {

    @Autowired
    private IOrderService postService;

    /**
     * 获取岗位列表
     */
    @PreAuthorize("@ss.hasPermi('monitor:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(Order post)
    {
        startPage();
        List<Order> list = postService.selectPostList(post);
        return getDataTable(list);
    }

    @Log(title = "岗位管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('monitor:order:list')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, Order post)
    {
        List<Order> list = postService.selectPostList(post);
        ExcelUtil<Order> util = new ExcelUtil<Order>(Order.class);
        util.exportExcel(response, list, "岗位数据");
    }

    /**
     * 根据岗位编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitor:order:list')")
    @GetMapping(value = "/{postId}")
    public AjaxResult getInfo(@PathVariable Long postId)
    {
        return success(postService.selectPostById(postId));
    }

    /**
     * 新增岗位
     */
    @PreAuthorize("@ss.hasPermi('monitor:order:list')")
    @Log(title = "岗位管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody Order post)
    {
        if (!postService.checkPostNameUnique(post))
        {
            return error("新增岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        }
        else if (!postService.checkPostCodeUnique(post))
        {
            return error("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setCreateBy(getUsername());
        return toAjax(postService.insertPost(post));
    }

    /**
     * 修改岗位
     */
    @PreAuthorize("@ss.hasPermi('monitor:order:list')")
    @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody Order post)
    {
        if (!postService.checkPostNameUnique(post))
        {
            return error("修改岗位'" + post.getPostName() + "'失败，岗位名称已存在");
        }
        else if (!postService.checkPostCodeUnique(post))
        {
            return error("修改岗位'" + post.getPostName() + "'失败，岗位编码已存在");
        }
        post.setUpdateBy(getUsername());
        return toAjax(postService.updatePost(post));
    }

    /**
     * 删除岗位
     */
    @PreAuthorize("@ss.hasPermi('monitor:order:list')")
    @Log(title = "岗位管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{postIds}")
    public AjaxResult remove(@PathVariable Long[] postIds)
    {
        return toAjax(postService.deletePostByIds(postIds));
    }

    /**
     * 获取岗位选择框列表
     */
    @GetMapping("/optionselect")
    public AjaxResult optionselect()
    {
        List<Order> posts = postService.selectPostAll();
        return success(posts);
    }
}
