package mate.academy.rickandmorty.repository.character.spec;

import java.util.Arrays;
import mate.academy.rickandmorty.model.Character; //
import mate.academy.rickandmorty.repository.character.SpecificationProvider; //
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class NameSpecificationProvider implements SpecificationProvider<Character> {
    public static final String KEY = "name";

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public Specification<Character> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> root.get("name")
                .in(Arrays.stream(params).toArray());
    }
}
