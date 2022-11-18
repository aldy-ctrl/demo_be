package com.example.demo_be.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.demo_be.vo.RoleVo;

import org.springframework.stereotype.Repository;
import javax.persistence.Query;

@Repository
public class RoleDAOImpl implements RoleDAO {

   @PersistenceContext
   private EntityManager entityManager;

   @Override
   public List<RoleVo> getActiveRoles(String username) {
      List<RoleVo> roles = new ArrayList<>();
      StringBuilder sqlQuery = new StringBuilder();
      sqlQuery.append(" select urd.role_cd, r.role_name, r.role_desc, r.super_admin_flag ");
      sqlQuery.append(" from tb_m_user_role ur ");
      sqlQuery.append(" 	inner join tb_m_user_role_d urd ");
      sqlQuery.append(" 		on urd.username = ur.username ");
      sqlQuery.append(" 		and urd.valid_from_dt = ur.valid_from_dt ");
      sqlQuery.append(" 	inner join tb_m_role r ");
      sqlQuery.append(" 		on r.role_cd = urd.role_cd ");
      sqlQuery.append(" where 1 = 1 ");
      sqlQuery.append(" 	and ur.deleted_flag = false ");
      sqlQuery.append(" 	and r.deleted_flag = false ");
      sqlQuery.append(" 	and current_date between ur.valid_from_dt and ur.valid_to_dt ");
      sqlQuery.append(" 	and ur.username = :username ");

      Query query = entityManager.createNativeQuery(sqlQuery.toString());
      query.setParameter("username", username);

      @SuppressWarnings("unchecked")
      List<Object[]> rows = query.getResultList();

      for (Object[] row : rows) {
         RoleVo roleVO = new RoleVo();

         roleVO.setRoleCd((String) row[0]);
         roleVO.setRoleName((String) row[1]);
         roleVO.setAdminFlag((Boolean) row[3]);

         roles.add(roleVO);
      }

      return roles;
   }

}
