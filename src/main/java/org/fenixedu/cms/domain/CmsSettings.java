/**
 * Copyright © 2014 Instituto Superior Técnico
 *
 * This file is part of FenixEdu CMS.
 *
 * FenixEdu CMS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FenixEdu CMS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FenixEdu CMS.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.fenixedu.cms.domain;

import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.bennu.core.groups.Group;
import org.fenixedu.bennu.core.security.Authenticate;
import org.fenixedu.cms.exceptions.CmsDomainException;

public class CmsSettings extends CmsSettings_Base {

    private CmsSettings() {
        setFoldersManagers(Group.nobody().toPersistentGroup());
        setRolesManagers(Group.nobody().toPersistentGroup());
        setThemesManagers(Group.nobody().toPersistentGroup());
        setSettingsManagers(Group.nobody().toPersistentGroup());
        setGlobalManagers(Group.nobody().toPersistentGroup());
    }

    public boolean canManageFolders() {
        return getGlobalManagers().isMember(Authenticate.getUser()) ||
            getFoldersManagers().isMember(Authenticate.getUser());
    }

    public boolean canManageRoles() {
        return getGlobalManagers().isMember(Authenticate.getUser()) ||
            getRolesManagers().isMember(Authenticate.getUser());
    }

    public boolean canManageSettings() {
        return getGlobalManagers().isMember(Authenticate.getUser()) ||
            getSettingsManagers().isMember(Authenticate.getUser());
    }

    public boolean canManageThemes() {
        return getGlobalManagers().isMember(Authenticate.getUser()) |
            getThemesManagers().isMember(Authenticate.getUser());
    }

    public boolean canManageCms() {
        return getGlobalManagers().isMember(Authenticate.getUser());
    }

    public void ensureCanManageFolders() {
        if (!canManageFolders()) {
            throw CmsDomainException.forbiden();
        }
    }

    public void ensureCanManageRoles() {
        if (!canManageRoles()) {
            throw CmsDomainException.forbiden();
        }
    }

    public void ensureCanManageSettings() {
        if (!canManageSettings()) {
            throw CmsDomainException.forbiden();
        }
    }

    public void ensureCanManageThemes() {
        if (!canManageThemes()) {
            throw CmsDomainException.forbiden();
        }
    }

    public void ensureCanManageCms() {
        if (!canManageCms()) {
            throw CmsDomainException.forbiden();
        }
    }

    public static CmsSettings getInstance() {
        if(Bennu.getInstance().getCmsSettings()==null){
            Bennu.getInstance().setCmsSettings(new CmsSettings());
        }
        
        return Bennu.getInstance().getCmsSettings();
    }

}
