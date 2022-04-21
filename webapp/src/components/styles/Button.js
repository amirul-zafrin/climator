import styled from "styled-components";

export const Button = styled.button`
  padding: 1.3em 3em;
  width: 150px;
  margin: 7px 12px;
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
    box-shadow: 0px 15px 20px rgba(46, 229, 157, 0.4);
    color: #fff;
    transform: translateY(-7px);
  }

  &:active {
    transform: translateY(-1px);
  }

  &:disabled {
    background-color: #d3d3d3;
    transform: translateY(0);
    box-shadow: none;
  }
`;
