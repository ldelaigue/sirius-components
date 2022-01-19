/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.components.collaborative.forms.dto;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.graphql.GraphQLField;
import org.eclipse.sirius.components.annotations.graphql.GraphQLID;
import org.eclipse.sirius.components.annotations.graphql.GraphQLNonNull;
import org.eclipse.sirius.components.annotations.graphql.GraphQLObjectType;
import org.eclipse.sirius.components.core.api.IPayload;

/**
 * The payload of the "Widget Focus" mutation returned on success.
 *
 * @author smonnier
 */
@GraphQLObjectType
public final class UpdateWidgetFocusSuccessPayload implements IPayload {
    private final UUID id;

    private final String widgetId;

    public UpdateWidgetFocusSuccessPayload(UUID id, String widgetId) {
        this.id = Objects.requireNonNull(id);
        this.widgetId = Objects.requireNonNull(widgetId);
    }

    @Override
    @GraphQLID
    @GraphQLField
    @GraphQLNonNull
    public UUID getId() {
        return this.id;
    }

    @GraphQLField
    @GraphQLNonNull
    public String getUpdateFocusWidgetId() {
        return this.widgetId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, widgetId: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.widgetId);
    }
}