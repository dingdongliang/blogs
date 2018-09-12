package net.htjs.blog.service.impl;

import net.htjs.blog.constant.SystemConstant;
import net.htjs.blog.dao.SysRoleMapper;
import net.htjs.blog.dao.SysRolePmsnMapper;
import net.htjs.blog.entity.BaseDomain;
import net.htjs.blog.entity.SysRole;
import net.htjs.blog.entity.SysRolePmsn;
import net.htjs.blog.service.SysRoleService;
import net.htjs.blog.util.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import net.htjs.blog.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * sharing/net.htjs.blog.service.impl
 *
 * @Description : 角色操作类，包含角色的CURD、角色的权限分配等
 * @Author : dingdongliang
 * @Date : 2018/4/18 15:20
 */
@Transactional(rollbackFor = Exception.class)
@Service
@Slf4j
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRolePmsnMapper sysRolePmsnMapper;

    @Override
    public List<String> selectDefault() {
        return sysRoleMapper.selectDefault();
    }

    /**
     * 给角色赋予权限，一对多
     *
     * @param roleId  角色ID
     * @param pmsnIds 权限ID数组
     * @return void
     * @author dingdongliang
     * @date 2018/4/24 8:33
     */
    @Override
    public void putPmsnToRole(String roleId, String[] pmsnIds) {
        String userId = ShiroUtil.getUserId();
        sysRolePmsnMapper.insertMany(roleId, Arrays.asList(pmsnIds), userId);
    }

    /**
     * 更新用户的角色
     *
     * @param roleId  角色ID
     * @param pmsnIds 权限ID数组
     * @return void
     * @author dingdongliang
     * @date 2018/4/24 9:31
     */
    @Override
    public void updatePmsnToRole(String roleId, String[] pmsnIds) {

        //首先获取该角色现有的权限集合，挑出其中的pmsnId为A集合
        List<SysRolePmsn> sysRolePmsnList = sysRolePmsnMapper.selectByRoleId(roleId);
        List<String> aList = new ArrayList<>();
        for (SysRolePmsn sysRolePmsn : sysRolePmsnList) {
            aList.add(sysRolePmsn.getPmsnId());
        }

        //新权限数组pmsnIds为B集合，准备获取A集合和B集合的交集
        List<String> bList = new ArrayList<>();
        Collections.addAll(bList, pmsnIds);

        //复制A集合到C集合，因为在取交集的过程中，会更改集合本身，此时C集合==A集合
        List<String> cList = new ArrayList<>(aList);

        //此时C集合 == （A集合与B集合的交集）为角色依然拥有的权限
        cList.retainAll(bList);

        //更新A集合-C集合的差集为过期的权限，更新状态为"不可用",此时A集合已经全部为过期权限了
        aList.removeAll(cList);

        //根据角色ID和权限ID查询，更新状态为过期
        for (String pmsnId : aList) {
            SysRolePmsn sysRolePmsn = sysRolePmsnMapper.selectByDoubleId(roleId, pmsnId);
            sysRolePmsn.setStatus("I");
            BaseDomain.updateLog(sysRolePmsn);
            sysRolePmsnMapper.updateByPrimaryKey(sysRolePmsn);
        }

        //取B集合-C集合的差集为新增的角色，批量添加
        bList.removeAll(cList);

        //批量插入的时候，如果集合为空，会报错，所以要判断是否为空
        if (!bList.isEmpty()) {
            String userId = ShiroUtil.getUserId();
            sysRolePmsnMapper.insertMany(roleId, bList, userId);
        }
    }

    /**
     * 新增角色，同时分配权限
     *
     * @param sysRole 角色对象
     * @param pmsnIds 权限集合
     * @return void
     * @author dingdongliang
     * @date 2018/4/24 11:13
     */
    @Override
    public void insert(SysRole sysRole, String[] pmsnIds) {
        sysRoleMapper.insert(sysRole);
        putPmsnToRole(sysRole.getRoleId(), pmsnIds);
    }

    /**
     * 修改角色，同时修改角色-权限映射
     *
     * @param sysRole 角色对象
     * @param pmsnIds 权限集合
     * @return void
     * @author dingdongliang
     * @date 2018/4/24 11:13
     */
    @Override
    public void update(SysRole sysRole, String[] pmsnIds) {
        sysRoleMapper.updateByPrimaryKey(sysRole);
        updatePmsnToRole(sysRole.getRoleId(), pmsnIds);
    }

    @Override
    public void delete(String roleId) {
        sysRolePmsnMapper.deleteByRoleId(roleId);
        sysRoleMapper.deleteByPrimaryKey(roleId);
    }

    /**
     * 保存选取的权限与角色映射
     *
     * @param roleId
     * @param pmsnIds
     * @return boolean
     * @author dingdongliang
     * @date 2018/9/12 19:58
     */
    @Override
    public boolean savePermission(String roleId, String pmsnIds) {

        //盛放没有修改以前的角色权限对应记录，用于在修改后删除多余的记录
        Map<String, SysRolePmsn> map = new HashMap<>(8);

        //获取某角色ID对应的所有权限
        List<SysRolePmsn> rolePermissionList = sysRolePmsnMapper.selectByRoleId(roleId);
        //循环处理这些权限与角色的对应记录，逐一放入map中，然后设置该记录为过期，用于标记删除
        for (SysRolePmsn rolePmsn : rolePermissionList) {
            String permissionId = rolePmsn.getPmsnId();
            //对于角色权限对应记录来说，权限ID是互斥的，所以当成key处理
            map.put(permissionId.toString(), rolePmsn);
            //设置所有记录过期
            updRolePermission(rolePmsn, SystemConstant.INVALID);
        }
        //开始处理修改后提交的对应数据，checkedIds为权限集合
        if (null != pmsnIds && !"".equals(pmsnIds)) {
            String[] ids = pmsnIds.split(",");
            for (String id : ids) {
                if (StringUtils.isBlank(id)) {
                    continue;
                }
                //然后看这些权限ID是否在map中
                SysRolePmsn rolePmsn = map.get(id);
                if (rolePmsn != null) {
                    //如果在map中，说明在数据库中有记录，把状态改成正常
                    updRolePermission(rolePmsn, SystemConstant.EFFECTIVE);
                } else {
                    //如果不在msp中，说明该对应记录在数据库中没有，要新增
                    rolePmsn = new SysRolePmsn();
                    BaseDomain.createLog(rolePmsn);
                    rolePmsn.setStatus(SystemConstant.EFFECTIVE);
                    rolePmsn.setPmsnId(id);
                    rolePmsn.setRoleId(roleId);
                    rolePmsn.setRpId(StringUtil.getUUID());
                    sysRolePmsnMapper.insert(rolePmsn);
                }
                //同时删除已经处理过的map值
                map.remove(id);
            }
        }
        //当所有值都处理完毕以后，剩下的map值就是：原来有对应关系，修改后没有对应关系，删除之
        for (Map.Entry<String, SysRolePmsn> entry : map.entrySet()) {
            baseMapper.deleteByPrimaryKey(entry.getValue().getRpId());
        }

        return true;
    }

    private void updRolePermission(SysRolePmsn rolePermission, String status) {
        BaseDomain.updateLog(rolePermission);
        rolePermission.setStatus(status);
        sysRolePmsnMapper.updateByPrimaryKeySelective(rolePermission);
    }
}
