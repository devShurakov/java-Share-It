package ru.practicum.shareit.request;

import ru.practicum.shareit.request.ItemRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;


public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    Collection<ItemRequest> findAllByRequestIdOrderByCreatedDesc(long requestId);

    List<ItemRequest> findAllByRequest_IdIsNotOrderByCreatedDesc(long userId, Pageable page);

}
