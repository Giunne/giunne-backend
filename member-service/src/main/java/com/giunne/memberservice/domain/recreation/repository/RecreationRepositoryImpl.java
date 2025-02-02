package com.giunne.memberservice.domain.recreation.repository;

import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.memberservice.domain.avatar.repository.entity.QAvatarEntity;
import com.giunne.memberservice.domain.member.domain.Member;
import com.giunne.memberservice.domain.member.repository.entity.QMemberEntity;
import com.giunne.memberservice.domain.recreation.application.dto.request.GetRecreationRequestDto;
import com.giunne.memberservice.domain.recreation.application.dto.response.GetRecreationResponseDto;
import com.giunne.memberservice.domain.recreation.application.interfaces.RecreationRepository;
import com.giunne.memberservice.domain.recreation.domain.Recreation;
import com.giunne.memberservice.domain.recreation.repository.entity.QRecreationEntity;
import com.giunne.memberservice.domain.recreation.repository.entity.RecreationEntity;
import com.giunne.memberservice.domain.recreation.repository.jpa.JpaRecreationRepository;
import com.giunne.memberservice.domain.school.api.response.GetSchoolPageResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.giunne.commonservice.util.PaginationUtil.getPageRequest;
import static com.giunne.commonservice.util.PaginationUtil.toPaginationModel;

@Repository
@AllArgsConstructor
public class RecreationRepositoryImpl implements RecreationRepository {

    private final JpaRecreationRepository jpaRecreationRepository;
    private final JPAQueryFactory queryFactory;
    private static final QMemberEntity memberEntity = QMemberEntity.memberEntity;
    private static final QAvatarEntity avatarEntity = QAvatarEntity.avatarEntity;
    private static final QRecreationEntity recreationEntity = QRecreationEntity.recreationEntity;

    @Override
    public Recreation createRecreation(Recreation recreation) {
        RecreationEntity recreationEntity = new RecreationEntity(recreation);
        RecreationEntity entity = jpaRecreationRepository.save(recreationEntity);
        return entity.toRecreation();
    }

    @Override
    public PaginationModel<GetRecreationResponseDto> findRecreation(GetRecreationRequestDto dto) {
        Pageable pageable = getPageRequest(
                dto.getPageIndex()
                , dto.getPageSize()
                , Sort.by(Sort.Direction.valueOf(dto.getDirection()), dto.getSortProperty())
        );

        JPAQuery<Long> count = queryFactory
                .select(recreationEntity.id)
                .from(recreationEntity)
                .join(memberEntity).on(memberEntity.id.eq(recreationEntity.teacher.id))
                .where(
                        likeRecreationName(dto.getRecreationName()),
                        likeTeacherName(dto.getRecreationName()),
                        recreationEntity.recreationCode.recreationCode.eq(dto.getRecreationCode()),
                        recreationEntity.baseNumber.baseNumber.eq(dto.getBaseNumber())
                        );

        List<GetRecreationResponseDto> fetch = queryFactory
                .select(
                        Projections.fields(
                                GetRecreationResponseDto.class,
                                recreationEntity.id.as("id"),
                                recreationEntity.recreationName.recreationName.as("recreationName"),
                                recreationEntity.recreationCode.recreationCode.as("recreationCode"),
                                memberEntity.id.as("teacherId"),
                                memberEntity.loginId.loginId.as("teacherLoginId"),
                                memberEntity.userName.userName.as("teacherName")
                        )
                ).from(recreationEntity)
                .join(memberEntity).on(memberEntity.id.eq(recreationEntity.teacher.id))
                .where(
                        likeRecreationName(dto.getRecreationName()),
                        likeTeacherName(dto.getRecreationName()),
                        recreationEntity.recreationCode.recreationCode.eq(dto.getRecreationCode()),
                        recreationEntity.baseNumber.baseNumber.eq(dto.getBaseNumber())
                ) .orderBy(recreationEntity.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Page<GetRecreationResponseDto> pageResult = PageableExecutionUtils.getPage(fetch, pageable, count::fetchCount);
        return toPaginationModel(pageResult);
    }

    @Override
    public PaginationModel<GetRecreationResponseDto> getMyRecreation(Member member, com.giunne.commonservice.domain.common.Pageable dto) {
        Pageable pageable = getPageRequest(
                dto.getPageIndex()
                , dto.getPageSize()
                , Sort.by(Sort.Direction.valueOf(dto.getDirection()), dto.getSortProperty())
        );
        JPAQuery<Long> count = queryFactory
                .select(recreationEntity.id)
                .from(recreationEntity)
                .join(avatarEntity).on(avatarEntity.member.id.eq(member.getId()))
        ;

        List<GetRecreationResponseDto> fetch = queryFactory
                .select(
                        Projections.fields(
                                GetRecreationResponseDto.class,
                                recreationEntity.id.as("id"),
                                recreationEntity.recreationName.recreationName.as("recreationName"),
                                recreationEntity.recreationCode.recreationCode.as("recreationCode"),
                                memberEntity.id.as("teacherId"),
                                memberEntity.loginId.loginId.as("teacherLoginId"),
                                memberEntity.userName.userName.as("teacherName")
                        )
                ).from(recreationEntity)
                .join(avatarEntity).on(avatarEntity.member.id.eq(member.getId()))
                .orderBy(recreationEntity.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Page<GetRecreationResponseDto> pageResult = PageableExecutionUtils.getPage(fetch, pageable, count::fetchCount);
        return toPaginationModel(pageResult);
    }


    @Override
    public Recreation findById(Long id) {
        RecreationEntity recreationEntity = jpaRecreationRepository
                .findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return recreationEntity.toRecreation();
    }

    private BooleanExpression likeRecreationName(String name) {
        if (name == null || name.isBlank()) {
            return null;
        }
        return recreationEntity.recreationName.recreationName.like(name + "%");
    }

    private BooleanExpression likeTeacherName(String name) {
        if (name == null || name.isBlank()) {
            return null;
        }
        return memberEntity.userName.userName.like(name + "%");
    }


}
