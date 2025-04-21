package acc.library.reservation.mapper;

import org.springframework.stereotype.Component;
import acc.library.reservation.dto.BookInfoDTO;
import acc.library.reservation.entity.Books;

@Component
public class BookMapper {
    public BookInfoDTO mapBookEntityToDTO(Books book) {
        BookInfoDTO bookInfoDTO = new BookInfoDTO();
        bookInfoDTO.setAuthor(book.getAuthor());
        bookInfoDTO.setIsbn(book.getIsbn());
        bookInfoDTO.setTitle(book.getTitle());
        return bookInfoDTO;
    }
}
