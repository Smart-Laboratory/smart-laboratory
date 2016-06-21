package com.smart.dao;

import com.smart.model.user.Role;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

/**
 * Lookup Data Access Object (GenericDao) interface.  This is used to lookup values in
 * the database (i.e. for drop-downs).
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface LookupDao {
    //~ Methods ================================================================

    /**
     * Returns all Roles ordered by name
     * @return populated list of roles
     */
	@Transactional
    List<Role> getRoles();
}
