import React from 'react'
import { useNavigate } from 'react-router-dom'
import * as Swal from '../apis/alert'
import * as auth from '../apis/auth'
import Header from '../components/Header/Header'
import JoinForm from '../components/join/JoinForm'

const Join = () => {

  const navigate = useNavigate()

  // 회원 가입 요청
  const join = async (form) => {
    console.log(form);

    let response
    let data
    try {
      response = await auth.join(form)
    } catch (error) {
      console.log(error)
      console.log(`회원가입 중 에러가 발생하였습니다.`)
      return
    }

    data = response.data
    const status = response.status
    console.log(`data : ${data}`);
    console.log(`status : ${status}`);

    if(status == 200){
      console.log('회원가입 성공!');
      // alert('회원가입 성공!')
      Swal.alert('회원 가입 성공', '로그인 화면으로 이동합니다', 'success', () => { navigate('/login') })
    } else{
      console.log('회원가입 실패!');
      Swal.alert('회원 가입 실패', '회원가입에 실패했습니다.', 'error')
      // alert('회원가입 실패!')
    }
  }
  return (
    <>
        <Header/>
        <div className="container">
          <JoinForm join={join}/>
        </div>
    </>
  )
}

export default Join