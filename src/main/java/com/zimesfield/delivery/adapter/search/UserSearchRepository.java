package com.zimesfield.delivery.adapter.search;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryStringQuery;
import com.zimesfield.delivery.adapter.mapper.UserMapper;
import com.zimesfield.delivery.adapter.rdbms.entity.UserEntity;
import com.zimesfield.delivery.domain.model.UserModel;
import com.zimesfield.delivery.repository.UserRepository;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data Elasticsearch repository for the UserModel entity.
 */
public interface UserSearchRepository extends ElasticsearchRepository<UserEntity, String>, UserSearchRepositoryInternal {}

interface UserSearchRepositoryInternal {
    Stream<UserModel> search(String query);

    @Async
    @Transactional
    void index(UserModel entity);

    @Async
    @Transactional
    void deleteFromIndex(UserModel entity);
}

@RequiredArgsConstructor
class UserSearchRepositoryInternalImpl implements UserSearchRepositoryInternal {

    private final ElasticsearchTemplate elasticsearchTemplate;
    private final UserRepository repository;
    private final UserMapper userMapper;

    @Override
    public Stream<UserModel> search(String query) {
        NativeQuery nativeQuery = new NativeQuery(QueryStringQuery.of(qs -> qs.query(query))._toQuery());
        return elasticsearchTemplate.search(nativeQuery, UserEntity.class).map(SearchHit::getContent).map(userMapper::toModel).stream();
    }

    @Override
    public void index(UserModel entity) {
        repository.findById(entity.getId()).ifPresent(elasticsearchTemplate::save);
    }

    @Override
    public void deleteFromIndex(UserModel entity) {
        elasticsearchTemplate.delete(entity);
    }
}
