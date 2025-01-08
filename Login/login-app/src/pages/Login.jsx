import React from 'react'
import Header from '../components/Header/Header'
import LoginForm from '../components/Login/LoginForm'

const Login = () => {
  return (
    <>
        <Header/>
        <div className="container">
          <LoginForm Login={Login}/>
        </div>
    </>
  )
}

export default Login