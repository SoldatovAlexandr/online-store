package edu.asoldatov.online.store.repository.specification;

import edu.asoldatov.online.store.mogel.Product;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThan;
import net.kaczmarzyk.spring.data.jpa.domain.LessThan;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Join;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@Join(path = "genre", alias = "g")
@And({
        @Spec(path = "name", spec = Like.class),
        @Spec(path = "description", spec = Like.class),
        @Spec(path = "author", spec = Like.class),
        @Spec(path = "g.id", params = "genreId", spec = Equal.class),
        @Spec(path = "yearOfPublication", spec = Equal.class),
        @Spec(path = "ageLimit", spec = GreaterThan.class),
        @Spec(path = "amount", spec = LessThan.class),
        @Spec(path = "isActive", spec = Equal.class)
})
public interface ProductSpecification extends Specification<Product> {
}
