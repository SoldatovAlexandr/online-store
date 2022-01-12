package edu.asoldatov.online.store.repository.specification;

import edu.asoldatov.online.store.mogel.User;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Join(path= "roles", alias = "r")
@And({
        @Spec(path = "login", spec = Like.class),
        @Spec(path = "r.name", params = "role", spec = Like.class)
})
public interface UserSpecification extends Specification<User> {
}
