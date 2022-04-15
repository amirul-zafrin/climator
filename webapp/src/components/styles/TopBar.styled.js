import styled from "styled-components";

export const TopContainer = styled.div`
  width: 100%;
  height: 10%;
  background: rgba(0, 0, 0, 0.05);
  backdrop-filter: blur(10px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
  z-index: 1;
  color: #fff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  &:hover {
    background-color: #fff;
    box-shadow: 0px 15px 20px rgba(255, 255, 255, 0.4);
    color: #fff;
  }
`;
