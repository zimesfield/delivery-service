package com.zimesfield.delivery.web.rest;

import com.zimesfield.delivery.adapter.mapper.AdminUserMapper;
import com.zimesfield.delivery.adapter.search.UserSearchRepository;
import com.zimesfield.delivery.domain.model.UserModel;
import com.zimesfield.delivery.model.UserInfo;
import com.zimesfield.delivery.service.UserService;
import java.util.List;
import java.util.stream.StreamSupport;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

@Data
@RestController
@RequestMapping("/api")
public class PublicUserResource {

    private static final Logger log = LoggerFactory.getLogger(PublicUserResource.class);

    private final UserService userService;
    private final UserSearchRepository userSearchRepository;
    private final AdminUserMapper adminUserMapper;

    /**
     * {@code GET /users} : get all users with only public information - calling this method is allowed for anyone.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserInfo>> getAllPublicUsers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get all public UserModel names");

        final Page<UserInfo> page = userService.getAllPublicUsers(pageable).map(adminUserMapper::toResponse);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code SEARCH /users/_search/:query} : search for the UserModel corresponding to the query.
     *
     * @param query the query to search.
     * @return the result of the search.
     */
    @GetMapping("/users/_search/{query}")
    public List<UserModel> search(@PathVariable("query") String query) {
        //        return StreamSupport.stream(userSearchRepository.search(query).spliterator(), false).map(userMapper::toResponse).toList();
        return StreamSupport.stream(userSearchRepository.search(query).spliterator(), false).toList();
    }
}
