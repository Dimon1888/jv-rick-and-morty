package mate.academy.rickandmorty.repository.character.spec;

import java.util.Arrays;
import mate.academy.rickandmorty.model.Character; //[cite: 12]
import mate.academy.rickandmorty.repository.character.SpecificationProvider; //[cite: 16]
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class StatusSpecificationProvider implements SpecificationProvider<Character> {
    public static final String KEY = "status";

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public Specification<Character> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> root.get("status")
                .in(Arrays.stream(params).toArray());
    }
}
