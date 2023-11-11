package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.Supplier;
import com.ruoyi.system.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/system/supplier")
public class SupplierController extends BaseController {

    @Autowired
    private ISupplierService postService;

    /**
     * 获取岗位列表
     */
    @PreAuthorize("@ss.hasPermi('system:supplier:list')")
    @GetMapping("/list")
    public TableDataInfo list(Supplier post)
    {
        startPage();
        List<Supplier> list = postService.selectPostList(post);
        return getDataTable(list);
    }

    @Log(title = "岗位管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:supplier:list')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, Supplier post)
    {
        List<Supplier> list = postService.selectPostList(post);
        ExcelUtil<Supplier> util = new ExcelUtil<Supplier>(Supplier.class);
        util.exportExcel(response, list, "岗位数据");
    }

    /**
     * 根据岗位编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:supplier:list')")
    @GetMapping(value = "/{postId}")
    public AjaxResult getInfo(@PathVariable Long postId)
    {
        return success(postService.selectPostById(postId));
    }

    /**
     * 新增岗位
     */
    @PreAuthorize("@ss.hasPermi('system:supplier:list')")
    @Log(title = "岗位管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody Supplier post)
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
    @PreAuthorize("@ss.hasPermi('system:supplier:list')")
    @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody Supplier post)
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
    @PreAuthorize("@ss.hasPermi('system:supplier:list')")
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
        List<Supplier> posts = postService.selectPostAll();
        return success(posts);
    }
}
