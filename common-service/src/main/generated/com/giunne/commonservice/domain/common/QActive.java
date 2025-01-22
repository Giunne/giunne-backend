package com.giunne.commonservice.domain.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QActive is a Querydsl query type for Active
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QActive extends BeanPath<Active> {

    private static final long serialVersionUID = -1672582350L;

    public static final QActive active = new QActive("active");

    public final BooleanPath value = createBoolean("value");

    public QActive(String variable) {
        super(Active.class, forVariable(variable));
    }

    public QActive(Path<? extends Active> path) {
        super(path.getType(), path.getMetadata());
    }

    public QActive(PathMetadata metadata) {
        super(Active.class, metadata);
    }

}

