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
import { useMutation } from '@apollo/client';
import { LinkButton } from 'core/linkbutton/LinkButton';
import { Select } from 'core/select/Select';
import gql from 'graphql-tag';
import { NewRepresentation } from 'icons';
import { NewRepresentationAreaProps } from 'onboarding/NewRepresentationArea.types';
import React, { useEffect, useState } from 'react';
import { v4 as uuid } from 'uuid';
import { AreaContainer } from './AreaContainer';
import styles from './NewRepresentationArea.module.css';

const createRepresentationMutation = gql`
  mutation createRepresentation($input: CreateRepresentationInput!) {
    createRepresentation(input: $input) {
      __typename
      ... on CreateRepresentationSuccessPayload {
        representation {
          id
          label
          kind
          __typename
        }
      }
      ... on ErrorPayload {
        message
      }
    }
  }
`;

export const NewRepresentationArea = ({
  editingContextId,
  representationDescriptions,
  selection,
  setSelection,
  readOnly,
}: NewRepresentationAreaProps) => {
  const initialState = {
    message: undefined,
  };
  const [state, setState] = useState(initialState);
  const { message } = state;

  // Representation creation
  const [createRepresentation, { loading, data, error }] = useMutation(createRepresentationMutation);
  useEffect(() => {
    if (!loading && !error && data?.createRepresentation) {
      const { createRepresentation } = data;
      if (createRepresentation.representation) {
        const { id, label, kind } = createRepresentation.representation;
        setSelection({ id, label, kind });
      } else if (createRepresentation.__typename === 'ErrorPayload') {
        setState((prevState) => {
          const newState = { ...prevState };
          newState.message = createRepresentation.message;
          return newState;
        });
      }
    }
  }, [loading, data, error, setSelection]);
  const onCreateRepresentation = (representationDescriptionId) => {
    const selected = representationDescriptions.find((candidate) => candidate.id === representationDescriptionId);
    const objectId = selection.id;
    const input = {
      id: uuid(),
      editingContextId,
      objectId,
      representationDescriptionId,
      representationName: selected.label,
    };
    createRepresentation({ variables: { input } });
  };

  // Representation Descriptions list
  let newRepresentationButtons =
    representationDescriptions.length > 0
      ? representationDescriptions.slice(0, 5).map((representationDescription) => {
          return (
            <LinkButton
              key={representationDescription.id}
              label={representationDescription.label}
              data-testid={representationDescription.id}
              onClick={() => {
                onCreateRepresentation(representationDescription.id);
              }}>
              <NewRepresentation title={representationDescription.label} className={styles.icon} />
            </LinkButton>
          );
        })
      : [];

  // More select
  const moreName = 'moreRepresentationDescriptions';
  const moreLabel = 'More representations types...';
  let moreSelect =
    representationDescriptions.length > 5 ? (
      <Select
        onChange={(event) => {
          onCreateRepresentation(event.target.value);
        }}
        name={moreName}
        options={[{ id: moreLabel, label: moreLabel }, representationDescriptions.slice(5)].flat()}
        data-testid={moreName}
      />
    ) : null;

  let title = 'Create a new Representation';
  if (readOnly) {
    return <AreaContainer title={title} subtitle="You need edit access to create representations" />;
  } else {
    let subtitle =
      selection && representationDescriptions.length > 0
        ? 'Select the representation to create on ' + selection.label
        : 'There are no representations available for the current selection';
    return (
      <AreaContainer title={title} subtitle={subtitle} banner={message}>
        {newRepresentationButtons}
        {moreSelect}
      </AreaContainer>
    );
  }
};
