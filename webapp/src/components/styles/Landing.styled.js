import styled from "styled-components";

export const LandingCard = styled.div`
  width: 90%;
  height: 90%;
  margin: 2% 5%;
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(10px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
  border-radius: 16px;
  z-index: 1;
  color: #fff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

export const TextContainer = styled.div`
  position: flex;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  padding: 0%;
  margin-bottom: 5%;
`;

export const LTitle = styled.h1`
  font-size: 10rem;
  padding-bottom: 0;
`;

export const ButtonContainer = styled.div`
  display: flex;
`;

export const LButton = styled.button`
  padding: 1.3em 3em;
  width: 150px;
  margin: 0 12px;
  font-size: 12px;
  text-transform: uppercase;
  letter-spacing: 2.5px;
  font-weight: 500;
  color: #fff;
  background-color: rgb(253, 222, 65);
  border: none;
  border-radius: 45px;
  box-shadow: 0px 8px 15px rgba(255, 255, 255, 0.1);
  transition: all 0.3s ease 0s;
  cursor: pointer;
  outline: none;

  &:hover {
    background-color: #2ee59d;
    box-shadow: 0px 15px 20px rgba(64, 179, 162, 0.6);
    color: #fff;
    transform: translateY(-7px);
  }

  &:active {
    transform: translateY(-1px);
  }
`;
