package mate.academy.rickandmorty.dto.external;

import java.util.List;
import lombok.Data;

@Data
public class RickAndMortyResponseDto {
    private Info info;
    private List<CharacterResponseDto> results;

    @Data
    public static class Info {
        private String next; // Посилання на наступну сторінку
    }
}
