import { BrowserRouter, Route, Routes, Link, useParams, useLocation, Navigate } from 'react-router-dom'
import './App.css'
import { use, useState } from 'react'

function App() {

  const [isLoggedIn, setIsLoggedIn] = useState(false);

  return (
   <BrowserRouter basename='/my-app'>
    <Routes>
      <Route path='/' element={<Home/>}></Route>
      <Route path='/about' element={<About/>}></Route>
      <Route path='/boards/:id' element={ <Board/>}></Route>
      <Route path='/login' element={ <Login/>}></Route>
      {/* <Route path='/admin' render={() => {
        if(isLoggedIn) return <Admin/>
        else <Redirect to="/login"/>
      }}></Route> */}
      <Route path='/admin' element={ isLoggedIn ? <Admin/> : <Navigate to="/login"/>}></Route>
    </Routes>
   </BrowserRouter>
  )
}

function Home() {
  return(
    <>
      <h1>Home</h1>
      <Link to="/about">About</Link> <br />
      <Link to="/boards/123?category=공지사항&option=10">Board</Link> <br />
      <Link to="/admin">Admin</Link> <br />
      <Link to="/login">Login</Link> <br />
    </>
  )
}

function About() {
  return(
    <>
      <h1>About</h1>
      <Link to="/">Home</Link>
    </>
  )
}

// /boards/:id
function Board() {
  // useParams
  // : react-router v6 이상에서 부터 사용하는 URL 경로에 정의된 파라미터를 가져오는 훅(Hook)
  const {id} = useParams();

  // ?파라미터=값 가져오는 방법
  const location = useLocation();
  const query = new URLSearchParams(location.search);
  const category = query.get('category'); // "category" 파라미터 값을 가져온다
  const option = query.get('option'); // "option" 파라미터 값을 가져온다

  return(
    <>
      <h1>게시판</h1>
      <h3>게시판 id : {id}</h3>
      <h3>파라미터 category : {category} </h3>
      <h3>파라미터 option : {option} </h3>
      <Link to="/">Home</Link>
    </>
  )
}

function Admin() {
  return(
    <>
      <h1>Admin</h1>
      <Link to="/">Home</Link>
    </>
  )
}

function Login() {
  return(
    <>
      <h1>Login</h1>
      <Link to="/">Home</Link>
    </>
  )
}

export default App
