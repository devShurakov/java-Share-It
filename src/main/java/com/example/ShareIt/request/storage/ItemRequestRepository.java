package com.example.ShareIt.request.storage;

import com.example.ShareIt.request.dto.ItemRequestDto;
import com.example.ShareIt.request.model.ItemRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    Collection<ItemRequest> findAllByRequestIdOrderByCreatedDesc(long requestId);


    List<ItemRequest> findItemRequestsByRequest_IdIsNotOrderByCreatedDesc(long requestId, Pageable page);


//    List<ItemRequest> findAllByRequest_IdIsNotOrderByCreatedDesc(long requestId, Pageable page);

    List<ItemRequest> findAllByRequest_IdIsNotOrderByCreatedDesc(long userId, Pageable page);

    List<ItemRequest>  findAllByRequestIdNot(long userId, Pageable page);
}
