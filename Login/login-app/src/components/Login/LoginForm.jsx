import React, { useContext } from 'react'
import { LoginContext } from '../../context/LoginContextProvider'
import './LoginForm.css'


const LoginForm = () => {

    const {login} = useContext(LoginContext)

    const onLogin = (e) => {
        e.preventDefault()
        const form = e.target
        const username = form.username.value
        const password = form.password.value

        login(username, password)
    }
    return (
        <div className="form">
            <h2 className='login-title'>로그인</h2>
    
            <form className='login-form' onSubmit={(e) => onLogin(e)}>
                {/* username */}
                <div>
                    <label htmlFor="username">username</label>
                    <input type="text" id='username' placeholder='username' name='username' autoComplete='username' required/>
                </div>
                {/* password */}
                <div>
                    <label htmlFor="password">password</label>
                    <input type="password" id='password' placeholder='password' name='password' autoComplete='password' required/>
                </div>
                <button type='submit' className='btn btn--form btn-login'>로그인</button>
            </form>
        </div>
      )
}

export default LoginForm