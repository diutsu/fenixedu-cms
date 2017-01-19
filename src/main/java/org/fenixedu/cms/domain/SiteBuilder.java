package org.fenixedu.cms.domain;

import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.bennu.core.domain.groups.PersistentGroup;
import org.fenixedu.bennu.core.groups.Group;
import org.fenixedu.commons.i18n.LocalizedString;

public class SiteBuilder extends SiteBuilder_Base implements Sluggable {
    
    public SiteBuilder(String slug) {
        this();
        this.setSlug(SlugUtils.makeSlug(this,slug));
    }
    
    public SiteBuilder(){
        super();
        Bennu.getInstance().getSiteBuildersSet().add(this);
        this.setCanViewGroup(Group.anyone());
    }
    
    public Site create(LocalizedString name, LocalizedString description, String slug){
        Site site = new Site(name,description);
        site.setSlug(slug);
        site.setTheme(this.getTheme());
        
        //TODO
        //site.setCanViewGroup(this.getCanViewGroup());
        site.setPublished(this.getPublished());
        site.setEmbedded(this.getEmbedded());

        for (RoleTemplate roleTemplate : this.getRoleTemplateSet()) {
            new Role(roleTemplate,site);
        }
    
        site.setFolder(getFolder());
        getCategoriesSet().stream().forEach(category -> site.categoryForSlug(category.getSlug()));
    
        return site;
    }
    
    public boolean isSystemBuilder(){return false;}
    
    public Group getCanViewGroup(){
        return getViewGroup().toGroup();
    }
    
    public void setCanViewGroup(Group group){
        setViewGroup(group.toPersistentGroup());
    }
    
    public final static SiteBuilder forSlug(String builderSlug) {
        return Bennu.getInstance().getSiteBuildersSet().stream()
                .filter(sb->sb.getSlug().equals(builderSlug))
                .findAny().orElseGet(()->null);
    }
    
    @Override
    public final boolean isValidSlug(String slug) {
        return Bennu.getInstance().getSiteBuildersSet().stream().noneMatch(sb->sb!=this && sb.getCategoriesSet().equals(slug));
    }
    
    public void delete() {
        this.setBennu(null);
        deleteDomainObject();
    }
}

