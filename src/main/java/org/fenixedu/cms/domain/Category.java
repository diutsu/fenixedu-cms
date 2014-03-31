package org.fenixedu.cms.domain;

import org.fenixedu.bennu.core.security.Authenticate;
import org.fenixedu.commons.i18n.LocalizedString;
import org.joda.time.DateTime;

import pt.ist.fenixframework.Atomic;

public class Category extends Category_Base {
    
    public Category() {
        super();
        if(Authenticate.getUser() == null){
            throw new RuntimeException("Needs Login");
        }
        this.setCreatedBy(Authenticate.getUser());
        this.setCreationDate(new DateTime());
    }
    
    @Override
    public void setName(LocalizedString name) {
        LocalizedString prevName = getName();
        super.setName(name);

        if (prevName == null) {
            setSlug(Site.slugify(name.getContent()));
        }
    }
    
    public String getAddress(){
        return this.getSite().getViewCategoryPage().getAddress() + "?c=" + this.getSlug();
    }
   
    @Atomic
    public void delete() {
        for(Component c : this.getComponentsSet()){
            c.delete();
        }
        this.setCreatedBy(null);
        this.setSite(null);
        for (Post post : this.getPostsSet()) {
            post.setCategory(null);
        }
        this.deleteDomainObject();
    }
}