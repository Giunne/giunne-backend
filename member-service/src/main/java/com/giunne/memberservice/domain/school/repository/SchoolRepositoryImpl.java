package com.giunne.memberservice.domain.school.repository;


import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.memberservice.domain.school.api.request.GetSchoolPageRequestDto;
import com.giunne.memberservice.domain.school.api.response.GetSchoolPageResponseDto;
import com.giunne.memberservice.domain.school.domain.School;
import com.giunne.memberservice.domain.school.interfaces.SchoolRepository;
import com.giunne.memberservice.domain.school.repository.entity.QSchoolEntity;
import com.giunne.memberservice.domain.school.repository.entity.SchoolEntity;
import com.giunne.memberservice.domain.school.repository.jpa.JpaSchoolRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.giunne.commonservice.util.PaginationUtil.getPageRequest;
import static com.giunne.commonservice.util.PaginationUtil.toPaginationModel;


@Repository
@AllArgsConstructor
public class SchoolRepositoryImpl implements SchoolRepository {

    private final JpaSchoolRepository jpaSchoolRepository;
    private final JPAQueryFactory queryFactory;
    private static final QSchoolEntity schoolEntity = QSchoolEntity.schoolEntity;
    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public School save(School school) {
        SchoolEntity entity = new SchoolEntity(school);
        entity = jpaSchoolRepository.save(entity);
        return entity.toSchool();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAll(List<School> schoolList) {
        List<SchoolEntity> schoolEntities = schoolList.stream().map(SchoolEntity::new).toList();
        jpaSchoolRepository.saveAll(schoolEntities);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateSchool(List<School> schoolList) {
        List<SchoolEntity> schoolEntities = schoolList.stream().map(SchoolEntity::new).toList();
        schoolEntities.forEach(jpaSchoolRepository::updateSchool);
    }


    @Override
    public School findBySchoolId(String schoolId) {
        Optional<SchoolEntity> optionalSchoolEntity = jpaSchoolRepository.findBySchoolId_SchoolId(schoolId);
        return optionalSchoolEntity.map(SchoolEntity::toSchool).orElse(null);

    }

    @Override
    public List<School> findBySchoolIdList(List<String> schoolIdList) {
        List<SchoolEntity> schoolEntities = jpaSchoolRepository.findBySchoolIdList(schoolIdList);

        return schoolEntities.stream().map(SchoolEntity::toSchool).toList();
    }

    @Override
    public List<School> findBySchoolIdListNotIn(List<String> schoolIdList) {
        List<SchoolEntity> schoolEntities = jpaSchoolRepository.findBySchoolIdListNotIn(schoolIdList);
        return schoolEntities.stream().map(SchoolEntity::toSchool).toList();
    }

    @Override
    public School findById(Long id) {
        SchoolEntity userEntity = jpaSchoolRepository
                .findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return userEntity.toSchool();
    }

    @Override
    public PaginationModel<GetSchoolPageResponseDto> findByNameLike(GetSchoolPageRequestDto dto) {
        Pageable pageable = getPageRequest(
                dto.getPageIndex()
                , dto.getPageSize()
                , Sort.by(Sort.Direction.valueOf(dto.getDirection()), dto.getSortProperty())
        );

        JPAQuery<Long> count = queryFactory
                .select(schoolEntity.id)
                .from(schoolEntity)
                .where(
                        likeName(dto.getName())
                );

        List<GetSchoolPageResponseDto> fetch = queryFactory
                .select(
                        Projections.fields(
                                GetSchoolPageResponseDto.class,
                                schoolEntity.id.as("id"),
                                schoolEntity.schoolNm.value.as("name")
                        )
                )
                .from(schoolEntity)
                .where(
                        likeName(dto.getName())
                )
                .orderBy(schoolEntity.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Page<GetSchoolPageResponseDto> pageResult = PageableExecutionUtils.getPage(fetch, pageable, count::fetchCount);
        return toPaginationModel(pageResult);
    }

    private BooleanExpression likeName(String name) {
        if (name == null || name.isBlank()) {
            return null;
        }
        return schoolEntity.schoolNm.value.like(name + "%");
    }

}
