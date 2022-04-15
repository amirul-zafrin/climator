import React from "react";
import { FlexContainer } from "../components/styles/FlexContainer.styled";
import {
  LandingCard,
  LTitle,
  TextContainer,
} from "../components/styles/Landing.styled";

const Activation = () => {
  return (
    <FlexContainer>
      <LandingCard>
        <TextContainer>
          <LTitle>Climator</LTitle>
          <p>
            Thank you for register with us. We have sent activation link to your
            email. Please activate your account before moving forward!
          </p>
        </TextContainer>
      </LandingCard>
    </FlexContainer>
  );
};

export default Activation;
