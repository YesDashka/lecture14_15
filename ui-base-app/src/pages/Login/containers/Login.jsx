import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import AuthorizationForm from 'components/AuthorizationForm';
import {useHistory} from "react-router-dom";
import useChangePage from "../../../hooks/useChangePage";
import {INITIAL} from "../../../constants/pages";
import * as Pages from "../../../constants/pages";

const getClasses = makeStyles(() => ({
  container: {
    alignItems: 'center',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    width: '100%'
  },
}));

const Login = ({
}) => {
  const classes = getClasses();
  const changePage = useChangePage();

  const onSuccess = () => {
    changePage({
      path: Pages.INITIAL
    })
  }
  return (
    <div className={classes.container}>
      <AuthorizationForm onSuccess={onSuccess}/>
    </div>
  )
};

export default Login;
